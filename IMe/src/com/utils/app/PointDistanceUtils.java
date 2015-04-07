package com.utils.app;

import java.text.DecimalFormat;

/**
 * @description 根据经纬度获取距离
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-12上午12:04:45
 */
public class PointDistanceUtils {
	
	private final static double EARTH_RADIUS = 6378137.0;

	public static double gps2m(String lat_local, String lng_local, String lat_b,
			String lng_b) {
		double ulatitude = 0.0d;
		double ulongitude = 0.0d;
		if (lat_b.length() > 0)
			ulatitude = Double.valueOf(lat_b);
		if (lng_b.length() > 0)
			ulongitude = Double.valueOf(lng_b);
		double radLat1 = (Double.parseDouble(lat_local) * Math.PI / 180.0);
		double radLat2 = (ulatitude * Math.PI / 180.0);
		double a = radLat1 - radLat2;
		double b = (Double.parseDouble(lng_local) - ulongitude) * Math.PI / 180.0;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	public static String getDistanceForKm(double lj){
		double d=lj/1000;
		DecimalFormat df = new DecimalFormat("###,##0.00");
		return df.format(d)+"km";
	}

}
