package com.sugar.worker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("metricEventProcessor")
public class MetricEventProcessor {
	public static final Logger logger = LoggerFactory.getLogger(MetricEventProcessor.class);
	private static final long REPEAT_PERIOD_SECS = 60L;
	
	private ScheduledExecutorService scheduledExecutor;

	public MetricEventProcessor() {
		scheduledExecutor = Executors.newScheduledThreadPool(1);
		initQueue();
	}

	private void initQueue() {
		logger.debug("Initializing NormalizerWorker...");
		this.scheduledExecutor.scheduleAtFixedRate(new NormalizerWorker(), 0, REPEAT_PERIOD_SECS, TimeUnit.SECONDS);
	}

}
