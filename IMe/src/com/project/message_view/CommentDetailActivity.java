package com.project.message_view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.project.adpter.MyselfInfoListAdapter;
import com.project.adpter.MyselfInfoListAdapter.IOperateDongTai;
import com.project.iwant.R;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.MyselfInfoBean;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_model.MyselfInfoCommentList;
import com.project.myself_model.MyselfInfoListBean;
import com.project.myself_model.NearByInfoComment;
import com.project.myself_model.SingleComment;
import com.project.myself_model.SingleCommentList;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.SrollViewListView;
import com.utils.widght.InputMessageView.IsendMessage;

/**评论详情
 * @author liyuan
 *
 */
public class CommentDetailActivity extends BaseActivity{

	private SrollViewListView slv_comment_list;
	private LinearLayout btn_return_exit;
	private MyselfInfoListBean myselfInfoListBean=new MyselfInfoListBean();
	private Configuration configuration;
	DisplayImageOptions options;
	private String dtid="";
	public CommentDetailActivity() {
		super("");
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commentlist);
		options = InitDisplayImageOptions.getInstance().getOptions(true, true,0);
		initView();
		setListener();
		initConfiguration(CommentDetailActivity.this,"CommentDetailActivity");
		getCommentDetailInfo();
	}

	private void getCommentDetailInfo() {
		DefineDisplayView.showLoadingDialog(CommentDetailActivity.this);
		SingleCommentList singleComment=new SingleCommentList();
		ArrayList<SingleComment> myselfInfoBeans = new ArrayList<SingleComment>();
		SingleComment myselfInfoBean = new SingleComment();
		myselfInfoBean.setDtid(dtid);
		myselfInfoBeans.add(myselfInfoBean);
		singleComment.setArray(myselfInfoBeans);
		configuration.setClassName(MyselfInfoListBean.class);
		configuration.setViewDataObject(singleComment);
		new MyselfInfoController(configuration).getCommentByDtid();
	}

	@Override
	public void initView() {
		Intent intent=getIntent();
		dtid=intent.getStringExtra("dtid");
		slv_comment_list=(SrollViewListView)findViewById(R.id.comment_list);
		btn_return_exit = (LinearLayout) findViewById(R.id.setup_return_btns);
	}
	 @Override
	public void setListener() {
		 btn_return_exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	}
	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration=getConfiguration();
	}
	
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		myselfInfoListBean = (MyselfInfoListBean) object;
		slv_comment_list.setAdapter(new MyselfInfoListAdapter(
				CommentDetailActivity.this, options,
				myselfInfoListBean, new IsendMessage() {

					@Override
					public void sendNearComment(
							NearByInfoComment nearByInfoComment) {
						// TODO Auto-generated method stub

					}

					@Override
					public void sendMyselfComment(
							MyselfInfoComment myselfInfoComment) {
						MyselfInfoCommentList myselfInfoCommentList = new MyselfInfoCommentList();
						ArrayList<MyselfInfoComment> myselfInfoComments = new ArrayList<MyselfInfoComment>();
						myselfInfoComments.add(myselfInfoComment);
						myselfInfoCommentList.setArray(myselfInfoComments);
						configuration
								.setClassName(MyselfInfoCommentList.class);
						configuration
								.setViewDataObject(myselfInfoCommentList);
						new MyselfInfoController(configuration)
								.addComment();
						
					}
				},new IOperateDongTai() {
					
					@Override
					public void delete(int id) {
						// TODO Auto-generated method stub
						
					}
				}));
	}
}
