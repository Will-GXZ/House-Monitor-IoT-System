package com.twl.xg.taskScheduler;

import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ScheduledFuture;

/**
 * Customized task scheduler, only support one task in this implementation.
 */
public class CustomTaskScheduler extends ThreadPoolTaskScheduler {
  private static final Logger logger = Logger.getLogger(CustomTaskScheduler.class);

  private ScheduledFuture<?> future;

  /**
   * Schedule a repeated task. If there is already a task scheduled, cancel it first.
   * Ensure that there is no more than one task at a time.
   *
   * @param task
   * @param period
   * @return
   */
  @Override
  public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
    // avoid race condition
    synchronized (this) {
      if (future != null) {
        // "false", because we should let this task finish itself, and do not repeat
        future.cancel(false);
        logger.debug("scheduleAtFixedRate: stop scheduled task.");
      }
      logger.debug("scheduleAtFixedRate: Schedule task in period of --> " + period);
      future = super.scheduleAtFixedRate(task, period);
      return future;
    }
  }

  /**
   * Cancel the scheduled task if it exists.
   */
  synchronized public void stopScheduledTask() {
    logger.debug("stopScheduledTask:  stop scheduled task.");
    future.cancel(false);
    future = null;
  }
}

