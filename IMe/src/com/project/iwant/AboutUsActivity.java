package com.project.iwant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.waps.AppConnect;

import com.base.view.BaseActivity;

public class AboutUsActivity  extends BaseActivity{
	private LinearLayout setup_return_btns,update_app;
	private TextView so_iteam_tv, about_us_vorsionsb, aboutue_testvorsion, about_us_mail, about_us_vorsion2, about_us_lower,
			about_us_vorsion3;
	public AboutUsActivity() {
		super("");
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutus);
		initConfiguration(AboutUsActivity.this,"AboutUsActivity");
		setup_return_btns = (LinearLayout) this.findViewById(R.id.setup_return_btns);
		update_app=(LinearLayout)this.findViewById(R.id.update_app);
		about_us_lower = (TextView) this.findViewById(R.id.about_us_lower);
		so_iteam_tv = (TextView) this.findViewById(R.id.so_iteam_tv);
		so_iteam_tv.setText("关于我们");
		about_us_vorsionsb = (TextView) this.findViewById(R.id.about_us_vorsionsb);
		aboutue_testvorsion = (TextView) this.findViewById(R.id.aboutue_testvorsion);
		about_us_mail = (TextView) this.findViewById(R.id.about_us_mail);
		about_us_vorsion2 = (TextView) this.findViewById(R.id.about_us_vorsion2);
		about_us_lower = (TextView) this.findViewById(R.id.about_us_lower);
		about_us_vorsion3 = (TextView) this.findViewById(R.id.about_us_vorsion3);
		setup_return_btns.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		about_us_lower.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AboutUsActivity.this, AboutUsLowerActivity.class);
				startActivity(intent);
			}
		});
		
		update_app.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AppConnect.getInstance(AboutUsActivity.this).checkUpdate(AboutUsActivity.this);
			}
		});
		}
	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
	}
	
}
