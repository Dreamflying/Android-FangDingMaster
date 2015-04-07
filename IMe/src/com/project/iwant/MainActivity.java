package com.project.iwant;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.waps.AppConnect;

import com.base.common.Common;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.add_view.AddActivity;
import com.project.iwant_or_ihave_view.IhaveFragment;
import com.project.iwant_or_ihave_view.IwantFragment;
import com.project.message_view.MessageActivity;
import com.project.myself_view.MyselfFragment;
import com.utils.app.ActivityManagerUtils;
import com.utils.chat.ChatService;
import com.utils.chat.ChatServiceUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;

/**
 * @description app 主页面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-2上午9:58:57
 */
public class MainActivity extends FragmentActivity implements OnClickListener {
	private ViewPager vp_main;
	private TextView tv_iwant;
	private TextView tv_ihave;
	private TextView tv_myself;
	private static TextView tv_msgnum;
	private FragAdapter adapter;
	private ImageView iv_head;
	private int currIndex = 1;// 当前页卡编号
	private int ivCursorWidth;// 动画图片宽度
	private int tabWidth;// 每个tab头的宽度
	private int offsetX;// tab头的宽度减去动画图片的宽度再除以2（保证动画图片相对tab头居中）
	private Button btn_search, btn_add, btn_more;
	private static Context context;
	public static Handler mMainControl = new Handler() {
		Message msg1;

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ChatServiceUtils.PUBLISH_DYNAMIC_STATIC_CHANGE:
				setHomeMyAllTv("1");
				long num =SQLDatebaseUtils.getInstance(context).getUnReadMsgNum(SharePreferenceUtils.getIntance(context).getCurrentUserName())+SQLDatebaseUtils.getInstance(context).getUnReadCommentMsgNumForToUser(SharePreferenceUtils.getIntance(context).getCurrentUserName());
				Log.v("NUM", ""+num);
				setHomeMyAllTv(num + "");
				break;
			case ChatServiceUtils.PUBLISH_MAIN_TEXT:
				setHomeMyAllTv("0");
				break;
			default:
				break;
			}

		}

	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*添加第三方*/
		AppConnect.getInstance(this);
		ChatServiceUtils.isMainActivityPage=true;
		ActivityManagerUtils.getIntance().addActivity("MainActivity",
				MainActivity.this);
		context=MainActivity.this;
		setContentView(R.layout.activity_main);
		/**开启chat服务*/
		
	Intent intent=new Intent(MainActivity.this,ChatService.class) ;
	startService(intent);
		initView();
		setListener();
		initViewPage();
		initImageView();
	}

	/**
	 * @function 注册监听事件
	 * @time 2014-10-2上午11:05:43 void
	 */
	private void setListener() {
		tv_iwant.setOnClickListener(this);
		tv_ihave.setOnClickListener(this);
		tv_myself.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		btn_add.setOnClickListener(this);
		btn_more.setOnClickListener(this);
		vp_main.setOnPageChangeListener(new MyVPageChangeListener());
	}

	/**
	 * @function 装载UI ，获取Id
	 * @time 2014-10-2上午11:05:58 void
	 */
	private void initView() {
		tv_iwant = (TextView) findViewById(R.id.tv_iwant);
		tv_ihave = (TextView) findViewById(R.id.tv_ihave);
		tv_myself = (TextView) findViewById(R.id.tv_myself);
		btn_search = (Button) findViewById(R.id.main_search);
		btn_add = (Button) findViewById(R.id.main_submit);
		btn_more = (Button) findViewById(R.id.main_more);
		vp_main = (ViewPager) findViewById(R.id.vp_main);
		iv_head = (ImageView) findViewById(R.id.cursor);
		tv_msgnum=(TextView)findViewById(R.id.home_myall_text);
		setHomeMyAllTv("1");
		long num =SQLDatebaseUtils.getInstance(MainActivity.this).getUnReadMsgNum(SharePreferenceUtils.getIntance(MainActivity.this).getCurrentUserName())+SQLDatebaseUtils.getInstance(MainActivity.this).getUnReadCommentMsgNumForToUser(SharePreferenceUtils.getIntance(MainActivity.this).getCurrentUserName());
		Log.v("NUM", ""+num);
		setHomeMyAllTv(num + "");
	}

	/**
	 * @function 初始化 view page的相关数据
	 * @time 2014-10-2上午11:06:12 void
	 */
	private void initViewPage() {
		/*List<Fragment> fragments = new ArrayList<Fragment>();
		fragments.add(IwantFragment.newInstance("Iwant"));
		fragments.add(IhaveFragment.newInstance("Ihave"));
		fragments.add(MyselfFragment.newInstance("Myself"));
		adapter = new FragAdapter(getSupportFragmentManager(), fragments);
		vp_main.setAdapter(adapter);
		vp_main.setCurrentItem(0);
		changeTextColor(0);*/

	}

	/**
	 * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
	 **/

	private void initImageView() {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int screenW = dm.widthPixels;// 获取分辨率宽度
		ivCursorWidth = BitmapFactory.decodeResource(getResources(),
				R.drawable.blue_drop).getWidth();// 获取图片宽度
		tabWidth = screenW / 3;
		if (ivCursorWidth > tabWidth) {
			iv_head.getLayoutParams().width = tabWidth;
			ivCursorWidth = tabWidth;
		}
		offsetX = (tabWidth - ivCursorWidth) / 2;
	}

	
	/*
	 * 显示我的列表上面的红色数字
	 */
	public static  void setHomeMyAllTv(String mStr) {
		if (mStr.equals("0")) {
			SharePreferenceUtils.getIntance(context).saveUnLookMessageFlag(0);
			tv_msgnum.setVisibility(View.GONE);
		} else {
			int num = SharePreferenceUtils.getIntance(context).getUnLookMessageFlag();
			if(num==0){
				tv_msgnum.setVisibility(View.GONE);
			}else{
				tv_msgnum.setVisibility(View.VISIBLE);
				tv_msgnum.setText(num + "");
			}
		}
	}
	

	private class MyVPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int location) {
			Animation animation = new TranslateAnimation(tabWidth * currIndex
					+ offsetX, tabWidth * location + offsetX, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = location;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(350);
			iv_head.startAnimation(animation);

		}

	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.tv_iwant:
			vp_main.setCurrentItem(0);
			break;
		case R.id.tv_ihave:
			vp_main.setCurrentItem(1);
			break;
		case R.id.tv_myself:
			vp_main.setCurrentItem(2);
			break;
		case R.id.main_more:
			MorePopUpWindow.getIntance().showMorePopWindow(MainActivity.this,
					arg0);
			break;
		case R.id.main_search:
			Intent searIntent = new Intent(MainActivity.this,
					SearchActivity.class);
			startActivity(searIntent);
			break;
		case R.id.main_submit:
			Intent intent = new Intent(MainActivity.this, AddActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	/**
	 * @function 改变选择状态
	 * @time 2014-10-2上午11:06:30
	 * @param location
	 *            void
	 */
	private void changeTextColor(int location) {
		switch (location) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ChatServiceUtils.isMainActivityPage=false;
		AppConnect.getInstance(this).close();
		  ImageLoader.getInstance().clearMemoryCache();  
		  ImageLoader.getInstance().clearDiskCache();  
	}
	
	@Override
	protected void onResume() {
		ChatServiceUtils.isMainActivityPage=true;
		super.onResume();
	}
	
 @Override
protected void onPause() {
	 ChatServiceUtils.isMainActivityPage=false;
	super.onPause();
}
 @Override
protected void onStop() {
	 ChatServiceUtils.isMainActivityPage=false;
	super.onPause();
}
}
