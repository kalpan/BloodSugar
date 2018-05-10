package com.sugar.worker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import com.sugar.dto.NormalizeMetricEvent;
import com.sugar.service.MetricEventService;
import com.sugar.service.MetricEventServiceImpl;

@Component
@Configurable
public class NormalizerWorker implements Runnable {
	public static final Logger logger = LoggerFactory.getLogger(NormalizerWorker.class);
	
	private MetricEventService metricEventService = new MetricEventServiceImpl();
	
	@Override
	public void run() {
		try {
			NormalizeMetricEvent normalizeMetricEvent = new NormalizeMetricEvent();
			logger.debug("Sending normalizeMetricEvent - {}", normalizeMetricEvent);
			this.metricEventService.processNormalizeEvent(normalizeMetricEvent );
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error - {}", e.getMessage());
		}
	}
}
