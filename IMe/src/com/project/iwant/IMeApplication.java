/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.project.iwant;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import com.gotye.api.GotyeAPI;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.open_demo.CrashApplication;
import com.project.myself_view.Constants;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public class IMeApplication extends Application {
	
	public static final String APPKEY = "234b4143-e549-4924-b708-2aebda8e8267";
	public static final String PACKAGENAME = "com.project.iwant";
	public static  ArrayList<Integer>  times=new ArrayList<Integer>();
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (Constants.Config.DEVELOPER_MODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
		}

		super.onCreate();
		//异常拦截记录
				CrashApplication.getInstance(this).onCreate();
				//初始化
				GotyeAPI.getInstance().init(getBaseContext(), APPKEY, PACKAGENAME);
		initImageLoader(getApplicationContext());
		//初始化API，放在application中，保证第一次就启动
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY )
				  .memoryCacheExtraOptions(480, 800) // maxwidth, max height，即保存的每个缓存文件的最大长宽   
                 .threadPoolSize(3)//线程池内加载的数量   
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(100 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}