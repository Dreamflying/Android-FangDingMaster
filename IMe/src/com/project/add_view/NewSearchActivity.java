package com.project.add_view;

import java.util.ArrayList;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.open_demo.main.MessageFragment;
import com.project.adpter.NewIwantListAdapter;
import com.project.adpter.NewIwantListAdapter.Iigonre;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.iwant_or_ihave_controller.IwantController;
import com.project.iwant_or_ihave_model.IgnoreBean;
import com.project.iwant_or_ihave_model.IgnoreListBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveListBean;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.iwant_or_ihave_view.IMeApply;
import com.project.message_view.NewMessageFragment;
import com.utils.chat.ChatServiceUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.XListView;

/**
 * new search
 * 
 * @author liyuan
 * 
 */
public class NewSearchActivity extends BaseActivity {

	public NewSearchActivity() {
		super("");
	}

	private LinearLayout backLayout;
	private XListView xListView;
	private TextView friend_name_tv;

	private String getIntentContent = null;
	private Configuration configuration;
	private IwantAndIhaveListBean ihaveListBean = new IwantAndIhaveListBean();
	private NewIwantListAdapter iwantListAdapter;
	private boolean isLocal = false;
	private int isLick = 0;
	private static int isIgnore=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_search);
		initConfiguration(NewSearchActivity.this, "NewSearchActivity");
		initView();
		setListener();
		getData();
	}

	public void getData() {
		 isIgnore=0;
		DefineDisplayView.showLoadingDialog(NewSearchActivity.this);
		if (isLick == 1) {
			ArrayList<IwantAndIhaveBean> iwantBeans = new ArrayList<IwantAndIhaveBean>();
			IwantAndIhaveBean iwantBean = new IwantAndIhaveBean();
			iwantBean.setUsername(SharePreferenceUtils.getIntance(
					NewSearchActivity.this).getCurrentUserName());
			iwantBean.setSex(SharePreferenceUtils.getIntance(
					NewSearchActivity.this).getSex()+"");
			iwantBean.setLat(SharePreferenceUtils.getIntance(
					NewSearchActivity.this).getLocationLat());
			iwantBean.setLng(SharePreferenceUtils.getIntance(
					NewSearchActivity.this).getLocationLng());
			iwantBeans.add(iwantBean);
			ihaveListBean.setArray(iwantBeans);
			configuration.setClassName(IwantAndIhaveListBean.class);
			configuration.setViewDataObject(ihaveListBean);
			new IwantController(configuration).getMatchContent();
			
			

		} else{
			ArrayList<IwantAndIhaveBean> iwantBeans=null;
			
		/*	iwantBean.setNickname(SharePreferenceUtils.getIntance(
					NewSearchActivity.this).getCurrentNickName());*/
			if (isLocal) {
				iwantBeans = new ArrayList<IwantAndIhaveBean>();
				IwantAndIhaveBean iwantBean = new IwantAndIhaveBean();
				iwantBean.setGpoint(1);
				iwantBean.setLat(SharePreferenceUtils.getIntance(
						NewSearchActivity.this).getLocationLat());
				iwantBean.setLng(SharePreferenceUtils.getIntance(
						NewSearchActivity.this).getLocationLng());
				iwantBean.setNickname(getIntentContent);
				iwantBeans.add(iwantBean);
				ihaveListBean.setArray(iwantBeans);
				configuration.setClassName(IwantAndIhaveListBean.class);
				configuration.setViewDataObject(ihaveListBean);
				new IwantController(configuration).wishSearch();
			} else {
				iwantBeans = new ArrayList<IwantAndIhaveBean>();
				IwantAndIhaveBean iwantBean = new IwantAndIhaveBean();
				iwantBean.setUsername(SharePreferenceUtils.getIntance(
						NewSearchActivity.this).getCurrentUserName());
				iwantBean.setGpoint(0);
				iwantBean.setLat(SharePreferenceUtils.getIntance(
						NewSearchActivity.this).getLocationLat());
				iwantBean.setLng(SharePreferenceUtils.getIntance(
						NewSearchActivity.this).getLocationLng());
				iwantBean.setContent(getIntentContent);
				iwantBeans.add(iwantBean);
				ihaveListBean.setArray(iwantBeans);
				configuration.setClassName(IwantAndIhaveListBean.class);
				configuration.setViewDataObject(ihaveListBean);
				new IwantController(configuration).wishSearch();
			}
			
			
		}

	}

	@Override
	public void initView() {
		Intent intent = getIntent();
		getIntentContent = intent.getStringExtra("searchcontent");
		isLocal=intent.getBooleanExtra("isLocal", false);
		isLick = intent.getIntExtra("isLike", 0);
		backLayout = (LinearLayout) findViewById(R.id.back);
		xListView = (XListView) findViewById(R.id.new_iwant_lv);
		friend_name_tv = (TextView) findViewById(R.id.friend_name_tv);
		xListView.setPullRefreshEnable(false);
		xListView.setPullLoadEnable(false);
		if (isLick == 1) {
			friend_name_tv.setText("感兴趣");
		} else {
			friend_name_tv.setText("搜索结果");
		}

	}

	@Override
	public void setListener() {
		super.setListener();
		backLayout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (isIgnore==0) {
			
		
		ihaveListBean = (IwantAndIhaveListBean) object;
		iwantListAdapter = new NewIwantListAdapter(NewSearchActivity.this,
				ihaveListBean.getArray(), new IMeApply() {

					@Override
					public void applySatisfyDemand(MeApply meApply) {
						// TODO Auto-generated method stub

					}
				}

		,new Iigonre() {
			
			@Override
			public void ignore(int position) {
				if (ihaveListBean.getArray().size()!=0) {
					IgnoreListBean ignoreListBean=new IgnoreListBean();
					ArrayList<IgnoreBean> ignoreBeans=new ArrayList<IgnoreBean>();
					IgnoreBean ignoreBean=new IgnoreBean();
					ignoreBean.setContent(ihaveListBean.getArray().get(position).getContent());
					ignoreBean.setGetuser(SharePreferenceUtils.getIntance(NewSearchActivity.this).getCurrentUserName());
					ignoreBean.setTouser(ihaveListBean.getArray().get(position).getUsername());
					ignoreBean.setStatus(2);
					ignoreBean.setPostsid(ihaveListBean.getArray().get(position).getId());
					ignoreBeans.add(ignoreBean);
					ignoreListBean.setArray(ignoreBeans);
					configuration.setClassName(IgnoreListBean.class);
					configuration.setViewDataObject(ignoreListBean);
					new IwantController(configuration).ignorePost();
					isIgnore=1;
					ihaveListBean.getArray().remove(position);
					iwantListAdapter.notifyDataSetChanged();
					}
				
			}
		});
		xListView.setAdapter(iwantListAdapter);
		if (isLick==1) {
			SharePreferenceUtils.getIntance(NewSearchActivity.this).saveLinkNum(0);
			if (ChatServiceUtils.isMessageFragment) {
		    	Message message=new Message();
		    	message.what=ChatServiceUtils.FLASH_MAIN_MESSAGE;
		    	NewMainTabActivity.mMainControl.sendMessage(message);
		    	Message myFMessage=new Message();
		    	myFMessage.what=ChatServiceUtils.FLUSH_MESSAGE;
		    	MessageFragment.mMainFragmentControl.sendMessage(myFMessage);
		    	}
		}
		   
		}
	}
}
