package com.project.adpter;

import java.util.ArrayList;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.base.common.Common;
import com.gotye.api.GotyeUser;
import com.lidroid.xutils.exception.DbException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.open_demo.activity.ChatPage;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.FriendInfoBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_view.IMeApply;
import com.project.message_view.MessageActivity;
import com.project.myself_view.Constants;
import com.project.myself_view.MyselfInfoActivity;
import com.project.myself_view.PicDisplayActivity;
import com.utils.app.PointDistanceUtils;
import com.utils.app.TimeFormat;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.DeleteIwantPop;
import com.utils.widght.DeleteIwantPop.IApply;
import com.utils.widght.Expression;

public class NewIwantListAdapter extends BaseAdapter implements
		OnClickListener, OnItemClickListener {

	RequestQueue mQueue;
	ImageLoader imageLoader;

	public class Holder_Iwant {
		public TextView nicknameTv;
		public TextView contentTv;
		public TextView addressTv;
		public TextView propertyTv;
		public TextView home_iwant_time_tv;
		public NetworkImageView headIv;
		public Button home_iwant_satisfied_butt, home_iwant_tallk_butt;
		public RelativeLayout relativeLayout12;
		public RelativeLayout home_iwant_iteam_rl;
		public GridView imagelistGridView;
		public LinearLayout new_talk, new_ignore;
		public TableLayout image_list_table;
		public ImageView image, image1, image2, image3, image4, image5, image6,
				image7, image8, image9,sex_image;
	}

	LayoutInflater inflater;
	private ArrayList<IwantAndIhaveBean> list;
	private double dlatitude;
	private double dlongitude;
	private Context mContext;
	private int currentPosition;
	private boolean[] isApply;

	private IMeApply ImeApply;
	DisplayImageOptions options;
	private Iigonre iigonre;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public NewIwantListAdapter(Context mContext,
			ArrayList<IwantAndIhaveBean> list, IMeApply ImeApplys,Iigonre iigonre) {
		this.mContext = mContext;
		this.list = list;
		if (mContext!=null) {
		inflater = LayoutInflater.from(mContext);
		this.ImeApply = ImeApplys;
		this.iigonre=iigonre;
		mQueue = Volley.newRequestQueue(mContext);

		final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
				20);
		ImageCache imageCache = new ImageCache() {
			@Override
			public void putBitmap(String key, Bitmap value) {
				lruCache.put(key, value);
			}

			@Override
			public Bitmap getBitmap(String key) {
				return lruCache.get(key);
			}
		};
		imageLoader = new ImageLoader(mQueue, imageCache);
		options = InitDisplayImageOptions.getInstance().getOptions(true, true,
				0);
		}

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
		final Holder_Iwant holder_iwant;
		if (convertView == null) {
			Log.v("here", "iwant");
			holder_iwant = new Holder_Iwant();
			convertView = inflater.inflate(R.layout.view_mainlist_item, null);
			initView(holder_iwant, convertView, position);
			convertView.setTag(holder_iwant);

		} else {
			holder_iwant = (Holder_Iwant) convertView.getTag();
		}
		setListener(holder_iwant);
		setData(holder_iwant, position);
		holder_iwant.headIv.setDefaultImageResId(R.drawable.logo_logo);
		holder_iwant.headIv.setErrorImageResId(R.drawable.logo_logo);
		holder_iwant.headIv.setTag(position);
		holder_iwant.headIv.setImageUrl(list.get(position).getHeadimg(),
				imageLoader);

		return convertView;
	}

	/**
	 * @function 加载UI
	 * @time 2014-10-2下午5:03:31
	 * @param holder_iwant
	 * @param convertView
	 *            void
	 */
	public void initView(Holder_Iwant holder_iwant, View convertView,
			int position) {
		holder_iwant.home_iwant_satisfied_butt = (Button) convertView
				.findViewById(R.id.home_iwant_satisfied_butt);
		holder_iwant.home_iwant_tallk_butt = (Button) convertView
				.findViewById(R.id.home_iwant_tallk_butt);
		holder_iwant.headIv = (NetworkImageView) convertView
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
		holder_iwant.sex_image=(ImageView)convertView.findViewById(R.id.sex_image);

		// 新加时间
		holder_iwant.home_iwant_time_tv = (TextView) convertView
				.findViewById(R.id.home_iwant_time_tv);
		if (list.get(position).getPostImgs().size() != 0) {
			holder_iwant.imagelistGridView = (GridView) convertView
					.findViewById(R.id.imagelistGridView);
			holder_iwant.imagelistGridView.setVisibility(View.VISIBLE);
			holder_iwant.imagelistGridView.setTag(position);
		} else {
			holder_iwant.imagelistGridView = (GridView) convertView
					.findViewById(R.id.imagelistGridView);
			holder_iwant.imagelistGridView.setVisibility(View.GONE);
		}

		/** 新加聊聊，以及哦按钮 */
		holder_iwant.new_talk = (LinearLayout) convertView
				.findViewById(R.id.new_talk);
		holder_iwant.new_ignore = (LinearLayout) convertView
				.findViewById(R.id.new_ignore);
		holder_iwant.image = (ImageView) convertView.findViewById(R.id.image);
		holder_iwant.image1 = (ImageView) convertView.findViewById(R.id.image1);
		holder_iwant.image2 = (ImageView) convertView.findViewById(R.id.image2);
		holder_iwant.image3 = (ImageView) convertView.findViewById(R.id.image3);
		holder_iwant.image4 = (ImageView) convertView.findViewById(R.id.image4);
		holder_iwant.image5 = (ImageView) convertView.findViewById(R.id.image5);
		holder_iwant.image6 = (ImageView) convertView.findViewById(R.id.image6);
		holder_iwant.image7 = (ImageView) convertView.findViewById(R.id.image7);
		holder_iwant.image8 = (ImageView) convertView.findViewById(R.id.image8);
		holder_iwant.image9 = (ImageView) convertView.findViewById(R.id.image9);
		holder_iwant.image_list_table = (TableLayout) convertView
				.findViewById(R.id.image_list_table);
		// holder_iwant.imagelistGridView.setNumColumns(3);

	}

	/**
	 * @function 设置监听
	 * @time 2014-10-2下午5:05:22
	 * @param holder_iwant
	 *            void
	 */
	public void setListener(Holder_Iwant holder_iwant) {
		holder_iwant.headIv.setOnClickListener(this);
		holder_iwant.new_talk.setOnClickListener(this);
		holder_iwant.new_ignore.setOnClickListener(this);
		holder_iwant.image.setOnClickListener(this);
		holder_iwant.image1.setOnClickListener(this);
		holder_iwant.image2.setOnClickListener(this);
		holder_iwant.image3.setOnClickListener(this);
		holder_iwant.image4.setOnClickListener(this);
		holder_iwant.image5.setOnClickListener(this);
		holder_iwant.image6.setOnClickListener(this);
		holder_iwant.image7.setOnClickListener(this);
		holder_iwant.image8.setOnClickListener(this);
		holder_iwant.image9.setOnClickListener(this);
	}

	/**
	 * @param holder_iwant
	 * @param position
	 */
	public void setData(Holder_Iwant holder_iwant, int position) {
		currentPosition = position;
		holder_iwant.headIv.setTag(position);
		holder_iwant.new_talk.setTag(position);
		holder_iwant.new_ignore.setTag(position);
		holder_iwant.image.setTag(position);
		holder_iwant.image1.setTag(position);
		holder_iwant.image2.setTag(position);
		holder_iwant.image3.setTag(position);
		holder_iwant.image4.setTag(position);
		holder_iwant.image5.setTag(position);
		holder_iwant.image6.setTag(position);
		holder_iwant.image7.setTag(position);
		holder_iwant.image8.setTag(position);
		holder_iwant.image9.setTag(position);

		/**
		 * 满足button 显示不同的内容 status :0,表示可以满足 1,表示已经处理 2.表示已经处理
		 * 
		 * */
		holder_iwant.nicknameTv.setText(list.get(position).getNickname());
		holder_iwant.propertyTv.setText(list.get(position).getIdiograph());
		/*if (list.get(position).getContent().contains("#")) {
			StringBuffer temp = new StringBuffer();
			char[] content = list.get(position).getContent().toCharArray();
			TextView textView = new TextView(mContext);
			for (int i = 0; i < content.length; i++) {

				if (content[i] == '#') {
					textView.append(Expression
							.getCS(mContext, list.get(position).getContent()
									.substring(i, i + 3)));
					i = i + 2;
				} else {
					textView.append(temp.append(content[i]));
				}
			}

			holder_iwant.contentTv.setText(textView.getText());
		} else {
*/
			holder_iwant.contentTv.setText(Expression.getCS(mContext,list.get(position).getContent()));
		//}

		holder_iwant.addressTv.setText(PointDistanceUtils
				.getDistanceForKm(PointDistanceUtils.gps2m(SharePreferenceUtils
						.getIntance(mContext).getLocationLat(),
						SharePreferenceUtils.getIntance(mContext)
								.getLocationLng(), list.get(position).getLat(),
						list.get(position).getLng())));
		// // 设置不可以点
		if (list.get(position)
				.getUsername()
				.equals(SharePreferenceUtils.getIntance(mContext)
						.getCurrentUserName())) {
			SharePreferenceUtils.getIntance(mContext).saveCurrentIdio(
					list.get(position).getIdiograph());
			holder_iwant.home_iwant_iteam_rl.setEnabled(true);
			holder_iwant.home_iwant_iteam_rl.setClickable(true);
		} else {
			holder_iwant.home_iwant_iteam_rl.setEnabled(true);
			holder_iwant.home_iwant_iteam_rl.setClickable(true);
		}
		/**
		 * add time
		 * */
		if (list.get(position).getAddtime() != null) {
			String timeStr = TimeFormat.getAMorPMTime(list.get(position)
					.getAddtime());
			holder_iwant.home_iwant_time_tv.setText(timeStr);
		} else {
			holder_iwant.home_iwant_time_tv.setText(" ");
		}
		final String imgUrl = list.get(position).getHeadimg();
		holder_iwant.headIv.setTag(position);


		if (list.get(position).getSex().equals("1")) {
			holder_iwant.sex_image.setImageResource(R.drawable.new_sex_man);
			
		}else {
			
			holder_iwant.sex_image.setImageResource(R.drawable.new_sex_women);
		}
		
		switch (list.get(position).getPostImgs().size()) {
		case 0:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.GONE);
			break;
		case 1:
			holder_iwant.image.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
					.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image, options,
							animateFirstListener);
			holder_iwant.image_list_table.setVisibility(View.GONE);
			break;
		case 2:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.GONE);
			holder_iwant.image4.setVisibility(View.GONE);
			holder_iwant.image5.setVisibility(View.GONE);
			holder_iwant.image6.setVisibility(View.GONE);
			holder_iwant.image7.setVisibility(View.GONE);
			holder_iwant.image8.setVisibility(View.GONE);
			holder_iwant.image9.setVisibility(View.GONE);
			break;
		case 3:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(2).getImg_url(), holder_iwant.image3, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.VISIBLE);
			holder_iwant.image4.setVisibility(View.GONE);
			holder_iwant.image5.setVisibility(View.GONE);
			holder_iwant.image6.setVisibility(View.GONE);
			holder_iwant.image7.setVisibility(View.GONE);
			holder_iwant.image8.setVisibility(View.GONE);
			holder_iwant.image9.setVisibility(View.GONE);
			break;
		case 4:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(2).getImg_url(), holder_iwant.image3, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(3).getImg_url(), holder_iwant.image4, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.VISIBLE);
			holder_iwant.image4.setVisibility(View.VISIBLE);
			
			holder_iwant.image5.setVisibility(View.GONE);
			holder_iwant.image6.setVisibility(View.GONE);
			holder_iwant.image7.setVisibility(View.GONE);
			holder_iwant.image8.setVisibility(View.GONE);
			holder_iwant.image9.setVisibility(View.GONE);
			break;
		case 5:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(2).getImg_url(), holder_iwant.image3, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(3).getImg_url(), holder_iwant.image4, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(4).getImg_url(), holder_iwant.image5, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.VISIBLE);
			holder_iwant.image4.setVisibility(View.VISIBLE);
			holder_iwant.image5.setVisibility(View.VISIBLE);
			holder_iwant.image6.setVisibility(View.GONE);
			holder_iwant.image7.setVisibility(View.GONE);
			holder_iwant.image8.setVisibility(View.GONE);
			holder_iwant.image9.setVisibility(View.GONE);
			break;
		case 6:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(2).getImg_url(), holder_iwant.image3, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(3).getImg_url(), holder_iwant.image4, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(4).getImg_url(), holder_iwant.image5, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(5).getImg_url(), holder_iwant.image6, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.VISIBLE);
			holder_iwant.image4.setVisibility(View.VISIBLE);
			holder_iwant.image5.setVisibility(View.VISIBLE);
			holder_iwant.image6.setVisibility(View.VISIBLE);
			holder_iwant.image7.setVisibility(View.GONE);
			holder_iwant.image8.setVisibility(View.GONE);
			holder_iwant.image9.setVisibility(View.GONE);
			break;
		case 7:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(2).getImg_url(), holder_iwant.image3, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(3).getImg_url(), holder_iwant.image4, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(4).getImg_url(), holder_iwant.image5, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(5).getImg_url(), holder_iwant.image6, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(6).getImg_url(), holder_iwant.image7, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.VISIBLE);
			holder_iwant.image4.setVisibility(View.VISIBLE);
			holder_iwant.image5.setVisibility(View.VISIBLE);
			holder_iwant.image6.setVisibility(View.VISIBLE);
			holder_iwant.image7.setVisibility(View.VISIBLE);
			holder_iwant.image8.setVisibility(View.GONE);
			holder_iwant.image9.setVisibility(View.GONE);
			break;
		case 8:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(2).getImg_url(), holder_iwant.image3, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(3).getImg_url(), holder_iwant.image4, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(4).getImg_url(), holder_iwant.image5, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(5).getImg_url(), holder_iwant.image6, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(6).getImg_url(), holder_iwant.image7, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(7).getImg_url(), holder_iwant.image8, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.VISIBLE);
			holder_iwant.image4.setVisibility(View.VISIBLE);
			holder_iwant.image5.setVisibility(View.VISIBLE);
			holder_iwant.image6.setVisibility(View.VISIBLE);
			holder_iwant.image7.setVisibility(View.VISIBLE);
			holder_iwant.image8.setVisibility(View.VISIBLE);
			holder_iwant.image9.setVisibility(View.GONE);
			break;
		case 9:
			holder_iwant.image.setVisibility(View.GONE);
			holder_iwant.image_list_table.setVisibility(View.VISIBLE);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(0).getImg_url(), holder_iwant.image1, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(1).getImg_url(), holder_iwant.image2, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(2).getImg_url(), holder_iwant.image3, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(3).getImg_url(), holder_iwant.image4, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(4).getImg_url(), holder_iwant.image5, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(5).getImg_url(), holder_iwant.image6, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(6).getImg_url(), holder_iwant.image7, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(7).getImg_url(), holder_iwant.image8, options,
					animateFirstListener);
			com.nostra13.universalimageloader.core.ImageLoader.getInstance()
			.displayImage(list.get(position).getPostImgs().get(8).getImg_url(), holder_iwant.image9, options,
					animateFirstListener);
			holder_iwant.image1.setVisibility(View.VISIBLE);
			holder_iwant.image2.setVisibility(View.VISIBLE);
			holder_iwant.image3.setVisibility(View.VISIBLE);
			holder_iwant.image4.setVisibility(View.VISIBLE);
			holder_iwant.image5.setVisibility(View.VISIBLE);
			holder_iwant.image6.setVisibility(View.VISIBLE);
			holder_iwant.image7.setVisibility(View.VISIBLE);
			holder_iwant.image8.setVisibility(View.VISIBLE);
			holder_iwant.image9.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}

		/*
		 * imageLoader.displayImage( list.get(position).getHeadimg(),
		 * holder_iwant.headIv,
		 * InitDisplayImageOptions.getInstance().getHeadOptions(true, true, 20),
		 * InitDisplayImageOptions.getInstance().getImageLoadingListener());
		 * 
		 * GridView girGridView=holder_iwant.imagelistGridView; if
		 * (girGridView!=null) { girGridView.setPadding(0, 2, 0, 0);
		 * LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
		 * holder_iwant.imagelistGridView .getLayoutParams(); // 取控件mGrid当前的布局参数
		 * 
		 * if(list.get(position).getPostImgs().size()==0){ linearParams.height =
		 * 10;// 当控件的高强制设成75象素 linearParams.width = 10; }
		 * 
		 * else if (list.get(position).getPostImgs().size() == 1) {
		 * girGridView.setNumColumns(1); linearParams.height = 360;//
		 * 当控件的高强制设成75象素 linearParams.width = 360; } else if
		 * (list.get(position).getPostImgs().size() <= 4) {
		 * 
		 * linearParams.height = 4 * 90;// 当控件的高强制设成75象素 linearParams.width =
		 * 340; girGridView.setNumColumns(2); } else if
		 * (list.get(position).getPostImgs().size() > 4) { linearParams.width =
		 * 6 * 90 - 20;// 当控件的高强制设成75象素 if
		 * (list.get(position).getPostImgs().size() <= 6) { linearParams.height
		 * = 4 * 90 - 20; } else { linearParams.height = 6 * 90 - 20; }
		 * 
		 * girGridView.setNumColumns(3); }
		 * 
		 * 
		 * girGridView.setAdapter(new NewImageGridViewAdapter(
		 * list.get(position).getPostImgs(), mContext));
		 * girGridView.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件mGrid2
		 * girGridView.setOnItemClickListener(this); }
		 */

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.home_iwant_iteam_rl:

			break;
		case R.id.home_iwant_list_itean_head_img:
			currentPosition = (Integer) arg0.getTag();
			Intent myinfoIntent = new Intent(mContext, MyselfInfoActivity.class);
			myinfoIntent.putExtra("getuser", list.get(currentPosition)
					.getUsername());
			myinfoIntent.putExtra("nickname", list.get(currentPosition)
					.getNickname());
			myinfoIntent.putExtra("headimage", list.get(currentPosition)
					.getHeadimg());
			myinfoIntent.putExtra("ido", list.get(currentPosition)
					.getIdiograph());
			myinfoIntent.putExtra("from", true);
			mContext.startActivity(myinfoIntent);

			break;

		case R.id.new_talk:
			currentPosition = (Integer) arg0.getTag();
		/*	Intent intent = new Intent(mContext, MessageActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("username", list.get(currentPosition)
					.getUsername());
			bundle.putString("name", list.get(currentPosition).getNickname());
			bundle.putString("headimage", list.get(currentPosition)
					.getHeadimg());
			intent.putExtras(bundle);
			mContext.startActivity(intent);*/
			GotyeUser user=new GotyeUser(list.get(currentPosition)
					.getUsername());
			Intent intent = new Intent(mContext, ChatPage.class);
			intent.putExtra("user", user);
			intent.putExtra("username", list.get(currentPosition)
					.getUsername());
			FriendInfoBean friendsBean=new FriendInfoBean();
			friendsBean.setNickname(list.get(currentPosition)
					.getNickname());
			friendsBean.setHeadimage(list.get(currentPosition).getHeadimg());
			friendsBean.setUsername(list.get(currentPosition)
					.getUsername());
			friendsBean.setIdo(list.get(currentPosition).getIdiograph());
			try {
				SQLDatebaseUtils.getInstance(mContext).getDbUtils().save(friendsBean);
			} catch (DbException e) {
				e.printStackTrace();
			}
		
			mContext.startActivity(intent);
			break;
		case R.id.new_ignore:
			Log.v("here", "here");
			currentPosition = (Integer) arg0.getTag();
			DeleteIwantPop.startPopWin(mContext, arg0, new IApply() {
				
				@Override
				public void refuse(String postid) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void apply(int position) {
					iigonre.ignore(currentPosition);
				}
			}, currentPosition);
			
			break;
		case R.id.image:
			currentPosition = (Integer) arg0.getTag();
			Log.v("here", currentPosition+"");
			Intent image = new Intent(mContext, PicDisplayActivity.class);
			image.putExtra("img_index", 0);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image);
			break;
		case R.id.image1:
			currentPosition = (Integer) arg0.getTag();
			Intent image1 = new Intent(mContext, PicDisplayActivity.class);
			image1.putExtra("img_index", 0);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image1);
			break;
		case R.id.image2:
			currentPosition = (Integer) arg0.getTag();
			Intent image2= new Intent(mContext, PicDisplayActivity.class);
			image2.putExtra("img_index", 1);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image2);
			break;
		case R.id.image3:
			currentPosition = (Integer) arg0.getTag();
			Intent image3= new Intent(mContext, PicDisplayActivity.class);
			image3.putExtra("img_index", 2);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image3);
			break;
		case R.id.image4:
			currentPosition = (Integer) arg0.getTag();
			Intent image4= new Intent(mContext, PicDisplayActivity.class);
			image4.putExtra("img_index", 3);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image4);
			break;

		case R.id.image5:
			currentPosition = (Integer) arg0.getTag();
			Intent image5= new Intent(mContext, PicDisplayActivity.class);
			image5.putExtra("img_index", 4);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image5);
			break;
		case R.id.image6:
			currentPosition = (Integer) arg0.getTag();
			Intent image6= new Intent(mContext, PicDisplayActivity.class);
			image6.putExtra("img_index", 5);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image6);
			break;
		case R.id.image7:
			currentPosition = (Integer) arg0.getTag();
			Intent image7= new Intent(mContext, PicDisplayActivity.class);
			image7.putExtra("img_index", 6);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image7);
			break;
		case R.id.image8:
			currentPosition = (Integer) arg0.getTag();
			Intent image8= new Intent(mContext, PicDisplayActivity.class);
			image8.putExtra("img_index", 7);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image8);
			break;
		case R.id.image9:
			currentPosition = (Integer) arg0.getTag();
			Intent image9= new Intent(mContext, PicDisplayActivity.class);
			image9.putExtra("img_index", 8);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(image9);
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.v("hrere", "ssssss");
		if (list.get(currentPosition).getPostImgs().size() != 0) {
			Intent intent = new Intent(mContext, PicDisplayActivity.class);
			intent.putExtra("img_index", arg2);
			Constants.IMAGES_LIST = Constants.Images(list.get(currentPosition)
					.getPostImgs(), null, null, 3);
			mContext.startActivity(intent);
		}
	}
	
	
	public interface Iigonre{
		
		public void ignore(int position);
		
	}

}
