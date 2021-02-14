/**
 * 
 */
package com.sc.test.pal.rituparno.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sc.test.pal.rituparno.exception.RateLimiterException;
import com.sc.test.pal.rituparno.model.RateLimit;
import com.sc.test.pal.rituparno.scheduler.SimpleRateLimiter;

/**
 * @author rituparno.pal@gmail.com
 *
 */
@Service
public class RateLimitService {

	@Value("${rate.limit.default.count}")
    private int defaultLimitCount;
    
    @Value("${rate.limit.default.time.unit}")
    private String defaultTimePeriod;
    
    private static Map<String, SimpleRateLimiter> limiters = new ConcurrentHashMap<>();
    
    /**
     * 
     * @param apiKey
     * @return
     * @throws RateLimiterException
     */
    public boolean allow(String apiKey) throws RateLimiterException {
		SimpleRateLimiter rateLimiter = getRateLimit(apiKey);
		boolean allowRequest = rateLimiter.tryAcquire();
		if (!allowRequest) {
			throw new RateLimiterException("Too many requests");
	    }
		return allowRequest;
	}

	private SimpleRateLimiter getRateLimit(String apiKey) {
		if (limiters.containsKey(apiKey)) {
            return limiters.get(apiKey);
        } else {
            synchronized(apiKey.intern()) {
                if (limiters.containsKey(apiKey)) {
                    return limiters.get(apiKey);
                }
                SimpleRateLimiter rateLimiter = createDefaultRateLimiter();
                limiters.put(apiKey, rateLimiter);
                return rateLimiter;
            }
        }
	}

	private SimpleRateLimiter createDefaultRateLimiter() {
		SimpleRateLimiter defaultSimpleRateLimiter = new SimpleRateLimiter(defaultLimitCount, getTimeUnit(defaultTimePeriod));
		return defaultSimpleRateLimiter;
	}

	/**
	 * 
	 * @param rateLimit
	 * @return
	 * @throws RateLimiterException
	 */
	public boolean createRateLimiter(RateLimit rateLimit) throws RateLimiterException {
		String apiKey = rateLimit.getApiKey(); 
		int limitCount = rateLimit.getLimitCount();	
		String timePeriod = rateLimit.getTimePeriod();
		if (apiKey == null) {
			throw new RateLimiterException("Api Key not provided");
		}
		if (limitCount <= 0) {
			throw new RateLimiterException("Limit count should be a non-zero integer");
		}
		if (timePeriod == null) {
			throw new RateLimiterException("Time Period not provided");
		}
		TimeUnit timeUnit = getTimeUnit(timePeriod);
		if (timeUnit == null) {
			throw new RateLimiterException("Valid time unit not provided");
		}
		SimpleRateLimiter rateLimiter = new SimpleRateLimiter(limitCount, timeUnit);
		limiters.put(apiKey, rateLimiter);
		return true;
	}
	
	private TimeUnit getTimeUnit(String timePeriod) {
		TimeUnit timeUnit = null;
		if ("PER_DAY".equals(timePeriod)) {
			timeUnit = TimeUnit.DAYS;
		} else if ("PER_HOUR".equals(timePeriod)) {
			timeUnit = TimeUnit.HOURS;
		} else if ("PER_MINUTE".equals(timePeriod)) {
			timeUnit = TimeUnit.MINUTES;
		} else if ("PER_SECOND".equals(timePeriod)) {
			timeUnit = TimeUnit.SECONDS;
		}
		return timeUnit;
	}

}