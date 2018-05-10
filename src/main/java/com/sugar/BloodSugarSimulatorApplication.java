package com.sugar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sugar.metadata.IndexesDB;

@SpringBootApplication(scanBasePackages={"com.sugar"})
public class BloodSugarSimulatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloodSugarSimulatorApplication.class, args);
		
		IndexesDB.loadLookupData();
	}
}
