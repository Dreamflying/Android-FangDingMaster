package com.project.adpter;


import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.GetPostImageBean;
import com.utils.os.InitDisplayImageOptions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class NewImageGridViewAdapter extends BaseAdapter {
	LayoutInflater inflater;
	private ArrayList<GetPostImageBean> images;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	public class HoldView{
		
		public ImageView imageView;
	}
     	
	public NewImageGridViewAdapter(ArrayList<GetPostImageBean> images,Context context){
		this.images=images;
		this.inflater=LayoutInflater.from(context);
		options=InitDisplayImageOptions.getInstance().getOptions(true, true, 0);
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return images.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		HoldView holdView;
		if (arg1==null) {
			holdView=new HoldView();
			arg1=inflater.inflate(R.layout.view_newimageviewadapter_item, null);
			holdView.imageView=(ImageView)arg1.findViewById(R.id.imageitem);
			arg1.setTag(holdView);
		}else {
			holdView=(HoldView)arg1.getTag();
		}
		if (images.size()==1) {
			 LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)  holdView.imageView.getLayoutParams();
			    params.height=360;  
			    params.width =360;  
			    holdView.imageView.setLayoutParams(params);  
			
		}
		ImageLoader.getInstance().displayImage(
				images.get(arg0).getImg_url()
						,   holdView.imageView, options,
				animateFirstListener);
		
		
		return arg1;
	}

}
