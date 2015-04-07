package com.project.adpter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.message_view.CommentActivity;
import com.project.message_view.MessageActivity;
import com.project.myself_view.Constants;
import com.project.myself_view.MyselfInfoActivity;
import com.utils.app.TimestampTool;
import com.utils.chat.ChatServiceUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.Expression;
public class HomeMyListAdapter extends BaseAdapter {
	private Context mContext;
	LayoutInflater inflater;
	private List<MessageBean> list;
	private String applyTv;
	public static boolean []isComment=null;

	public HomeMyListAdapter(Context mContext, List<MessageBean> list) {
		this.list = list;
		this.mContext = mContext;
		isComment=new boolean[list.size()];
		inflater = LayoutInflater.from(mContext);
	}

	public class Holder {
		public ImageView mHeadIv;
		public TextView mNameTv, mContentTv, mTimeTv, mMessageTv;
		private RelativeLayout mGebiLly;
		public TextView mApplyTv;
		private RelativeLayout mBgRly, mApplyLly;
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
			convertView.setTag(holder);
			holder.mTimeTv.setVisibility(View.GONE);
			
		}else{
			
			holder=(Holder)convertView.getTag();
		}
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v == holder.mBgRly) {
					// 聊天界面
					if (isComment[position]) {
						Intent intent=new Intent(mContext,CommentActivity.class);
						mContext.startActivity(intent);
					}else {
						Intent intent=new Intent(mContext,MessageActivity.class);
						// 传递数据
						intent.putExtra("username", list.get(position).getToUser());
						intent.putExtra("name", list.get(position).getToName());
						mContext.startActivity(intent);
					}
				
					
				}
				if (v == holder.mHeadIv) {
					// 跳转到个人动态
					if (isComment[position]) {
						
					}else {
						Intent intent=new Intent(mContext,MyselfInfoActivity.class);
						intent.putExtra("getuser", list.get(position)
								.getToUser());
						intent.putExtra("headimage", list.get(position)
								.getHeadImage());
						intent.putExtra("nickname", list.get(position)
								.getToName());
						intent.putExtra("ido", " ");
						intent.putExtra("from", true);
						mContext.startActivity(intent);
					}
					
				}
			}
		};
		//holder.mBgRly.setOnClickListener(listener);
		holder.mHeadIv.setOnClickListener(listener);
		convertView.setTag(holder);
		ImageLoader.getInstance()
		.displayImage(
				list.get(position).getHeadImage(),
				holder.mHeadIv,
				InitDisplayImageOptions.getInstance().getHeadOptions(true,
						true, 20),
				InitDisplayImageOptions.getInstance()
						.getImageLoadingListener());
		holder.mNameTv.setText(list.get(position).getToName());
		String[] contentString=  list.get(position).getContent().replace(ChatServiceUtils.APPLY,"").replace(ChatServiceUtils.HAN, "").split("@");
		if (contentString[0].contains("#")) {
			StringBuffer temp=new StringBuffer();
			char []content=contentString[0].toCharArray();
			TextView textView=new TextView(mContext);
			for (int i = 0; i < content.length; i++) {
				
				if (content[i]=='#') {
					textView.append(Expression.getCS(mContext,contentString[0].substring(i, i+3)));
					i=i+2;
				}else {
					textView.append(temp.append(content[i]));
				}
			}
			
			 
			holder.mContentTv.setText(textView.getText());
		}else {
			
			holder.mContentTv.setText(contentString[0]);
		}
		
		if (list.get(position).getContent().equals("comment")) {
			isComment[position]=true;
			holder.mContentTv.setText("您有了新的评论");
			int num=SQLDatebaseUtils.getInstance(mContext).getUnReadCommentMsgNumForToUser(list.get(position).getUser());
			if (num==0) {
				holder.mMessageTv.setVisibility(View.GONE);
			}else {
				holder.mMessageTv.setVisibility(View.VISIBLE);
				holder.mMessageTv.setText(""+num);
			}
			
		}else {
			
		}
		holder.mTimeTv.setText(TimestampTool.getTimeString(list.get(position).getTime()));
		int num=SQLDatebaseUtils.getInstance(mContext).getUnReadMsgNumForToUser(list.get(position).getUser(), list.get(position).getToUser());
	    Log.v("NUM", num+"");
		if (num==0) {
			holder.mMessageTv.setVisibility(View.GONE);
		}else {
			holder.mMessageTv.setVisibility(View.VISIBLE);
			holder.mMessageTv.setText(""+num);
		}
		
			
		return convertView;
	}

	
}
