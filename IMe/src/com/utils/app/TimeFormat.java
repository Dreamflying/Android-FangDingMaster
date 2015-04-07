package com.utils.app;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@SuppressLint("SimpleDateFormat")
public class TimeFormat {
	public static String timeToDay(String mTimeStr){
		String sd="";
		if (mTimeStr!=null) {
			if(!mTimeStr.equals("")){
				SimpleDateFormat sdf=new SimpleDateFormat("dd");  
				sd = sdf.format(new Date(Long.parseLong(mTimeStr)));  
			}
			
			
		}
		
		return sd;
	}
	public static String timeToMon(String mTimeStr){
		String sd="";
		if (mTimeStr!=null) {
			if(!mTimeStr.equals("")){
				SimpleDateFormat sdf=new SimpleDateFormat("MM");  
			 sd = sdf.format(new Date(Long.parseLong(mTimeStr)));  
			}
			
			
		}
		
		return sd;
	}
	/*public static String timeToMMddhhmm(String mTimeStr){
		SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 hh:mm");  
		String sd = sdf.format(new Date(Long.parseLong(mTimeStr+1000)));  
		return sd;
	}*/
	public static boolean isOneMinute(String mOldTimeStr,String mNowTimeStr){
		int mOldTime=Integer.parseInt(mOldTimeStr);
		int mNowTime=Integer.parseInt(mNowTimeStr);
		if(mNowTime-mOldTime<=60){
			return true;
		}else{
			return false;
		}
	}
	public static String getMessageTime(String mTimeStr){
		String sd = null;
		if (mTimeStr.equals("")||mTimeStr.equals("null")||mTimeStr==null) {
			
		}else {
			int mNowTime = (int) (System.currentTimeMillis() / 1000);
			int mTime=Integer.parseInt(mTimeStr);
			if(mNowTime-mTime>=86400){
				//超过24小时，显示日期
				SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 HH:mm");  
				sd = sdf.format(new Date(Long.parseLong(mTimeStr+"000")));  
			}else{
				//魏超过24小时，只显示时间
				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");  
				sd = sdf.format(new Date(Long.parseLong(mTimeStr+"000")));  
			}
		}
		
		
		return sd;
	}
	
	public static String getAMorPMTime(String mTimeStr){
		String sd = null;
		if (mTimeStr.equals("")||mTimeStr.equals("null")||mTimeStr==null) {
			
		}else {
			int mNowTime = (int) (System.currentTimeMillis() / 1000);
			int mTime=Integer.parseInt(mTimeStr);
				//未超过24小时，只显示时间
				SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
				Date date=new Date(Long.parseLong(mTimeStr+"000"));
				sd = sdf.format(new Date(Long.parseLong(mTimeStr+"000")));  
				Calendar now = Calendar.getInstance();
			long  days=	(System.currentTimeMillis()-date.getTime())/(1000 * 60 * 60 * 24);
			Log.v("days", days+""+"---"+mTimeStr);
			if (days==1) {
				
				if (date.getHours()<12) {
					sd= "昨天 "+"AM "+sd;
				}else {
					
					sd="昨天 "+""+sd;
				}
				return sd;
			}else if (days==2) {
				if (date.getHours()<12) {
					sd= "前天 "+"AM "+sd;
				}else {
					
					sd="前天 "+""+sd;
				}
				return sd;
			}
			if (days==0) {
				if (date.getHours()<12) {
					sd= "AM "+sd;
				}else {
					
					sd=""+sd;
					return sd;
				}
			}else {
				SimpleDateFormat sdf1=new SimpleDateFormat("MM月dd日 HH:mm");  
				sd = sdf1.format(new Date(Long.parseLong(mTimeStr+"000"))); 
				return sd;
			}
				
				
		}
		
		
		return sd;
	}
	
	public static String timeToMMddhhmm(String mTimeStr){
		String sd = null;
		if (mTimeStr.equals("")||mTimeStr.equals("null")||mTimeStr==null) {
			
		}else {
			SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日 HH:mm");  
			sd = sdf.format(new Date(Long.parseLong(mTimeStr+"000"))); 
				
		}		
		
		return sd;
	}
}
