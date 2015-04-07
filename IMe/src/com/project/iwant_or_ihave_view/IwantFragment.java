package com.project.iwant_or_ihave_view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.base.model.Configuration;
import com.base.view.BaseFragment;
import com.project.adpter.IwantAndIhaveListAdapter;
import com.project.iwant.R;
import com.project.iwant_or_ihave_controller.IwantController;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveListBean;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.iwant_or_ihave_model.MeApplyList;
import com.utils.app.UIHelper;
import com.utils.http.LocationUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.MainListView;
import com.utils.widght.MainListView.OnRefreshListener;
import com.utils.widght.XListView.IXListViewListener;
import com.utils.widght.XListView;

/**
 * @description 我要界面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-2上午10:49:10
 */
@SuppressLint("NewApi") public class IwantFragment extends BaseFragment  implements OnItemClickListener{
	private static final String TEXT_CHAT = "CHAT";
	private static XListView mlv_iwant;
	private static IwantAndIhaveListAdapter iwantadaAdapter;
	public  static IwantAndIhaveListBean iwantListBean=new IwantAndIhaveListBean();
	public  static  ArrayList<IwantAndIhaveBean> iwantBeanslist=new ArrayList<IwantAndIhaveBean>();
    private static Configuration configuration;
    private static int dataObjectFlag=0;//0表示，主list，1表示从list
    private static Context context;
    private static int index;
    private static int moreLoad=0;
	public IwantFragment() {
	}

	public IwantFragment(String title) {
		super(title);
	}

	/**
	 * Factory method to generate a new instance of the fragment given a string
	 * .
	 * 
	 * @param char 主页面要传过来的信息
	 * @return A new instance of MyFragment with chat extras
	 */
	public static IwantFragment newInstance(String chat) {
		final IwantFragment f = new IwantFragment();
		final Bundle args = new Bundle();
		args.putString(TEXT_CHAT, chat);
		f.setArguments(args);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		moreLoad=0;
		initConfiguration(IwantFragment.this);
		context=getActivity();
		if (SharePreferenceUtils.getIntance(getActivity()).getLocationLat().equals("")||SharePreferenceUtils.getIntance(getActivity()).getLocationLat()==null) {
			LocationUtils.getInstance(getActivity()).getLocationUseBaiDuSDK();
			UIHelper.ToastMessage(getActivity(), "请滑动界面刷新");
		}else {
			loadData(0);
		}
		
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_iwant, container, false);
		initView(view);
		setListener();
		return view;
	}

	@Override
	public void setListener() {
   mlv_iwant.setPullLoadEnable(true);
		
		mlv_iwant.setXListViewListener(new IXListViewListener(){

			@Override
			public void onRefresh() {
				iwantBeanslist.clear();
				moreLoad=0;
				reflesh(0);
			}

			@Override
			public void onLoadMore() {
				moreLoad=1;
				index=index-15;
				loadData(index);
				
			}});
		
	
	
	}
	
	public  static void loadData(int index) {
		//iwantListBean=new IwantAndIhaveListBean();
		if(index==0){
			iwantBeanslist.clear();
		}
		DefineDisplayView.showLoadingDialog(context);
		ArrayList<IwantAndIhaveBean> iwantBeans=new ArrayList<IwantAndIhaveBean>();
		IwantAndIhaveBean iwantBean=new IwantAndIhaveBean();
		iwantBean.setUsername(SharePreferenceUtils.getIntance(context).getCurrentUserName());
		iwantBean.setLat(SharePreferenceUtils.getIntance(context).getLocationLat());
		iwantBean.setLng(SharePreferenceUtils.getIntance(context).getLocationLng());
		iwantBean.setType(0);
		iwantBean.setId(index);
		iwantBeans.add(iwantBean);
		iwantListBean.setArray(iwantBeans);
		configuration.setClassName(IwantAndIhaveListBean.class);
		configuration.setViewDataObject(iwantListBean);
		new IwantController(configuration).getIwantInfo();
		dataObjectFlag=0;
		
	}
