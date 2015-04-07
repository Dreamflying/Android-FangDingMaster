package com.project.myself_view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.base.common.Common;
import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open_demo.util.FileUtil;
import com.project.add_view.NewAddActivity;
import com.project.adpter.AnimateFirstDisplayListener;
import com.project.adpter.AutoGridViewAdapter;
import com.project.adpter.MyselfInfoListAdapter;
import com.project.adpter.MyselfInfoListAdapter.IOperateDongTai;
import com.project.iwant.R;
import com.project.login_controller.LoginController;
import com.project.login_model.UserInfoBean;
import com.project.login_model.UserInfoListBean;
import com.project.login_view.ModifyUserInfoActivity;
import com.project.login_view.UpLoadHeadActivity;
import com.project.message_model.MessageBean;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.DeleteDongtaiBean;
import com.project.myself_model.DeleteDongtaiListBean;
import com.project.myself_model.ImageBean;
import com.project.myself_model.ImageListBean;
import com.project.myself_model.MyselfInfoBean;
import com.project.myself_model.MyselfInfoComment;
import com.project.myself_model.MyselfInfoCommentList;
import com.project.myself_model.MyselfInfoListBean;
import com.project.myself_model.NearByInfoComment;
import com.utils.app.PhotoUtils;
import com.utils.http.FtpLoadTool;
import com.utils.http.UploadInterface;
import com.utils.io.SharePreferenceUtils;
import com.utils.io.StorageEnvironmentUtils;
import com.utils.os.ImgAction;
import com.utils.os.InitDisplayImageOptions;
import com.utils.security.MD5;
import com.utils.widght.DefineDisplayView;
import com.utils.widght.InputMessageView;
import com.utils.widght.InputMessageView.IsendMessage;
import com.utils.widght.RunImageViewUtils;
import com.utils.widght.SrollViewListView;
import com.utils.widght.TagsGridView;

/**
 * @description 个人资料界面
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-3上午11:38:23
 */
public class MyselfInfoActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnFocusChangeListener {
	String[] imageUrls = Constants.IMAGES;
	DisplayImageOptions options;
	private SrollViewListView svl_list;
	private LinearLayout btn_return_exit;
	private static LinearLayout btn_publish_or_add;
	private ImageView iv_head_img;
	private LinearLayout linearLayout;
	private TextView showmyinfo_tv_nickname, dynamic_title_tv,
			showmyinfo_tv_idiograph;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static String FilePath = Environment.getExternalStorageDirectory()
			.getPath() + "/iwantme/head/";
	private static String Username = "";
	private static String NickName = "";
	private static String HeadImage = "";
	private static MyselfInfoListBean myselfInfoListBean = new MyselfInfoListBean();
	private static Configuration configuration;
	private static UserInfoListBean userInfoListBean;
	static ViewFlipper mViewFlipper;
	/** 图片滚动功能 */
	public static int NUMCOLUMNS = 1;
	public static int NUMROW = 1;
	private static int comment_or_getinfo = 6;
	private String headImageNameString = "";
	private static Context context;
	public static boolean isFrom = false;
	private boolean isOk = false, isLoss = false;
	String nickname;
	String property;
	private LinearLayout modifynickname;
	private ImageView edit_nicknamepoint;

