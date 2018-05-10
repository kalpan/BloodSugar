package com.sugar.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sugar.dto.BloodSugar;
import com.sugar.dto.ExerciseMetricEvent;
import com.sugar.dto.FoodMetricEvent;
import com.sugar.dto.NormalizeMetricEvent;
import com.sugar.metadata.BloodSugarDB;
import com.sugar.metadata.IndexesDB;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class MetricEventServiceTest {
	
	 @TestConfiguration
	    static class UserServiceImplTestContextConfiguration {
	        @Bean
	        public MetricEventService metricEventService() {
	            return new MetricEventServiceImpl();
	        }
	    }

	    @Autowired
	    MetricEventService metricEventService;
	    
	    @Test
	    public void processFoodEvent() {
	    	IndexesDB.loadLookupData();
	    	FoodMetricEvent foodMetricEvent = new FoodMetricEvent();
	    	foodMetricEvent.setMetricName("Apple, made with sugar");
	    	metricEventService.processFoodEvent(foodMetricEvent);
	    	BloodSugar bloodSugar = BloodSugarDB.getInstance().findByTs(foodMetricEvent.getTs());
	    	assertEquals(bloodSugar.getTs(), foodMetricEvent.getTs());
	    }
	    
	    @Test
		public void processExerciseEvent() {
	    	IndexesDB.loadLookupData();
	    	ExerciseMetricEvent exerciseMetricEvent = new ExerciseMetricEvent();
	    	exerciseMetricEvent.setMetricName("Running");
	    	metricEventService.processExerciseEvent(exerciseMetricEvent);
	    	BloodSugar bloodSugar = BloodSugarDB.getInstance().findByTs(exerciseMetricEvent.getTs());
	    	assertEquals(bloodSugar.getTs(), exerciseMetricEvent.getTs());
		}
		
	    @Test
		public void processNormalizeEventNullCase() {
	    	IndexesDB.loadLookupData();
	    	FoodMetricEvent foodMetricEvent = new FoodMetricEvent();
	    	foodMetricEvent.setMetricName("Baguette, white, plain");
	    	metricEventService.processFoodEvent(foodMetricEvent);
	    	
	    	NormalizeMetricEvent normalizeMetricEvent = new NormalizeMetricEvent();
	    	metricEventService.processNormalizeEvent(normalizeMetricEvent);
	    	BloodSugar bloodSugar = BloodSugarDB.getInstance().findByTs(normalizeMetricEvent.getTs());
	    	System.out.println(normalizeMetricEvent.getTs());
	    	assertEquals(bloodSugar, null);
		}
	    
	    @Test
		public void processNormalizeEvent() {
	    	IndexesDB.loadLookupData();
	    	FoodMetricEvent foodMetricEvent = new FoodMetricEvent();
	    	foodMetricEvent.setMetricName("Baguette, white, plain");
	    	metricEventService.processFoodEvent(foodMetricEvent);
	    	
	    	//We increased blood sugar beyond 80, let's reset times
	    	BloodSugarDB.getInstance().getLastFoodTs().set(0L);
	    	BloodSugarDB.getInstance().getLastExerciseTs().set(0L);
	    	
	    	NormalizeMetricEvent normalizeMetricEvent = new NormalizeMetricEvent();
	    	metricEventService.processNormalizeEvent(normalizeMetricEvent);
	    	BloodSugar bloodSugar = BloodSugarDB.getInstance().findByTs(normalizeMetricEvent.getTs());
	    	System.out.println(normalizeMetricEvent.getTs());
	    	assertEquals(bloodSugar.getTs(), normalizeMetricEvent.getTs());
		}
}
