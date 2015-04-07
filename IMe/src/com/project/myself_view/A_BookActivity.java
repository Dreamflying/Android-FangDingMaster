package com.project.myself_view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.gotye.api.GotyeUser;
import com.open_demo.activity.ChatPage;
import com.project.adpter.A_BookAdapter;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.FriendInfoBean;
import com.project.message_controller.MessageController;
import com.project.message_model.AddFriendBean;
import com.project.message_model.AddFriendListBean;
import com.project.message_model.FriendsBean;
import com.project.message_model.GetFriendListBean;
import com.project.message_view.MessageActivity;
import com.utils.app.StringUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.A_Book_AssortView;
import com.utils.widght.A_Book_AssortView.OnTouchAssortListener;

public class A_BookActivity extends BaseActivity implements
		OnItemClickListener, OnChildClickListener {
	public A_BookActivity() {
		super("");
	}

	/** Called when the activity is first created. */

	private A_BookAdapter adapter;
	private ExpandableListView eListView;
	private A_Book_AssortView assortView;
	public static List<FriendInfoBean> friendsBeans;
	private LinearLayout setup_return_btns;
	private Configuration configuration;
	private int getFriend = 0;
	private Map<String, FriendInfoBean> map = new HashMap<String, FriendInfoBean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_book_activity);
		initConfiguration(A_BookActivity.this, "A_BookActivity");
		eListView = (ExpandableListView) findViewById(R.id.elist);
		assortView = (A_Book_AssortView) findViewById(R.id.assort);
		setup_return_btns = (LinearLayout) findViewById(R.id.setup_return_btns);
		friendsBeans = SQLDatebaseUtils.getInstance(A_BookActivity.this).getAllFriends();
				
		Log.v("size", friendsBeans.size() + "");
		for (int i = 0; i < friendsBeans.size(); i++) {

				FriendInfoBean friendsBean = new FriendInfoBean();
				friendsBean.setHeadimage(friendsBeans.get(i).getHeadimage());
				friendsBean.setNickname(friendsBeans.get(i).getNickname());
				friendsBean.setUsername(friendsBeans.get(i).getUsername());

				map.put(friendsBeans.get(i).getNickname(), friendsBean);
		}
		if (friendsBeans != null && friendsBeans.size() != 0 && map != null
				&& map.size() != 0) {
			Log.v("friendsBeans", friendsBeans + "");

			adapter = new A_BookAdapter(A_BookActivity.this, friendsBeans, map);
			eListView.setAdapter(adapter);
			for (int i = 0, length = adapter.getGroupCount(); i < length; i++) {
				eListView.expandGroup(i);
			}
			assortView.setOnTouchAssortListener(new OnTouchAssortListener() {
				View layoutView = LayoutInflater.from(A_BookActivity.this)
						.inflate(R.layout.alert_dialog_menu_layout, null);
				TextView text = (TextView) layoutView
						.findViewById(R.id.content);
				PopupWindow popupWindow;

				public void onTouchAssortListener(String str) {
					int index = adapter.getAssort().getHashList()
							.indexOfKey(str);
					if (index != -1) {
						eListView.setSelectedGroup(index);
					}
					if (popupWindow != null) {
						text.setText(str);
					} else {
						popupWindow = new PopupWindow(layoutView, 200, 200,
								false);
						popupWindow.showAtLocation(getWindow().getDecorView(),
								Gravity.CENTER, 0, 0);
					}
					text.setText(str);
				}

				public void onTouchAssortUP() {
					if (popupWindow != null)
						popupWindow.dismiss();
					popupWindow = null;
				}
			});

			// eListView.setOnChildClickListener(this);

			/*
			 * for (int i = 0; i < friendsBeans.size(); i++) {
			 * addFriend(friendsBeans
			 * .get(i).getUsername(),friendsBeans.get(i).getFriendUserName()); }
			 * getFriends();
			 */
		}
		setup_return_btns.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

	}

	public void addFriend(String user, String touser) {
		AddFriendListBean addFriendListBean = new AddFriendListBean();
		ArrayList<AddFriendBean> addFriendBeans = new ArrayList<AddFriendBean>();
		AddFriendBean addFriendBean = new AddFriendBean();
		addFriendBean.setTouser(touser);
		addFriendBean.setUser(user);
		addFriendBeans.add(addFriendBean);
		addFriendListBean.setArray(addFriendBeans);
		configuration.setClassName(AddFriendListBean.class);
		configuration.setViewDataObject(addFriendListBean);
		new MessageController(configuration).addFriend();

	}

	public void getFriends() {
		getFriend = 1;
		AddFriendListBean addFriendListBean = new AddFriendListBean();
		ArrayList<AddFriendBean> addFriendBeans = new ArrayList<AddFriendBean>();
		AddFriendBean addFriendBean = new AddFriendBean();
		addFriendBean.setUser(SharePreferenceUtils.getIntance(
				A_BookActivity.this).getCurrentUserName());
		addFriendBeans.add(addFriendBean);
		addFriendListBean.setArray(addFriendBeans);
		configuration.setClassName(GetFriendListBean.class);
		configuration.setViewDataObject(addFriendListBean);
		new MessageController(configuration).getFriend();

	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.v("log", "hes");
		Intent intent = new Intent(A_BookActivity.this, ChatPage.class);
		GotyeUser user = new GotyeUser(friendsBeans.get(arg2)
				.getUsername());
		intent.putExtra("user", user);
		intent.putExtra("username", friendsBeans.get(arg2).getUsername());
		/*
		 * Bundle bundle = new Bundle(); bundle.putString("username",
		 * friendsBeans.get(arg2).getFriendUserName()); bundle.putString("name",
		 * friendsBeans.get(arg2).getFriendNickName());
		 * bundle.putString("headimage",
		 * friendsBeans.get(arg2).getFriendHeadImage());
		 * intent.putExtras(bundle);
		 */
		startActivity(intent);
	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
			int arg3, long arg4) {
		Log.v("log", "hes");
		Intent intent = new Intent(A_BookActivity.this, ChatPage.class);
		GotyeUser user = new GotyeUser(friendsBeans.get(arg2)
				.getUsername());
		intent.putExtra("user", user);
		intent.putExtra("username", friendsBeans.get(arg2).getUsername());
		/*
		 * Bundle bundle = new Bundle(); bundle.putString("username",
		 * friendsBeans.get(arg2).getFriendUserName()); bundle.putString("name",
		 * friendsBeans.get(arg2).getFriendNickName());
		 * bundle.putString("headimage",
		 * friendsBeans.get(arg2).getFriendHeadImage());
		 * intent.putExtras(bundle);
		 */
		startActivity(intent);
		return false;
	}

}