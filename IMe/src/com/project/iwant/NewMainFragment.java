package com.project.iwant;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.base.model.Configuration;
import com.base.view.BaseFragment;
import com.lidroid.xutils.exception.DbException;
import com.project.adpter.NewIwantListAdapter;
import com.project.adpter.NewIwantListAdapter.Iigonre;
import com.project.iwant_or_ihave_controller.IwantController;
import com.project.iwant_or_ihave_model.FriendInfoBean;
import com.project.iwant_or_ihave_model.IgnoreBean;
import com.project.iwant_or_ihave_model.IgnoreListBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveBean;
import com.project.iwant_or_ihave_model.IwantAndIhaveListBean;
import com.project.iwant_or_ihave_model.MeApply;
import com.project.iwant_or_ihave_model.MeApplyList;
import com.project.iwant_or_ihave_view.IMeApply;
import com.project.iwant_or_ihave_view.IhaveFragment;
import com.project.message_model.FriendsBean;
import com.project.message_model.LikeBean;
import com.project.message_model.LikeBeans;
import com.utils.app.UIHelper;
import com.utils.chat.ChatServiceUtils;
import com.utils.db.SQLDatebaseUtils;
import com.utils.http.ShareTextAndPicUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.XListView;
import com.utils.widght.XListView.IXListViewListener;

/**
 * 首页
 * 
 * @author liyuan
 * 
 */
