package com.sugar.service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sugar.dto.ExerciseMetricEvent;
import com.sugar.dto.FoodMetricEvent;
import com.sugar.dto.NormalizeMetricEvent;
import com.sugar.metadata.CsvRow;
import com.sugar.metadata.IndexesDB;

@Service("metricEventService")
public class MetricEventServiceImpl implements MetricEventService {
	public static final Logger logger = LoggerFactory.getLogger(MetricEventServiceImpl.class);
	
	private double FOOD_RATE_DIVISOR_MINUTES = 120D;
	private long FOOD_DIRECTION = 1L;
	private double EXERCISE_RATE_DIVISOR_MINUTES = 60D;
	private long EXERCISE_DIRECTION = -1L;
	
	private Double currentBloodSugar = new Double(80L);
	private AtomicLong currentTs = new AtomicLong();
	private final ReentrantLock lock = new ReentrantLock();

	@Override
	public void processFoodEvent(FoodMetricEvent foodMetricEvent) {
		if (!IndexesDB.FOODDB.containsKey(foodMetricEvent.getMetricName()))
			return;
		CsvRow csv = IndexesDB.FOODDB.get(foodMetricEvent.getMetricName());
		
		Double rateOfIncrease = (double)csv.getIndex() / FOOD_RATE_DIVISOR_MINUTES;
		Double bloodSugarIncreasePerMinute = rateOfIncrease * FOOD_DIRECTION;
		long ts = foodMetricEvent.getTs();
		for (int i=0; i < FOOD_RATE_DIVISOR_MINUTES; i++) {
			updateBloodSugar(bloodSugarIncreasePerMinute, ts);
			ts = currentTs.addAndGet(60_000L);
		}
	}

	@Override
	public void processExerciseEvent(ExerciseMetricEvent exerciseMetricEvent) {
		if (!IndexesDB.EXERCISEDB.containsKey(exerciseMetricEvent.getMetricName()))
			return;
		CsvRow csv = IndexesDB.EXERCISEDB.get(exerciseMetricEvent.getMetricName());
		Double rateOfDecrease = (double)csv.getIndex() / EXERCISE_RATE_DIVISOR_MINUTES;
		Double bloodSugarDecreasePerMinute = rateOfDecrease * EXERCISE_DIRECTION;
		long ts = exerciseMetricEvent.getTs();
		for (int i=0; i < EXERCISE_RATE_DIVISOR_MINUTES; i++) {
			updateBloodSugar(bloodSugarDecreasePerMinute, ts);
			ts = currentTs.addAndGet(60_000L);
		}
	}

	@Override
	public void processNormalizeEvent(NormalizeMetricEvent normalizeMetricEvent) {
		long ts = normalizeMetricEvent.getTs();
		this.lock.lock();
		try {
			if (currentBloodSugar > 80D) {
				updateBloodSugar(-1D, ts);
			} else if (currentBloodSugar < 80D) {
				updateBloodSugar(1D, ts);
			}
		} finally {
			this.lock.unlock();
		}
	}
	
	private void updateBloodSugar(Double bloodSugarIncreasePerMinute, long ts) {
		this.lock.lock();
		try {
			currentBloodSugar+= bloodSugarIncreasePerMinute;
			ts = currentTs.addAndGet(60_000L);
		} finally {
			this.lock.unlock();
		}
		Date now = new Date(ts);
		logger.debug("Blood sugar change per minute:{} to {} at ts: {}", bloodSugarIncreasePerMinute, currentBloodSugar, now);
	}

}
