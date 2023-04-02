package com.da0hn.multithreading.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtil {

  private static final StopWatch stopWatch = new StopWatch();
  private static final int TRANSFORM_DELAY = 500;

  public static void delay(final int delayInMilliseconds) {
    try {
      Thread.sleep(delayInMilliseconds);
    } catch (final InterruptedException e) {
      LoggerUtil.log("Exception occurs: " + e.getMessage());
    }
  }

  public static String transform(final String text) {
    CommonUtil.delay(TRANSFORM_DELAY);
    return text.toUpperCase();
  }

  public static void startTimer() {
    stopWatch.start();
  }

  public static void timeElapsed() {
    stopWatch.stop();
    LoggerUtil.log("Total time elapsed " + stopWatch.getTime() + "ms");
    stopWatch.reset();
  }

  public static void resetTimer() {
    stopWatch.reset();
  }

  public static int numberOfCores() {
    return Runtime.getRuntime().availableProcessors();
  }

}