public class NewMainFragment extends BaseFragment implements
		OnItemClickListener {
	public static XListView mlv_iwant;
	public static NewIwantListAdapter iwantadaAdapter;
	public static IwantAndIhaveListBean iwantListBean = new IwantAndIhaveListBean();
	public static ArrayList<IwantAndIhaveBean> iwantBeanslist = new ArrayList<IwantAndIhaveBean>();
	private static Configuration configuration;
	private static int dataObjectFlag = 0;// 0表示，主list，1表示从list
	private static Context context;
	private static int index;
	private static int moreLoad = 0;
	private View rootView;// 缓存Fragment view
	private static int isIgnore=0;
	private static int isLikeNum=0;
	private LinearLayout down_again;
	private static Button moveTopBtn;
	public NewMainFragment() {
	}

	public NewMainFragment(String title) {
		super(title);
	}

	/**
	 * Factory method to generate a new instance of the fragment given a string
	 * .
	 * 
	 * @param char 主页面要传过来的信息
	 * @return A new instance of MyFragment with chat extras
	 */

	@SuppressLint("NewApi") public static IhaveFragment newInstance(String chat) {
		final IhaveFragment f = new IhaveFragment();
		final Bundle args = new Bundle();
		args.putString("chat", chat);
		f.setArguments(args);
		return f;
	}

	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		moreLoad = 0;
		context = getActivity();
		initConfiguration(NewMainFragment.this);
		DefineDisplayView.showLoadingDialog(getActivity());
		loadData(0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.v("onCreateView", "onCreateView");
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_main_tab, null);
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

	public void getLikeNum(){
		Log.v("isget", "get");
		LikeBeans likeBean=new LikeBeans();
		ArrayList<LikeBean> iwantBeans = new ArrayList<LikeBean>();
		LikeBean iwantBean = new LikeBean();
		iwantBean.setUsername(SharePreferenceUtils.getIntance(context)
				.getCurrentUserName());
		iwantBeans.add(iwantBean);
		likeBean.setArray(iwantBeans);
		configuration.setClassName(LikeBeans.class);
		configuration.setViewDataObject(likeBean);
		new IwantController(configuration).getMatchNumber();
		isLikeNum=1;
		
	}
	
	@Override
	public void setListener() {
		mlv_iwant.setPullLoadEnable(true);
		mlv_iwant.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				moveTopBtn.setVisibility(View.GONE);
				iwantBeanslist.clear();
				moreLoad = 0;
				reflesh(0);
			}

			@Override
			public void onLoadMore() {
				moveTopBtn.setVisibility(View.GONE);
				moreLoad = 1;
				index = index - 15;
				loadData(index);

			}
		});
		moveTopBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//ShareTextAndPicUtils.shareMsg(context, getActivity().T, "分享", "这是内容", SharePreferenceUtils.getIntance(context).getHeadImgUserName());
				mlv_iwant.setSelection(0);
			}
		});
	}

	public static void loadData(int index) {
		// DefineDisplayView.showLoadingDialog(context);
		isIgnore=0;
		if (index == 0) {
			iwantBeanslist.clear();
		}
		ArrayList<IwantAndIhaveBean> iwantBeans = new ArrayList<IwantAndIhaveBean>();
		IwantAndIhaveBean iwantBean = new IwantAndIhaveBean();
		iwantBean.setUsername(SharePreferenceUtils.getIntance(context)
				.getCurrentUserName());
		iwantBean.setLat(SharePreferenceUtils.getIntance(context)
				.getLocationLat());
		iwantBean.setLng(SharePreferenceUtils.getIntance(context)
				.getLocationLng());
		iwantBean.setType(0);
		iwantBean.setId(index);
		iwantBeans.add(iwantBean);
		iwantListBean.setArray(iwantBeans);
		configuration.setClassName(IwantAndIhaveListBean.class);
		configuration.setViewDataObject(iwantListBean);
		new IwantController(configuration).getIhaveInfo();
		dataObjectFlag = 0;

	}

	@Override
	public void initConfiguration(BaseFragment fragment) {
		super.initConfiguration(fragment);
		configuration = getConfiguration();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@SuppressLint("NewApi")
	@Override
	public void initView(View view) {
		mlv_iwant = (XListView) view.findViewById(R.id.new_iwant_lv);
		down_again=(LinearLayout)view.findViewById(R.id.down_again);
		moveTopBtn=(Button)view.findViewById(R.id.moveTop);
		mlv_iwant.setOnItemClickListener(this);
		down_again.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DefineDisplayView.showLoadingDialog(getActivity());
				loadData(0);
			}
		});
		
	}

	@SuppressLint("NewApi") @Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (down_again!=null) {
			down_again.setVisibility(View.GONE);
		}
		if (isIgnore == 0) {
			IwantAndIhaveListBean iwantListBeanPage = (IwantAndIhaveListBean) object;
			iwantBeanslist.addAll(iwantListBeanPage.getArray());
			iwantListBean.setArray(iwantBeanslist);
			Log.d("iwantListBean.getArray().Size", iwantListBean.getArray()
					.size() + "");
			if (iwantListBeanPage.getArray().size() != 0
					&& iwantListBeanPage.getArray() != null) {
				index = iwantListBeanPage.getArray()
						.get(iwantListBeanPage.getArray().size() - 1).getId();
			}
			/*ArrayList<FriendInfoBean> friendsBeans=new ArrayList<FriendInfoBean>();
			for (int i = 0; i < iwantListBean.getArray().size(); i++) {
				FriendInfoBean friendsBean=new FriendInfoBean();
				friendsBean.setHeadimage(iwantListBean.getArray().get(i).getHeadimg());
				friendsBean.setNickname(iwantListBean.getArray().get(i).getNickname());
				friendsBean.setUsername(iwantListBean.getArray().get(i).getUsername());
				friendsBean.setIdo(iwantListBean.getArray().get(i).getIdiograph());
				friendsBeans.add(friendsBean);
				
			}
			try {
				SQLDatebaseUtils.getInstance(context).getDbUtils().saveOrUpdateAll(friendsBeans);
			} catch (DbException e) {
				e.printStackTrace();
			}*/
			iwantadaAdapter = new NewIwantListAdapter(getActivity(),
					iwantListBean.getArray(), new IMeApply() {

						@Override
						public void applySatisfyDemand(MeApply meApply) {
							dataObjectFlag = 1;
							ArrayList<MeApply> meApplies = new ArrayList<MeApply>();
							meApplies.add(meApply);
							MeApplyList meApplyList = new MeApplyList();
							meApplyList.setArray(meApplies);
							configuration.setClassName(MeApplyList.class);
							configuration.setViewDataObject(meApplyList);
							new IwantController(configuration)
									.applyUserSelection();

						}
					},new Iigonre(){

						@Override
						public void ignore(int position) {
							if (iwantBeanslist.size()!=0) {
							IgnoreListBean ignoreListBean=new IgnoreListBean();
							ArrayList<IgnoreBean> ignoreBeans=new ArrayList<IgnoreBean>();
							IgnoreBean ignoreBean=new IgnoreBean();
							ignoreBean.setContent(iwantBeanslist.get(position).getContent());
							ignoreBean.setGetuser(SharePreferenceUtils.getIntance(getActivity()).getCurrentUserName());
							ignoreBean.setTouser(iwantBeanslist.get(position).getUsername());
							ignoreBean.setStatus(2);
							ignoreBean.setPostsid(iwantBeanslist.get(position).getId());
							ignoreBeans.add(ignoreBean);
							ignoreListBean.setArray(ignoreBeans);
							configuration.setClassName(IgnoreListBean.class);
							configuration.setViewDataObject(ignoreListBean);
							new IwantController(configuration).ignorePost();
							isIgnore=1;
							iwantBeanslist.remove(position);
							if (iwantBeanslist.size()!=0) {
								iwantadaAdapter.notifyDataSetChanged();
							}
							}
						}});
			
            moveTopBtn.setVisibility(View.GONE);
			mlv_iwant.setAdapter(iwantadaAdapter);
			iwantadaAdapter.notifyDataSetChanged();
			if (moreLoad == 1) {
				mlv_iwant.setSelection(iwantBeanslist.size()
						- iwantListBeanPage.getArray().size());
			}
			// mlv_iwant.onRefreshComplete();
			mlv_iwant.stopRefresh();
			mlv_iwant.stopLoadMore();
			
		}else
		{
			//loadData(0);
		}
		
  if(iwantBeanslist.size()!=0){
	if (iwantBeanslist.get(0).getSum()!=null) {
		if(SharePreferenceUtils.getIntance(getActivity()).getSoundOpen()){
			Log.v("NUM","down"+Integer.parseInt(iwantBeanslist.get(0).getSum())+"");
			SharePreferenceUtils.getIntance(getActivity()).saveLinkNum(Integer.parseInt(iwantBeanslist.get(0).getSum()));
			Message message=new Message();
			message.what=ChatServiceUtils.FLASH_MAIN_MESSAGE;
			NewMainTabActivity.mMainControl.sendMessage(message);
		}else {
			Log.v("NUM","down"+Integer.parseInt(iwantBeanslist.get(0).getSum())+"");
			SharePreferenceUtils.getIntance(getActivity()).saveLinkNum(0);
			Message message=new Message();
			message.what=ChatServiceUtils.FLASH_MAIN_MESSAGE;
			NewMainTabActivity.mMainControl.sendMessage(message);
		}
		
	}
			
  }
		
	}

	@Override
	public void requestTimeout(String timeoutString) {
		super.requestTimeout(timeoutString);
		down_again.setVisibility(View.VISIBLE);
		mlv_iwant.stopRefresh();
		mlv_iwant.stopLoadMore();
	}

	@Override
	public void requestServerError(String serverError) {
		super.requestServerError(serverError);
		down_again.setVisibility(View.VISIBLE);
		mlv_iwant.stopRefresh();
		mlv_iwant.stopLoadMore();
	}

	@SuppressLint("NewApi") @Override
	public void requestErrorCode(String eString) {
		super.requestErrorCode(eString);
		UIHelper.ToastMessage(getActivity(), eString);
		down_again.setVisibility(View.VISIBLE);
		mlv_iwant.stopRefresh();
		mlv_iwant.stopLoadMore();
		index = index - 15;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.v("here", "" + arg2);

	}

	/**
	 * @function
	 * @time 2014-10-14下午11:57:36 void
	 */
	public static void addSuccessUpdateUi(IwantAndIhaveListBean ihaveListBean) {
		/*
		 * iwantListBean.getArray().addAll(ihaveListBean.getArray()); if
		 * (iwantadaAdapter==null) {
		 * 
		 * }else {
		 * iwantadaAdapter.setNumLength(iwantListBean.getArray().size());
		 * iwantadaAdapter.notifyDataSetChanged(); }
		 */
		loadData(0);

	}

	public void reflesh(int index) {
		isIgnore=0;
		iwantBeanslist.clear();
		ArrayList<IwantAndIhaveBean> iwantBeans = new ArrayList<IwantAndIhaveBean>();
		IwantAndIhaveBean iwantBean = new IwantAndIhaveBean();
		iwantBean.setUsername(SharePreferenceUtils.getIntance(context)
				.getCurrentUserName());
		iwantBean.setLat(SharePreferenceUtils.getIntance(context)
				.getLocationLat());
		iwantBean.setLng(SharePreferenceUtils.getIntance(context)
				.getLocationLng());
		iwantBean.setType(0);
		iwantBean.setId(index);
		iwantBeans.add(iwantBean);
		iwantListBean.setArray(iwantBeans);
		configuration.setClassName(IwantAndIhaveListBean.class);
		configuration.setViewDataObject(iwantListBean);
		new IwantController(configuration).getIwantInfo();
		dataObjectFlag = 0;
	}

	/**
	 * 根据条件选择
	 */
	public void getDataBySelection(int select,int sex) {
		moveTopBtn.setVisibility(View.GONE);
		iwantBeanslist.clear();
		switch (select) {
		case 1://时间
			ArrayList<IwantAndIhaveBean> iwantBeans = new ArrayList<IwantAndIhaveBean>();
			IwantAndIhaveBean iwantBean = new IwantAndIhaveBean();
			iwantBean.setUsername(SharePreferenceUtils.getIntance(context)
					.getCurrentUserName());
			iwantBean.setLat(SharePreferenceUtils.getIntance(context)
					.getLocationLat());
			iwantBean.setLng(SharePreferenceUtils.getIntance(context)
					.getLocationLng());
			iwantBean.setType(0);
			iwantBean.setId(index);
			iwantBeans.add(iwantBean);
			iwantListBean.setArray(iwantBeans);
			configuration.setClassName(IwantAndIhaveListBean.class);
			configuration.setViewDataObject(iwantListBean);
			new IwantController(configuration).getIwantInfo();
			break;
		case 2://距离
			ArrayList<IwantAndIhaveBean> iwantBeans1 = new ArrayList<IwantAndIhaveBean>();
			IwantAndIhaveBean iwantBean1 = new IwantAndIhaveBean();
			iwantBean1.setUsername(SharePreferenceUtils.getIntance(context)
					.getCurrentUserName());
			iwantBean1.setLat(SharePreferenceUtils.getIntance(context)
					.getLocationLat());
			iwantBean1.setLng(SharePreferenceUtils.getIntance(context)
					.getLocationLng());
			iwantBean1.setType(0);
			iwantBean1.setId(0);
			iwantBeans1.add(iwantBean1);
			iwantListBean.setArray(iwantBeans1);
			configuration.setClassName(IwantAndIhaveListBean.class);
			configuration.setViewDataObject(iwantListBean);
			new IwantController(configuration).getPostDistance();
			break;
		case 3://性别
			ArrayList<IwantAndIhaveBean> iwantBeans2 = new ArrayList<IwantAndIhaveBean>();
			IwantAndIhaveBean iwantBean2 = new IwantAndIhaveBean();
			iwantBean2.setUsername(SharePreferenceUtils.getIntance(context)
					.getCurrentUserName());
			iwantBean2.setType(0);
			iwantBean2.setId(0);
			iwantBean2.setSex(sex+"");
			iwantBeans2.add(iwantBean2);
			iwantListBean.setArray(iwantBeans2);
			configuration.setClassName(IwantAndIhaveListBean.class);
			configuration.setViewDataObject(iwantListBean);
			new IwantController(configuration).getPostBySex();
			break;
		default:
			break;
		}
		
	}
}
