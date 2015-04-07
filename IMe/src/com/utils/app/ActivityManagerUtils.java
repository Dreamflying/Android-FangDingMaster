package com.utils.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;

/**
 * @description Activity 控制管理器
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-11下午1:42:15
 */
public class ActivityManagerUtils {

	private static ActivityManagerUtils activityManager = new ActivityManagerUtils();
	public  static Map<String, Activity> activities = new HashMap<String, Activity>();
	public static ArrayList<Activity> activityList = new ArrayList<Activity>();

	private ActivityManagerUtils() {

	}

	public static ActivityManagerUtils getIntance() {
		return activityManager;
	}

	/**
	 * @function 增加activity
	 * @time 2014-10-11下午7:02:32
	 * @param activity
	 *            void
	 */
	public void addActivity(String activityName, Activity activity) {
		activities.put(activityName, activity);
		activityList.add(activity);
	}

	/**
	 * @function 关闭所有activity
	 * @time 2014-10-11下午7:02:29 void
	 */
	public void finishAllActivity() {
		if (activityList != null) {
			if (activityList.size() != 0) {
				for (int i = 0; i < activityList.size(); i++) {
					if (activityList.get(i)!=null) {
						activityList.get(i).finish();
					}
					
				}

			}
		}
	}

	/**
	 * @function 根据activity的名字关闭制定的activity
	 * @time 2014-10-11下午7:10:40
	 * @param activityName
	 *            void
	 */
	public void finishActivityForName(String activityName) {
		if (activities != null) {
			if (activities.size() != 0) {
				activities.get(activityName).finish();
			}

		}

	}
	
	public int getCurrentActivityNum(){
		
		if (activities != null) {
			return activities.size();

		}
		return 0;
	}

	
/**获取activity栈顶的名字
 * @param context
 * @return
 */
public String getTopActivity(Activity context)
{
     ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE) ;
     List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1) ;
         
     if(runningTaskInfos != null)
       return (runningTaskInfos.get(0).topActivity).toString() ;
          else
       return null ;
}
}
