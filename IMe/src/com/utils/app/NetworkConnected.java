package com.utils.app;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/** 检测网络
 * @author liyuan
 *
 */
public class NetworkConnected {
	
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }


}
