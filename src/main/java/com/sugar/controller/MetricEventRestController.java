package com.sugar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.sugar.dto.ExerciseMetricEvent;
import com.sugar.dto.FoodMetricEvent;
import com.sugar.service.MetricEventService;

@RestController
@RequestMapping("/api")
public class MetricEventRestController {
	
	public static final Logger logger = LoggerFactory.getLogger(MetricEventRestController.class);
	
	@Autowired
	MetricEventService metricEventService;
	
	@RequestMapping(value = "/food", method = RequestMethod.POST)
	public ResponseEntity<Void> eatFood(@RequestBody FoodMetricEvent foodMetricEvent,
			UriComponentsBuilder uriComponentsBuilder) {
		
		logger.debug("food:"+foodMetricEvent.toString());
		metricEventService.processFoodEvent(foodMetricEvent);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/exercise", method = RequestMethod.POST)
	public ResponseEntity<Void> exercise(@RequestBody ExerciseMetricEvent exerciseMetricEvent,
			UriComponentsBuilder uriComponentsBuilder) {
		
		logger.debug("exercise:"+exerciseMetricEvent.toString());
		metricEventService.processExerciseEvent(exerciseMetricEvent);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

}
