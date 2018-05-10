package com.sugar.dto;

import com.sugar.metadata.MetricEnum;

public class NormalizeMetricEvent extends MetricEvent {
	
	public NormalizeMetricEvent() {
		super();
		this.metricType = MetricEnum.Normalize;
	}

	public NormalizeMetricEvent(String metricName, Long metricValue) {
		super(metricName, metricValue);
		this.metricType = MetricEnum.Normalize;
	}

}
