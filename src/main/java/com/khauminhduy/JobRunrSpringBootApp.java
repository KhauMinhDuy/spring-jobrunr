package com.khauminhduy;

import javax.annotation.PostConstruct;

import org.jobrunr.configuration.JobRunr;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.khauminhduy.service.SampleJobService;

@SpringBootApplication
public class JobRunrSpringBootApp {

	@Autowired
	private JobScheduler jobScheduler;

	public static void main(String[] args) {
		SpringApplication.run(JobRunrSpringBootApp.class, args);
	}

	@Bean
	public StorageProvider storageProvider(JobMapper jobMapper) {
		InMemoryStorageProvider provider = new InMemoryStorageProvider();
		provider.setJobMapper(jobMapper);
		return provider;
	}

	@PostConstruct
	public void init() {
		jobScheduler.<SampleJobService>scheduleRecurrently(Cron.every5minutes(),
				x -> x.executeSampleJob("a recurring job"));
	}

	@Bean
	public JobScheduler initJobRunr(ApplicationContext context) {
		return JobRunr.configure().useJobActivator(context::getBean).useStorageProvider(new InMemoryStorageProvider())
				.useBackgroundJobServer().useJmxExtensions().useDashboard().initialize();
	}

}
