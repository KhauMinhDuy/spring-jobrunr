package com.khauminhduy.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.jobrunr.jobs.annotations.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SampleJobService {

	public static final long EXECUTION_TIME = 5000L;

	private Logger logger = LoggerFactory.getLogger(SampleJobService.class);
	
	private AtomicInteger count = new AtomicInteger();
	
	@Job(name = "The sample job with variable %0", retries = 2)
	public void executeSampleJob(String input) {
		logger.info("The sample job has begun. The variable you passed is {}", input);
		try {
			Thread.sleep(EXECUTION_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			count.incrementAndGet();
			logger.info("Sample job has finished...");
		}
	}
	
	public int getNumberOfInvocations() {
		return count.get();
	}

}
