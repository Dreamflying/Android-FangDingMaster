package com.project.iwant;

import java.util.ArrayList;

import com.project.login_view.LoginActivity;
import com.utils.io.SharePreferenceUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * ��ӭ��������
 * 
 * @author Reek_Lee
 * @time 2014-4-15 ����3:20:43
 * @email��1522651962@qq.com
 */
public class SplashActivity extends FragmentActivity  {
	private ViewPager welcomeViewPager;
	private int ChangePosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 if (SharePreferenceUtils.getIntance(SplashActivity.this).getFirstOpen()) {
			 SharePreferenceUtils.getIntance(SplashActivity.this).saveFirstOpen(true);
			 Intent intent =new Intent(SplashActivity.this,EveryDayDisplayActivity.class);
		     startActivity(intent);
			 finish();
		   }else {
			   SharePreferenceUtils.getIntance(SplashActivity.this).saveFirstOpen(true);
		   }
		setContentView(R.layout.activity_splash);
		initView();
		setListener();
	}

	public void initView() {
		welcomeViewPager = (ViewPager) this.findViewById(R.id.welcomeViewpager);
	}

	public void setListener() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		welcomeViewPager.setAdapter(new WelComePagerAdapter(fragmentManager));
		welcomeViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int position) {
				ChangePosition=position;
				Log.v("position", position+"");
				switch (position) {
				
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					Intent intent =new Intent(SplashActivity.this,EveryDayDisplayActivity.class);
					startActivity(intent);
					finish();
					break;
				default:
					break;
				}
			}

		});

		welcomeViewPager.setCurrentItem(0);
	}


	public class WelComePagerAdapter extends FragmentStatePagerAdapter {

		private ArrayList<Fragment> mFragments;

		private final int[] PageCount = new int[3];

		public WelComePagerAdapter(FragmentManager fm) {
			super(fm);
			mFragments = new ArrayList<Fragment>();
			for (int i = 0; i < 4; i++) {
				mFragments.add(new WelcomePagerFragment(i));
			}

		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

	}




}
