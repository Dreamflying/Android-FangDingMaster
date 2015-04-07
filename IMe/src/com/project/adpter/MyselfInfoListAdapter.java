package com.project.adpter;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.project.adpter.HomeIhaveListAdapter.holder_Iwant;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.myself_model.MyselfInfoListBean;
import com.project.myself_view.Constants;
import com.project.myself_view.DongtaiHandlerPPW;
import com.project.myself_view.NewCommentActivity;
import com.project.myself_view.DongtaiHandlerPPW.IApply;
import com.project.myself_view.MyselfInfoActivity;
import com.project.myself_view.PicDisplayActivity;
import com.utils.app.TimeFormat;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.Expression;
import com.utils.widght.InputMessageView;
import com.utils.widght.InputMessageView.IsendMessage;
import com.utils.widght.SrollViewListView;

public class MyselfInfoListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	String[] imageUrls;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private Context context;
	private MyselfInfoListBean myselfInfoListBean;
	private IsendMessage isendMessage;
	private IOperateDongTai iOperateDongTai;

	private static class ViewHolder {
		TextView text;
		ImageView image;
		ImageView image1;
		ImageView image2;
		ImageView image3;
		ImageView image4;
		TableLayout image_list_table;
		SrollViewListView lv_comment;
		RelativeLayout clickRelativeLayout;
		LinearLayout linearLayout;
		TextView dynamic_content_day_tv,dynamic_content_mon_tv,input_space,item_list_btn;
		Button delete_dongtai;
	}

	public MyselfInfoListAdapter(Context context,
			DisplayImageOptions options, MyselfInfoListBean myselfInfoListBean,IsendMessage isendMessage,IOperateDongTai iOperateDongTai) {
		this.isendMessage=isendMessage;
		this.iOperateDongTai=iOperateDongTai;
		inflater = LayoutInflater.from(context);
		this.options = options;
		this.context = context;
		this.myselfInfoListBean = myselfInfoListBean;
	}

	@Override
	public int getCount() {
		return myselfInfoListBean.getArray().size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(final int position, View view,
			final ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.view_myinfolist_item, parent,
					false);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.text);
			holder.image = (ImageView) view.findViewById(R.id.image);
			holder.image1 = (ImageView) view.findViewById(R.id.image1);
			holder.image2 = (ImageView) view.findViewById(R.id.image2);
			holder.image3 = (ImageView) view.findViewById(R.id.image3);
			holder.image4 = (ImageView) view.findViewById(R.id.image4);
			holder.input_space= (TextView) view.findViewById(R.id.input_space);
			holder.item_list_btn=(TextView)view.findViewById(R.id.item_list_btn);
			/*holder.dynamic_content_day_tv=(TextView) view.findViewById(R.id.dynamic_content_day_tv);
			holder.dynamic_content_mon_tv=(TextView) view.findViewById(R.id.dynamic_content_mon_tv);*/
			holder.lv_comment = (SrollViewListView) view
					.findViewById(R.id.comment_lv);
			holder.image_list_table = (TableLayout) view
					.findViewById(R.id.image_list_table);
			holder.clickRelativeLayout = (RelativeLayout) view
					.findViewById(R.id.relativeLayout1);
			holder.linearLayout = (LinearLayout) view
					.findViewById(R.id.top_parent);
			holder.delete_dongtai=(Button) view
					.findViewById(R.id.delete_dongtai);
			/*holder.image. setScaleType(ImageView.ScaleType.CENTER_INSIDE);  
			holder.image1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);  
			holder.image2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);  
			holder.image3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);  
			holder.image4 .setScaleType(ImageView.ScaleType.CENTER_INSIDE);  */
			
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
			view.setClickable(true);
		}
		switch (myselfInfoListBean.getArray().get(position).getImg().size()) {
		case 0:

			break;
		case 1:
			holder.image_list_table.setVisibility(View.GONE);
			if (myselfInfoListBean.getArray().get(position).getImg()
							.get(0).getHeadurl()==null) {
				holder.image.setVisibility(View.GONE);
			}else {
				ImageLoader.getInstance().displayImage(
						myselfInfoListBean.getArray().get(position).getImg()
								.get(0).getHeadurl(), holder.image, options,
						animateFirstListener);
				holder.image.setVisibility(View.VISIBLE);
			}
			
			break;

		case 2:
			holder.image.setVisibility(View.GONE);
			holder.image_list_table.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(0).getHeadurl(), holder.image1, options,
					animateFirstListener);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(1).getHeadurl(), holder.image2, options,
					animateFirstListener);
			holder.image3.setVisibility(View.GONE);
			holder.image4.setVisibility(View.GONE);
			break;
		case 3:
			holder.image.setVisibility(View.GONE);
			holder.image_list_table.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(0).getHeadurl(), holder.image1, options,
					animateFirstListener);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(1).getHeadurl(), holder.image2, options,
					animateFirstListener);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(2).getHeadurl(), holder.image3, options,
					animateFirstListener);
			break;
		case 4:
			holder.image.setVisibility(View.GONE);
			holder.image_list_table.setVisibility(View.VISIBLE);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(0).getHeadurl(), holder.image1, options,
					animateFirstListener);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(1).getHeadurl(), holder.image2, options,
					animateFirstListener);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(2).getHeadurl(), holder.image3, options,
					animateFirstListener);
			ImageLoader.getInstance().displayImage(
					myselfInfoListBean.getArray().get(position).getImg()
							.get(3).getHeadurl(), holder.image4, options,
					animateFirstListener);
			
			break;
		default:
			break;
		}
		/*holder.dynamic_content_day_tv.setText(TimeFormat.timeToDay((myselfInfoListBean.getArray().get(position).getTime())+"000"));
		holder.dynamic_content_mon_tv.setText(TimeFormat.timeToMon((myselfInfoListBean.getArray().get(position).getTime())+"000")+"月");*/
		holder.lv_comment.setAdapter(new CommentListAdapter(context,
				myselfInfoListBean.getArray().get(position).getComment()));
		holder.text.setText(Expression.getCS(context,myselfInfoListBean.getArray().get(position).getContent()));
		holder.image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, PicDisplayActivity.class);
				intent.putExtra("img_index", position);
				Constants.Images(null,myselfInfoListBean.getArray().get(position).getImg(), null, 0);
				context.startActivity(intent);
				AnimateFirstDisplayListener.displayedImages.clear();
			}
		});
		holder.linearLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MessageBean messageBean=new MessageBean();
				messageBean.setId(myselfInfoListBean.getArray().get(position).getId());
				messageBean.setContent(myselfInfoListBean.getArray().get(position).getContent());
				messageBean.setToUser(myselfInfoListBean.getArray().get(position).getUser());
				messageBean.setUser(SharePreferenceUtils.getIntance(context).getCurrentUserName());
				messageBean.setTime(myselfInfoListBean.getArray().get(position).getTime()+"");
				messageBean.setToName(SharePreferenceUtils.getIntance(context).getCurrentNickName());
				messageBean.setHeadImage(SharePreferenceUtils.getIntance(context).getHeadImgUserName());
				/*InputMessageView.getIntance().showInputMessageView(context,
						holder.input_space,messageBean,isendMessage,1);*/
			}
		});

		holder.image_list_table.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, PicDisplayActivity.class);
				intent.putExtra("img_index", position);
				Constants.Images(null,myselfInfoListBean.getArray().get(position).getImg(), null, 0);
				context.startActivity(intent);
				AnimateFirstDisplayListener.displayedImages.clear();
				
			}
		});
		if (MyselfInfoActivity.isFrom) {
			holder.delete_dongtai.setVisibility(View.GONE);
		}else {
			holder.delete_dongtai.setVisibility(View.VISIBLE);
		}
		holder.delete_dongtai.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				DongtaiHandlerPPW.startPopWin(context, arg0, new IApply() {

					@Override
					public void apply(int position) {
						iOperateDongTai.delete(myselfInfoListBean.getArray().get(position).getId());
						
					}

					@Override
					public void refuse(String postid) {
						// TODO Auto-generated method stub
						
					}
					},position);

			}
		});
		
		holder.item_list_btn.setOnClickListener(new View. OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				MessageBean messageBean=new MessageBean();
				messageBean.setId(myselfInfoListBean.getArray().get(position).getId());
				messageBean.setContent(myselfInfoListBean.getArray().get(position).getContent());
				messageBean.setToUser(myselfInfoListBean.getArray().get(position).getUser());
				messageBean.setUser(SharePreferenceUtils.getIntance(context).getCurrentUserName());
				messageBean.setTime(myselfInfoListBean.getArray().get(position).getTime()+"");
				messageBean.setToName(SharePreferenceUtils.getIntance(context).getCurrentNickName());
				messageBean.setHeadImage(SharePreferenceUtils.getIntance(context).getHeadImgUserName());
				Intent intent =new Intent(context,NewCommentActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("messageBean", messageBean);
				intent.putExtras(bundle);
				context.startActivity(intent);
			}
		});
		return view;
	}

	public interface IOperateDongTai{
		public void delete(int  id);
		
	}
	
}
