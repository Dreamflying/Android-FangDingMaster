package com.project.iwant;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class NewSplashActivity extends Activity{
	 private int[] ids = { R.drawable.new_welcome1, R.drawable.new_welcome2,
	            R.drawable.new_welcome3 };

	    private List<View> views = new ArrayList<View>();// Views动态数组
	    private ViewPager pager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.new_activity_splash);
	        initId();
	        initViewPager();
	        setpagerListener();
	}
	 private void initId() {


	    }

	    //初始化Viewpager，设置适配器以填充内容
	    @SuppressLint("NewApi") private void initViewPager() {
	        // TODO Auto-generated method stub
	        pager = (ViewPager) findViewById(R.id.Pager);
	        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
	                LayoutParams.MATCH_PARENT);
	        ImageView imaV;
	        for (int i = 0; i < ids.length; i++) {
	            imaV = new ImageView(this);
	            imaV.setImageResource(ids[i]);

	            imaV.setLayoutParams(params);
	            imaV.setScaleType(ScaleType.FIT_XY);
	            views.add(imaV);
	        }
	        Log.e("views", "-->" + views.size());
	        pager.setAdapter(new MyAdapter(views));
	    }
	    
	    private void setpagerListener() {
	        // TODO Auto-generated method stub
	        pager.setOnPageChangeListener(new OnPageChangeListener() {

	            /**
	             * onPageSelected:每次切换页面就会执行 在此实现圆点指示器以及在最后页面显示button
	             */
	            @Override
	            public void onPageSelected(int arg0) {
	                // TODO Auto-generated method stub
	                // pager.getCurrentItem()话去当前页面的序号
	                int tmp = pager.getCurrentItem() % views.size();

	            }


	            /**
	             * 当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回得到调用。 其中三个参数的含义分别为：
	             * 
	             * arg0 :当前页面，及你点击滑动的页面
	             * 
	             * arg1:当前页面偏移的百分比
	             * 
	             * arg2:当前页面偏移的像素位置
	             */
	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	                // TODO Auto-generated method stub
	                if (arg0 == views.size() - 1) {
	                    Log.i("jump", "NextActivity");
	                    Log.i("finished", "MainActivity");
	                }
	            }

	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	                // TODO Auto-generated method stub
	            }
	        });
	    }
	    public class MyAdapter extends PagerAdapter {

	        private List<View> views;

	        public MyAdapter(List<View> views) {
	            this.views = views;
	        }

	        //destroyItem，getCount,instantiateItem实现循环滑动
	        @Override
	        public void destroyItem(View arg0, int arg1, Object arg2) {
	            ((ViewPager) arg0).removeView(views.get(arg1 % views.size()));
	        }

	        @Override
	        public void finishUpdate(View arg0) {
	        }


	        @Override
	        public int getCount() {
	            return views.size();
	            //向右循环滑动
	            //return views.size()*5;
	        }

	        @Override
	        public Object instantiateItem(View arg0, int arg1) {
	            Log.e("tag", "instantiateItem = " + arg1);
	            ((ViewPager) arg0).addView(views.get(arg1 % views.size()), 0);
	            return views.get(arg1 % views.size());
	        }

	        @Override
	        public boolean isViewFromObject(View arg0, Object arg1) {
	            return arg0 == (arg1);
	        }

	        @Override
	        public void restoreState(Parcelable arg0, ClassLoader arg1) {

	        }

	        @Override
	        public Parcelable saveState() {
	            return null;
	        }

	        @Override
	        public void startUpdate(View arg0) {

	        }

	    }
}
