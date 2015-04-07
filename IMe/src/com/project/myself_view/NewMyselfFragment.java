package com.project.myself_view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.view.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.project.iwant.R;
import com.project.iwant.SetActivity;
import com.project.iwant_or_ihave_view.IwantAndIhaveManageActivity;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;

/**
 * 我
 * 
 * @author liyuan
 * 
 */
public class NewMyselfFragment extends BaseFragment implements OnClickListener {

	private View rootView;// 缓存Fragment view
	private LinearLayout new_myfriends, new_myrelease, new_myneighbor,
			new_mysetting, new_myinfo, new_share;
	private TextView currentUserNickName, currentUserIdo;
	private ImageView new_img;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_new_myself, null);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		initView(rootView);
		setListener();
		return rootView;
	}

	@Override
	public void initView(View view) {
		new_myinfo = (LinearLayout) view.findViewById(R.id.L1);
		new_myfriends = (LinearLayout) view.findViewById(R.id.new_myfriends);
		new_myrelease = (LinearLayout) view.findViewById(R.id.new_myrelease);
		new_mysetting = (LinearLayout) view.findViewById(R.id.new_mysetting);
		new_myneighbor = (LinearLayout) view.findViewById(R.id.new_myneighbor);
		new_share = (LinearLayout) view.findViewById(R.id.new_share);
		new_img = (ImageView) view.findViewById(R.id.info_img);
		currentUserNickName = (TextView) view
				.findViewById(R.id.currentUserNickName);
		currentUserIdo = (TextView) view.findViewById(R.id.currentUserIdo);
		currentUserNickName.setText(SharePreferenceUtils.getIntance(
				getActivity()).getCurrentNickName());
		currentUserIdo.setText(SharePreferenceUtils.getIntance(getActivity())
				.getCurrentIdio());
		ImageLoader.getInstance()
				.displayImage(
						SharePreferenceUtils.getIntance(getActivity())
								.getHeadImgUserName(),
						new_img,
						InitDisplayImageOptions.getInstance().getHeadOptions(
								false, false, 0),
						InitDisplayImageOptions.getInstance()
								.getImageLoadingListener());

	}

	@Override
	public void setListener() {
		new_myinfo.setOnClickListener(this);
		new_myfriends.setOnClickListener(this);
		new_myrelease.setOnClickListener(this);
		new_mysetting.setOnClickListener(this);
		new_myneighbor.setOnClickListener(this);
		new_share.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.L1:
			Intent new_myinfo = new Intent(getActivity(),
					MyselfInfoActivity.class);
			startActivity(new_myinfo);
			break;
		case R.id.new_myfriends:
			Intent new_myfriends = new Intent(getActivity(),
					A_BookActivity.class);
			startActivity(new_myfriends);
			break;
		case R.id.new_myneighbor:
			Intent new_myneighbor = new Intent(getActivity(),
					NearbyActivity.class);
			startActivity(new_myneighbor);
			break;
		case R.id.new_myrelease:
			Intent new_myrelease = new Intent(getActivity(),
					IwantAndIhaveManageActivity.class);
			startActivity(new_myrelease);
			break;
		case R.id.new_mysetting:
			Intent new_mysetting = new Intent(getActivity(), SetActivity.class);
			startActivity(new_mysetting);
			break;
		case R.id.new_share:
			Intent shaIntent = new Intent(Intent.ACTION_SEND);
			// 分享的数据类型
			shaIntent.setType("text/plain");
			// 分享的主题
			shaIntent.putExtra(Intent.EXTRA_SUBJECT, "好友分享");
			// 分享的内容
			shaIntent.putExtra(Intent.EXTRA_TEXT,
					"我下载了一个好牛的软件，好好玩！ http://www.iwantme.com");
			// 允许启动新的Activity
			shaIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 目标应用寻找对话框的标题
			startActivity(Intent.createChooser(shaIntent, getActivity().getTitle()));
			break;
		default:
			break;
		}

	}
}
