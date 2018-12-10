package com.ad.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

@Ignore
public class AsyncLoggerTest {

  @Test
  public void testLogging() throws Exception {
    final AsyncLogTestUtil util = new AsyncLogTestUtil();
    util.log();
    final DefaultLogTestUtil util2 = new DefaultLogTestUtil();
    util2.log();
    final File file = new File("/tmp/minute_rolling.log");
    Assert.assertTrue(file.exists());
    Thread.sleep(61000);
    final File dir = new File("/tmp");
    final File[] files = dir.listFiles();
    boolean exists = false;
    for (final File temp : files) {
      if (temp.getName().startsWith("minute_rolling.log.")) {
        exists = true;
        Assert.assertTrue(temp.length() > 0);
        break;
      }
    }
    Assert.assertTrue(exists);
  }

  @Slf4j(topic = "MINUTE_LOGGER")
  private static class AsyncLogTestUtil {

    private void log() {
      log.error("LOGGING ASYNC");
    }
  }

  @Slf4j
  private static class DefaultLogTestUtil {

    private void log() {
      log.error("LOGGING Default");
    }
  }

}
