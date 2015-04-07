package com.project.adpter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_view.Constants;
import com.utils.app.TimeFormat;
import com.utils.os.InitDisplayImageOptions;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @description 评论列表的adapter
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-8下午7:46:15
 */
public class CommentListAdapter extends BaseAdapter {

	private ArrayList<MyselfInfoComment> commentList;
	private Context context;
	LayoutInflater inflater;

	public CommentListAdapter(Context context, ArrayList<MyselfInfoComment> commentList) {
		this.context = context;
		this.commentList = commentList;
		inflater = LayoutInflater.from(context);
	}

	public class Holder {
		public TextView mTitleTv, mTimeTv,mContentTv;
		public ImageView mImg;
		public Button weiyi;
		public RelativeLayout mBgRly;
	}

	@Override
	public int getCount() {
		return commentList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.view_content_comment_lv,
					null);
			holder.mTitleTv = (TextView) convertView
					.findViewById(R.id.dynamic_content_comment_title_tv);
			holder.weiyi = (Button) convertView.findViewById(R.id.weiyi);
			holder.mTimeTv = (TextView) convertView
					.findViewById(R.id.dynamic_content_comment_time_tv);
			holder.mImg = (ImageView) convertView
					.findViewById(R.id.dynamic_content_comment_img);
			holder.mBgRly = (RelativeLayout) convertView
					.findViewById(R.id.dynamic_comment_bg_rly);
			holder.mContentTv = (TextView) convertView
					.findViewById(R.id.dynamic_content_comment_content_tv);
			
			
			/*ImageLoader.getInstance()
			.displayImage(
					commentList.get(position).getHeadimg(),
					holder.mImg,
					InitDisplayImageOptions.getInstance().getHeadOptions(true,
							true, 20),
					InitDisplayImageOptions.getInstance()
							.getImageLoadingListener());*/
			convertView.setTag(holder);
		} else {

			holder = (Holder) convertView.getTag();
		}
		holder.mTitleTv.setText(commentList.get(position).getName()+":");
		holder.mContentTv.setText(commentList.get(position).getContent());
		Log.v("commentList", (commentList.get(position).getTime())+"");
		holder.mTimeTv.setText(TimeFormat.timeToMMddhhmm((commentList.get(position).getTime())+""));
		return convertView;
	}

}
