package com.hules.horizontalnumberpicker.demo.commons;

import android.util.Log;
import com.hules.horizontalnumberpicker.demo.BuildConfig;

public class DebugLog {
  private static final int LOGGING_TAG_MAX_LENGTH = 23;

  private static boolean isDebuggable() {
    return BuildConfig.DEBUG;
  }

  private static String formatMessage(String message) {
    StackTraceElement[] stackTrace = (new Throwable().getStackTrace());
    if (stackTrace.length >= 2) {
      return String.format("[%s:%s] -> %s(): %s", stackTrace[2].getFileName(),
          stackTrace[2].getLineNumber(), stackTrace[2].getMethodName(), message);
    } else {
      return message;
    }
  }

  private static String getApplicationId() {
    String applicationId = BuildConfig.APPLICATION_ID;
    if (applicationId.length() > LOGGING_TAG_MAX_LENGTH) {
      applicationId = applicationId.substring(0, LOGGING_TAG_MAX_LENGTH);
    }
    return applicationId;
  }

  public static void d(String message) {
    if (isDebuggable()) {
      Log.d(getApplicationId(), formatMessage(message));
    }
  }

  public static void e(String message) {
    if (isDebuggable()) {
      Log.e(getApplicationId(), formatMessage(message));
    }
  }

  public static void i(String message) {
    if (isDebuggable()) {
      Log.i(getApplicationId(), formatMessage(message));
    }
  }

  public static void v(String message) {
    if (isDebuggable()) {
      Log.v(getApplicationId(), formatMessage(message));
    }
  }

  public static void w(String message) {
    if (isDebuggable()) {
      Log.w(getApplicationId(), formatMessage(message));
    }
  }
}