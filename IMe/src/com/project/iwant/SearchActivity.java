package com.project.iwant;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.adpter.IwantAndIhaveListAdapter;
import com.project.iwant_or_ihave_controller.IwantController;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveListBean;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.iwant_or_ihave_model.MeApplyList;
import com.project.iwant_or_ihave_view.IMeApply;
import com.utils.app.TimestampTool;
import com.utils.app.UIHelper;
import com.utils.http.LocationUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.MainListView;
import com.utils.widght.MainListView.OnRefreshListener;

/**
 *  @description 搜索界面
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-4下午3:09:27
 */
public class SearchActivity extends BaseActivity {

	

	private EditText so_edit;
	private Button so_butt;
	private LinearLayout so_title_return_btns;
	private String searchWords;
	private MainListView so_result_lv;
	private IwantAndIhaveListBean iwantAndIhaveListBean=new IwantAndIhaveListBean();
	private static IwantAndIhaveListAdapter iwantadaAdapter;
	private static int dataObjectFlag=0;//0表示，主list，1表示从list
	private Configuration configuration;
	public SearchActivity() {
		super("");
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_so);
		initConfiguration(SearchActivity.this,"SearchActivity");
		so_title_return_btns = (LinearLayout) findViewById(R.id.so_title_return_btns);
		so_result_lv = (MainListView) findViewById(R.id.so_result_lv);
		so_result_lv.setonRefreshListener(new SoRefreshListener());
		so_edit = (EditText) this.findViewById(R.id.so_edit);
		so_butt = (Button) this.findViewById(R.id.so_butt);
		so_butt.setOnClickListener(new SearchOnClickListener());
		so_title_return_btns.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SearchActivity.this.finish();
			}

		});
	}
	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration=getConfiguration();
	}

	private class SoRefreshListener implements OnRefreshListener {

		@Override
		public void onRefresh() {
			loadData();
		}

		@Override
		public void onLoadMore() {
			
		}


	}
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (dataObjectFlag==0) {
			Log.v("dataObjectFlag", "dataObjectFlag"+dataObjectFlag);
			iwantAndIhaveListBean=(IwantAndIhaveListBean)object;
			iwantadaAdapter=new IwantAndIhaveListAdapter(SearchActivity.this, iwantAndIhaveListBean.getArray(),new IMeApply() {
				
				@Override
				public void applySatisfyDemand(MeApply meApply) {
				   dataObjectFlag=1;
	               ArrayList<MeApply> meApplies=new ArrayList<MeApply>();
	               meApplies.add(meApply);
	               MeApplyList meApplyList=new MeApplyList();
	               meApplyList.setArray(meApplies);
	               configuration.setClassName(MeApplyList.class);
	               configuration.setViewDataObject(meApplyList);
	               new IwantController(configuration).applyUserSelection();
	   				
				}
			});
			iwantadaAdapter.setNumLength(iwantAndIhaveListBean.getArray().size());
			so_result_lv.setAdapter(iwantadaAdapter);
			so_result_lv.onRefreshComplete();	
		}
		
	}

	private class SearchOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View arg0) {
			
			searchWords = so_edit.getText().toString();
			if (null != searchWords && !"".equals(searchWords)) {
				if (!"".equals(searchWords.trim())) {
					DefineDisplayView.showLoadingDialog(SearchActivity.this);
					loadData();
					hideSoftKeyboard();
				} else {
					UIHelper.ToastMessage(SearchActivity.this,
							"亲，请输入搜索关键字...");
				}
			} else {
				UIHelper.ToastMessage(SearchActivity.this,
						"亲，请输入搜索关键字...");
			}
		}

	}

	private void loadData() {
		ArrayList<IwantAndIhaveBean> iwantBeans=new ArrayList<IwantAndIhaveBean>();
		IwantAndIhaveBean iwantBean=new IwantAndIhaveBean();
		iwantBean.setContent(searchWords);
		iwantBeans.add(iwantBean);
		iwantAndIhaveListBean.setArray(iwantBeans);
		configuration.setClassName(IwantAndIhaveListBean.class);
		configuration.setViewDataObject(iwantAndIhaveListBean);
		new IwantController(configuration).searchInfo();
		dataObjectFlag=0;
		
	}

	private void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	

	
}
