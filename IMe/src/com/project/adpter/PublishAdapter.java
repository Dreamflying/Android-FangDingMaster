package com.project.adpter;

import java.io.IOException;
import java.util.List;

import com.project.iwant.R;
import com.project.myself_view.PublishActivity;
import com.utils.os.BitmapTool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

public class PublishAdapter extends BaseAdapter {
	private Context context;
	LayoutInflater inflater;
	List<String> list;

	public PublishAdapter(Context context, List<String> list) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (list.size() >= 4) {
			return 4;
		} else {
			return list.size()+1;
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
			holder = new Holder();
			convertView = inflater.inflate(R.layout.dynamic_publish_gv, null);
			holder.mDeleteBtn = (Button) convertView
					.findViewById(R.id.dynamic_publish_gv_btn);
			holder.mShowImg = (ImageView) convertView
					.findViewById(R.id.dynamic_publish_gv_img);
			convertView.setTag(holder);
		if(position==list.size())
		{
			holder.mDeleteBtn.setVisibility(View.GONE);
			holder.mShowImg.setBackgroundResource(R.drawable.add_img);
		}else{
			holder.mDeleteBtn.setVisibility(View.VISIBLE);
			Bitmap bmp = null;
			try {
				bmp = BitmapTool.revitionImageSize(list.get(position));
			} catch (IOException e) {
				e.printStackTrace();
			}
			holder.mShowImg.setImageBitmap(bmp);
		}
		
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == holder.mDeleteBtn) {
					PublishActivity.deleteImage(position);
				}
				if (v == holder.mShowImg) {
					PublishActivity.imageChooseItem();
				}
			}
		};
		holder.mDeleteBtn.setOnClickListener(listener);
		holder.mShowImg.setOnClickListener(listener);
		convertView.setTag(holder);
		return convertView;
	}
	static class Holder {
		private Button mDeleteBtn;
		private ImageView mShowImg;
	}
}