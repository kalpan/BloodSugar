package com.sugar;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.sugar.dto.ExerciseMetricEvent;
import com.sugar.dto.FoodMetricEvent;
import com.sugar.dto.MetricEvent;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BloodSugarSimulatorApplicationTests {
	
	public static final Logger logger = LoggerFactory.getLogger(BloodSugarSimulatorApplicationTests.class);
	public static final String TEST_URI = "/api";

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
	
	@Test
	public void createFoodMetricEvent() {
		MetricEvent metric = new FoodMetricEvent();
		metric.setMetricName("");
		HttpEntity<Object> request = new HttpEntity<Object>(metric, getHeaders());
		ResponseEntity<FoodMetricEvent> response = restTemplate.exchange(TEST_URI + "/food/", HttpMethod.POST, request,
				FoodMetricEvent.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	public void createExerciseMetricEvent() {
		MetricEvent metric = new ExerciseMetricEvent();
		metric.setMetricName("Bench press");
		HttpEntity<Object> request = new HttpEntity<Object>(metric, getHeaders());
		ResponseEntity<ExerciseMetricEvent> response = restTemplate.exchange(TEST_URI + "/exercise/", HttpMethod.POST, request,
				ExerciseMetricEvent.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
