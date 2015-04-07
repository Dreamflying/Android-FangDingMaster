package com.project.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.myself_model.NearByInfoListBean;
import com.project.myself_view.Constants;
import com.project.myself_view.PicDisplayActivity;
import com.utils.app.TimeFormat;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.InputMessageView;
import com.utils.widght.SrollViewListView;
import com.utils.widght.InputMessageView.IsendMessage;

public class NearByInfoListAdapter extends BaseAdapter {
	
	private LayoutInflater inflater;
	String [] imageUrls;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context context;
	private NearByInfoListBean nearByInfoListBean;
	private IsendMessage isendMessage;
	private static class ViewHolder {
		TextView text;
		ImageView image;
		ImageView image1;
		ImageView image2;
		ImageView image3;
		ImageView image4 ;
		TableLayout image_list_table;
		SrollViewListView lv_comment;
		LinearLayout linearLayout;
		TextView dynamic_content_day_tv,dynamic_content_mon_tv,input_space;
		Button delete_dongtai;
		
	}

	


	public NearByInfoListAdapter(Context context,String []imageUrls,DisplayImageOptions options,NearByInfoListBean nearByInfoListBean,IsendMessage isendMessage) {
			inflater = LayoutInflater.from(context);
			this.isendMessage=isendMessage;
			this.imageUrls=imageUrls;
			this.options=options;
			this.context=context;
			this.nearByInfoListBean=nearByInfoListBean;
		}

		@Override
		public int getCount() {
			return nearByInfoListBean.getArray().size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			final ViewHolder holder;
			if (convertView == null) {
				view = inflater.inflate(R.layout.view_nearby_item, parent, false);
				holder = new ViewHolder();
				holder.text = (TextView) view.findViewById(R.id.text);
				holder.image = (ImageView) view.findViewById(R.id.image);
				holder.image1 = (ImageView) view.findViewById(R.id.image1);
				holder.image2 = (ImageView) view.findViewById(R.id.image2);
				holder.image3 = (ImageView) view.findViewById(R.id.image3);
				holder.image4 = (ImageView) view.findViewById(R.id.image4);
				holder.lv_comment = (SrollViewListView) view
						.findViewById(R.id.comment_lv);
				holder.image_list_table = (TableLayout) view
						.findViewById(R.id.image_list_table);
				holder.linearLayout = (LinearLayout) view
						.findViewById(R.id.top_parent);
				holder.input_space= (TextView) view.findViewById(R.id.input_space);
				holder.dynamic_content_day_tv=(TextView) view.findViewById(R.id.dynamic_content_day_tv);
				holder.dynamic_content_mon_tv=(TextView) view.findViewById(R.id.dynamic_content_mon_tv);
				holder.delete_dongtai=(Button) view.findViewById(R.id.delete_dongtai);
				
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			switch (nearByInfoListBean.getArray().get(position).getImgs().size()) {
			case 0:

				break;
			case 1:
				holder.image_list_table.setVisibility(View.GONE);
				if (nearByInfoListBean.getArray().get(position).getImgs()
								.get(0).getImg_url()==null) {
					holder.image.setVisibility(View.GONE);
				}else {
					ImageLoader.getInstance().displayImage(
							nearByInfoListBean.getArray().get(position).getImgs()
									.get(0).getImg_url(), holder.image, options,
							animateFirstListener);
					holder.image.setVisibility(View.VISIBLE);
				}
				break;

			case 2:
				holder.image.setVisibility(View.GONE);
				holder.image_list_table.setVisibility(View.VISIBLE);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(0).getImg_url(), holder.image1, options,
						animateFirstListener);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(1).getImg_url(), holder.image2, options,
						animateFirstListener);
				break;
			case 3:
				holder.image.setVisibility(View.GONE);
				holder.image_list_table.setVisibility(View.VISIBLE);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(0).getImg_url(), holder.image1, options,
						animateFirstListener);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(1).getImg_url(), holder.image2, options,
						animateFirstListener);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(2).getImg_url(), holder.image3, options,
						animateFirstListener);
				break;
			case 4:
				holder.image.setVisibility(View.GONE);
				holder.image_list_table.setVisibility(View.VISIBLE);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(0).getImg_url(), holder.image1, options,
						animateFirstListener);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(1).getImg_url(), holder.image2, options,
						animateFirstListener);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(2).getImg_url(), holder.image3, options,
						animateFirstListener);
				ImageLoader.getInstance().displayImage(
						nearByInfoListBean.getArray().get(position).getImgs()
								.get(3).getImg_url(), holder.image4, options,
						animateFirstListener);
				break;
			default:
				break;
			}
			holder.dynamic_content_day_tv.setText(TimeFormat.timeToDay((nearByInfoListBean.getArray().get(position).getTime())+"000"));
			holder.dynamic_content_mon_tv.setText(TimeFormat.timeToMon((nearByInfoListBean.getArray().get(position).getTime())+"000")+"月");
			holder.lv_comment.setAdapter(new NearByCommentListAdapter(context,
					nearByInfoListBean.getArray().get(position).getCommentGebicomments()));
			holder.text.setText(nearByInfoListBean.getArray().get(position).getContent());
			
			holder.delete_dongtai.setVisibility(View.GONE);
			holder.image.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent= new Intent(context,PicDisplayActivity.class);
					intent.putExtra("img_index", position);
					Constants.Images(null,null, nearByInfoListBean.getArray().get(position).getImgs(), 1);
					context.startActivity(intent);
					
				}
			});
			holder.linearLayout.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					int index=position;
					/*MessageBean messageBean=new MessageBean();
					messageBean.setId(nearByInfoListBean.getArray().get(index).getId());
					messageBean.setContent(nearByInfoListBean.getArray().get(index).getContent());
					messageBean.setToUser(nearByInfoListBean.getArray().get(index).getUser());
					messageBean.setUser(SharePreferenceUtils.getIntance(context).getCurrentUserName());
					messageBean.setTime(nearByInfoListBean.getArray().get(index).getTime()+"");
					messageBean.setHeadImage(SharePreferenceUtils.getIntance(context).getHeadImgUserName());
					InputMessageView.getIntance().showInputMessageView(context,
							holder.input_space,messageBean,isendMessage,0);
					;*/
				}
			});
				
			holder.image_list_table.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context, PicDisplayActivity.class);
					intent.putExtra("img_index", position);
					Constants.Images(null,null,nearByInfoListBean.getArray().get(position).getImgs() , 1);
					context.startActivity(intent);
					AnimateFirstDisplayListener.displayedImages.clear();
					
				}
			});

			return view;
		}
}
