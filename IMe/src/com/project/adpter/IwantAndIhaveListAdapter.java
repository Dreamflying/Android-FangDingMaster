package com.project.adpter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v4.util.LruCache;
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
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.Cache.Entry;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.iwant_or_ihave_view.IMeApply;
import com.project.message_model.MessageBean;
import com.project.message_view.MessageActivity;
import com.project.myself_view.MyselfInfoActivity;
import com.utils.app.PointDistanceUtils;
import com.utils.app.TimeFormat;
import com.utils.app.UIHelper;
import com.utils.chat.ChatService;
import com.utils.chat.ChatServiceUtils;
import com.utils.http.BitmapCache;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.RoundImageView;
import com.utils.widght.RoundNetWorkImageView;

public class IwantAndIhaveListAdapter extends BaseAdapter implements
		OnClickListener {
	 //ImageLoader imageLoader = ImageLoader.getInstance(); 
	 RequestQueue mQueue;
	 ImageLoader imageLoader ;
	public class Holder_Iwant {
		public TextView nicknameTv;
		public TextView contentTv;
		public TextView addressTv;
		public TextView propertyTv;
		public TextView home_iwant_time_tv;
		public RoundNetWorkImageView headIv;
		public SeekBar peopleSb;
		public Button home_iwant_satisfied_butt, home_iwant_tallk_butt;
		public RelativeLayout relativeLayout12;
		public RelativeLayout home_iwant_iteam_rl;
		public TextView textView5, mMoreTv;// 期望人数
	}
   
	LayoutInflater inflater;
	private ArrayList<IwantAndIhaveBean> list;
	private double dlatitude;
	private double dlongitude;
	private Context mContext;
	private int num1[];
	private int num2 = 0;
	private int currentPosition;
	private int mLastPosition = -1;
	private boolean[] isApply;

	private IMeApply ImeApply;

	public IwantAndIhaveListAdapter(Context mContext,
			ArrayList<IwantAndIhaveBean> list, IMeApply ImeApplys) {
		this.mContext = mContext;
		this.list = list;
		inflater = LayoutInflater.from(mContext);
		this.ImeApply = ImeApplys;
		mQueue = Volley.newRequestQueue(mContext);
		
	final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20); 
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
	     imageLoader= new ImageLoader(mQueue, imageCache);
	
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
			currentPosition = position;
			holder_iwant = new Holder_Iwant();
			convertView = inflater
					.inflate(R.layout.view_iwant_list_iteam, null);
			initView(holder_iwant, convertView);
			
			convertView.setTag(holder_iwant);

		} else {
			holder_iwant = (Holder_Iwant) convertView.getTag();
		}
		setListener(holder_iwant);
		setData(holder_iwant, position);
		if (position != list.size()) {
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
						if (position != list.size()) {
							if (num1[position] == 0) {
								for (int i = 0; i < list.size(); i++) {
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
		
	
	/*	ImageRequest imageRequest = new ImageRequest(
				    list.get(position).getHeadimg(),
				    new Response.Listener() {

					@Override
					public void onResponse(Object arg0) {
						holder_iwant.headIv.setImageBitmap((Bitmap)arg0);
					}
				    }, 0, 0, Config.RGB_565, new Response.ErrorListener() {
				    @Override
				    public void onErrorResponse(VolleyError error) {
				    holder_iwant.headIv.setImageResource(R.drawable.logo_logo);
				    }
				    });
		imageRequest.setShouldCache(true);
		 mQueue.add(imageRequest);
	
		
			ImageListener listener = ImageLoader.getImageListener(holder_iwant.headIv,
					R.drawable.logo_logo, R.drawable.logo_logo);
			imageLoader.get( list.get(position).getHeadimg(), listener);*/
		 
		   holder_iwant.headIv.setDefaultImageResId(R.drawable.logo_logo);
		    holder_iwant.headIv.setErrorImageResId(R.drawable.logo_logo);
			holder_iwant.headIv.setTag(position);
	        holder_iwant.headIv.setImageUrl(list.get(position).getHeadimg(),imageLoader); 
	      
		return convertView;
	}

	/**
	 * @function 加载UI
	 * @time 2014-10-2下午5:03:31
	 * @param holder_iwant
	 * @param convertView
	 *            void
	 */
	public void initView(Holder_Iwant holder_iwant, View convertView) {
		holder_iwant.home_iwant_satisfied_butt = (Button) convertView
				.findViewById(R.id.home_iwant_satisfied_butt);
		holder_iwant.home_iwant_tallk_butt = (Button) convertView
				.findViewById(R.id.home_iwant_tallk_butt);
		holder_iwant.headIv = (RoundNetWorkImageView) convertView
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
		
		// 新加时间
		holder_iwant.home_iwant_time_tv=(TextView)convertView.findViewById(R.id.home_iwant_time_tv);

	}

	/**
	 * @function 设置监听
	 * @time 2014-10-2下午5:05:22
	 * @param holder_iwant
	 *            void
	 */
	public void setListener(Holder_Iwant holder_iwant) {
		holder_iwant.home_iwant_satisfied_butt.setOnClickListener(this);
		holder_iwant.home_iwant_tallk_butt.setOnClickListener(this);
		// holder_iwant.home_iwant_iteam_rl.setOnClickListener(this);
		holder_iwant.headIv.setOnClickListener(this);
		holder_iwant.peopleSb.setOnClickListener(this);

	}

	/**
	 * @param holder_iwant
	 * @param position
	 */
	public void setData(Holder_Iwant holder_iwant, int position) {
		holder_iwant.home_iwant_satisfied_butt.setTag(position);
		holder_iwant.home_iwant_tallk_butt.setTag(position);
		holder_iwant.headIv.setTag(position);
		
		/**
		 * 满足button 显示不同的内容 status :0,表示可以满足 1,表示已经处理 2.表示已经处理
		 * 
		 * */
	/*	if (list.get(position).getApply()==null) {
			return;
		}
		for (int i = 0; i < list.get(position).getApply().size(); i++) {
			Log.v("status", list.get(position).getApply().get(i).getStatus()+"");
			if (list.get(position).getApply().get(i).getStatus()==0) {
				holder_iwant.home_iwant_satisfied_butt.setText("已申请");
				holder_iwant.home_iwant_satisfied_butt.setClickable(true);
			}
			if (list.get(position).getApply().get(i).getStatus()==1) {
				Log.v("status111", list.get(position).getApply().get(i).getStatus()+"");
				holder_iwant.home_iwant_satisfied_butt.setText("已接受");
				holder_iwant.home_iwant_satisfied_butt.setClickable(false);
			}
			if (list.get(position).getApply().get(i).getStatus()==2) {
				Log.v("status222", list.get(position).getApply().get(i).getStatus()+"");
				holder_iwant.home_iwant_satisfied_butt.setText("拒绝");
				holder_iwant.home_iwant_satisfied_butt.setClickable(false);
			}
		}*/
		
		holder_iwant.nicknameTv.setText(list.get(position).getNickname());
		holder_iwant.propertyTv.setText(list.get(position).getIdiograph());
		holder_iwant.contentTv.setText(list.get(position).getContent());
		holder_iwant.addressTv.setText(PointDistanceUtils
				.getDistanceForKm(PointDistanceUtils.gps2m(SharePreferenceUtils
						.getIntance(mContext).getLocationLat(),
						SharePreferenceUtils.getIntance(mContext)
								.getLocationLng(), list.get(position).getLat(),
						list.get(position).getLng())));
		holder_iwant.textView5.setText( list.get(position).getAllpeople()+"");
		// // 设置不可以点
		if (list.get(position).getUsername().equals(SharePreferenceUtils.getIntance(mContext).getCurrentUserName())) {
			SharePreferenceUtils.getIntance(mContext).saveCurrentIdio(list.get(position).getIdiograph());
			holder_iwant.home_iwant_iteam_rl.setEnabled(false);
			holder_iwant.home_iwant_iteam_rl.setClickable(false);
		}else {
			holder_iwant.home_iwant_iteam_rl.setEnabled(true);
			holder_iwant.home_iwant_iteam_rl.setClickable(true);
		}
		holder_iwant.peopleSb.setEnabled(false);
		holder_iwant.peopleSb.setClickable(false);
		holder_iwant.peopleSb.setSelected(false);
		holder_iwant.peopleSb.setFocusable(false);
		/*holder_iwant.peopleSb.setMax(list.get(position).getAllpeople());
		holder_iwant.peopleSb.setProgress(list.get(position).getNowpeople());*/
		/**
		 * add time 
		 * */
		if (list.get(position).getAddtime()!=null) {
			String timeStr=TimeFormat.getAMorPMTime(list.get(position).getAddtime());
			holder_iwant.home_iwant_time_tv.setText(timeStr);
		}else {
			holder_iwant.home_iwant_time_tv.setText(" ");
		}
		final String imgUrl=list.get(position).getHeadimg();
		holder_iwant.headIv.setTag(position);
		/*imageLoader.displayImage(
				list.get(position).getHeadimg(),
				holder_iwant.headIv,
				InitDisplayImageOptions.getInstance().getHeadOptions(true,
						true, 20),
						InitDisplayImageOptions.getInstance().getImageLoadingListener());*/

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.home_iwant_satisfied_butt:
			isApply[currentPosition]=true;
			Button button=(Button)arg0;
			if (button.getText().toString().equals("已申请")) {
				UIHelper.ToastMessage(mContext, "已经申请,无法再次申请");
				
			}else{
				currentPosition = (Integer) arg0.getTag();
				MessageBean newBean = new MessageBean();
				newBean.setUser(SharePreferenceUtils.getIntance(mContext)
						.getCurrentUserName());
				newBean.setToUser(list.get(currentPosition).getUsername());
				newBean.setToName(list.get(currentPosition).getNickname());
				newBean.setContent(list.get(currentPosition).getContent()
						+ ChatServiceUtils.APPLY);
				ChatService.sendChatMessage(newBean,false);
				MeApply meApply = new MeApply();
				meApply.setTime(list.get(currentPosition).getAddtime());
				meApply.setContent(list.get(currentPosition).getContent());
				meApply.setPostsid(list.get(currentPosition).getId());
				meApply.setTouser(list.get(currentPosition).getUsername());
				meApply.setStatus(list.get(currentPosition).getStatus());
				meApply.setGetuser(SharePreferenceUtils.getIntance(mContext)
						.getCurrentUserName());
				ImeApply.applySatisfyDemand(meApply);	
				button.setText("已申请");
			}
				
			
			
			break;
		case R.id.home_iwant_tallk_butt:
			currentPosition = (Integer) arg0.getTag();
			Intent intent = new Intent(mContext, MessageActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("username", list.get(currentPosition)
					.getUsername());
			bundle.putString("name", list.get(currentPosition).getNickname());
			bundle.putString("headimage", list.get(currentPosition)
					.getHeadimg());
			intent.putExtras(bundle);
			mContext.startActivity(intent);
			break;
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
		case R.id.home_iwant_people_sb:

			break;
		default:
			break;
		}

	}

	public void setNumLength(int size) {
		isApply=new boolean[size];
		num2 = size;
		num1 = new int[size];
		for (int i = 0; i < size; i++) {
			num1[i] = 0;
		}

	}
}