package org.springframework.index.configuration;

import org.springframework.bootstrap.SpringApplication;
import org.springframework.bootstrap.context.annotation.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.site.configuration.DocumentationConfiguration;
import org.springframework.site.configuration.ElasticSearchConfiguration;
import org.springframework.site.configuration.GitHubConfiguration;
import org.springframework.site.configuration.SecurityConfiguration;

@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"org.springframework.index", "org.springframework.site.search"})
@EnableScheduling
@Import({ElasticSearchConfiguration.class, DocumentationConfiguration.class, GitHubConfiguration.class, SecurityConfiguration.class})
public class CrawlerConfiguration {

	public static void main(String[] args) {
		build(CrawlerConfiguration.class).run(args);
	}

	public static SpringApplication build(Class<?>... config) {
		SpringApplication application = new SpringApplication(config);
		application.setDefaultCommandLineArgs(
				"--server.port=9000");
		return application;
	}

	@Bean
	public TaskScheduler scheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(10);
		return scheduler;
	}

}