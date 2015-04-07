package com.project.myself_view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.project.add_view.NewAddActivity;
import com.project.adpter.NearByInfoListAdapter;
import com.project.iwant.R;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_model.MyselfInfoCommentList;
import com.project.myself_model.NearByInfoBean;
import com.project.myself_model.NearByInfoComment;
import com.project.myself_model.NearByInfoCommentList;
import com.project.myself_model.NearByInfoListBean;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.InputMessageView.IsendMessage;
import com.utils.widght.ListViewUtils;
import com.utils.widght.SrollViewListView;

public class NearbyActivity extends BaseActivity implements OnClickListener {
	String[] imageUrls = Constants.IMAGES;
	DisplayImageOptions options;
	private SrollViewListView svl_list;
	private LinearLayout btn_return_exit;
	private static NearByInfoListBean nearByInfoListBean = new NearByInfoListBean();
	private static Configuration configuration;
	private static Button btn_publish_or_add;
	private static int comment_or_nearby=0;
	private static  Context context;
	public NearbyActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);
		context= NearbyActivity.this;
		options = InitDisplayImageOptions.getInstance().getOptions(true, true,
				0);
		initView();
		setListener();
		initConfiguration(NearbyActivity.this, "NearbyActivity");
		getNearData();
		
	}

	public static  void getNearData() {
		DefineDisplayView.showLoadingDialog(context);
		comment_or_nearby=0;
		ArrayList<NearByInfoBean> nearByInfoBeans = new ArrayList<NearByInfoBean>();
		NearByInfoBean nearByInfoBean = new NearByInfoBean();
		nearByInfoBean.setUser(SharePreferenceUtils.getIntance(
				context).getCurrentUserName());
		nearByInfoBean.setLat(Double.parseDouble(SharePreferenceUtils
				.getIntance(context).getLocationLat()));
		nearByInfoBean.setLng(Double.parseDouble(SharePreferenceUtils
				.getIntance(context).getLocationLng()));
		nearByInfoBeans.add(nearByInfoBean);
		nearByInfoListBean.setArray(nearByInfoBeans);
		configuration.setClassName(NearByInfoListBean.class);
		configuration.setViewDataObject(nearByInfoListBean);
		new MyselfInfoController(configuration).getNearByInfo();

	}

	@Override
	public void initView() {
		btn_return_exit = (LinearLayout) findViewById(R.id.setup_return_btns);
		svl_list = (SrollViewListView) findViewById(R.id.myinfo_list);
		btn_publish_or_add=(Button)findViewById(R.id.gebi1_public);
	}

	@Override
	public void setListener() {
		btn_return_exit.setOnClickListener(this);
		btn_publish_or_add.setOnClickListener(this);

	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (comment_or_nearby==0) {
			nearByInfoListBean = (NearByInfoListBean) object;
			svl_list.setAdapter(new NearByInfoListAdapter(context,
					imageUrls, options, nearByInfoListBean,new IsendMessage() {
						
						@Override
						public void sendNearComment(NearByInfoComment nearByInfoComment) {
							comment_or_nearby=1;
							NearByInfoCommentList nearByInfoCommentList =new NearByInfoCommentList();
							ArrayList<NearByInfoComment> myselfInfoComments=new ArrayList<NearByInfoComment>();
							myselfInfoComments.add(nearByInfoComment);
							nearByInfoCommentList.setArray(myselfInfoComments);
							configuration.setClassName(MyselfInfoCommentList.class);
							configuration.setViewDataObject(nearByInfoCommentList);
							new MyselfInfoController(configuration).addNearByComment();
						}
						
						@Override
						public void sendMyselfComment(MyselfInfoComment myselfInfoComment) {
							// TODO Auto-generated method stub
							
						}
					}));
			//ListViewUtils.setListViewHeightBasedOnChildren(svl_list);
		}else {
			getNearData();
		}
		
	}
	@Override
	public void requestDataIsNull(String objectString) {
		super.requestDataIsNull(objectString);
		getNearData();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.setup_return_btns:
			finish();
			break;
		case R.id.gebi1_public:
			Intent intent = new Intent(context,
					NewAddActivity.class);
			intent.putExtra("flag", 2);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	
	

}