	public MyselfInfoActivity() {
		super("个人资料");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myselfinfo);
		context = MyselfInfoActivity.this;
		options = InitDisplayImageOptions.getInstance().getOptions(true, true,
				0);
		initView();
		setListener();
		initConfiguration(MyselfInfoActivity.this, "MyselfInfoActivity");
		getUserInfo();

	}

	public static void getNickName(String username) {
		if (configuration != null) {
			comment_or_getinfo = 6;
			userInfoListBean = new UserInfoListBean();
			ArrayList<UserInfoBean> userInfoBeans = new ArrayList<UserInfoBean>();
			UserInfoBean userInfoBean = new UserInfoBean();
			userInfoBean.setUsername(username);
			userInfoBeans.add(userInfoBean);
			userInfoListBean.setArray(userInfoBeans);
			configuration.setClassName(UserInfoListBean.class);
			configuration.setViewDataObject(userInfoListBean);
			new LoginController(configuration).getUserInfo_New();
		}
	}

	public static void getUserInfo() {
		comment_or_getinfo = 0;
		DefineDisplayView.showLoadingDialog(context);
		ArrayList<MyselfInfoBean> myselfInfoBeans = new ArrayList<MyselfInfoBean>();
		MyselfInfoBean myselfInfoBean = new MyselfInfoBean();
		myselfInfoBean.setUser(Username);
		myselfInfoBeans.add(myselfInfoBean);
		myselfInfoListBean.setArray(myselfInfoBeans);
		configuration.setClassName(MyselfInfoListBean.class);
		configuration.setViewDataObject(myselfInfoListBean);
		new MyselfInfoController(configuration).getMyselfInfo();

	}

	@Override
	public void initView() {
		iv_head_img = (ImageView) findViewById(R.id.showmyinfo_img);
		showmyinfo_tv_nickname = (TextView) findViewById(R.id.showmyinfo_tv_nickname);
		showmyinfo_tv_idiograph = (TextView) findViewById(R.id.showmyinfo_tv_idiograph);
		btn_return_exit = (LinearLayout) findViewById(R.id.setup_return_btns);
		btn_publish_or_add = (LinearLayout) findViewById(R.id.dynamic_toadd_btn);
		svl_list = (SrollViewListView) findViewById(R.id.myinfo_list);
		linearLayout = (LinearLayout) findViewById(R.id.top_parent);
		dynamic_title_tv = (TextView) findViewById(R.id.dynamic_title_tv);
		edit_nicknamepoint = (ImageView) findViewById(R.id.edit_nicknamepoint);
		Intent intent = getIntent();
		showmyinfo_tv_idiograph.setEnabled(false);
		showmyinfo_tv_idiograph.setFocusable(false);
		showmyinfo_tv_nickname.setEnabled(false);
		showmyinfo_tv_nickname.setFocusable(false);
		boolean from = intent.getBooleanExtra("from", false);
		if (from) {
			isFrom = from;
			Username = intent.getStringExtra("getuser");
			NickName = intent.getStringExtra("nickname");
			HeadImage = intent.getStringExtra("headimage");
			dynamic_title_tv.setText(NickName);
			showmyinfo_tv_nickname.setText(NickName);
			btn_publish_or_add.setEnabled(false);
			showmyinfo_tv_idiograph.setText(intent.getStringExtra("ido"));
			edit_nicknamepoint.setVisibility(View.GONE);
			btn_publish_or_add.setVisibility(View.GONE);
			// getUserInfo();
			// showmyinfo_tv_nickname.setText(NickName);
			// showmyinfo_tv_nickname.setEnabled(false);
			// showmyinfo_tv_nickname.setFocusable(false);
			// showmyinfo_tv_idiograph.setEnabled(false);
			// showmyinfo_tv_idiograph.setFocusable(false);
			// modifynickname.setEnabled(false);
			// btn_publish_or_add.setVisibility(View.GONE);
			ImageLoader.getInstance().displayImage(
					HeadImage,
					iv_head_img,
					InitDisplayImageOptions.getInstance().getHeadOptions(false,
							false, 0),
					InitDisplayImageOptions.getInstance()
							.getImageLoadingListener());
		} else {
			btn_publish_or_add.setEnabled(true);
			isFrom = false;
			Username = SharePreferenceUtils.getIntance(MyselfInfoActivity.this)
					.getCurrentUserName();
			showmyinfo_tv_nickname.setText(SharePreferenceUtils.getIntance(
					MyselfInfoActivity.this).getCurrentNickName());
			showmyinfo_tv_idiograph.setText(SharePreferenceUtils.getIntance(
					MyselfInfoActivity.this).getCurrentIdio());
			ImageLoader.getInstance().displayImage(
					SharePreferenceUtils.getIntance(MyselfInfoActivity.this)
							.getHeadImgUserName(),
					iv_head_img,
					InitDisplayImageOptions.getInstance().getHeadOptions(false,
							false, 0), InitDisplayImageOptions.getInstance()

					.getImageLoadingListener());

		}
		// RunImageViewUtils.runAnimation(context, iv_head_img);
	}

	@Override
	public void setListener() {
		btn_return_exit.setOnClickListener(this);
		btn_publish_or_add.setOnClickListener(this);
		iv_head_img.setOnClickListener(this);
		svl_list.setOnItemClickListener(this);
		// modifynickname.setOnClickListener(this);
		edit_nicknamepoint.setOnClickListener(this);
		// showmyinfo_tv_nickname.setOnFocusChangeListener(this);
		// showmyinfo_tv_idiograph.setOnFocusChangeListener(this);

	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		Log.v("comment_or_getinfo=", "" + comment_or_getinfo);
		if (comment_or_getinfo == 6) {
			userInfoListBean = (UserInfoListBean) object;
			showmyinfo_tv_nickname.setText(userInfoListBean.getArray().get(0)
					.getNickname());
			showmyinfo_tv_idiograph.setText(userInfoListBean.getArray().get(0)
					.getIdiograph());
			if (!isFrom) {
				SharePreferenceUtils.getIntance(context).saveCurrentIdio(
						userInfoListBean.getArray().get(0).getIdiograph());
				SharePreferenceUtils.getIntance(context).saveCurrentNickName(
						userInfoListBean.getArray().get(0).getNickname());
			}

		}
		if (comment_or_getinfo == 0) {
			svl_list.setVisibility(View.VISIBLE);
			Log.v("comment_or_getinfo", "here");
			myselfInfoListBean = (MyselfInfoListBean) object;
			svl_list.setAdapter(new MyselfInfoListAdapter(
					MyselfInfoActivity.this, options, myselfInfoListBean,
					isendMessage, iOperateDongTai));
			// addAutoPic();
			if (isFrom) {
				getNickName(Username);
			} else {

				getNickName(SharePreferenceUtils.getIntance(context)
						.getCurrentUserName());
			}

		}
		if (comment_or_getinfo == 1) {// 评论返回
			getUserInfo();
		}
		if (comment_or_getinfo == 3) {
			getUserInfo();
		}

		if (comment_or_getinfo == 2) {
			SharePreferenceUtils.getIntance(context).clearHeadImage();
			Log.v("headimag", Common.URL_IMG + headImageNameString + ".jpg");
			SharePreferenceUtils.getIntance(context).saveHeadImgUserName(
					Common.URL_IMG + headImageNameString + ".jpg");
			getUserInfo();
		}

		
		if (comment_or_getinfo==7) {
			ImageLoader.getInstance().displayImage(
					Common.URL_IMG+SharePreferenceUtils.getIntance(MyselfInfoActivity.this).getCurrentUserName()+".jpg",
					iv_head_img,
					InitDisplayImageOptions.getInstance().getHeadOptions(true,
							true, 0),
					InitDisplayImageOptions.getInstance()
							.getImageLoadingListener());
			SharePreferenceUtils.getIntance(context).saveHeadImgUserName(Common.URL_IMG+SharePreferenceUtils.getIntance(MyselfInfoActivity.this).getCurrentUserName()+".jpg");
		}
		if (isFrom) {
			ImageLoader.getInstance().displayImage(
					HeadImage,
					iv_head_img,
					InitDisplayImageOptions.getInstance().getHeadOptions(true,
							true, 0),
					InitDisplayImageOptions.getInstance()
							.getImageLoadingListener());
		} else {

		}
	}

	@Override
	public void requestErrorCode(String eString) {
		super.requestErrorCode(eString);
		svl_list.setVisibility(View.GONE);

	}

	@Override
	public void requestDataIsNull(String objectString) {
		super.requestDataIsNull(objectString);
		getUserInfo();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AnimateFirstDisplayListener.displayedImages.clear();
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.setup_return_btns:
			comment_or_getinfo = 0;
			configuration = getConfiguration();
			finish();
			break;
		case R.id.dynamic_toadd_btn:
			Intent intent = new Intent(MyselfInfoActivity.this,
					NewAddActivity.class);
			intent.putExtra("flag", 1);
			startActivity(intent);
			break;
		case R.id.edit_nicknamepoint:
			Intent edit_nicknamepoint = new Intent(MyselfInfoActivity.this,
					ModifyUserInfoActivity.class);
			startActivity(edit_nicknamepoint);
			break;
		case R.id.showmyinfo_img:
			if (isFrom) {

				Intent intentImage = new Intent(context,
						PicDisplayActivity.class);
				intentImage.putExtra("img_index", 0);
				Constants.Images(null, null, null, 2);
				Constants.IMAGES_LIST[0] = HeadImage;
				context.startActivity(intentImage);

			} else {
				new PhotoPopupWindow(MyselfInfoActivity.this, arg0,
						new IPhotoChoice() {

							@Override
							public void choiceTakePhotos() {

								startActivityForResult(
										PhotoUtils.choiceTakePhotos(Uri
												.fromFile(new File(
														FilePath
																+ SharePreferenceUtils
																		.getIntance(
																				MyselfInfoActivity.this)
																		.getCurrentUserName()
																+ ".jpg"))),
										PhotoUtils.PHOTOHRAPH);

							}

							@Override
							public void choiceLocalPhotos() {
								startActivityForResult(
										PhotoUtils.choiceLocalPhotos(),
										PhotoUtils.PHOTOZOOM);

							}
						});
			}

			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == PhotoUtils.NONE)
			return;
		// 拍照
		if (requestCode == PhotoUtils.PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(FilePath
					+ SharePreferenceUtils.getIntance(MyselfInfoActivity.this)
							.getCurrentUserName() + ".jpg");
			startActivityForResult(
					PhotoUtils.startPhotoZoom(Uri.fromFile(picture)),
					PhotoUtils.PHOTORESOULT);
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PhotoUtils.PHOTOZOOM) {

			startActivityForResult(PhotoUtils.startPhotoZoom(data.getData()),
					PhotoUtils.PHOTORESOULT);
		}
		// 处理结果
		if (requestCode == PhotoUtils.PHOTORESOULT) {
			try {

				Bundle extras = data.getExtras();
				Uri selectedImage = data.getData();
				if (selectedImage != null) {
					String path = FileUtil.uriToPath(this, selectedImage);
					// setPicture(path);
				}
				if (extras != null) {
					Log.v("here", "heada");
					Bitmap photo = extras.getParcelable("data");
					byte[] imgByte = ImgAction.BitmaptoBytes(photo);
					// ====将图片保存到本地=====
					headImageNameString = SharePreferenceUtils.getIntance(
							MyselfInfoActivity.this).getCurrentUserName();
					String fileName = ImgAction.bytesToFile(imgByte,
							headImageNameString);// 临时文件名为username
					final String filePath = Environment
							.getExternalStorageDirectory().getPath()
							+ "/iwantme/head/";
					DefineDisplayView
							.showLoadingDialog(MyselfInfoActivity.this);
					new Thread() {
						public void run() {

							FtpLoadTool
									.getInstance(new UploadInterface() {

										@Override
										public void onSuccess(int arg0,
												String arg1) {
											/** 上传图像 */
											Message message=new Message();
											message.what=1;
											handler.sendMessage(message);
											
										}

										@Override
										public void onFailure(Throwable error,
												String content) {

										}
									})
									.ftpUpload(filePath,
											headImageNameString + ".jpg");
						}

					}.start();

				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			/** 上传图像 */
			ArrayList<ImageBean> imageBeans = new ArrayList<ImageBean>();
			ImageBean imageBean = new ImageBean();
			imageBean.setHeadimg(Common.URL_IMG
					+ headImageNameString
					+ ".jpg");
			imageBean
					.setUsername(SharePreferenceUtils
							.getIntance(
									MyselfInfoActivity.this)
							.getCurrentUserName());
			imageBeans.add(imageBean);
			ImageListBean listBean = new ImageListBean();
			listBean.setArray(imageBeans);
			configuration
					.setClassName(ImageListBean.class);
			configuration
					.setViewDataObject(listBean);
			new MyselfInfoController(
					configuration).addHeadImg();
			comment_or_getinfo=7;
		}
		
		
	};

	IsendMessage isendMessage = new IsendMessage() {

		@Override
		public void sendNearComment(NearByInfoComment nearByInfoComment) {
			// TODO Auto-generated method stub

		}

		@Override
		public void sendMyselfComment(MyselfInfoComment myselfInfoComment) {
			comment_or_getinfo = 1;
			MyselfInfoCommentList myselfInfoCommentList = new MyselfInfoCommentList();
			ArrayList<MyselfInfoComment> myselfInfoComments = new ArrayList<MyselfInfoComment>();
			myselfInfoComments.add(myselfInfoComment);
			myselfInfoCommentList.setArray(myselfInfoComments);
			configuration.setClassName(MyselfInfoCommentList.class);
			configuration.setViewDataObject(myselfInfoCommentList);
			new MyselfInfoController(configuration).addComment();

		}
	};

	IOperateDongTai iOperateDongTai = new IOperateDongTai() {

		@Override
		public void delete(int id) {
			DeleteDongtaiListBean deleteDongtaiListBean = new DeleteDongtaiListBean();
			ArrayList<DeleteDongtaiBean> deleteDongtaiBeans = new ArrayList<DeleteDongtaiBean>();
			DeleteDongtaiBean deleteDongtaiBean = new DeleteDongtaiBean();
			deleteDongtaiBean.setId(id);
			deleteDongtaiBean.setUsername(SharePreferenceUtils.getIntance(
					context).getCurrentUserName());
			deleteDongtaiBeans.add(deleteDongtaiBean);
			deleteDongtaiListBean.setArray(deleteDongtaiBeans);
			comment_or_getinfo = 3;
			configuration.setClassName(DeleteDongtaiListBean.class);
			configuration.setViewDataObject(deleteDongtaiListBean);
			new MyselfInfoController(configuration).deleteDongtai();

		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Log.v("hre", "sss");
		/*
		 * MessageBean messageBean=new MessageBean();
		 * messageBean.setId(myselfInfoListBean
		 * .getArray().get(position).getId());
		 * messageBean.setContent(myselfInfoListBean
		 * .getArray().get(position).getContent());
		 * messageBean.setToUser(myselfInfoListBean
		 * .getArray().get(position).getUser());
		 * messageBean.setUser(SharePreferenceUtils
		 * .getIntance(context).getCurrentUserName());
		 * messageBean.setTime(myselfInfoListBean
		 * .getArray().get(position).getTime()+"");
		 * messageBean.setHeadImage(SharePreferenceUtils
		 * .getIntance(context).getHeadImgUserName());
		 * InputMessageView.getIntance().showInputMessageView(context,
		 * arg1,messageBean,isendMessage,1);
		 */

	}

	public void addAutoPic() {
		ArrayList<String> imagesUrlArrayList = new ArrayList<String>();
		for (int i = 0; i < myselfInfoListBean.getArray().size(); i++) {
			for (int j = 0; j < myselfInfoListBean.getArray().get(i).getImg()
					.size(); j++) {
				if (myselfInfoListBean.getArray().get(i).getImg().get(j)
						.getHeadurl() != null) {
					imagesUrlArrayList.add(myselfInfoListBean.getArray().get(i)
							.getImg().get(j).getHeadurl());
				}

			}
		}
		int length = imagesUrlArrayList.size();
		int gridViewNum = 0;
		List<GridView> listGridViews = new ArrayList<GridView>();
		List<List<Map<String, Object>>> listImageIds = new ArrayList<List<Map<String, Object>>>();
		List<AutoGridViewAdapter> ListSimpleAdapters = new ArrayList<AutoGridViewAdapter>();
		if (0 != length % (NUMCOLUMNS * NUMROW)) {
			gridViewNum = length / (NUMCOLUMNS * NUMROW) + 1;
		} else {
			gridViewNum = length / (NUMCOLUMNS * NUMROW);
		}

		for (int i = 0; i < gridViewNum; i++) {
			TagsGridView gridView = new TagsGridView(new ContextThemeWrapper(
					MyselfInfoActivity.this, R.style.GridviewStyle));
			// mViewFlipper.addView(gridView);
			gridView.setNumColumns(1);
			gridView.setPadding(-12, -12, -12, -12);
			gridView.setBackgroundColor(Color.TRANSPARENT);
			gridView.setEnabled(false);
			listGridViews.add(gridView);
			List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
			listImageIds.add(listItems);
		}

		for (int i = 0; i < length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			int index = i / (NUMCOLUMNS * NUMROW);
			listItem.put("image", imagesUrlArrayList.get(i));
			List<Map<String, Object>> listItems = (List<Map<String, Object>>) listImageIds
					.get(index);
			listItems.add(listItem);
		}

		for (int i = 0; i < gridViewNum; i++) {
			List<Map<String, Object>> listItems = (List<Map<String, Object>>) listImageIds
					.get(i);
			/*
			 * SimpleAdapter simpleAdapter = new SimpleAdapter(this , listItems
			 * //使用/layout/cell.xml文件作为界面布局 , R.layout.cell , new
			 * String[]{"image"} , new int[]{R.id.image1});
			 * ListSimpleAdapters.add(simpleAdapter);
			 */

			ListSimpleAdapters.add(new AutoGridViewAdapter(
					MyselfInfoActivity.this, listItems));
			listGridViews.get(i).setAdapter(ListSimpleAdapters.get(i));
		}
		if (mViewFlipper.isAutoStart() && !mViewFlipper.isFlipping()) {
			mViewFlipper.startFlipping();

		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		switch (v.getId()) {
		case R.id.showmyinfo_tv_nickname:
			if (hasFocus) {
				isOk = true;
			}
			break;
		case R.id.showmyinfo_tv_idiograph:
			if (hasFocus) {
				isLoss = true;
			}

			break;
		default:
			break;
		}

	}

	/*
	 * @Override public boolean dispatchTouchEvent(MotionEvent ev) { if
	 * (ev.getAction() == MotionEvent.ACTION_DOWN) { View v = getCurrentFocus();
	 * if (EditTextUtils.isShouldHideInput(v, ev)) {
	 * 
	 * InputMethodManager imm = (InputMethodManager)
	 * getSystemService(Context.INPUT_METHOD_SERVICE); if (imm != null) {
	 * imm.hideSoftInputFromWindow(v.getWindowToken(), 0); } if (isOk) { if
	 * (StringUtils
	 * .isEmpty(showmyinfo_tv_nickname.getText().toString())&&StringUtils
	 * .isEmpty(showmyinfo_tv_idiograph.getText().toString())) {
	 * 
	 * UIHelper.ToastMsg(MyselfInfoActivity .this, "昵称不能为空");
	 * 
	 * }else { //开始上传 showmyinfo_tv_nickname.setFocusable(false); String
	 * username =Username;
	 *//** test */
	/*
	 * nickname = showmyinfo_tv_nickname.getText().toString(); NickNameBeanList
	 * nameBeanList=new NickNameBeanList(); ArrayList<NickNameBean>
	 * nameBeans=new ArrayList<NickNameBean>(); NickNameBean nameBean=new
	 * NickNameBean(); nameBean.setNickname(nickname);
	 * nameBean.setUsername(username); nameBeans.add(nameBean);
	 * nameBeanList.setArray(nameBeans);
	 * configuration.setClassName(NickNameBean.class);
	 * configuration.setViewDataObject(nameBeanList); new
	 * MyselfInfoController(configuration).updateNickName(); comment_or_getinfo
	 * = 3; }
	 * 
	 * isOk=false;
	 * 
	 * }
	 * 
	 * if (isLoss) {
	 * 
	 * showmyinfo_tv_idiograph.setFocusable(false); String username =Username;
	 *//** test */
	/*
	 * property = showmyinfo_tv_idiograph.getText().toString(); NickNameBeanList
	 * nameBeanList=new NickNameBeanList(); ArrayList<NickNameBean>
	 * nameBeans=new ArrayList<NickNameBean>(); NickNameBean nameBean=new
	 * NickNameBean(); nameBean.setIdiograph(property);
	 * nameBean.setUsername(username); nameBeans.add(nameBean);
	 * nameBeanList.setArray(nameBeans);
	 * configuration.setClassName(NickNameBean.class);
	 * configuration.setViewDataObject(nameBeanList); new
	 * MyselfInfoController(configuration).getIdiograph(); comment_or_getinfo =
	 * 4; isLoss=false;
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * } else {
	 * 
	 * } return super.dispatchTouchEvent(ev); } // 必不可少，否则所有的组件都不会有TouchEvent了
	 * if (getWindow().superDispatchTouchEvent(ev)) { return true; } return
	 * onTouchEvent(ev); }
	 */

}
