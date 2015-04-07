package com.project.message_view;

import com.project.iwant.R;
import com.utils.widght.Expression;
import com.utils.widght.InputMessageView;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MsgExpGv extends BaseAdapter {
	private Context context;
	LayoutInflater inflater;
	private int mNum;
	private String numStr="";
	IaddExp iaddExp;

	public MsgExpGv(Context context,int mNum,IaddExp iaddExp2) {
		this.mNum=mNum;
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.iaddExp=iaddExp2;
	}

	@Override
	public int getCount() {
		return 21;
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
		final int mPos;
		if(mNum==0){
			mPos=position+1;
		}else{
			if(mNum==1){
				mPos=position+22;
			}else{
				mPos=position+43;
			}
		}
		final Holder holder;
		if (convertView != null) {
			holder = (Holder) convertView.getTag();
		} else {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.msg_exp_gv,
					null);
			holder.mTv = (TextView) convertView
					.findViewById(R.id.msg_exp_gv_tv);
			if(mPos<=9){
				numStr="#0"+mPos;
			}else{
				numStr="#"+mPos;
			}
			holder.mTv.setText(Expression.getCS(context,numStr));
			convertView.setTag(holder);
		}
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v == holder.mTv) {
					String str;
					if(mPos<=9){
						str="#0"+mPos;
					}else{
						str="#"+mPos;
					}
					iaddExp.addIExp(str,mPos);
				}
			}
		};
		holder.mTv.setOnClickListener(listener);
		convertView.setTag(holder);
		return convertView;
	}

	public class Holder {
		public TextView mTv;
	}
	public interface IaddExp{
		public void addIExp(String str,int positon);
		
		
	}
}
