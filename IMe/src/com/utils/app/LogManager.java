package com.utils.app;
import android.util.Log;
/**Log日志管理类
 *@author Reek_Lee
 *@time 2014-4-11 下午5:31:42
 *@Email 1522651962@qq.com
 */
public class LogManager{
	private  static String TAG="LOG";
	private static boolean isLog=true;

	
	public  static void setTag(String tag){
		TAG=tag;
	}
	
	public static void d(String msg) {
		if (isLog) {
			Log.d(TAG, msg);
		}
		
	}

	public void d(String msg, Throwable tr) {
		if (isLog) {
			Log.d(TAG, msg, tr);
		}
		
	}

	public void e(String msg) {
		if (isLog) {
			Log.e(TAG, msg);
		}
		
	}

	public void e(String msg, Throwable tr) {
		Log.e(TAG, msg, tr);
	}

	public void i(String msg) {
		Log.i(TAG, msg);
	}

	public void i(String msg, Throwable tr) {
		Log.i(TAG, msg, tr);
	}

	public void v(String msg) {
		Log.v(TAG, msg);
	}

	public void v(String msg, Throwable tr) {
		Log.v(TAG, msg, tr);
	}

	public void w(String msg) {
		Log.w(TAG, msg);
	}

	public void w(Throwable tr) {
		Log.w(TAG, tr);
	}

	public void w(String msg, Throwable tr) {
		Log.w(TAG, msg, tr);
	}

	public void getStackTraceString(Throwable tr) {
		Log.getStackTraceString(tr);
	}

	public void isLoggable(int level) {
		Log.isLoggable(TAG, level);
	}

	public void println(int priority, String msg) {
		Log.println(priority, TAG, msg);
	}

}
