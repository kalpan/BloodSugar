package com.sugar.dto;

import com.sugar.metadata.MetricEnum;

public class ExerciseMetricEvent extends MetricEvent {
	private long DIRECTION = -1L;
	private long RATE_DIVISOR_MILLIS = 60 * 60 * 1000;

	public ExerciseMetricEvent() {
		super();
		this.metricType = MetricEnum.Exercise;
	}

	public ExerciseMetricEvent(String metricName, Long metricValue) {
		super(metricName, metricValue);
		this.metricType = MetricEnum.Exercise;
	}
	
	public long getDirection() {
		return DIRECTION;
	}

	public long getRateOfIncrease() {
		return getTs() / RATE_DIVISOR_MILLIS;
	}

}
