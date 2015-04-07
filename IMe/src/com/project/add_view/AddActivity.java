package com.project.add_view;

/**
 * 发帖
 */

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.add_controller.AddController;
import com.project.add_model.AddBean;
import com.project.add_model.AddListBean;
import com.project.iwant.R;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveListBean;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.iwant_or_ihave_view.IhaveFragment;
import com.project.iwant_or_ihave_view.IwantFragment;
import com.utils.app.StringUtils;
import com.utils.app.TimestampTool;
import com.utils.app.UIHelper;
import com.utils.http.LocationUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;

public class AddActivity extends BaseActivity implements OnClickListener {
	private CheckBox addIwantCb, addIhaveCb;
	private EditText addContentEv;
	private SeekBar peopleSB;
	private Button addOkBtn;
	private String result = null, addUsername = null, addContent = "",
			addPeople = null;
	private String addType = "0";
	private TextView addAddressTv;
	private LinearLayout returnBtn;
	private TextView add_people_current_num, add_people_current_num1;
	private Button add_btt_ktv1, add_btt_ktv2, add_btt_ktv3, add_btt_ktv4,
			add_btt_ktv5;
	private Configuration configuration;
	private AddListBean addListBean=new AddListBean();

	public AddActivity() {
		super("添加");

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		initView();
		setListener();
		initConfiguration(AddActivity.this,"AddActivity");
		peopleSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// progress为当前数值的大小
				add_people_current_num.setText(String.valueOf(progress));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// author:ll Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// author:ll Auto-generated method stub
			}

		});

	}


	@Override
	public void initConfiguration(BaseActivity viewActivity,String activityName) {
		super.initConfiguration(viewActivity,activityName);
		configuration = getConfiguration();
		configuration.setClassName(AddListBean.class);
	}

	@Override
	public void initView() {
		add_people_current_num = (TextView) this
				.findViewById(R.id.add_people_current_num);
		add_people_current_num1 = (TextView) this
				.findViewById(R.id.add_people_current_num1);
		addAddressTv = (TextView) this.findViewById(R.id.add_address_tv);
		addIwantCb = (CheckBox) this.findViewById(R.id.add_iwant_cb);
		addIhaveCb = (CheckBox) this.findViewById(R.id.add_ihave_cb);
		add_btt_ktv1 = (Button) this.findViewById(R.id.add_btt_ktv1);
		add_btt_ktv2 = (Button) this.findViewById(R.id.add_btt_ktv2);
		add_btt_ktv3 = (Button) this.findViewById(R.id.add_btt_ktv3);
		add_btt_ktv4 = (Button) this.findViewById(R.id.add_btt_ktv4);
		add_btt_ktv5 = (Button) this.findViewById(R.id.add_btt_ktv5);
		returnBtn = (LinearLayout) this.findViewById(R.id.add__title_back_ll);
		addContentEv = (EditText) this.findViewById(R.id.add_new_tv);
		peopleSB = (SeekBar) this.findViewById(R.id.add_people_num_sb);
		addOkBtn = (Button) this.findViewById(R.id.add_ok_btn);
		add_people_current_num.setText("0");
		add_people_current_num1.setText("5");
		addAddressTv.setText(LocationUtils.getAddress());
	}

	@Override
	public void setListener() {
		addIwantCb.setOnClickListener(this);
		addIhaveCb.setOnClickListener(this);
		addOkBtn.setOnClickListener(this);
		add_btt_ktv1.setOnClickListener(this);
		add_btt_ktv2.setOnClickListener(this);
		add_btt_ktv3.setOnClickListener(this);
		add_btt_ktv4.setOnClickListener(this);
		add_btt_ktv5.setOnClickListener(this);
		returnBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.add_iwant_cb:
			if (addIwantCb.isChecked()) {
				addIhaveCb.setChecked(false);
				addType = "0";
			} else {
				addIhaveCb.setChecked(true);
				addType = "1";
			}
			break;
		case R.id.add_ihave_cb:
			if (addIhaveCb.isChecked()) {
				addIwantCb.setChecked(false);
				addType = "1";
			} else {
				addIwantCb.setChecked(true);
				addType = "0";
			}
			break;
		case R.id.add_ok_btn:
			addContent="";
			// 发布 yes
			addContent = addContentEv.getText().toString();
			if (StringUtils.isEmpty(addContent)) {
				UIHelper.ToastMessage(AddActivity.this, "请输入愿望");
				return;
			}
			/*if (peopleSB.getProgress() == 0) {
				UIHelper.ToastMessage(AddActivity.this, "人数不能为0");
				return;
			}*/
			DefineDisplayView.showLoadingDialog(AddActivity.this);
			addPeople = 5+"";
			ArrayList<AddBean> addBeans=new ArrayList<AddBean>();
			AddBean addBean=new AddBean();
			addBean.setUsername(SharePreferenceUtils.getIntance(AddActivity.this).getCurrentUserName());
			addBean.setAddress(addAddressTv.getText().toString());
			addBean.setAddtime((TimestampTool.getCurrentTime())+"");
			addBean.setContent(addContent);
			addBean.setLat(LocationUtils.getLatitude());
			addBean.setLng(LocationUtils.getLongitude());
			addBean.setStatus(0);
			addBean.setType(Integer.parseInt(addType));
			addBean.setAllpeople(Integer.parseInt(addPeople));
			addBean.setNowpeople(0);
			addBeans.add(addBean);
			addListBean.setArray(addBeans);
			configuration.setViewDataObject(addListBean);
			new AddController(configuration).addPublishInfo();
			break;
		case R.id.add_btt_ktv1:
			addContent = addContent + " " + "钱柜KTV";
			addContentEv.setText(addContent);
			break;
		case R.id.add_btt_ktv2:
			addContent = addContent + " " + "秀玉红茶坊";
			addContentEv.setText(addContent);
			break;
		case R.id.add_btt_ktv3:
			addContent = addContent + " " + "欢乐谷";
			addContentEv.setText(addContent);
			break;
		case R.id.add_btt_ktv4:
			addContent = addContent + " " + "金汉斯";
			addContentEv.setText(addContent);
			break;
		case R.id.add_btt_ktv5:
			addContent = addContent + " " + "自助餐";
			addContentEv.setText(addContent);
			break;
		case R.id.add__title_back_ll:
			finish();
			break;
		default:
			break;
		}

	}
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		addListBean=(AddListBean)object;
		IwantAndIhaveListBean iwant=new IwantAndIhaveListBean();
		ArrayList<IwantAndIhaveBean> list=new ArrayList<IwantAndIhaveBean>();
		IwantAndIhaveBean iwantAndIhaveBean=new IwantAndIhaveBean();
		iwantAndIhaveBean.setAddress(addListBean.getArray().get(0).getAddress());
		iwantAndIhaveBean.setAddtime(addListBean.getArray().get(0).getAddtime());
		iwantAndIhaveBean.setUsername(addListBean.getArray().get(0).getUsername());
		iwantAndIhaveBean.setLat(addListBean.getArray().get(0).getLat());
		iwantAndIhaveBean.setLng(addListBean.getArray().get(0).getLng());
		iwantAndIhaveBean.setContent(addListBean.getArray().get(0).getContent());
		iwantAndIhaveBean.setHeadimg(addListBean.getArray().get(0).getHeadimg());
		iwantAndIhaveBean.setType(addListBean.getArray().get(0).getType());
		iwantAndIhaveBean.setNowpeople(addListBean.getArray().get(0).getNowpeople());
		ArrayList<MeApply> meApplies=new ArrayList<MeApply>();
		iwantAndIhaveBean.setApply(meApplies);
		Log.v("nowpeople", iwantAndIhaveBean.getNowpeople()+"");
		list.add(iwantAndIhaveBean);
		iwant.setArray(list);
		if(addType.equals("0")){
			// 发布成功之后，直接刷新我要界面
			//IwantFragment.addSuccessUpdateUi(iwant);
			IwantFragment.loadData(0);
			
		}else {
			// 直接刷新我有界面
			//IhaveFragment.addSuccessUpdateUi(iwant);
			IhaveFragment.loadData(0);
		}
		finish();
	}
}
