package com.project.adpter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.project.adpter.BitmapCache.ImageCallback;
import com.project.iwant.R;
import com.utils.widght.SortModel;

public class FriendListAdapter extends BaseAdapter implements SectionIndexer {
	private String getUId;
	// private String firstWord;
	public int getIteam = 0;
	LayoutInflater inflater;
	private List<SortModel> list;
	Context context;
	private ListView listview0;

	class ViewHolder {
		// 这里声明控件
		private TextView friend_first_tv;
		private ImageView friend_item_img;
		private TextView friend_item_name_tv;
		private RelativeLayout friend_item_rl;
	}

	public FriendListAdapter(Context context0, List<SortModel> list0,
			ListView listview1) {
		this.context = context0;
		inflater = LayoutInflater.from(context);
		this.list = list0;
		this.listview0 = listview1;// 初始化FinalBitmap模块
		// Log.v("list的长度",""+list.get(0).get("uid"));
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<SortModel> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		final SortModel mContent = (SortModel) list.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.view_friend_list_iteam, null);
			viewHolder.friend_first_tv = (TextView) convertView
					.findViewById(R.id.friend_first_tv);
			viewHolder.friend_item_img = (ImageView) convertView
					.findViewById(R.id.friend_item_img);
			viewHolder.friend_item_name_tv = (TextView) convertView
					.findViewById(R.id.friend_item_name_tv);
			viewHolder.friend_item_rl = (RelativeLayout) convertView
					.findViewById(R.id.friend_item_rl);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.friend_first_tv.setVisibility(View.VISIBLE);
			viewHolder.friend_first_tv.setText(mContent.getSortLetters());
		} else {
			viewHolder.friend_first_tv.setVisibility(View.GONE);
		}

		viewHolder.friend_item_name_tv.setText(this.list.get(position)
				.getName());
		
		// viewHolder.friend_item_rl.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// }
		// });
		return convertView;

	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

}