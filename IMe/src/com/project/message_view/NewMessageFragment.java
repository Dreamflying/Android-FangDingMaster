package com.project.message_view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.base.model.Configuration;
import com.base.view.BaseFragment;
import com.fortysevendeg.android.swipelistview.listview.BaseSwipeListViewListener;
import com.fortysevendeg.android.swipelistview.listview.SwipeListView;
import com.fortysevendeg.android.swipelistview.sample.utils.SettingsManager;
import com.project.add_view.NewSearchActivity;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.utils.chat.ChatServiceUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;

/**
 * 消息
 * 
 * @author liyuan
 * 
 */
public class NewMessageFragment extends BaseFragment implements
		OnClickListener, OnItemClickListener {
	private static SwipeListView swipeListView;
	private static MessageAdapter messageAdapter;
	private static List<MessageBean> messageBeans = new ArrayList<MessageBean>();
	private View rootView;// 缓存Fragment view
	private static Context context;
	private RelativeLayout new_comment_click, new_like_click;
	private static TextView home_my_comment_text;
	private static TextView home_my_like_text;

	/** 添加一个总的handler 去控制页面消息信号的刷新 */
	public static Handler mMainFragmentControl = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ChatServiceUtils.FLUSH_MESSAGE:
				if (ChatServiceUtils.isMessageFragmentPage) {
					Log.v("isMessageFragmentPage", "刷新了");
					setCommentAllTv("1");
					setLikeAllTv("1");
					getdata();
				}
			
				break;
			default:
				break;
			}

		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
		ChatServiceUtils.isMessageFragmentPage = true;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ChatServiceUtils.isMessageFragmentPage = true;
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_message, null);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ChatServiceUtils.isMessageFragmentPage = true;
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}

		initView(rootView);
		getdata();
		setLikeAllTv("1");
		return rootView;
	}

	private static void getdata() {

		messageBeans = SQLDatebaseUtils.getInstance(context).getDisticMessage(
				SharePreferenceUtils.getIntance(context).getCurrentUserName());
		messageAdapter = new MessageAdapter(context, messageBeans,
				new com.project.message_view.MessageAdapter.IoperateMessage() {

					@Override
					public void delete(int position) {
						if (messageBeans.size() != 0) {
							SQLDatebaseUtils.getInstance(context)
									.deleteMessage(
											messageBeans.get(position)
													.getToUser());
							getdata();
						}
					}

					@Override
					public void add(int position) {
						// TODO Auto-generated method stub

					}
				});
		swipeListView.setAdapter(messageAdapter);
		setLikeAllTv(SharePreferenceUtils.getIntance(context).getLinkNum()+"");
	}

	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {

		swipeListView = (SwipeListView) view.findViewById(R.id.example_lv_list);
		new_comment_click = (RelativeLayout) view
				.findViewById(R.id.new_comment_click);
		new_like_click = (RelativeLayout) view
				.findViewById(R.id.new_like_click);
		home_my_comment_text = (TextView) view
				.findViewById(R.id.home_my_comment_text);
		home_my_like_text = (TextView) view
				.findViewById(R.id.home_my_like_text);
		new_comment_click.setOnClickListener(this);
		new_like_click.setOnClickListener(this);

		if (Build.VERSION.SDK_INT >= 11) {
			swipeListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			swipeListView
					.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

						@Override
						public void onDestroyActionMode(ActionMode mode) {
							swipeListView.unselectedChoiceStates();
						}

						@Override
						public boolean onPrepareActionMode(ActionMode mode,
								Menu menu) {
							return false;
						}

						@Override
						public boolean onActionItemClicked(ActionMode arg0,
								MenuItem arg1) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public boolean onCreateActionMode(ActionMode arg0,
								Menu arg1) {
							// TODO Auto-generated method stub
							return false;
						}

						@Override
						public void onItemCheckedStateChanged(ActionMode arg0,
								int arg1, long arg2, boolean arg3) {
							// TODO Auto-generated method stub

						}
					});
		}

		swipeListView.setSwipeListViewListener(new BaseSwipeListViewListener() {
			@Override
			public void onOpened(int position, boolean toRight) {
			}

			@Override
			public void onClosed(int position, boolean fromRight) {
			}

			@Override
			public void onListChanged() {
			}

			@Override
			public void onMove(int position, float x) {
			}

			@Override
			public void onStartOpen(int position, int action, boolean right) {
				Log.d("swipe", String.format("onStartOpen %d - action %d",
						position, action));
			}

			@Override
			public void onStartClose(int position, boolean right) {
				Log.d("swipe", String.format("onStartClose %d", position));
			}

			@Override
			public void onClickFrontView(int position) {
				Log.d("swipe", String.format("onClickFrontView %d", position));
				Intent intent = new Intent(getActivity(), MessageActivity.class);
				// 传递数据
				intent.putExtra("username", messageBeans.get(position)
						.getToUser());
				intent.putExtra("name", messageBeans.get(position).getToName());
				intent.putExtra("headimage", messageBeans.get(position)
						.getHeadImage());
				getActivity().startActivity(intent);
			}

			@Override
			public void onClickBackView(int position) {
				Log.d("swipe", String.format("onClickBackView %d", position));
			}

			@Override
			public void onDismiss(int[] reverseSortedPositions) {
				for (int position : reverseSortedPositions) {
					messageBeans.remove(position);
				}
				messageAdapter.notifyDataSetChanged();
			}

			@Override
			public void onLastListItem() {

				// adapter.notifyDataSetChanged();
			}
		});

		reload();
		swipeListView.setOnItemClickListener(this);
		setCommentAllTv("1");
		setLikeAllTv("1");
	}

	private void reload() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		int x = dm.widthPixels;
		SettingsManager settings = SettingsManager.getInstance();
		swipeListView.setSwipeMode(3);
		swipeListView.setSwipeActionLeft(settings.getSwipeActionLeft());
		swipeListView.setSwipeActionRight(settings.getSwipeActionRight());
		swipeListView.setOffsetLeft(x - convertDpToPixel(160f));
		swipeListView.setOffsetRight(x - convertDpToPixel(160f));
		swipeListView.setAnimationTime(100);
		swipeListView
				.setSwipeOpenOnLongPress(settings.isSwipeOpenOnLongPress());
	}

	public int convertDpToPixel(float dp) {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return (int) px;
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	/*
	 * 显示我的列表上面的红色数字
	 */
	public static void setCommentAllTv(String mStr) {
		if (mStr.equals("0")) {
			home_my_comment_text.setVisibility(View.GONE);
		} else {
			int num = SQLDatebaseUtils.getInstance(context)
					.getUnReadCommentMsgNumForToUser(
							SharePreferenceUtils.getIntance(context)
									.getCurrentUserName());
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
