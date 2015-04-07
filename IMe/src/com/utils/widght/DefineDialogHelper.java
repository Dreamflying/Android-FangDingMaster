package com.utils.widght;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.iwant.R;

/**
 * @author liyuan
 * @Description 
 * @Email 1522651962@qq.com
 * @date 2014年8月6日下午6:04:49
 */
public class DefineDialogHelper extends Dialog {

	public DefineDialogHelper(Context context, int theme) {
		super(context, theme);
	}
	
	/**创建对话框
	 *2014年8月6日下午6:06:57
	 * @param context
	 * @return
	 */
	public static DefineDialogHelper createDialog(Context context) {
		DefineDialogHelper dialog = new DefineDialogHelper(context, R.style.SimpleHUD);
		dialog.setContentView(R.layout.view_definedialog);
		dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return dialog;
	}

	public void setMessage(String message) {
		TextView msgView = (TextView)findViewById(R.id.simplehud_message);
		msgView.setText(message);
	}
	
	public void setImage(Context ctx, int resId) {
		ImageView image = (ImageView)findViewById(R.id.simplehud_image);
		image.setImageResource(resId);
		
		if(resId==R.drawable.simplehud_spinner) {
			Animation anim = AnimationUtils.loadAnimation(ctx, R.anim.progressbar);  
			anim.start();
			image.startAnimation(anim);
		}
	}

}
