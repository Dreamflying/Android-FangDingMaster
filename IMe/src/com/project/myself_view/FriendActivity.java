package com.project.myself_view;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.adpter.FriendListAdapter;
import com.project.iwant.R;
import com.project.message_view.MessageActivity;
import com.utils.widght.CharacterParser;
import com.utils.widght.ClearEditText;
import com.utils.widght.PinyinComparator;
import com.utils.widght.SideBar;
import com.utils.widght.SideBar.OnTouchingLetterChangedListener;
import com.utils.widght.SortModel;

/**
 *  @description   通讯录
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-5下午1:36:44
 */
public class FriendActivity extends Activity {

	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog_tv;
	// private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private FriendListAdapter adapter = null;

	/**
	 * 汉字转换成拼音的类
	 */
	CharacterParser characterParser = null;
	private List<SortModel> SourceDateList = null;
	List<SortModel> mSortList = new ArrayList<SortModel>();

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator = null;

	// 另外开线程访问SQLite
	// ===getdata==========
	private LinearLayout home_title_logo_img;
	private static String Username = null;

	private Button friend_add_friend;

	@Override
	protected void onDestroy() {
		super.onDestroy();
		adapter = null;
		characterParser = null;
		SourceDateList = null;
		mSortList = null;
		pinyinComparator = null;
		Username = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_friend);
		initViews();
		iniclcklistner();
	}

	

	private void iniclcklistner() {
		home_title_logo_img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FriendActivity.this.finish();
			}
		});
		friend_add_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
			}
		});
		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}
			}
		});
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 这里要利用adapter.getItem(position)来获取当前position所对应的对象
				String touser = ((SortModel) adapter.getItem(position))
						.getGroupid();
				String nickname = ((SortModel) adapter.getItem(position))
						.getName();
				// =====跳转到聊天页面=====
				Intent intent = new Intent(FriendActivity.this,
						MessageActivity.class);
				// 传值过去
				intent.putExtra("name", nickname);
				intent.putExtra("username", touser);
				startActivity(intent);
			}
		});
		// 长按删除好友
		sortListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				return false;
			}
		});
		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private void initViews() {
		sideBar = (SideBar) this.findViewById(R.id.sidrbar);
		dialog_tv = (TextView) this.findViewById(R.id.dialog);
		sideBar.setTextView(dialog_tv);
		sortListView = (ListView) this.findViewById(R.id.country_lvcountry);
		mClearEditText = (ClearEditText) this.findViewById(R.id.filter_edit);
		home_title_logo_img = (LinearLayout) this
				.findViewById(R.id.home_title_logo_img);
		friend_add_friend = (Button) this.findViewById(R.id.friend_add_friend);
		// =====从数据库和手机联系人获取数据========

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();

	}

	

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {

		List<SortModel> filterDateList = new ArrayList<SortModel>();
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	private List<SortModel> filledData(List<SortModel> date) {

		for (int i = 0; i < date.size(); i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date.get(i).getName());
			// 汉字转换成拼音
			String pinyin = characterParser.getSelling(date.get(i).getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 正则表达式，判断首字母是否是英文字母
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			sortModel.setGroupid(date.get(i).getGroupid());
			sortModel.setHeadimg(date.get(i).getHeadimg());
			// sortModel.setSefusername(date.get(i).getSefusername());
			sortModel.setUid(date.get(i).getUid());
			mSortList.add(sortModel);
		}
		return mSortList;

	}

}
