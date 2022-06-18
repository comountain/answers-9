package com.example.activity.utils;

import android.util.Log;

import  com.example.activity.constants.config;

public class LogUtils {

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (config.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.e(config.LogTag, createLog(message));
        }
    }

    public static void i(String message) {
        if (config.DEBUG) {
            getMethodNames(new Throwable().getStackTrace());
            Log.i(config.LogTag, createLog(message));
        }
    }

}
