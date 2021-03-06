package com.sugar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wiki")
public class WikiRestController {
public static final Logger logger = LoggerFactory.getLogger(WikiRestController.class);
	
	@RequestMapping(value = "/version", method = RequestMethod.GET)
	public @ResponseBody String info() {
	
		return "{ 'name': 'Blood Sugar Simulator', 'version' : 'alpha' } ";
	}

}
