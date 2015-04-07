package com.project.iwant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
public class AboutUsLowerActivity extends Activity {
	private LinearLayout setup_return_btns;
	private TextView so_iteam_tv;
	// WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aboutlower_layout);
		setup_return_btns = (LinearLayout) this.findViewById(R.id.setup_return_btns);
		so_iteam_tv = (TextView) this.findViewById(R.id.so_iteam_tv);
		setup_return_btns.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		so_iteam_tv.setText("法律条款和免责声明");
		// webView = (WebView) findViewById(R.id.webview);
		// WebSettings wSet = webView.getSettings();
		// wSet.setJavaScriptEnabled(true);
		// webView.loadUrl("file:///android_asset/test.html");
	}

}
