package com.utils.app;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private final static SimpleDateFormat dateFormater = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将毫秒字符串转为年月日
	 * http://iteye.blog.163.com/blog/static/1863080962012111351358244/?suggestedreading&wumii
	 * @param sdate
	 * @return
	 */
	public static int[] TimetoDate(String sdate) {
		try {
			int[] date = new int[5];
			long longtime = Long.parseLong(sdate);
			longtime = longtime * 1000;
			Calendar calendar=Calendar.getInstance();
			calendar.setTimeInMillis(longtime);
			date[0] = calendar.get(Calendar.YEAR);
			date[1] = calendar.get(Calendar.MONTH)+1;
			date[2] = calendar.get(Calendar.DATE);
			date[3] = calendar.get(Calendar.HOUR_OF_DAY);
			date[4] =calendar.get(Calendar.MINUTE);
			System.out.println("==TimetoDate==" + date[0] + "==" + date[1]
					+ "==" + date[2] + "==" + date[3] + "==" + date[4] + "==");
			return date;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将日期类型转换毫秒
	 * 
	 * @param sdate
	 * @return
	 */
	public static Long togetTimeInMillis(int years, int moth, int date) {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(years, moth, date);
			return cal.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		String publis = "发表于：";
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.format(cal.getTime());
		String paramDate = dateFormater2.format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = publis
						+ Math.max(
								(cal.getTimeInMillis() - time.getTime()) / 60000,
								1) + "分钟前";
			else
				ftime = publis + hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = publis
						+ Math.max(
								(cal.getTimeInMillis() - time.getTime()) / 60000,
								1) + "分钟前";
			else
				ftime = publis + hour + "小时前";
		} else if (days == 1) {
			ftime = publis + "昨天";
		} else if (days == 2) {
			ftime = publis + "前天";
		} else if (days > 2 && days <= 10) {
			ftime = publis + days + "天前";
		} else if (days > 10) {
			ftime = publis + dateFormater2.format(time);
		}
		return ftime;
	}

	/**
	 * 以友好的方式显示时间 判断一下 时间是否超过了12个小时了，如果没有超过的话，就以 小时的形式显示 小于1分钟 就显示“刚刚”
	 * 
	 * @param sdate
	 * @return
	 */
	public static String time_friendly(String sdate) {
		String publis = "发表于：";
		String Time = sdate;
		try {
			// 设置日期格式 new Date()为获取当前系统时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String NowTime = sdf.format(new Date());
			Date startdatetime = sdf.parse(sdate);
			Date enddatetime = sdf.parse(NowTime); // 格式化
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startdatetime);
			long timestart = calendar.getTimeInMillis();
			calendar.setTime(enddatetime);
			long timeend = calendar.getTimeInMillis();
			// 时间间隔——————————————————除以1000得秒 - 60000 得分 -3600000得到小时 -
			// 24*3600000得到天
			// day
			long theCurrentDay = (timeend - timestart) / (1000 * 60 * 60 * 24);
			// time
			long theCurrentTime = (timeend - timestart) / (1000 * 60 * 60);
			// minute
			long theCurrentMinute = (timeend - timestart) / (1000 * 60);
			// second
			long theCurrentSecond = (timeend - timestart) / (1000);
			// 在此判断 要显示的时间
			if (theCurrentDay > 0) {
				Time = publis + String.valueOf(theCurrentDay) + " 天前";
				return Time;
			} else if (theCurrentTime > 0) {
				Time = publis + String.valueOf(theCurrentTime) + " 小时前";
				return Time;

			} else if (theCurrentMinute > 0) {
				Time = publis + String.valueOf(theCurrentMinute) + " 分钟前";
				return Time;
			} else {
				Time = publis + "刚刚";
				return Time;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return Time;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.format(today);
			String timeDate = dateFormater2.format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input) || "null".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ll 发表日志包含表情的字段替换成 手机表示的数组格式 将<emt>替换成[
	 */

	// public static String face_WebTOPhone(String str) {
	// String keyStart = "<e";
	// String keyStart1 = "</e";
	// String keyEnd = ">";
	// // String keyStart = "<emt>";
	// // String keyEnd = "</emt>";
	// boolean bool = str.contains(keyStart);
	// boolean bool1 = str.contains(keyStart1);
	// System.out.println("=========日志face_WebTOPhone=bool====="+bool);
	// System.out.println("=========日志face_WebTOPhone=bool1====="+bool1);
	// if (bool&&bool1) {
	// str.replaceAll(keyStart, "[");
	// str.replaceAll(keyStart1, "[/");
	// str.replaceAll(keyEnd, "]");
	// }
	// if(str.contains("[mt]")){
	// str.replaceAll("[mt]", "[");
	// str.replaceAll("[/mt]", "]");
	// }
	// return str;
	// }

	/**
	 * ll 发表日志 包含表情的字段替换成 web表示的数组格式
	 */

	// public static String face_PhoneTOWeb(String str) {
	// String keyStart = "[";
	// String keyEnd = "]";
	// boolean bool = str.contains(keyStart);
	// if (bool) {
	// str.replaceAll(keyStart, "<emt>");
	// str.replaceAll(keyEnd, "</emt>");
	// }
	// return str;
	// }

	// public static String face_Position(String str) {
	//
	// String[] arr = new String[str.length()];
	// System.out.println("v在字符串出现的位置：" + str.indexOf("v"));
	// for (int i = 0; i < arr.length; i++) {
	// arr[i] = str.substring(i, (i + 1));
	// if ("v".equals(arr[i])) {
	// System.out.println("v在字符串出现的位置：" + i);
	// }
	// }
	// return str;
	// }
	
	public static byte[] stringToByteArray(String content){
		
		try {
			return content.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String byteArrayToString(byte[] content){
		String result = null;
		try {
			result = new String(content,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String StringFilter(String str){ 
		  String regEx = "[/\\:*?<>|\"\n\t]"; //要过滤掉的字符
		  Pattern p = Pattern.compile(regEx); 
		  Matcher m = p.matcher(str); 
		  return m.replaceAll("").trim(); 
		  }

}