package com.utils.widght;

import com.project.iwant.R;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class VoicePop extends PopupWindow{
	private static VoicePop inputMessageView;
	private VoicePop() {

	}
	private PopupWindow pop;

	public static VoicePop getIntance() {
		if (inputMessageView == null) {
			inputMessageView = new VoicePop();
		}
		return inputMessageView;
	}
	public void showVoice(Context context ,View view){
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		 PopupWindow pop =null ;
		if (pop==null) {
			final View popupWindow = layoutInflater.inflate(
					R.layout.view_voice_pop, null);
			pop= new PopupWindow(view,
					300, 300,true);
			// 设置SelectPicPopupWindow弹出窗体可点击
			pop.setContentView(popupWindow);   
			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setOutsideTouchable(true);
			/*pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); 
			pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
	                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);*/
			//pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
			if (pop.isShowing()) {
				pop.dismiss();
			} else {
				int xoff = pop.getWidth()/2-view.getWidth()/2;
				pop.update();
				pop.showAsDropDown(view, -xoff, 20);
			}
			this.pop=pop;
		}
	}
	public void close(){
		
		if (pop!=null) {
			pop.dismiss();
			
		}
	}
	

}
