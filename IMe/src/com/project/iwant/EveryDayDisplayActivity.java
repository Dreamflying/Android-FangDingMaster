package com.project.iwant;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.common.Common;
import com.base.view.BaseActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.login_view.LoginActivity;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;

public class EveryDayDisplayActivity extends BaseActivity {
	private ImageView mShowPicture;
	private TextView mShowText;
	private int recLen = 4;
	Timer timer = new Timer();

	public EveryDayDisplayActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_everdaydisplay);
		initConfiguration(EveryDayDisplayActivity.this,"EveryDayDisplayActivity");
		mShowPicture = (ImageView) findViewById(R.id.guide_picture);
		mShowText = (TextView) findViewById(R.id.guide_content);
		ImageLoader.getInstance()
				.displayImage(
						Common.URL_IMG + "everyday.png",
						mShowPicture,
						InitDisplayImageOptions.getInstance().getFirstOptions(
								true, true, 0),
						InitDisplayImageOptions.getInstance()
								.getImageLoadingListener());
		if (SharePreferenceUtils.getIntance(EveryDayDisplayActivity.this)
				.getFirstShowOpen()) {
			mShowText.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (SharePreferenceUtils.getIntance(EveryDayDisplayActivity.this)
							.getCurrentUserName().equals("default")) {
						Intent intent = new Intent(EveryDayDisplayActivity.this,
								LoginActivity.class);
						startActivity(intent);
						finish();
					} else {
						Intent intent = new Intent(EveryDayDisplayActivity.this,
								NewMainTabActivity.class);
						startActivity(intent);
						finish();

					}
				}
			});
			
			///timer.schedule(task, 1000, 1000);
		} else {
			if (SharePreferenceUtils.getIntance(EveryDayDisplayActivity.this)
					.getCurrentUserName().equals("default")) {
				Intent intent = new Intent(EveryDayDisplayActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			} else {
				Intent intent = new Intent(EveryDayDisplayActivity.this,
						NewMainTabActivity.class);
				startActivity(intent);
				finish();

			}

		}
		// timeTask

		/*
		 * new Handler().postDelayed(new Runnable() {
		 * 
		 * @Override public void run() {
		 * 
		 * }
		 * 
		 * }, 3000);
		 */

	}
	
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		
	}
	

	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			recLen--;
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				mShowText.setText("" + recLen);
				if (recLen <= 0) {
					timer.cancel();
					mShowText.setVisibility(View.GONE);
					if (SharePreferenceUtils.getIntance(EveryDayDisplayActivity.this)
							.getCurrentUserName().equals("default")) {
						Intent intent = new Intent(EveryDayDisplayActivity.this,
								LoginActivity.class);
						startActivity(intent);
						finish();
					} else {
						Intent intent = new Intent(EveryDayDisplayActivity.this,
								MainActivity.class);
						startActivity(intent);
						finish();

					}

				}
			}
		}
	};

}
