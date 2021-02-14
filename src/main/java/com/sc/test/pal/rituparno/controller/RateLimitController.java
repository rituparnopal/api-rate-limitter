/**
 * 
 */
package com.sc.test.pal.rituparno.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sc.test.pal.rituparno.exception.RateLimiterException;
import com.sc.test.pal.rituparno.model.RateLimit;
import com.sc.test.pal.rituparno.service.RateLimitService;

/**
 * @author ritpal
 *
 */
@RequestMapping("/rate-limit")
@RestController
public class RateLimitController {

	@Autowired
	RateLimitService rateLimitService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> createRateLimit(@RequestBody(required = true) RateLimit rateLimit) {
		ResponseEntity<String> responseEntity; 
		try {
			rateLimitService.createRateLimiter(rateLimit);
			responseEntity = new ResponseEntity<String>("Rate Limit created successfully", HttpStatus.CREATED);
		} catch (RateLimiterException exception) {
			responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{apiKey}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> allow(@PathVariable String apiKey) {
		ResponseEntity<String> responseEntity; 
		try {
			rateLimitService.allow(apiKey);
			responseEntity = new ResponseEntity<String>("Access allowed", HttpStatus.OK);
		} catch (RateLimiterException exception) {
			responseEntity = new ResponseEntity<String>(exception.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
		}
		return responseEntity;
	}
	
}
