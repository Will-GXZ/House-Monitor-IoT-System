package com.twl.xg.config;

import com.twl.xg.taskScheduler.CustomTaskScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * This class set up the task scheduler for the application. This is needed for
 * scheduled tasks.
 *
 * @author Xiaozheng Guo
 * @version 1.0
 */
@Configuration
public class TaskSchedulerConfig {
  /**
   * Register a <code>ThreadPoolTaskScheduler</code> bean.
   */
  @Bean
  public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new CustomTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(1);
    threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
    return threadPoolTaskScheduler;
  }
}
