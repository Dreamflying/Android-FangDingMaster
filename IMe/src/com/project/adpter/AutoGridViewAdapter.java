package com.project.adpter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.utils.os.InitDisplayImageOptions;

public class AutoGridViewAdapter extends BaseAdapter {
	private Context context;
	private List<Map<String, Object>> listItems;

	public AutoGridViewAdapter(Context context,
			List<Map<String, Object>> listItems) {

		this.context = context;
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		return listItems.size();
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
	public View getView(int arg0, View view, ViewGroup arg2) {
		Log.v("postion", arg0 + "");
		ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.cell, null);
			holder.imageView = (ImageView) view.findViewById(R.id.image1);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		ImageLoader.getInstance()
				.displayImage(
						(String) listItems.get(arg0).get("image"),
						holder.imageView,
						InitDisplayImageOptions.getInstance().getOptionsAuto(true,
								true, 0),
						InitDisplayImageOptions.getInstance()
								.getImageLoadingListener());
		return view;
	}

	private static class ViewHolder {

		ImageView imageView;
	}
}
