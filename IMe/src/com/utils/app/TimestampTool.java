package com.utils.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;
/**
 * @author liyuan
 * @Description  时间与时间戳转换工具
 * @Email 1522651962@qq.com
 * @date 2014年8月6日下午5:29:51
 */
public class TimestampTool {

	/**
	 * 
	 * TODO:输入一个时间，获取该时间的时间戳
	 * 
	 * @param @param dateString
	 * @param @return
	 * 
	 */
	public static long getStringTimestamp(String dateString)
			 {
		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse(dateString);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long temp = date1.getTime();// JAVA的时间戳长度是13位
		return temp;
	}

	/**
	 * 获取系统当前时间
	 * @throws ParseException 
	 * 
	 * @time 2014-4-24 下午5:16:01
	 */
	public static long getCurrentTime() {
		/*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return getStringTimestamp(df.format(new Date()));// new Date()为获取当前系统时间
	
*/	
		Calendar now = Calendar.getInstance();
		Log.v("now", now.getTimeInMillis()/1000+"");
	return now.getTimeInMillis()/1000;	
	}
	
	
	public static String getTimeString(String time){
		String str_date = null;
		if (time!=null) {
			
		
		if (time.equals("")) {
			
		}else {
			long t=Long.parseLong(time);
			Date date =new Date(t);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			 str_date = sdf.format(date);
		}
		}
		return str_date;
	} 

}
