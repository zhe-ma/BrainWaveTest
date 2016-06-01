package com.example.sms1_0.utils;

import android.util.Log;

/**
 * 向logcat打印log的工具类
 * @author 马哲
 */
public class LogUtils {
	
	public static boolean isDebug = true;
	
	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, msg);
		}
	}
	
	public static void i(Object tag, String msg) {
		if (isDebug) {
			Log.i(tag.getClass().getSimpleName(), msg);
		}
	}
}
