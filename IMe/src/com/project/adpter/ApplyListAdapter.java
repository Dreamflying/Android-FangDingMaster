package com.project.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.MeApplyList;
import com.project.message_view.MessageActivity;
import com.project.myself_view.ApplyHandlerPPW;
import com.project.myself_view.MyselfInfoActivity;
import com.project.myself_view.ApplyHandlerPPW.IApply;
import com.project.myself_view.Constants;
import com.utils.os.InitDisplayImageOptions;

public class ApplyListAdapter extends BaseAdapter {
	private Context context;
	LayoutInflater inflater;
	private MeApplyList list;
	private int num1[];
	private int num2 = 0;
	private IApply iApply;

	public ApplyListAdapter(Context context, MeApplyList list,	IApply iApply) {
		this.iApply=iApply;
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		num2 = this.list.getArray().size();
		num1 = new int[this.list.getArray().size()];
		for (int i = 0; i < this.list.getArray().size(); i++) {
			num1[i] = 0;
		}
	}

	public class Holder {
		public TextView mNameTv, mContentTv;
		public ImageView mHeadIv;
		public RelativeLayout mBgBtn;
		public Button mliaoliaoBtn, mChuliBtn;
		private RelativeLayout mAllBgBtn;
	}

	@Override
	public int getCount() {
		return this.list.getArray().size();
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
		Log.v("apply", position+"");
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.apply_list_item, null);
			holder.mNameTv = (TextView) convertView
					.findViewById(R.id.home_my_sq_item_name);
			holder.mContentTv = (TextView) convertView
					.findViewById(R.id.home_my_sq_item_content);
			holder.mHeadIv = (ImageView) convertView
					.findViewById(R.id.home_my_sq_item_head_img);
			holder.mBgBtn = (RelativeLayout) convertView
					.findViewById(R.id.home_my_sq_item_rl2);
			holder.mliaoliaoBtn = (Button) convertView
					.findViewById(R.id.home_my_sq_item_tallk);
			holder.mChuliBtn = (Button) convertView
					.findViewById(R.id.home_my_sq_item_iwant);
			holder.mAllBgBtn = (RelativeLayout) convertView
					.findViewById(R.id.home_iwant_iteam_rl);
			holder.mChuliBtn.setTag(position);
			holder.mliaoliaoBtn.setTag(position);
			holder.mAllBgBtn.setTag(position);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.mNameTv.setText(list.getArray().get(position).getNickname());
		holder.mContentTv.setText(list.getArray().get(position).getContent());
		ImageLoader.getInstance()
		.displayImage(
				list.getArray().get(position).getHeadimg(),
				holder.mHeadIv,
				InitDisplayImageOptions.getInstance().getHeadOptions(true,
						true, 20),
				InitDisplayImageOptions.getInstance()
						.getImageLoadingListener());
		if (position != list.getArray().size()) {
			if (num1[position] == 0)
				holder.mBgBtn.setVisibility(View.GONE);
			else {
				AnimationSet as = new AnimationSet(true);
				AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
				aa.setDuration(300);
				as.addAnimation(aa);
				ScaleAnimation sa = new ScaleAnimation(1.0f, 1.0f, 0.0f,
						1.0f);
				sa.setDuration(200);
				as.addAnimation(sa);
				holder.mBgBtn.startAnimation(as);
				holder.mBgBtn.setVisibility(View.VISIBLE);
			}
		}
		holder.mliaoliaoBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, MessageActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("username", list.getArray().get(position)
						.getGetuser());
				bundle.putString("name",list.getArray().get(position).getNickname());
				bundle.putString("headimage", list.getArray().get(position)
						.getHeadimg());
				intent.putExtras(bundle);
				context.startActivity(intent);
				
			}
			
		});
		holder.mHeadIv.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent myinfoIntent = new Intent(context, MyselfInfoActivity.class);
				myinfoIntent.putExtra("getuser", list.getArray().get(position)
						.getUsername());
				myinfoIntent.putExtra("nickname",  list.getArray().get(position)
						.getNickname());
				myinfoIntent.putExtra("headimage",  list.getArray().get(position)
						.getHeadimg());
				myinfoIntent.putExtra("ido",  " ");
				myinfoIntent.putExtra("from", true);
				context.startActivity(myinfoIntent);
			}
		});
		
		holder.mAllBgBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				if (position != list.getArray().size()) {
					if (num1[position] == 0) {
						for (int i = 0; i < list.getArray().size(); i++) {
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
		holder.mChuliBtn.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				int index=(Integer)holder.mChuliBtn.getTag();
				ApplyHandlerPPW.startPopWin(context, arg0,  list.getArray().get(index).getId()+"",list.getArray().get(index).getPostsid()+"",
						list.getArray().get(index).getGetuser(), list.getArray().get(index).getContent(), "nickname",iApply);
			}
			
		});
	
		return convertView;
	}

	public static int getTotalHeightofListView(ListView listView) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null) {
			return 0;
		}
		int totalHeight = 0; 
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View mView = mAdapter.getView(i, null, listView);
			mView.measure(
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
					MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			totalHeight += mView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (mAdapter.getCount() - 1));
		listView.setLayoutParams(params);
		listView.requestLayout();
		return totalHeight;
	}

}
