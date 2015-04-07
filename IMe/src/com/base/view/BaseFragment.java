package com.base.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.base.common.Common;
import com.base.controller.IViewControlRequst;
import com.base.model.Configuration;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.DownloadListener;
import com.gotye.api.listener.UserListener;
import com.utils.widght.DefineDisplayView;

/**
 * Fragment 基类
 * 
 * @author Ly
 * @time 2014年5月6日下午12:59:34
 * @email liyuan6522151@gmail.com
 */
@SuppressLint({ "ValidFragment", "NewApi" })
public class BaseFragment extends Fragment implements IBaseFragment,
		IViewControlRequst,UserListener,
		DownloadListener {
	public GotyeAPI api=GotyeAPI.getInstance();
	private Configuration configuration = Configuration.getIntance();
	private RequestQueue queue;

	public BaseFragment() {
	}

	public BaseFragment(String title) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void initView(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	public void bingDefaultView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestSuccess(Object object) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}

	@Override
	public void requestDataIsNull(String objectString) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}

	@Override
	public void requestErrorCode(String eString) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}

	@Override
	public void requestTimeout(String timeoutString) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		DefineDisplayView.showTimeoutToast(getActivity());
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}

	@Override
	public void requestServerError(String serverError) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		DefineDisplayView.showServerErrorToast(getActivity());
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}

	@Override
	public void initConfiguration(BaseFragment fragment) {
		queue = Volley.newRequestQueue(getActivity());
		configuration.setRequestQueue(queue);
		configuration.setView_ControlRequst(fragment);

	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public RequestQueue getQueue() {
		return queue;
	}

	public void setQueue(RequestQueue queue) {
		this.queue = queue;
	}

	@Override
	public void responseSuccess(String responseSucess) {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseFailure(String responseFailure) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestFileSuccess(String filemessage, int updateprogressBar) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestFileError(String iserror) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestImageSuccess(Bitmap imageBitmap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestImageFailure(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDownloadMedia(int code, String path, String url) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRequestUserInfo(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModifyUserInfo(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSearchUserList(int code, List<GotyeUser> mList, int pagerIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddFriend(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetFriendList(int code, List<GotyeUser> mList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAddBlocked(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveFriend(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemoveBlocked(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetBlockedList(int code, List<GotyeUser> mList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetProfile(int code, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}
}
