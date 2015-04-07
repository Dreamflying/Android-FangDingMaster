package com.project.myself_view;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.iwant.R;
import com.project.message_model.MessageBean;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_model.MyselfInfoCommentList;
import com.utils.app.StringUtils;
import com.utils.app.TimestampTool;
import com.utils.app.UIHelper;
import com.utils.chat.ChatServiceUtils;
import com.utils.chat.NewChatService;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;

/**new add comment ac
 * @author liyuan
 *
 */
public class NewCommentActivity extends BaseActivity{

	public NewCommentActivity() {
		super("");
		
	}
	private LinearLayout add__title_back_ll;
	private Button add_comment_btn;
	private EditText commentContentEditText;
	private Configuration configuration;
	private MessageBean messageBean;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_comment);
		initConfiguration(NewCommentActivity.this, "NewCommentActivity");
		initView();
		setListener();
		
	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		// TODO Auto-generated method stub
		super.initConfiguration(viewActivity, activityName);
		configuration=getConfiguration();
	}
	@Override
	public void initView() {
		Log.v("messageBean", "---here");
		add__title_back_ll=(LinearLayout)findViewById(R.id.add__title_back_ll);
		add_comment_btn=(Button)findViewById(R.id.add_comment_btn);
		commentContentEditText=(EditText)findViewById(R.id.new_comment_content);
		Intent intent =getIntent(); 
		messageBean=(MessageBean)intent.getSerializableExtra("messageBean");
		Log.v("messageBean", messageBean.getUser()+"here");

	}
	@Override
	public void setListener() {
		add__title_back_ll.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
            finish();				
			}});
		
		add_comment_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (!StringUtils.isEmpty(commentContentEditText.getText().toString())) {
					DefineDisplayView.showLoadingDialog(NewCommentActivity.this);
					MyselfInfoComment myselfInfoComment =new MyselfInfoComment();
					myselfInfoComment.setContent(commentContentEditText.getText().toString()+ChatServiceUtils.COMMENT);
					myselfInfoComment.setDtid(messageBean.getId()+"");
					myselfInfoComment.setUser(SharePreferenceUtils.getIntance(NewCommentActivity.this).getCurrentUserName());
					myselfInfoComment.setLayer(1);
					Log.v("TimestampTool.getCurrentTime()", TimestampTool.getCurrentTime()+"");
					myselfInfoComment.setTime(TimestampTool.getCurrentTime());
					myselfInfoComment.setOrigin_username(messageBean.getToUser());
					myselfInfoComment.setName(SharePreferenceUtils.getIntance(NewCommentActivity.this).getCurrentNickName());
					MyselfInfoCommentList myselfInfoCommentList = new MyselfInfoCommentList();
					ArrayList<MyselfInfoComment> myselfInfoComments = new ArrayList<MyselfInfoComment>();
					myselfInfoComments.add(myselfInfoComment);
					myselfInfoCommentList.setArray(myselfInfoComments);
					Log.v("touser", messageBean.getToUser());
					configuration
							.setClassName(MyselfInfoCommentList.class);
					configuration
							.setViewDataObject(myselfInfoCommentList);
					messageBean.setContent(commentContentEditText.getText().toString());
					//NewChatService.sendMessage(messageBean, false);
					new MyselfInfoController(configuration).addComment();
				
					
				}else {
					
					UIHelper.ToastMessage(NewCommentActivity.this, "别吝啬，评论一下嘛！");
				}
				
			}
		});
		commentContentEditText.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				if (arg0.length()!=0) {
					add_comment_btn.setText("完成");
				}else {
					add_comment_btn.setText("评论");
				}
			
				
			}});
	}
	
	 @Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		finish();
	}
}
