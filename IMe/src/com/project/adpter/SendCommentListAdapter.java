package com.project.adpter;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.myself_model.Comment;
import com.project.myself_model.MyselfInfoComment;
import com.utils.app.TimeFormat;
import com.utils.app.TimestampTool;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.InputMessageView;
import com.utils.widght.InputMessageView.IsendMessage;

public class SendCommentListAdapter  extends BaseAdapter{
	private Context mContext;
	LayoutInflater inflater;
	private List<MyselfInfoComment> list;
	 boolean[] isComment=null;
	 private IsendMessage isendMessage;

	public SendCommentListAdapter(Context mContext,List<MyselfInfoComment> list,IsendMessage isendMessage) {
		this.list = list;
		this.mContext = mContext;
		isComment=new boolean[list.size()];
		inflater = LayoutInflater.from(mContext);
		this.isendMessage=isendMessage;
	}

	public class Holder {
		public ImageView mHeadIv;
		public TextView mNameTv, mContentTv, mTimeTv, mMessageTv;
		private RelativeLayout mGebiLly;
		public TextView mApplyTv,my_username,my_content;
		private RelativeLayout mBgRly, mApplyLly;
		private ImageView my_head_image;
		private Button comment_send;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint({ "InflateParams", "ViewHolder" })
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		final int mPosition = position ;
			// 加载评论
		if (convertView==null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.view_my_list_iteam, null);
			holder.mHeadIv = (ImageView) convertView
					.findViewById(R.id.my_list_item_img);
			holder.mNameTv = (TextView) convertView
					.findViewById(R.id.my_list_item_name);
			holder.mContentTv = (TextView) convertView
					.findViewById(R.id.my_list_item_content);
			holder.mTimeTv = (TextView) convertView
					.findViewById(R.id.my_list_item_time);
			holder.mMessageTv = (TextView) convertView
					.findViewById(R.id.home_my_liaoliao_text);
			holder.mBgRly = (RelativeLayout) convertView
					.findViewById(R.id.my_list_item_rlsb);
			holder.my_content = (TextView) convertView
					.findViewById(R.id.my_content);
			holder.comment_send=(Button)convertView.findViewById(R.id.comment_send);
			holder.my_username=(TextView)convertView.findViewById(R.id.my_username);
			convertView.setTag(holder);
			holder.my_head_image=(ImageView)convertView.findViewById(R.id.my_head_image);
			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (v == holder.mBgRly) {
						// 显示评论列表详情
					}
					if (v == holder.mHeadIv) {
						/*// 跳转到个人动态
						Intent intent=new Intent(mContext,MyselfInfoActivity.class);
						// 传递数据
						mContext.startActivity(intent);*/
					}
				}
			};
			//holder.mBgRly.setOnClickListener(listener);
			holder.mHeadIv.setOnClickListener(listener);
			convertView.setTag(holder);
		
		}else{
			
			holder=(Holder)convertView.getTag();
		}
		ImageLoader.getInstance()
		.displayImage(
				list.get(position).getHeadimg(),
				holder.mHeadIv,
				InitDisplayImageOptions.getInstance().getHeadOptions(true,
						true, 0),
				InitDisplayImageOptions.getInstance()
						.getImageLoadingListener());
		ImageLoader.getInstance()
		.displayImage(
				list.get(position).getActivityHeadimg(),
				holder.my_head_image,
				InitDisplayImageOptions.getInstance().getHeadOptions(true,
						true, 0),
				InitDisplayImageOptions.getInstance()
						.getImageLoadingListener());
		holder.mNameTv.setText(list.get(position).getName());
		holder.mContentTv.setText(list.get(position).getContent());
		holder.mTimeTv.setText(TimeFormat.timeToMMddhhmm(list.get(position).getTime()+""));
	    holder.my_content.setText(list.get(position).getActivityContent());
		
		int num=SQLDatebaseUtils.getInstance(mContext).getUnReadMsgNumForToUser(list.get(position).getUser(), list.get(position).getUser());
		if (num==0) {
			holder.mMessageTv.setVisibility(View.GONE);
		}else {
			holder.mMessageTv.setText(""+num);
		}
		holder.comment_send.setTag(position);
		holder.my_username.setText(list.get(position).getActivityNickname());
			
		holder.comment_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				int position1=(Integer)holder.comment_send.getTag();
				/*MessageBean messageBean=new MessageBean();
				messageBean.setContent(list.get(position).getContent());
				messageBean.setToUser(list.get(position).getUser());
				messageBean.setUser(SharePreferenceUtils.getIntance(mContext).getCurrentUserName());
				messageBean.setTime(list.get(position).getTime()+"");
				messageBean.setToName(SharePreferenceUtils.getIntance(mContext).getCurrentNickName());
				messageBean.setHeadImage(SharePreferenceUtils.getIntance(mContext).getHeadImgUserName());*/
				
				InputMessageView.getIntance().showInputMessageView(mContext,
						holder.my_content,list.get(position1),isendMessage,1);
				
			}
		});
		
		return convertView;
	}
}
