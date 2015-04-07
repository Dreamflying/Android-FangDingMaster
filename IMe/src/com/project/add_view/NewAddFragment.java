package com.project.add_view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.view.BaseFragment;
import com.project.add_controller.MainGVAdapter;
import com.project.iwant.R;
import com.utils.app.ScreenUtils;
import com.utils.app.Utility;
import com.utils.http.LocationUtils;

/**发布
 * @author liyuan
 *
 */
public class NewAddFragment extends BaseFragment {
	private static MainGVAdapter adapter;
    private static ArrayList<String> imagePathList;
    private static GridView imagesList;
    private EditText addContentEv;
	private Button addOkBtn;
	private TextView addAddressTv;
	private LinearLayout returnBtn;
	private static Context context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context=getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add, null);
		initView(view);
		return view;
	}
	@Override
	public void initView(View view) {
		   
	        //获取屏幕像素
	        ScreenUtils.initScreen(getActivity());
	        Button selectImgBtn = (Button) view.findViewById(R.id.add_image);
	        imagesList = (GridView) view.findViewById(R.id.main_gridView);
	        addAddressTv = (TextView) view.findViewById(R.id.add_address_tv);
	    	returnBtn = (LinearLayout) view.findViewById(R.id.add__title_back_ll);
			addContentEv = (EditText)view.findViewById(R.id.add_new_tv);
			addOkBtn = (Button) view.findViewById(R.id.add_ok_btn);
			addAddressTv.setText(LocationUtils.getAddress());
	        imagePathList = new ArrayList<String>();
	        adapter = new MainGVAdapter(getActivity(), imagePathList);
	        imagesList.setAdapter(adapter);
	        selectImgBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                //跳转至最终的选择图片页面
	                Intent intent = new Intent(getActivity(), PhotoWallActivity.class);
	                startActivity(intent);
	            }
	        });
	        imagePathList = new ArrayList<String>();
	        adapter = new MainGVAdapter(getActivity(), imagePathList);
	        imagesList.setAdapter(adapter);
	}
	public  static void addImage(ArrayList<String> intentpath){
             
             ArrayList<String> paths = intentpath;
             //添加，去重
             boolean hasUpdate = false;
             for (String path : paths) {
                 if (!imagePathList.contains(path)) {
                     //最多9张
                     if (imagePathList.size() == 9) {
                         Utility.showToast(context, "最多可添加9张图片。");
                         break;
                     }
                     Log.v("path=", path);
                     imagePathList.add(path);
                     hasUpdate = true;
                 }
             }

             if (hasUpdate) {
                 adapter.notifyDataSetChanged();
             }
        }
}       
