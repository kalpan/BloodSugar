package com.sugar.dto;

import com.sugar.metadata.MetricEnum;

public class FoodMetricEvent extends MetricEvent {
	
	public FoodMetricEvent() {
		super();
		this.metricType = MetricEnum.Food;
	}

	public FoodMetricEvent(String metricName, Long metricValue) {
		super(metricName, metricValue);
		this.metricType = MetricEnum.Food;
	}
}
