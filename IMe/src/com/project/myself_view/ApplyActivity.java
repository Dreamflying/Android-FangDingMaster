package com.project.myself_view;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.adpter.ApplyListAdapter;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.iwant_or_ihave_model.MeApplyList;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_view.ApplyHandlerPPW.IApply;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;

/**
 * @description 申请界面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-3下午3:35:53
 */
public class ApplyActivity extends BaseActivity {
	private LinearLayout btn_return;
	private static ListView lv_apply_list;
	private static Context mContext;
	private static ProgressBar pb_proBar;
	private static ApplyListAdapter applyListAdapter;
	private MeApplyList list;
	private Configuration configuration;
	private int isApply = 0;

	public ApplyActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apply);
		initView();
		setListener();
		initConfiguration(ApplyActivity.this, "ApplyActivity");
		getData(0, "");
	}

	private void getData(int status, String postid) {
		isApply = 0;
		list = new MeApplyList();
		pb_proBar.setVisibility(View.GONE);
		DefineDisplayView.showLoadingDialog(ApplyActivity.this);
		ArrayList<MeApply> meApplies = new ArrayList<MeApply>();
		MeApply meApply = new MeApply();
		meApply.setTouser(SharePreferenceUtils.getIntance(ApplyActivity.this)
				.getCurrentUserName());
		meApply.setStatus(status);
		if (!postid.equals("")) {
			meApply.setId(Integer.parseInt(postid));
		}
		meApplies.add(meApply);
		list.setArray(meApplies);
		configuration.setClassName(MeApplyList.class);
		configuration.setViewDataObject(list);
		switch (status) {
		case 0:
			new MyselfInfoController(configuration).getApplyInfo();
			break;
		default:
			new MyselfInfoController(configuration).dealApply();
			break;
		}

	}

	@Override
	public void initView() {
		pb_proBar = (ProgressBar) findViewById(R.id.loading_process);
		lv_apply_list = (ListView) findViewById(R.id.apply_lv);
		btn_return = (LinearLayout) findViewById(R.id.setup_return_btns);
	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (isApply == 0) {
			lv_apply_list.setVisibility(View.VISIBLE);
			list = (MeApplyList) object;
			applyListAdapter = new ApplyListAdapter(ApplyActivity.this, list,
					new IApply() {

						@Override
						public void refuse(String postid) {
							// 回调拒绝
							getData(2, postid);
							isApply = 2;
						}

						@Override
						public void apply(String postid) {
							// 回调申请
							getData(1, postid);
							isApply = 1;

						}
					});
			lv_apply_list.setAdapter(applyListAdapter);
		} else {
			getData(0, "");
		}
	}
	
	@Override
	public void requestErrorCode(String eString) {
		super.requestErrorCode(eString);
		lv_apply_list.setVisibility(View.GONE);
	}

	@Override
	public void setListener() {
		btn_return.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

	}
}
