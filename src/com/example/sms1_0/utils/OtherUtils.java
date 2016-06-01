package com.example.sms1_0.utils;

public class OtherUtils {
 
	/**
	 * —” ±∫¡√Î ˝
	 * @param time
	 */
	public static void sleepMs(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
