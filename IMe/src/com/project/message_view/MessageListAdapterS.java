package com.project.message_view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
import com.project.myself_view.Constants;
import com.utils.app.TimeFormat;
import com.utils.chat.ChatServiceUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.Expression;

/**
 * 聊天消息列表Adapter
 * 
 * @author daobo.yuan
 */
public class MessageListAdapterS extends BaseAdapter {

	class Holder {
		// 这里声明控件
		private TextView mLeftTv;
		private TextView mRightTv;
		private TextView mTimeTv;
		private RelativeLayout mTimeRly;
		private RelativeLayout mLeftRly;
		private RelativeLayout mRightRly;
		private ImageView mLeftHeadIv;
		private ImageView mRightHeadIv;
		private Button mLoadBtn;
	}

	LayoutInflater inflater;
	ArrayList<MessageBean> list;
	public String username;
	Context context;
	private Holder holder;
	public String headimage;
	private boolean message_from_where=true;
	

	public MessageListAdapterS(Context context, ArrayList<MessageBean> list,String headimage) {
		this.context = context;
		this.headimage=headimage;
		inflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// 自动生成的方法存根
		return null;
	}

	@Override
	public long getItemId(int position) {
		// 自动生成的方法存根
		return position;
	}

	@Override
	public boolean isEnabled(int position) {
		if (list.contains(getItem(position))) {
			return false;
		}
		return super.isEnabled(position);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final int mPosition;
		if (convertView != null) {
			holder = (Holder) convertView.getTag();
		} else {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.message_list_item_other,
					null);
			holder.mLeftTv = (TextView) convertView
					.findViewById(R.id.message_left_content_tv);
			holder.mTimeTv = (TextView) convertView
					.findViewById(R.id.message_time_tv); 
			holder.mRightTv = (TextView) convertView
					.findViewById(R.id.message_right_content_tv);
			holder.mTimeRly = (RelativeLayout) convertView
					.findViewById(R.id.message_time_rly);
			holder.mLeftRly = (RelativeLayout) convertView
					.findViewById(R.id.message_left_content_rly);
			holder.mRightRly = (RelativeLayout) convertView
					.findViewById(R.id.message_right_content_rly);
			holder.mLeftHeadIv = (ImageView) convertView
					.findViewById(R.id.message_left_head_iv);// 左边的头像
			holder.mRightHeadIv = (ImageView) convertView
					.findViewById(R.id.message_right_head_iv);// 右边的头像
			holder.mLoadBtn = (Button) convertView
					.findViewById(R.id.message_load_btn);// 查看更多按钮
			convertView.setTag(holder);
		}
		// 显示加载更多按钮
		mPosition = position;
		//Log.e("聊天判定", list.size() % 15 + "");
		if (position == 0 && list.size() % 15 == 0) {
			holder.mLoadBtn.setVisibility(View.VISIBLE);
		} else {
			holder.mLoadBtn.setVisibility(View.GONE);
		}
		// 显示时间
		holder.mTimeTv.setText(TimeFormat.getMessageTime(list.get(position).getTime()+""));
		if (mPosition == 0) {
			holder.mTimeRly.setVisibility(View.VISIBLE);
		} else {
		}
		Log.v("list.get(position).getStatus()", list.get(position).getStatus()+"");
		// 显示消息
		if (list.get(position).getStatus().equals("0")) {
			// 发送，右边
			holder.mLeftRly.setVisibility(View.GONE);
			holder.mRightRly.setVisibility(View.VISIBLE);
			String[] content=list.get(position).getContent().replace(ChatServiceUtils.APPLY, "").replace(ChatServiceUtils.HAN, "").split("@");
			holder.mRightTv.setText(Expression.getCS(context,content[0]));
			// 加载图片
			ImageLoader.getInstance()
			.displayImage(
					SharePreferenceUtils.getIntance(context).getHeadImgUserName(),
					holder.mRightHeadIv,
					InitDisplayImageOptions.getInstance().getHeadOptions(true,
							true, 20),
					InitDisplayImageOptions.getInstance()
							.getImageLoadingListener());
			//TODO 获取本地，或者网络图片
		} else {
			// 接受，左边
			holder.mLeftRly.setVisibility(View.VISIBLE);
			holder.mRightRly.setVisibility(View.GONE);
			holder.mLeftTv.setText(Expression.getCS(context,list.get(position).getContent().replace(ChatServiceUtils.APPLY, "").replace(ChatServiceUtils.HAN, "")));
			// 加载图片
			ImageLoader.getInstance()
			.displayImage(
					headimage,
					holder.mLeftHeadIv,
					InitDisplayImageOptions.getInstance().getHeadOptions(true,
							true, 20),
					InitDisplayImageOptions.getInstance()
							.getImageLoadingListener());
			//TODO 获取本地，或者网络图片
		}
		OnClickListener listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v == holder.mLoadBtn) {
					// 发送消息，提醒加载更多
					
					// 刷新页面
					
				}
			}
		};
		holder.mLoadBtn.setOnClickListener(listener);
		return convertView;
	}

	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<String> mImgBuffList = new ArrayList<String>();

	private boolean IsImg(String name) {
		for (int i = 0; i < mImgBuffList.size(); i++) {
			if (name.equals((String) mImgBuffList.get(i))) {
				return true;
			}
		}
		return false;
	}

	private void delImgBuff(String name) {
		for (int i = 0; i < mImgBuffList.size(); i++) {
			if (name.equals((String) mImgBuffList.get(i))) {
				mImgBuffList.remove(i);
				return;
			}
		}
	}

}