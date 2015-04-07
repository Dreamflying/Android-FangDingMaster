package com.base.view;

import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.base.common.Common;
import com.base.controller.IViewControlRequst;
import com.base.model.Configuration;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeChatTarget;
import com.gotye.api.GotyeGroup;
import com.gotye.api.GotyeMessage;
import com.gotye.api.GotyeNotify;
import com.gotye.api.GotyeRoom;
import com.gotye.api.GotyeUser;
import com.gotye.api.listener.ChatListener;
import com.gotye.api.listener.DownloadListener;
import com.gotye.api.listener.GroupListener;
import com.gotye.api.listener.NotifyListener;
import com.gotye.api.listener.PlayListener;
import com.gotye.api.listener.RoomListener;
import com.gotye.api.listener.UserListener;
import com.project.login_view.LoginActivity;
import com.utils.app.ActivityManagerUtils;
import com.utils.app.UIHelper;
import com.utils.widght.DefineDisplayView;

/**Activity 基类
 * @author Ly
 * @time 2014年5月6日下午1:00:07
 * @email liyuan6522151@gmail.com
 */
public class BaseActivity extends Activity  implements IBaseActivity,IViewControlRequst,ChatListener,
DownloadListener, GroupListener, UserListener, RoomListener,PlayListener,NotifyListener {
	ActionBar actionBar;
	private String titleString="";
	private Configuration configuration=Configuration.getIntance();
	private RequestQueue queue;
	public GotyeAPI api=GotyeAPI.getInstance();
	public BaseActivity(String titleString){
		this.titleString=titleString;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(titleString);*/
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	    switch (item.getItemId()) {
	        case android.R.id.home:
               finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);

	    }
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    return true;
	}	
	
	
	@Override
	public void initView() {
	
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initConfiguration(BaseActivity  viewActivity,String activityName) {
		ActivityManagerUtils.getIntance().addActivity(activityName, viewActivity);
		queue=Volley.newRequestQueue(viewActivity);
		configuration.setRequestQueue(queue);
		configuration.setView_ControlRequst(viewActivity);
		configuration.setViewActivity(viewActivity);
	}

	@Override
	public void bingDataForView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestSuccess(Object object) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		queue.cancelAll(Common.RequstTag);
		
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
		UIHelper.ToastMessage(BaseActivity.this, eString);
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}
	@Override
	public void requestTimeout(String timeoutString) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		DefineDisplayView.showTimeoutToast(BaseActivity.this);
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}

	@Override
	public void requestServerError(String serverError) {
		if (DefineDisplayView.dialog!=null) {
			DefineDisplayView.closeLoadingDialog();
		}
		DefineDisplayView.showServerErrorToast(BaseActivity.this);
		if (queue!=null) {
			queue.cancelAll(Common.RequstTag);
		}
	}

	public ActionBar getBaseActionBar(){
		return actionBar;
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
	public void requestFileSuccess(String filemessage,int updateprogressBar) {
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
	public void onReceiveMessage(int code, GotyeMessage message, boolean unRead) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReceiveNotify(int code, GotyeNotify notify) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNotifyStateChanged() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPlayStart(int code, GotyeMessage message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPlaying(int code, int position) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPlayStop(int code) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPlayStartReal(int code, long roomId, String who) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEnterRoom(int code, long lastMsgID, GotyeRoom room) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLeaveRoom(int code, GotyeRoom room) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetRoomList(int code, List<GotyeRoom> roomList) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetRoomMemberList(int code, GotyeRoom room,
			List<GotyeUser> totalMembers, List<GotyeUser> currentPageMembers,
			int pageIndex) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetHistoryMessageList(int code, List<GotyeMessage> list) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRequestRoomInfo(int code, GotyeRoom room) {
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
	@Override
	public void onCreateGroup(int code, GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onJoinGroup(int code, GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLeaveGroup(int code, GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDismissGroup(int code, GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onKickOutUser(int code, GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetGroupList(int code, List<GotyeGroup> grouplist) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRequestGroupInfo(int code, GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetGroupMemberList(int code, List<GotyeUser> allList,
			List<GotyeUser> curList, GotyeGroup group, int pagerIndex) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReceiveGroupInvite(int code, GotyeGroup group,
			GotyeUser sender, String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReceiveRequestJoinGroup(int code, GotyeGroup group,
			GotyeUser sender, String message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReceiveReplayJoinGroup(int code, GotyeGroup group,
			GotyeUser sender, String message, boolean isAgree) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGetOfflineMessageList(int code, List<GotyeMessage> messagelist) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSearchGroupList(int code, List<GotyeGroup> mList,
			List<GotyeGroup> curList, int pageIndex) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onModifyGroupInfo(int code, GotyeGroup gotyeGroup) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onChangeGroupOwner(int code, GotyeGroup group) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUserJoinGroup(GotyeGroup group, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUserLeaveGroup(GotyeGroup group, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUserDismissGroup(GotyeGroup group, GotyeUser user) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUserKickdFromGroup(GotyeGroup group, GotyeUser kicked,
			GotyeUser actor) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSendNotify(int code, GotyeNotify notify) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDownloadMedia(int code, String path, String url) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSendMessage(int code, GotyeMessage message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReceiveMessage(int code, GotyeMessage message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDownloadMessage(int code, GotyeMessage message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReleaseMessage(int code) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReport(int code, GotyeMessage message) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStartTalk(int code, boolean isRealTime, int targetType,
			GotyeChatTarget target) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onStopTalk(int code, GotyeMessage message, boolean isVoiceReal) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDecodeMessage(int code, GotyeMessage message) {
		// TODO Auto-generated method stub
		
	}

	
}

