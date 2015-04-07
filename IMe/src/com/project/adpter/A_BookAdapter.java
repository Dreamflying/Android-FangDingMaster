package com.project.adpter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gotye.api.GotyeUser;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open_demo.activity.ChatPage;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.FriendInfoBean;
import com.project.message_model.FriendsBean;
import com.utils.app.A_Book_AssortPinyinList;
import com.utils.app.LanguageComparator_CN;
import com.utils.os.InitDisplayImageOptions;

public class A_BookAdapter extends BaseExpandableListAdapter {

	private List<FriendInfoBean> strList;
	Map<String, FriendInfoBean> map;
	private A_Book_AssortPinyinList assort = new A_Book_AssortPinyinList();

	private Context context;

	private LayoutInflater inflater;
	private LanguageComparator_CN cnSort = new LanguageComparator_CN();

	public A_BookAdapter(Context context, List<FriendInfoBean> strList,Map<String, FriendInfoBean> map) {
		super();
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.strList = strList;
		this.map=map;
		if (strList == null) {
			strList = new ArrayList<FriendInfoBean>();
		}

		long time = System.currentTimeMillis();
		// ����
		sort();

	}
	private void sort() {
		for (int i = 0; i < strList.size(); i++) {
			assort.getHashList().add(strList.get(i).getNickname());
		}
		assort.getHashList().sortKeyComparator(cnSort);
		for(int i=0,length=assort.getHashList().size();i<length;i++)
		{
			Collections.sort((assort.getHashList().getValueListIndex(i)),cnSort);
		}
		
		
	}
	
	public Object getChild(int group, int child) {
		// TODO Auto-generated method stub
		return assort.getHashList().getValueIndex(group, child);
	}

	public long getChildId(int group, int child) {
		// TODO Auto-generated method stub
		return child;
	}

	public View getChildView(final int group, final int child, boolean arg2,
			View contentView, ViewGroup arg4) {
		// TODO Auto-generated method stub
		if (contentView == null) {
			contentView = inflater.inflate(R.layout.adapter_chat, null);
		}
		TextView textView = (TextView) contentView.findViewById(R.id.name);
		ImageView imgView = (ImageView) contentView.findViewById(R.id.head_img);
		RelativeLayout click_contacts=(RelativeLayout)contentView.findViewById(R.id.click_contacts);
		textView.setText(assort.getHashList().getValueIndex(group, child));
		if (map.get(assort.getHashList().getValueIndex(group, child))!=null) {
		if (map.get(assort.getHashList().getValueIndex(group, child)).getHeadimage()!=null) {
			ImageLoader.getInstance().displayImage(
					map.get(assort.getHashList().getValueIndex(group, child)).getHeadimage(),
					imgView,
					InitDisplayImageOptions.getInstance().getHeadOptions(false,
							false, 0),
					InitDisplayImageOptions.getInstance()
					.getImageLoadingListener());
		}
		}
		click_contacts.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(context, ChatPage.class);
				GotyeUser user=new GotyeUser(map.get(assort.getHashList().getValueIndex(group, child)).getUsername());
				intent.putExtra("user", user);
				intent.putExtra("username",map.get(assort.getHashList().getValueIndex(group, child)).getUsername());
				context.startActivity(intent);
			}
		});
		return contentView;
	}

	public int getChildrenCount(int group) {
		// TODO Auto-generated method stub
		return assort.getHashList().getValueListIndex(group).size();
	}

	public Object getGroup(int group) {
		// TODO Auto-generated method stub
		return assort.getHashList().getValueListIndex(group);
	}

	public int getGroupCount() {
		// TODO Auto-generated method stub
		return assort.getHashList().size();
	}

	public long getGroupId(int group) {
		// TODO Auto-generated method stub
		return group;
	}

	public View getGroupView(int group, boolean arg1, View contentView,
			ViewGroup arg3) {
		if (contentView == null) {
			contentView = inflater.inflate(R.layout.list_group_item, null);
			contentView.setClickable(true);
		}
		TextView textView = (TextView) contentView.findViewById(R.id.name);
		textView.setText(assort.getFirstChar(assort.getHashList()
				.getValueIndex(group, 0)));
		// ��ֹ��չ

		return contentView;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}

	public A_Book_AssortPinyinList getAssort() {
		return assort;
	}

}
