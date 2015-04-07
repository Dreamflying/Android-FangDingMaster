package com.project.myself_view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.project.iwant.R;
import com.utils.app.PhotoUtils;

public class PhotoPopupWindow extends PopupWindow{
	

		public PhotoPopupWindow(Context mContext, View parent,final IPhotoChoice iPhotoChoice) {

			View view = View
					.inflate(mContext, R.layout.view_item_photo_popupwindows, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.fade_ins));
			LinearLayout ll_popup = (LinearLayout) view
					.findViewById(R.id.ll_popup);
			ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();
			Button item_popupwindows_camera = (Button) view
					.findViewById(R.id.item_popupwindows_camera);
			Button item_popupwindows_Photo = (Button) view
					.findViewById(R.id.item_popupwindows_Photo);
			Button item_popupwindows_cancel = (Button) view
					.findViewById(R.id.item_popupwindows_cancel);
			item_popupwindows_camera.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					iPhotoChoice.choiceTakePhotos();
					dismiss();
				}
			});
			item_popupwindows_Photo.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					iPhotoChoice.choiceLocalPhotos();
					dismiss();
				}
			});
			item_popupwindows_cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}

		
		
		
		
		
}
