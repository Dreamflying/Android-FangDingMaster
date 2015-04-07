package com.project.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.IwantAndIhaveListBean;
import com.project.message_model.MessageBean;
import com.project.message_view.MessageActivity;
import com.project.myself_view.Constants;
import com.utils.chat.ChatService;
import com.utils.chat.ChatServiceUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;

public class HomeIhaveListAdapter extends BaseAdapter implements
		OnClickListener {

	public class holder_Iwant {
		public TextView nicknameTv;
		public TextView contentTv;
		public TextView addressTv;
		public TextView propertyTv;
		public ImageView headIv;
		public SeekBar peopleSb;
		public Button home_iwant_satisfied_butt, home_iwant_tallk_butt;
		public RelativeLayout relativeLayout12;
		public RelativeLayout home_iwant_iteam_rl;
		public TextView textView5, mMoreTv;// 期望人数
	}

	LayoutInflater inflater;
	private IwantAndIhaveListBean iwantAndIhaveListBean;
	private double dlatitude;
	private double dlongitude;
	private Context mContext;
	private int num1[];
	private int num2 = 0;
	private int currentPosition;
	private int mLastPosition = -1;
	holder_Iwant holder_iwant;

	public HomeIhaveListAdapter(Context mContext,
			IwantAndIhaveListBean iwantAndIhaveListBean) {
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
		this.iwantAndIhaveListBean = iwantAndIhaveListBean;
		num2 = this.iwantAndIhaveListBean.getArray().size();
		num1 = new int[this.iwantAndIhaveListBean.getArray().size()];
		for (int i = 0; i < iwantAndIhaveListBean.getArray().size(); i++) {
			num1[i] = 0;
		}
	}

	@Override
	public int getCount() {
		return iwantAndIhaveListBean.getArray().size();
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
		if (convertView == null) {

			Log.v("position", position + "");
			if (position == iwantAndIhaveListBean.getArray().size()) {
				holder_iwant = new holder_Iwant();
				convertView = inflater.inflate(R.layout.view_iwant_list_more,
						null);
				holder_iwant.mMoreTv = (TextView) convertView
						.findViewById(R.id.home_more_tv);
				if (iwantAndIhaveListBean.getArray().size() % 15 == 0
						&& iwantAndIhaveListBean.getArray().size() != 0) {
					holder_iwant.mMoreTv.setText("点击加载更多");

				} else {

					holder_iwant.mMoreTv.setText("暂无更多数据");
				}
				OnClickListener listener1 = new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (v == holder_iwant.mMoreTv) {

						}
					}
				};
				holder_iwant.mMoreTv.setOnClickListener(listener1);
				convertView.setTag(null);
			} else {
				currentPosition = position;

				holder_iwant = new holder_Iwant();
				convertView = inflater.inflate(R.layout.view_iwant_list_iteam,
						null);
				initView(holder_iwant, convertView);
				setData(holder_iwant, position);
				setListener(holder_iwant);
				convertView.setTag(holder_iwant);
				ImageLoader.getInstance().displayImage(
						Constants.IMAGES[position],
						holder_iwant.headIv,
						InitDisplayImageOptions.getInstance().getOptions(true,
								true, 20),
						InitDisplayImageOptions.getInstance()
								.getImageLoadingListener());
			}
		} else {
			holder_iwant = (holder_Iwant) convertView.getTag();

		}
		if (position != iwantAndIhaveListBean.getArray().size()) {
			if (num1[position] == 0)
				holder_iwant.relativeLayout12.setVisibility(View.GONE);
			else {
				AnimationSet as = new AnimationSet(true);
				AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
				aa.setDuration(300);
				as.addAnimation(aa);
				ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
				sa.setDuration(200);
				as.addAnimation(sa);
				// holder_iwant.relativeLayout12.setVisibility(View.VISIBLE);
				holder_iwant.relativeLayout12.startAnimation(as);
				holder_iwant.relativeLayout12.setVisibility(View.VISIBLE);
			}
		}

		holder_iwant.home_iwant_iteam_rl
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						if (position != iwantAndIhaveListBean.getArray().size()) {
							if (num1[position] == 0) {
								for (int i = 0; i < iwantAndIhaveListBean
										.getArray().size(); i++) {
									num1[i] = 0;
								}
								num1[position] = 1;
							} else {
								num1[position] = 0;
							}
							// 刷新一下布局
							notifyDataSetChanged();
						}

					}

				});
		return convertView;
	}

	/**
	 * @function 加载UI
	 * @time 2014-10-2下午5:03:31
	 * @param holder_iwant
	 * @param convertView
	 *            void
	 */
	public void initView(holder_Iwant holder_iwant, View convertView) {
		holder_iwant.home_iwant_satisfied_butt = (Button) convertView
				.findViewById(R.id.home_iwant_satisfied_butt);
		holder_iwant.home_iwant_tallk_butt = (Button) convertView
				.findViewById(R.id.home_iwant_tallk_butt);
		holder_iwant.headIv = (ImageView) convertView
				.findViewById(R.id.home_iwant_list_itean_head_img);
		holder_iwant.relativeLayout12 = (RelativeLayout) convertView
				.findViewById(R.id.dynamic_content_top_rly);
		holder_iwant.home_iwant_iteam_rl = (RelativeLayout) convertView
				.findViewById(R.id.home_iwant_iteam_rl);
		holder_iwant.nicknameTv = (TextView) convertView
				.findViewById(R.id.home_iwant_name_tv);
		holder_iwant.propertyTv = (TextView) convertView
				.findViewById(R.id.home_iwant_property_tv);
		holder_iwant.contentTv = (TextView) convertView
				.findViewById(R.id.home_iwant_content_tv);
		holder_iwant.addressTv = (TextView) convertView
				.findViewById(R.id.home_iwant_address_tv);
		holder_iwant.textView5 = (TextView) convertView
				.findViewById(R.id.textView5);
		holder_iwant.peopleSb = (SeekBar) convertView
				.findViewById(R.id.home_iwant_people_sb);

	}

	/**
	 * @function 设置监听
	 * @time 2014-10-2下午5:05:22
	 * @param holder_iwant
	 *            void
	 */
	public void setListener(holder_Iwant holder_iwant) {
		holder_iwant.home_iwant_satisfied_butt.setOnClickListener(this);
		holder_iwant.home_iwant_tallk_butt.setOnClickListener(this);
		// holder_iwant.home_iwant_iteam_rl.setOnClickListener(this);
		holder_iwant.headIv.setOnClickListener(this);
		holder_iwant.peopleSb.setOnClickListener(this);

	}

	public void setData(holder_Iwant holder_iwant, int position) {
		holder_iwant.nicknameTv.setText("test");
		holder_iwant.propertyTv.setText("test");
		holder_iwant.contentTv.setText("其实这是一个test");
		holder_iwant.addressTv.setText("test address");
		holder_iwant.textView5.setText("5");
		// // 设置不可以点
		holder_iwant.peopleSb.setEnabled(false);
		holder_iwant.peopleSb.setClickable(false);
		holder_iwant.peopleSb.setSelected(false);
		holder_iwant.peopleSb.setFocusable(false);
		holder_iwant.peopleSb.setMax(5);
		holder_iwant.peopleSb.setProgress(4);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.home_iwant_satisfied_butt:
			MessageBean newBean = new MessageBean();
			newBean.setUser(SharePreferenceUtils.getIntance(mContext)
					.getCurrentUserName());
			newBean.setToUser(iwantAndIhaveListBean.getArray()
					.get(currentPosition).getUsername());
			newBean.setToName(iwantAndIhaveListBean.getArray()
					.get(currentPosition).getNickname());
			newBean.setContent(iwantAndIhaveListBean.getArray()
					.get(currentPosition).getContent()
					+ ChatServiceUtils.APPLY);
			ChatService.sendChatMessage(newBean,
					false);
			break;
		case R.id.home_iwant_tallk_butt:
			Intent intent = new Intent(mContext, MessageActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("username",
					iwantAndIhaveListBean.getArray().get(currentPosition)
							.getUsername());
			bundle.putString("name",
					iwantAndIhaveListBean.getArray().get(currentPosition)
							.getNickname());
			intent.putExtras(bundle);
			mContext.startActivity(intent);
			break;
		case R.id.home_iwant_iteam_rl:

			break;
		case R.id.home_iwant_list_itean_head_img:

			break;
		case R.id.home_iwant_people_sb:

			break;
		default:
			break;
		}

	}
}