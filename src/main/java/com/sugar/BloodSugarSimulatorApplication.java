package com.sugar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sugar.metadata.IndexesDB;
import com.sugar.worker.MetricEventProcessor;

@SpringBootApplication(scanBasePackages={"com.sugar", "com.sugar.worker"})
public class BloodSugarSimulatorApplication {
	
	@Autowired
	MetricEventProcessor metricEventProcessor;

	public static void main(String[] args) {
		SpringApplication.run(BloodSugarSimulatorApplication.class, args);
		
		IndexesDB.loadLookupData();
	}
}
