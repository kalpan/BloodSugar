package com.sugar.worker;

public class MetricEventProcessor {
	
	private static MetricEventProcessor _instance;
	
	private MetricEventProcessor() {
	}
	
	public MetricEventProcessor getInstance() {
		if (_instance == null)
			_instance = new MetricEventProcessor();
		
		return _instance;
	}

}
