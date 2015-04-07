package com.project.myself_view;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.view.BaseFragment;
import com.project.adpter.HomeMyListAdapter;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.message_view.CommentActivity;
import com.project.message_view.MessageActivity;
import com.project.message_view.MessageHandlerPPW;
import com.project.message_view.MessageHandlerPPW.IApply;
import com.utils.chat.ChatServiceUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;

/**
 * @description 我的界面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-2上午10:49:10
 */
public class MyselfFragment extends BaseFragment implements OnClickListener,
		OnItemLongClickListener, OnItemClickListener {
	/**
	 * handler message
	 * 
	 * */

	private static final String TEXT_CHAT = "CHAT";
	private RelativeLayout rl_apply;
	private RelativeLayout rl_gebi;
	private static ListView lv_info_list;
	private static List<MessageBean> messageBeans = new ArrayList<MessageBean>();
	private static HomeMyListAdapter homeMyListAdapter;
	private static Context context;
	private static TextView home_my_apply_text;

	public MyselfFragment() {
	}

	public MyselfFragment(String title) {
		super(title);
	}

	public static Handler mMyselfControl = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case ChatServiceUtils.APPLY_UPDATE_MSG:
				Log.v("here", "myselft");
				if (ChatServiceUtils.isMyFragmentPage) {
					getdata();
				}
				break;
			default:
				break;
			}

		}

	};

	/**
	 * Factory method to generate a new instance of the fragment given a string
	 * .
	 * 
	 * @param char 主页面要传过来的信息
	 * @return A new instance of MyFragment with chat extras
	 */
	public static MyselfFragment newInstance(String chat) {
		final MyselfFragment f = new MyselfFragment();
		final Bundle args = new Bundle();
		args.putString(TEXT_CHAT, chat);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	private static void getdata() {
		messageBeans = SQLDatebaseUtils.getInstance(context).getDisticMessage(
				SharePreferenceUtils.getIntance(context).getCurrentUserName());
		Log.v("SharePreferenceUtils.getIntance(context).getCommentItem()",
				SharePreferenceUtils.getIntance(context).getCommentItem() + "");
		if (SQLDatebaseUtils.getInstance(context).getUnReadCommentMsgNumForToUser(SharePreferenceUtils.getIntance(context).getCurrentUserName())>0) {
			MessageBean messageBean = new MessageBean();
			messageBean.setToName("评论中心");
			messageBean.setContent("comment");
			messageBeans.add(messageBean);
			SharePreferenceUtils.getIntance(context).saveCommentItem(false);	
		}
			
		homeMyListAdapter = new HomeMyListAdapter(context, messageBeans);
		lv_info_list.setAdapter(homeMyListAdapter);
		if (SharePreferenceUtils.getIntance(context).getHomeUnApply() == 0) {
			home_my_apply_text.setVisibility(View.GONE);
		} else {
			home_my_apply_text.setVisibility(View.VISIBLE);

		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ChatServiceUtils.isMyFragmentPage=true;
		context = getActivity();
		View view = inflater
				.inflate(R.layout.fragment_myself, container, false);
		initView(view);
		setListener();
		getdata();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void initView(View view) {
		rl_apply = (RelativeLayout) view
				.findViewById(R.id.home_mylist_top_shenqing);
		rl_gebi = (RelativeLayout) view.findViewById(R.id.home_mylist_top_gebi);
		lv_info_list = (ListView) view.findViewById(R.id.home_my_lv);
		home_my_apply_text = (TextView) view
				.findViewById(R.id.home_my_apply_text);
	}

	@Override
	public void setListener() {
		rl_apply.setOnClickListener(this);
		rl_gebi.setOnClickListener(this);
		lv_info_list.setOnItemLongClickListener(this);
		lv_info_list.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View arg0) {

		switch (arg0.getId()) {
		case R.id.home_mylist_top_shenqing:
			// 申请
			Intent intent = new Intent(getActivity(), ApplyActivity.class);
			startActivity(intent);
			break;
		case R.id.home_mylist_top_gebi:
			// 隔壁
			Intent nearintent = new Intent(getActivity(), NearbyActivity.class);
			startActivity(nearintent);
			break;

		default:
			break;
		}

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		if (!HomeMyListAdapter.isComment[arg2]) {
			MessageHandlerPPW.startPopWin(context, arg1, new IApply() {

				@Override
				public void refuse(String postid) {
					// TODO Auto-generated method stub

				}

				@Override
				public void apply(int position) {
					Log.v("here", "here");
					if (messageBeans.size()!=0) {
						SQLDatebaseUtils.getInstance(context).deleteMessage(
								messageBeans.get(arg2).getToUser());
						getdata();
						homeMyListAdapter = new HomeMyListAdapter(getActivity(),
								messageBeans);
						lv_info_list.setAdapter(homeMyListAdapter);
					}
					
				}

			}, arg2,R.drawable.deletemssage);

		}

		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (HomeMyListAdapter.isComment[arg2]) {

			Intent intent = new Intent(getActivity(), CommentActivity.class);
			getActivity().startActivity(intent);
		} else {
			Intent intent = new Intent(getActivity(), MessageActivity.class);
			// 传递数据
			intent.putExtra("username", messageBeans.get(arg2).getToUser());
			intent.putExtra("name", messageBeans.get(arg2).getToName());
			intent.putExtra("headimage", messageBeans.get(arg2).getHeadImage());
			getActivity().startActivity(intent);
		}
	}

	@Override
	public void onResume() {
		Log.v("getHere", "agin");
		getdata();
		super.onResume();
	}

}
