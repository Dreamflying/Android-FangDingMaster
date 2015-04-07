package com.utils.io;

import java.io.File;
import java.io.IOException;

import android.os.Environment;
import android.util.Log;

/**
 * @description 手机存储 操作类
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-7下午3:48:04
 */
public class StorageEnvironmentUtils {

	/**
	 * @function 获取SD卡目录
	 * @time 2014-10-7下午3:58:09
	 * @return String
	 */
	public static String getSDCardPath() {
		String dir=null;
		File sdDir = null;
		if (isSDCard()) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
			dir=sdDir.toString();
		}else {
			dir=getInternalPath() ;
		}
		return dir;

	}

	/**
	 * @function 是否有SDcard
	 * @time 2014-10-7下午3:59:39
	 * @return boolean
	 */
	public static boolean isSDCard() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			return true;
		}
		Log.v("isSDCard", "no");
		return false;
	}

	public static String getInternalPath() {
		return  Environment.getRootDirectory().getPath();

	}
	public static void getRoot(){
		try {
			Process process=Runtime.getRuntime().exec("su");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