@Override
public void initConfiguration(BaseFragment fragment) {
	super.initConfiguration(fragment);
	configuration=getConfiguration();
}
	


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	@SuppressLint("NewApi") @Override
	public void initView(View view) {
		mlv_iwant=(XListView)view.findViewById(R.id.iwant_lv);
		mlv_iwant.setOnItemClickListener(this); 
		String str = getArguments() != null ? getArguments().getString(
				TEXT_CHAT) : null;
		if (str != null) {

		} else {

		}
		/*mlv_iwant.setState(3);
		mlv_iwant.setonRefreshListener(new OnRefreshListener(){

			@Override
			public void onRefresh() {
				Log.v("here", "onrefresh");
				reflesh();
				testList.add("XXX");
				testList.add("XXX");
				iwantadaAdapter.setNumLength(testList.size());
				iwantadaAdapter.notifyDataSetChanged();
				
			}

			@Override
			public void onLoadMore() {
				Log.v("here", "onLoadMore");
				
			}
			
			
		}
		);*/
	}
	
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (dataObjectFlag==0) {
			IwantAndIhaveListBean iwantAndIhaveListBean=(IwantAndIhaveListBean)object;
			iwantBeanslist.addAll(iwantAndIhaveListBean.getArray());
			Log.v("iwantBeanslist.size", iwantBeanslist.size()+"");
			iwantListBean.setArray(iwantBeanslist);
			if (iwantAndIhaveListBean.getArray().size()!=0&&iwantAndIhaveListBean.getArray()!=null) {
				index=iwantAndIhaveListBean.getArray().get(iwantAndIhaveListBean.getArray().size()-1).getId();
			}
/*			for (int i = 0; i < iwantListBean.getArray().size(); i++) {
				Log.d("iwantListBean.getArray().Size", iwantListBean.getArray().get(i).getNickname()+"");
			}
*/			iwantadaAdapter=new IwantAndIhaveListAdapter(getActivity(), iwantListBean.getArray(),new IMeApply() {
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
			mlv_iwant.setAdapter(iwantadaAdapter);
			if (moreLoad==1) {
				mlv_iwant.setSelection(iwantBeanslist.size()-iwantAndIhaveListBean.getArray().size());
			}
			Log.d("iwantListBean.getArray().Size", iwantListBean.getArray().size()+"");
 			iwantadaAdapter.setNumLength(iwantListBean.getArray().size());
			iwantadaAdapter.notifyDataSetChanged();
			//mlv_iwant.onRefreshComplete();	
			mlv_iwant.stopRefresh();
			mlv_iwant.stopLoadMore();
		}
		
	};

	@Override
	public void requestTimeout(String timeoutString) {
		super.requestTimeout(timeoutString);
		mlv_iwant.stopRefresh();
		mlv_iwant.stopLoadMore();
	}
	
	@Override
	public void requestServerError(String serverError) {
		super.requestServerError(serverError);
		mlv_iwant.stopRefresh();
		mlv_iwant.stopLoadMore();
	}
		
	@Override
	public void requestErrorCode(String eString) {
		super.requestErrorCode(eString);
		mlv_iwant.stopRefresh();
		mlv_iwant.stopLoadMore();
		index=index-15;
	}
		
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.v("here", ""+arg2);
		
	}

	/**
	 *@function  
	 * @time 2014-10-14下午11:57:36
	 * void
	 */
	public static void addSuccessUpdateUi(IwantAndIhaveListBean ihaveListBean){
	
			loadData(index);
		
		
	}
	public void reflesh(int index){
		iwantBeanslist.clear();
		ArrayList<IwantAndIhaveBean> iwantBeans=new ArrayList<IwantAndIhaveBean>();
		IwantAndIhaveBean iwantBean=new IwantAndIhaveBean();
		iwantBean.setUsername(SharePreferenceUtils.getIntance(context).getCurrentUserName());
     	iwantBean.setLat(SharePreferenceUtils.getIntance(context).getLocationLat());
		iwantBean.setLng(SharePreferenceUtils.getIntance(context).getLocationLng());
		iwantBean.setType(0);
		iwantBean.setId(index);
		iwantBeans.add(iwantBean);
		iwantListBean.setArray(iwantBeans);
		configuration.setClassName(IwantAndIhaveListBean.class);
		configuration.setViewDataObject(iwantListBean);
		new IwantController(configuration).getIwantInfo();
		dataObjectFlag=0;
	}

}
