package com.open_demo.main;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.base.model.Configuration;
import com.base.view.BaseFragment;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeChatTargetType;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeRoom;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.DownloadListener;
import com.lidroid.xutils.exception.DbException;
import com.open_demo.activity.ChatPage;
import com.open_demo.activity.NotifyListPage;
import com.open_demo.adapter.MessageListAdapter;
import com.open_demo.view.SwipeMenu;
import com.open_demo.view.SwipeMenuCreator;
import com.open_demo.view.SwipeMenuItem;
import com.open_demo.view.SwipeMenuListView;
import com.open_demo.view.SwipeMenuListView.OnMenuItemClickListener;
import com.project.add_view.NewSearchActivity;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.message_model.FriendsBean;
import com.project.message_view.CommentActivity;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_model.MyselfInfoCommentList;
import com.project.myself_model.NewMyCommentList;
import com.utils.chat.ChatServiceUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;

//此页面为回话历史页面，由客户端自己实现
@SuppressLint("NewApi")
public class MessageFragment extends BaseFragment implements DownloadListener, OnClickListener {
	private static SwipeMenuListView listView;
	private static MessageListAdapter adapter;

	public static final String fixName = "通知列表";
	private static GotyeAPI api = GotyeAPI.getInstance();
	private static MessageFragment thisObject;
	private static Context context;
	private RelativeLayout new_comment_click, new_like_click;
	private static TextView home_my_comment_text;
	private static TextView home_my_like_text;
	private View rootView;// 缓存Fragment view
	private Configuration configuration;
	private static boolean isClick=false;
	public static  MyselfInfoCommentList myselfInfoCommentList=new MyselfInfoCommentList();
	/** 添加一个总的handler 去控制页面消息信号的刷新 */
	public static Handler mMainFragmentControl = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ChatServiceUtils.FLUSH_MESSAGE:
				if (ChatServiceUtils.isMessageFragment) {
					Log.v("isMessageFragmentPage", "刷新了");
					setCommentAllTv("1");
					setLikeAllTv("1");
				}
				break;
			default:
				break;
			}

		}

	};

	
	@Override
	public void initConfiguration(BaseFragment fragment) {
		super.initConfiguration(fragment);
		configuration=getConfiguration();
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ChatServiceUtils.isMessageFragment = true;
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.layout_message_page, container,false);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ChatServiceUtils.isMessageFragment = true;
		initConfiguration(MessageFragment.this);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		GotyeAPI.getInstance().addListerer(this);
		ChatServiceUtils.isMessageFragment = true;
		thisObject = MessageFragment.this;
		context=getActivity();
		try {
			initView();
		} catch (Exception e) {
			Log.v("e", e.toString());
		}
		
		getCommentList();
	}
	
	public void getCommentList(){
		MyselfInfoCommentList myselfInfoCommentList=new MyselfInfoCommentList();
		ArrayList<MyselfInfoComment> myselfInfoComments=new ArrayList<MyselfInfoComment>();
	    MyselfInfoComment myselfInfoComment=new MyselfInfoComment();
	    myselfInfoComment.setUser(SharePreferenceUtils.getIntance(getActivity()).getCurrentUserName());
	    myselfInfoComments.add(myselfInfoComment);
	    myselfInfoCommentList.setArray(myselfInfoComments);
	    configuration.setClassName(MyselfInfoCommentList.class);
	    configuration.setViewDataObject(myselfInfoCommentList);
	    new MyselfInfoController(configuration).getAllComment();
		
		
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		myselfInfoCommentList=(MyselfInfoCommentList)object;
		if (myselfInfoCommentList.getArray().size()!=0) {
			SharePreferenceUtils.getIntance(context).saveCommetnNum(myselfInfoCommentList.getArray().get(0).getSum());
		}
		setCommentAllTv("1");
		Message message=new Message();
		message.what=ChatServiceUtils.FLASH_MAIN_MESSAGE;
		NewMainTabActivity.mMainControl.sendMessage(message);
		
	}
	
	private void initView() {
		listView = (SwipeMenuListView) getView().findViewById(R.id.listview);
		new_comment_click = (RelativeLayout) getView()
				.findViewById(R.id.new_comment_click);
		new_like_click = (RelativeLayout) getView()
				.findViewById(R.id.new_like_click);
		home_my_comment_text = (TextView) getView()
				.findViewById(R.id.home_my_comment_text);
		home_my_like_text = (TextView) getView()
				.findViewById(R.id.home_my_like_text);
		new_comment_click.setOnClickListener(this);
		new_like_click.setOnClickListener(this);
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					createMenu1(menu);
					break;
				case 1:
					createMenu2(menu);
					break;
				}
			}
		};
		listView.setMenuCreator(creator);
		listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(int position, SwipeMenu menu,
					int index) {
				GotyeChatTarget target = adapter.getItem(position);
				api.deleteSession(target);
				isClick=true;
				updateList();
				return false;
			}
		});
		setCommentAllTv("1");
		setLikeAllTv("1");
		updateList();
		setListener();
	}

	private void createMenu1(SwipeMenu menu) {

	}

	private void createMenu2(SwipeMenu menu) {
		// SwipeMenuItem item1 = new SwipeMenuItem(
		// getActivity());
		// item1.setBackground(new ColorDrawable(Color.rgb(0xE5, 0xE0,
		// 0x3F)));
		// item1.setWidth(dp2px(70));
		// item1.setIcon(R.drawable.ic_action_about);
		// menu.addMenuItem(item1);
		SwipeMenuItem item2 = new SwipeMenuItem(getActivity());
		item2.setBackground(new ColorDrawable(Color.rgb(0x87, 0xBF, 0x06)));
		item2.setWidth(dp2px(70));
		item2.setIcon(R.drawable.new_delete_talk);
		menu.addMenuItem(item2);
	}

	public void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				GotyeChatTarget target = (GotyeChatTarget) adapter
						.getItem(arg2);
				if (target.name.equals(fixName)) {
					Intent i = new Intent(getActivity(), NotifyListPage.class);
					startActivity(i);
				} else {
					GotyeAPI.getInstance().markMeeagesAsread(target);
					if (target.type == GotyeChatTargetType.GotyeChatTargetTypeUser) {
						Intent toChat = new Intent(getActivity(),
								ChatPage.class);
						toChat.putExtra("user", (GotyeUser) target);
						toChat.putExtra("username", ((GotyeUser) target).getName());
						startActivity(toChat);
						updateList();
					} else if (target.type == GotyeChatTargetType.GotyeChatTargetTypeRoom) {
						Intent toChat = new Intent(getActivity(),
								ChatPage.class);
						toChat.putExtra("room", (GotyeRoom) target);
						startActivity(toChat);

					} else if (target.type == GotyeChatTargetType.GotyeChatTargetTypeGroup) {
						Intent toChat = new Intent(getActivity(),
								ChatPage.class);
						toChat.putExtra("group", (GotyeGroup) target);
						startActivity(toChat);
					}
					refresh();
				}
			}
		});
	}

	private static void updateList() {
		
		Log.v("updateList", "updateList");
		if (ChatServiceUtils.isMessageFragment) {
			Log.v("ChatServiceUtils", "ChatServiceUtils");
			List<GotyeChatTarget> sessions = api.getSessionList();
/*
			GotyeChatTarget target = new GotyeChatTarget();
			target.name = fixName;

			if (sessions == null) {
				Log.v("session", " is null");
				sessions = new ArrayList<GotyeChatTarget>();
				sessions.add(target);
			} else {
				sessions.add(0, target);
				Log.v("session", " is ok");
			}*/

				
			
			if (sessions!=null) {
				if (sessions.size()!=0) {
					listView.setVisibility(View.VISIBLE);
				adapter = new MessageListAdapter(thisObject, sessions);
				listView.setAdapter(adapter);
				}
				if (isClick) {
					adapter = new MessageListAdapter(thisObject, sessions);
					listView.setAdapter(adapter);
				}
			}else {
				if (isClick) {
					listView.setVisibility(View.GONE);
				}
				
				
			}
}	
			
	
			/*if (adapter == null) {
				Log.v("adapter", " is null");
			
				
			} else {
				Log.v("adapter", " is ok");
				adapter.setData(sessions);
				adapter.notifyDataSetChanged();
			}*/
		}
	

	public static void refresh() {
		updateList();
	}

	@Override
	public void onDestroy() {
		ChatServiceUtils.isMessageFragment = false;
		super.onDestroy();

	}


	@Override
	public void onResume() {
		super.onResume();
		ChatServiceUtils.isMessageFragment = true;
	}

	@Override
	public void onDownloadMedia(int code, String path, String url) {
		// TODO Auto-generated method stub
		adapter.notifyDataSetChanged();
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.new_comment_click:
			Intent new_comment_click = new Intent(getActivity(),
					CommentActivity.class);
			startActivity(new_comment_click);

			break;
		case R.id.new_like_click:
			Intent intent = new Intent(getActivity(), NewSearchActivity.class);
			intent.putExtra("isLike", 1);
			startActivity(intent);

			break;
		default:
			break;
		}
	}
	/*
	 * 显示我的列表上面的红色数字
	 */
	public static void setCommentAllTv(String mStr) {
		if (mStr.equals("0")) {
			home_my_comment_text.setVisibility(View.GONE);
		} else {
			int num = SharePreferenceUtils.getIntance(context).getCommetnNum();
			if (num == 0) {
				home_my_comment_text.setVisibility(View.GONE);
			} else {
				home_my_comment_text.setVisibility(View.VISIBLE);
				home_my_comment_text.setText(num + "");
			}
		}
	}

	/*
	 * 显示我的列表上面的红色数字
	 */
	public static void setLikeAllTv(String mStr) {
		if (mStr.equals("0")) {
			home_my_like_text.setVisibility(View.GONE);
		} else {
			int num = SharePreferenceUtils.getIntance(context).getLinkNum();
			if (num == 0) {
				home_my_like_text.setVisibility(View.GONE);
			} else {
				home_my_like_text.setVisibility(View.VISIBLE);
				home_my_like_text.setText(num + "");
			}
		}
	}
}
