/**
 * 
 */
package com.sc.test.pal.rituparno.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sc.test.pal.rituparno.exception.RateLimiterException;
import com.sc.test.pal.rituparno.model.RateLimit;

import junit.framework.TestCase;

/**
 * @author rituparno.pal@gmail.com
 *
 */
public class RateLimitServiceTest extends TestCase {

	private RateLimitService service;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		service = new RateLimitService();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		service = null;
	}

	/**
	 * Test method for {@link com.sc.test.pal.rituparno.service.RateLimitService#createRateLimiter(com.sc.test.pal.rituparno.model.RateLimit)}.
	 */
	@Test
	public void testCreateRateLimiter() {
		RateLimit rateLimit = new RateLimit();
		rateLimit.setApiKey("testAPI");
		rateLimit.setLimitCount(5);
		rateLimit.setTimePeriod("PER_HOUR");
		try {
			assertTrue(service.createRateLimiter(rateLimit));
		} catch (RateLimiterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Test method for {@link com.sc.test.pal.rituparno.service.RateLimitService#allow(java.lang.String)}.
	 */
	@Test
	public void testAllow() {
		try {
			assertTrue(service.allow("testAPI"));
		} catch (RateLimiterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
