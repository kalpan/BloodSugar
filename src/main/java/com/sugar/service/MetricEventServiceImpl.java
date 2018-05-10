package com.sugar.service;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sugar.dto.BloodSugar;
import com.sugar.dto.ExerciseMetricEvent;
import com.sugar.dto.FoodMetricEvent;
import com.sugar.dto.NormalizeMetricEvent;
import com.sugar.metadata.BloodSugarDB;
import com.sugar.metadata.CsvRow;
import com.sugar.metadata.IndexesDB;

@Service("metricEventService")
public class MetricEventServiceImpl implements MetricEventService {
	public static final Logger logger = LoggerFactory.getLogger(MetricEventServiceImpl.class);

	private static double FOOD_RATE_DIVISOR_MINUTES = 120D;
	private static long FOOD_DIRECTION = 1L;
	private static double EXERCISE_RATE_DIVISOR_MINUTES = 60D;
	private static long EXERCISE_DIRECTION = -1L;
	private static long FOOD_NORMALIZATION_TIME_DELTA = 3_600_000L;
	private static long EXERCISE_NORMALIZATION_TIME_DELTA = 7_200_000L;
	
	private static Double currentBloodSugar = new Double(80L);
	private final ReentrantLock lock = new ReentrantLock();

	@Override
	public void processFoodEvent(FoodMetricEvent foodMetricEvent) {
		if (!IndexesDB.FOODDB.containsKey(foodMetricEvent.getMetricName()))
			return;
		CsvRow csv = IndexesDB.FOODDB.get(foodMetricEvent.getMetricName());

		Double rateOfIncrease = (double) csv.getIndex() / FOOD_RATE_DIVISOR_MINUTES;
		Double bloodSugarIncreasePerMinute = rateOfIncrease * FOOD_DIRECTION;
		long ts = foodMetricEvent.getTs();
		BloodSugarDB.getInstance().getCurrentTs().set(ts);
		for (int i = 0; i < FOOD_RATE_DIVISOR_MINUTES; i++) {
			updateBloodSugar(bloodSugarIncreasePerMinute, ts);
			ts = BloodSugarDB.getInstance().getCurrentTs().addAndGet(60_000L);
		}
		
		BloodSugarDB.getInstance().getLastFoodTs().set(BloodSugarDB.getInstance().getCurrentTs().get());
	}

	@Override
	public void processExerciseEvent(ExerciseMetricEvent exerciseMetricEvent) {
		if (!IndexesDB.EXERCISEDB.containsKey(exerciseMetricEvent.getMetricName()))
			return;
		CsvRow csv = IndexesDB.EXERCISEDB.get(exerciseMetricEvent.getMetricName());
		Double rateOfDecrease = (double) csv.getIndex() / EXERCISE_RATE_DIVISOR_MINUTES;
		Double bloodSugarDecreasePerMinute = rateOfDecrease * EXERCISE_DIRECTION;
		long ts = exerciseMetricEvent.getTs();
		BloodSugarDB.getInstance().getCurrentTs().set(ts);
		for (int i = 0; i < EXERCISE_RATE_DIVISOR_MINUTES; i++) {
			updateBloodSugar(bloodSugarDecreasePerMinute, ts);
			ts = BloodSugarDB.getInstance().getCurrentTs().addAndGet(60_000L);
		}
		BloodSugarDB.getInstance().getLastFoodTs().set(BloodSugarDB.getInstance().getCurrentTs().get());
	}

	@Override
	public void processNormalizeEvent(NormalizeMetricEvent normalizeMetricEvent) {
		long ts = normalizeMetricEvent.getTs();
		Date now = new Date(ts);
		
		if (! (ts - BloodSugarDB.getInstance().getLastFoodTs().get() > FOOD_NORMALIZATION_TIME_DELTA
				&& ts - BloodSugarDB.getInstance().getLastExerciseTs().get() > EXERCISE_NORMALIZATION_TIME_DELTA)) {
			logger.debug("Blood sugar unchanged:{} at ts: {}", currentBloodSugar, now);
			return;
		}

		synchronized (currentBloodSugar) {
			if (currentBloodSugar > 80D) {
				updateBloodSugar(-1D, ts);
			} else if (currentBloodSugar < 80D) {
				updateBloodSugar(1D, ts);
			}
		}
		
		logger.debug("Blood sugar change per minute:{} to {} at ts: {}", 1, currentBloodSugar, now);
	}

	private void updateBloodSugar(Double bloodSugarIncreasePerMinute, long ts) {
		this.lock.lock();
		try {
			currentBloodSugar += bloodSugarIncreasePerMinute;
			BloodSugar bloodSugar = new BloodSugar(currentBloodSugar, ts);
			BloodSugarDB.getInstance().save(bloodSugar);
			logger.debug("Saved : {}", bloodSugar);
		} finally {
			this.lock.unlock();
		}
		Date now = new Date(ts);
		logger.debug("Blood sugar change per minute:{} to {} at ts: {}", bloodSugarIncreasePerMinute, currentBloodSugar,
				now);
	}

}
