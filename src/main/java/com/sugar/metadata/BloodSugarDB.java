package com.sugar.metadata;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.sugar.dto.BloodSugar;

public class BloodSugarDB {
	private final Map<Long, BloodSugar> BLOOD_SUGAR_DB = new ConcurrentHashMap<>();
	private AtomicLong lastFoodTs = new AtomicLong(0);
	private AtomicLong lastExerciseTs = new AtomicLong(0);
	private AtomicLong currentTs = new AtomicLong();
	
	private static BloodSugarDB _instance = null;
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	private BloodSugarDB() {
	}

	public static BloodSugarDB getInstance() {
		if (_instance == null)
			_instance = new BloodSugarDB();

		return _instance;
	}

	public void save(BloodSugar bloodSugar) {
		lock.writeLock().lock();
		try {
			BLOOD_SUGAR_DB.put(bloodSugar.getId(), bloodSugar);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void findById(Long id) {
		lock.readLock().lock();
		try {
			BLOOD_SUGAR_DB.get(id);
		} finally {
			lock.readLock().unlock();
		}
	}

	public BloodSugar findByTs(Long ts) {
		lock.readLock().lock();
		try {
			return BLOOD_SUGAR_DB.values().stream().filter(r -> r.getTs().equals(ts)).findFirst().orElse(null);
		} finally {
			lock.readLock().unlock();
		}
	}

	public Iterator<BloodSugar> getBloodSugarStream() {
		return BLOOD_SUGAR_DB.entrySet().stream()
				.sorted((e1, e2) -> e1.getValue().getTs().compareTo(e2.getValue().getTs())).map(Map.Entry::getValue)
				.iterator();
	}

	public AtomicLong getLastFoodTs() {
		return lastFoodTs;
	}

	public void setLastFoodTs(AtomicLong lastFoodTs) {
		this.lastFoodTs = lastFoodTs;
	}

	public AtomicLong getLastExerciseTs() {
		return lastExerciseTs;
	}

	public void setLastExerciseTs(AtomicLong lastExerciseTs) {
		this.lastExerciseTs = lastExerciseTs;
	}

	public AtomicLong getCurrentTs() {
		return currentTs;
	}

	public void setCurrentTs(AtomicLong currentTs) {
		this.currentTs = currentTs;
	}

}
