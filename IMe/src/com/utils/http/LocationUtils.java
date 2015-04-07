package com.utils.http;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.utils.io.SharePreferenceUtils;

/**
 * @description 获取地理位置Utils，利用百度API 来获取
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-6下午2:39:30
 */
public class LocationUtils {
    private static Context context;
	private static LocationUtils locationUtils = new LocationUtils();
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	private static String longitude = "";
	private static String latitude = "";
	private static String address = null;
	public static String getLongitude() {
		return longitude;
	}

	public static void setLongitude(String longitude) {
		LocationUtils.longitude = longitude;
	}

	public static String getLatitude() {
		return latitude;
	}

	public static void setLatitude(String latitude) {
		LocationUtils.latitude = latitude;
	}

	public static String getAddress() {
		return address;
	}

	public static void setAddress(String address) {
		LocationUtils.address = address;
	}

	public static String getCity() {
		return city;
	}

	public static void setCity(String city) {
		LocationUtils.city = city;
	}

	private static String city = null;

	private LocationUtils() {

	}

	public static LocationUtils getInstance(Context icContext) {
		context=icContext;
		return locationUtils;
	}

	/**
	 * @function 利用百度SDK 获取
	 * @time 2014-10-6下午3:29:12
	 * @return Location
	 */
	public void getLocationUseBaiDuSDK() {
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		// option.setAddrType("all");
		option.setCoorType("gcj02");
		option.setScanSpan(5000);
		option.setIsNeedAddress(true);
		// option.disableCache(true);
		// option.setPoiNumber(5);
		// option.setPoiDistance(1000);
		// option.setPoiExtraInfo(true);
		mLocationClient.setLocOption(option);
		Log.v("here", "here");
		mLocationClient.start();

	}

	/**
	 *  @description 位置监听类
	 *  @author Ly
	 *  @email 1522651962@qq.com
	 *  @time 2014-10-6下午4:14:09
	 */
	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			Log.e("IwantService", "可以到的了这里吗？");
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			setLatitude(location.getLatitude() + "");
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			setLongitude(location.getLongitude() + "");
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				setAddress(location.getAddrStr());
				setCity(city=location.getCity());
				sb.append("\\city : ");
				sb.append(location.getCity());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\\naddr : ");
				sb.append(location.getAddrStr());
				setAddress(location.getAddrStr());
				setCity(city=location.getCity());
				sb.append("\\city : ");
				sb.append(location.getCity());
			}
			Log.e("IwantService", "定位完成:" + sb.toString());
			/*
			 * 1.定位完成，这里需要刷新全部
			 *
			 */
			SharePreferenceUtils.getIntance(context).setLocation(getLatitude(), getLongitude(), getAddress());
			mLocationClient.stop();
		}

     

}
}