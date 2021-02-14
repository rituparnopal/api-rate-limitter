/**
 * 
 */
package com.sc.test.pal.rituparno.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author rituparno.pal@gmail.com
 *
 */
public class SimpleRateLimiter {

	private Semaphore semaphore;
	
	private int maxPermits;
	
	private TimeUnit timePeriod;
	
	private ScheduledExecutorService scheduler;
	
	/**
	 * 
	 * @param permits
	 * @param timePeriod
	 * @return
	 */
	public static SimpleRateLimiter create(int permits, TimeUnit timePeriod) {
        SimpleRateLimiter limiter = new SimpleRateLimiter(permits, timePeriod);
        limiter.schedulePermitReplenishment();
        return limiter;
    }
	
	/**
	 * 
	 * @param permits
	 * @param timePeriod
	 */
	public SimpleRateLimiter(int permits, TimeUnit timePeriod) {
		this.semaphore = new Semaphore(permits);
        this.maxPermits = permits;
        this.timePeriod = timePeriod;
	}
	
	public boolean tryAcquire() {
        return semaphore.tryAcquire();
    }
	
	public void stop() {
        scheduler.shutdownNow();
    }
	
	public void schedulePermitReplenishment() {
		scheduler = Executors.newScheduledThreadPool(1);
	    scheduler.scheduleWithFixedDelay(() -> {
	    	semaphore.release(maxPermits - semaphore.availablePermits());
	    }, 0, 1, timePeriod);
	}

}
