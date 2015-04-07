package com.project.message_view;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.open_demo.main.MessageFragment;
import com.project.adpter.SendCommentListAdapter;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.message_model.CommentBean;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_model.MyselfInfoCommentList;
import com.project.myself_model.NearByInfoComment;
import com.utils.app.UIHelper;
import com.utils.chat.ChatServiceUtils;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.InputMessageView.IsendMessage;
import com.utils.widght.SrollViewListView;

public class CommentActivity extends BaseActivity implements OnItemClickListener {
	private LinearLayout setup_return_btns;
	private TextView so_iteam_tv;
	private SrollViewListView  lv_commentListView;
	private static List<CommentBean> commentBeans = new ArrayList<CommentBean>();
	private Configuration configuration;
	private int id;
	private int comment=0;
	public CommentActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		initConfiguration(CommentActivity.this,"CommentActivity");
		setup_return_btns = (LinearLayout) this.findViewById(R.id.setup_return_btns);
		so_iteam_tv = (TextView) this.findViewById(R.id.so_iteam_tv);
		lv_commentListView=(SrollViewListView)this.findViewById(R.id.comment_my_lv);
		lv_commentListView.setOnItemClickListener(this);
		setup_return_btns.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		so_iteam_tv.setText("评论");
		getData();
		changeLayer();
	}
	public void getData(){
		/*SQLDatebaseUtils.getInstance(CommentActivity.this).changeCommentMessageReadStatus(SharePreferenceUtils.getIntance(CommentActivity.this).getCurrentUserName());
		commentBeans=SQLDatebaseUtils.getInstance(CommentActivity.this).getDisticComment(
		SharePreferenceUtils.getIntance(CommentActivity.this).getCurrentUserName());*/
		if(MessageFragment.myselfInfoCommentList.getArray().size()!=0){
		lv_commentListView.setAdapter(new SendCommentListAdapter(CommentActivity.this,MessageFragment.myselfInfoCommentList.getArray(),new IsendMessage() {
			
			@Override
			public void sendNearComment(NearByInfoComment nearByInfoComment) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void sendMyselfComment(MyselfInfoComment myselfInfoComment) {
				comment=1;
				DefineDisplayView.showLoadingDialog(CommentActivity.this);
				myselfInfoComment.setOrigin_username(myselfInfoComment.getUser());
				MyselfInfoCommentList myselfInfoCommentList = new MyselfInfoCommentList();
				ArrayList<MyselfInfoComment> myselfInfoComments = new ArrayList<MyselfInfoComment>();
				myselfInfoComments.add(myselfInfoComment);
				myselfInfoCommentList.setArray(myselfInfoComments);
				configuration
						.setClassName(MyselfInfoCommentList.class);
				configuration
						.setViewDataObject(myselfInfoCommentList);
				id=myselfInfoComment.getId();
				new MyselfInfoController(configuration).addComment();
				
				
			}
		}));
		SharePreferenceUtils.getIntance(CommentActivity.this).saveCommetnNum(0);
		if (ChatServiceUtils.isMessageFragment) {
			Message message=new Message();
			message.what=ChatServiceUtils.FLASH_MAIN_MESSAGE;
			NewMainTabActivity.mMainControl.sendMessage(message);
			Message myFMessage=new Message();
			myFMessage.what=ChatServiceUtils.FLUSH_MESSAGE;
			MessageFragment.mMainFragmentControl.sendMessage(myFMessage);
			}
		
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Intent intent =new Intent(CommentActivity.this,CommentDetailActivity.class);
		intent.putExtra("dtid", commentBeans.get(arg2).getDtid());
		startActivity(intent);
		
		
	}
	
	public void changeLayer(){
		if(MessageFragment.myselfInfoCommentList.getArray().size()!=0){
	MyselfInfoCommentList myselfInfoCommentList=	new MyselfInfoCommentList();
		ArrayList<MyselfInfoComment> myselfInfoComments=new ArrayList<MyselfInfoComment>();
		MyselfInfoComment myselfInfoComment=new MyselfInfoComment();
		for (int i = 0; i <MessageFragment.myselfInfoCommentList.getArray().size(); i++) {
			myselfInfoComment.setId(MessageFragment.myselfInfoCommentList.getArray().get(i).getId());
			myselfInfoComment.setLayer(0);
			myselfInfoComments.add(myselfInfoComment);
			myselfInfoCommentList.setArray(myselfInfoComments);
			configuration.setClassName(MyselfInfoCommentList.class);
			configuration.setViewDataObject(myselfInfoCommentList);
		}
		new MyselfInfoController(configuration).changeActivityCommentLayerList();
		}
		
	}
	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration=getConfiguration();
	}
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (comment==1) {
			UIHelper.ToastMessage(CommentActivity.this, "回复成功");
		}
		comment=0;
		/*MyselfInfoCommentList myselfInfoCommentList=	new MyselfInfoCommentList();
		ArrayList<MyselfInfoComment> myselfInfoComments=new ArrayList<MyselfInfoComment>();
		MyselfInfoComment myselfInfoComment=new MyselfInfoComment();
			myselfInfoComment.setId(id);
			myselfInfoComment.setLayer(0);
			myselfInfoComments.add(myselfInfoComment);
			myselfInfoCommentList.setArray(myselfInfoComments);
			configuration.setClassName(MyselfInfoCommentList.class);
			configuration.setViewDataObject(myselfInfoCommentList);
			new MyselfInfoController(configuration).changeActivityCommentLayer();*/
		
	}

}
