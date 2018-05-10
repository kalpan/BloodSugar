package com.sugar.dto;

import com.sugar.metadata.MetricEnum;

public class ExerciseMetricEvent extends MetricEvent {

	public ExerciseMetricEvent() {
		super();
		this.metricType = MetricEnum.Exercise;
	}

	public ExerciseMetricEvent(String metricName, Long metricValue) {
		super(metricName, metricValue);
		this.metricType = MetricEnum.Exercise;
	}
}
