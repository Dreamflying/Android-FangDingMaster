package com.project.add_view;

import java.lang.reflect.Array;
import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.base.model.Configuration;
import com.base.view.BaseFragment;
import com.project.add_controller.AddController;
import com.project.add_model.LikeBeanList;
import com.project.add_view.SearchPop.ISearch;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.iwant.SelectorSexPop;
import com.utils.app.StringUtils;
import com.utils.app.UIHelper;
import com.utils.http.LocationUtils;


/**搜索
 * @author liyuan
 *
 */
public class NewSearchFragment extends BaseFragment implements OnItemClickListener, OnItemSelectedListener{
	private View rootView;// 缓存Fragment view
	 private static final String[] ITEMS = {"搜本地", "搜全国"};
	    private  String[] GRADS = {"打篮球", "看电影", "LOL", "书", "吃牛排", "唱K", "iPhone", "旅游", "约吗？"};
	    private ArrayList<String> local;
	    private EditText searchView;
	    private Button spinner_btn;
	    private GridView gridView;
	    private SearchGridViewAdapter gridViewAdapter;
	    private TextView search_text;
	    private boolean isLocal=true;
	    private Configuration configuration;
	    LikeBeanList likeBeanList=new LikeBeanList();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initConfiguration(NewSearchFragment.this);
		local=new ArrayList<String>();
		for (int i = 0; i < GRADS.length; i++) {
			local.add(GRADS[i]);
		}
	}
	private void getLikeBeanList() {
	
		configuration.setClassName(LikeBeanList.class);
		configuration.setViewDataObject(likeBeanList);
		new AddController(configuration).findKeyword();
	}
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		likeBeanList=(LikeBeanList)object;
		local=new ArrayList<String>();
		for (int i = 0; i < likeBeanList.getArray().size(); i++) {
			local.add(likeBeanList.getArray().get(i).getMatch_key_value());
		}
		gridViewAdapter = new SearchGridViewAdapter(local, getActivity());
        gridView.setAdapter(gridViewAdapter);
	}
	
	@Override
	public void requestErrorCode(String eString) {
		super.requestErrorCode(eString);
		gridViewAdapter = new SearchGridViewAdapter(local, getActivity());
        gridView.setAdapter(gridViewAdapter);
	}
	
	@Override
	public void requestTimeout(String timeoutString) {
		super.requestTimeout(timeoutString);
		gridViewAdapter = new SearchGridViewAdapter(local, getActivity());
        gridView.setAdapter(gridViewAdapter);
	}
	@Override
	public void requestServerError(String serverError) {
		super.requestServerError(serverError);
		gridViewAdapter = new SearchGridViewAdapter(local, getActivity());
        gridView.setAdapter(gridViewAdapter);
	}
	@Override
	public void requestDataIsNull(String objectString) {
		super.requestDataIsNull(objectString);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.fragment_new_search, null);
		}
		// 缓存的rootView需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		initView(rootView);
		setListener();
		getLikeBeanList();
		return rootView;
	}

	@Override
	public void initConfiguration(BaseFragment fragment) {
		super.initConfiguration(fragment);
		configuration=getConfiguration();
	}
	@Override
	public void initView(View view) {
		    searchView = (EditText) view.findViewById(R.id.search);
		    spinner_btn = (Button) view.findViewById(R.id.spinner_btn);
	        gridView = (GridView)view. findViewById(R.id.gridview);
	        search_text=(TextView)view.findViewById(R.id.search_text);
	    	LocationUtils.getInstance(getActivity())
			.getLocationUseBaiDuSDK();
	}
	@Override
	public void setListener() {
		gridView.setOnItemClickListener(this);
		spinner_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SearchPop.getIntance().showMorePopWindow(
						getActivity(), arg0,new ISearch() {
							
							@Override
							public void searchLocal() {
								isLocal=true;
								String searString=searchView.getText().toString();
								if (!StringUtils.isEmpty(searString)) {
									Intent intent =new Intent(getActivity(),NewSearchActivity.class);
									intent.putExtra("searchcontent",searString);
									intent.putExtra("isLocal", true);
									startActivity(intent);
								}else {
									UIHelper.ToastMessage(getActivity(), "搜索的用户不能为空");
								}
								spinner_btn.setText("搜用户>");
							}
							
							@Override
							public void searchCountry() {
								isLocal=false;
								String searString=searchView.getText().toString();
								if (!StringUtils.isEmpty(searString)) {
									Intent intent =new Intent(getActivity(),NewSearchActivity.class);
									intent.putExtra("searchcontent",searString);
									intent.putExtra("isLocal", false);
									startActivity(intent);
								}else {
									UIHelper.ToastMessage(getActivity(), "搜索的内容不能为空");
								}
								spinner_btn.setText("搜内容>");
							}
						});				
			}
		});
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent =new Intent(getActivity(),NewSearchActivity.class);
		intent.putExtra("searchcontent",local.get(arg2));
		intent.putExtra("isLocal", false);
		startActivity(intent);
		
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		Log.v("search", "here");
		String searString=searchView.getText().toString();
		if (!StringUtils.isEmpty(searString)) {
			
			Intent intent =new Intent(getActivity(),NewSearchActivity.class);
			intent.putExtra("searchcontent",searString);
			startActivity(intent);
		}else {
			UIHelper.ToastMessage(getActivity(), "搜索的内容不能为空");
		}
		
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		String searString=searchView.getText().toString();
		if (!StringUtils.isEmpty(searString)) {
			
			Intent intent =new Intent(getActivity(),NewSearchActivity.class);
			intent.putExtra("searchcontent",searString);
			startActivity(intent);
		}else {
			UIHelper.ToastMessage(getActivity(), "搜索的内容不能为空");
		}
	}
}
