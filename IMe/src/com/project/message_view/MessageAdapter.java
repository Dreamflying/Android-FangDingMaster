package com.project.message_view;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fortysevendeg.android.swipelistview.listview.SwipeListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.utils.chat.ChatServiceUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.Expression;

public class MessageAdapter extends BaseAdapter{

	private Context context;
	List<MessageBean> data;
	IoperateMessage ioperateMessage;
	public MessageAdapter(Context context,List<MessageBean> data,IoperateMessage ioperateMessage2){
		this.context=context;
		this.data=data;
		this.ioperateMessage=ioperateMessage2;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		 ViewHolder holder;
		if (convertView == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.package_row, parent, false);
            holder = new ViewHolder();
            holder.ivImage = (ImageView) convertView.findViewById(R.id.example_row_iv_image);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.example_row_tv_title);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.example_row_tv_description);
            holder.bAction1 = (Button) convertView.findViewById(R.id.example_row_b_action_1);
            holder.bAction2 = (Button) convertView.findViewById(R.id.example_row_b_action_2);
            holder.home_my_liaoliao_text=(TextView)convertView.findViewById(R.id.home_my_liaoliao_text);
            convertView.setTag(holder);
            ((SwipeListView)parent).recycle(convertView, position);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
		 holder.bAction1.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            }
	        });

	        holder.bAction2.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	            	ioperateMessage.delete(position);
	            	
	            }
	        });
	        ImageLoader.getInstance()
			.displayImage(
					data.get(position).getHeadImage(),
					holder.ivImage,
					InitDisplayImageOptions.getInstance().getHeadOptions(true,
							true, 0),
					InitDisplayImageOptions.getInstance()
							.getImageLoadingListener());
			holder.tvTitle.setText(data.get(position).getToName());
			if(!data.get(position).getContent().equals("#image#")){
			String[] contentString=  data.get(position).getContent().replace(ChatServiceUtils.APPLY,"").replace(ChatServiceUtils.HAN, "").split("@");
			if (contentString[0].contains("#")) {
				StringBuffer temp=new StringBuffer();
				char []content=contentString[0].toCharArray();
				TextView textView=new TextView(context);
				for (int i = 0; i < content.length; i++) {
					
					if (content[i]=='#') {
						textView.append(Expression.getCS(context,contentString[0].substring(i, i+3)));
						i=i+2;
					}else {
						textView.append(temp.append(content[i]));
					}
				}
				
				 
				holder.tvDescription.setText(textView.getText());
			}else {
				
				holder.tvDescription.setText(contentString[0]);
			}}else {
				
				Log.v("to", "iamge");
			}
			int num=SQLDatebaseUtils.getInstance(context).getUnReadMsgNumForToUser(data.get(position).getUser(), data.get(position).getToUser());
		    Log.v("NUM", num+"");
			if (num==0) {
				holder.home_my_liaoliao_text.setVisibility(View.GONE);
			}else {
				holder.home_my_liaoliao_text.setVisibility(View.VISIBLE);
				holder.home_my_liaoliao_text.setText(""+num);
			}
	        return convertView;
       
	}
	 static class ViewHolder {
	        ImageView ivImage;
	        TextView tvTitle;
	        TextView tvDescription;
	        Button bAction1;
	        Button bAction2;
	        TextView home_my_liaoliao_text;
	    }
	public  interface IoperateMessage{
		
		public void add(int position);
		public  void delete(int position);
		
		
	}
}
