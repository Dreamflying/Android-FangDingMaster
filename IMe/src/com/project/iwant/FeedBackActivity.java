package com.project.iwant;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.view.BaseActivity;

/**
 *  @description 反馈界面
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-3上午11:44:02
 */
public class FeedBackActivity extends BaseActivity{
	private LinearLayout setup_return_btn;
	private TextView so_iteam_tv;
	private EditText feelback_content_edit;
	private EditText feelback_message_btn;
	private Button feelback_putin_btn;
	

	public FeedBackActivity() {
		super("反馈");
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_feedbadk);
	    initView();
	    setListener();
	}
	@Override
	public void initView() {
		setup_return_btn = (LinearLayout) this
				.findViewById(R.id.setup_return_btns);
		so_iteam_tv = (TextView) this.findViewById(R.id.so_iteam_tv);
		so_iteam_tv.setText("意见反馈");

		feelback_content_edit = (EditText) this
				.findViewById(R.id.feelback_content_edit);
		feelback_message_btn = (EditText) this
				.findViewById(R.id.feelback_message_btn);
		feelback_putin_btn = (Button) this
				.findViewById(R.id.feelback_putin_btn);
		
	}
	
	@Override
	public void setListener() {
		
		setup_return_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});

		feelback_putin_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

			}
		});
	}
}
