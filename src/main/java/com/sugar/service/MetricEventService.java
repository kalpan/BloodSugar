package com.sugar.service;

import com.sugar.dto.ExerciseMetricEvent;
import com.sugar.dto.FoodMetricEvent;
import com.sugar.dto.NormalizeMetricEvent;

public interface MetricEventService {
	public void processFoodEvent(FoodMetricEvent foodMetricEvent);
	public void processExerciseEvent(ExerciseMetricEvent exerciseMetricEvent);
	public void processNormalizeEvent(NormalizeMetricEvent normalizeMetricEvent);
}
