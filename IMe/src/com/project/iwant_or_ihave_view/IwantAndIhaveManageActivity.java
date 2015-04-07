package com.project.iwant_or_ihave_view;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.adpter.HomeMyListAdapter;
import com.project.adpter.IwantAndIhaveManageAdapter;
import com.project.iwant.R;
import com.project.iwant_or_ihave_controller.IwantController;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveListBean;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.message_view.MessageHandlerPPW;
import com.project.message_view.MessageHandlerPPW.IApply;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.MainListView;

/** 信息发布管理，添加删除等操作
 * @author liyuan
 *
 */
public class IwantAndIhaveManageActivity  extends BaseActivity implements OnItemClickListener{
	private LinearLayout setup_return_btns;
	private TextView so_iteam_tv;
	private ListView so_result_lv;
	private IwantAndIhaveListBean iwantAndIhaveListBean=new IwantAndIhaveListBean();
	private static IwantAndIhaveManageAdapter iwantadaAdapter;
	private Configuration configuration;
	private int getdata=0;
	
	public IwantAndIhaveManageActivity() {
		super("");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iwantandihavemanager);
		initConfiguration(IwantAndIhaveManageActivity.this,"IwantAndIhaveManageActivity");
		
		setup_return_btns = (LinearLayout) this.findViewById(R.id.setup_return_btns);
		so_iteam_tv = (TextView) this.findViewById(R.id.so_iteam_tv);
		so_result_lv = (ListView) findViewById(R.id.own_post_lv);
		setup_return_btns.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		so_result_lv.setOnItemClickListener(this);
		loadData();
	}
	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration=getConfiguration();
	}
	
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (getdata==1) {
			loadData();
		}else {
		iwantAndIhaveListBean=(IwantAndIhaveListBean)object;
		iwantadaAdapter=new IwantAndIhaveManageAdapter(IwantAndIhaveManageActivity.this, iwantAndIhaveListBean.getArray(),new IMeApply() {
			
			@Override
			public void applySatisfyDemand(MeApply meApply) {
   				
			}
		});
		so_result_lv.setAdapter(iwantadaAdapter);
		}
	}
	
	
	private void loadData() {
		Log.v("here", "here");
		getdata=0;
		DefineDisplayView.showLoadingDialog(IwantAndIhaveManageActivity.this);
		ArrayList<IwantAndIhaveBean> iwantBeans=new ArrayList<IwantAndIhaveBean>();
		IwantAndIhaveBean iwantBean=new IwantAndIhaveBean();
		iwantBean.setUsername(SharePreferenceUtils.getIntance(IwantAndIhaveManageActivity.this).getCurrentUserName());
		iwantBeans.add(iwantBean);
		iwantAndIhaveListBean.setArray(iwantBeans);
		configuration.setClassName(IwantAndIhaveListBean.class);
		configuration.setViewDataObject(iwantAndIhaveListBean);
		new IwantController(configuration).getOwnPost();
		
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		MessageHandlerPPW.startPopWin(IwantAndIhaveManageActivity.this, arg1, new IApply() {

			@Override
			public void refuse(String postid) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void apply(int position) {
				Log.v("here", "here");
				IwantAndIhaveListBean deleteAndIhaveListBean=new IwantAndIhaveListBean();
				ArrayList<IwantAndIhaveBean> iwantBeans=new ArrayList<IwantAndIhaveBean>();
				IwantAndIhaveBean iwantBean=new IwantAndIhaveBean();
				iwantBean.setUsername(SharePreferenceUtils.getIntance(IwantAndIhaveManageActivity.this).getCurrentUserName());
				iwantBean.setId(iwantAndIhaveListBean.getArray().get(position).getId());
				iwantBean.setAddtime(iwantAndIhaveListBean.getArray().get(position).getAddtime());
				iwantBeans.add(iwantBean);
				deleteAndIhaveListBean.setArray(iwantBeans);
				configuration.setClassName(IwantAndIhaveListBean.class);
				configuration.setViewDataObject(deleteAndIhaveListBean);
				new IwantController(configuration).removePost();
				getdata=1;
			}

		}, arg2,R.drawable.delelteiwant);
	}

}
