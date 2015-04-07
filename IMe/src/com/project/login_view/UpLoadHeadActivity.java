package com.project.login_view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.base.common.Common;
import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.gotye.api.GotyeAPI;
import com.gotye.api.GotyeStatusCode;
import com.gotye.api.GotyeUser;
import com.gotye.api.PathUtil;
import com.gotye.api.listener.LoginListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.open_demo.GotyeService;
import com.open_demo.util.BitmapUtil;
import com.open_demo.util.FileUtil;
import com.open_demo.util.ProgressDialogUtil;
import com.open_demo.util.ToastUtil;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.ImageBean;
import com.project.myself_model.ImageListBean;
import com.project.myself_view.IPhotoChoice;
import com.project.myself_view.PhotoPopupWindow;
import com.utils.app.PhotoUtils;
import com.utils.app.UIHelper;
import com.utils.http.FtpLoadTool;
import com.utils.http.UploadInterface;
import com.utils.io.SharePreferenceUtils;
import com.utils.os.ImgAction;
import com.utils.os.InitDisplayImageOptions;
import com.utils.widght.DefineDisplayView;

public class UpLoadHeadActivity extends BaseActivity implements LoginListener{
	private ImageView headImageView;
	private Button add_headimage_start_btn;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static String FilePath = Environment
			.getExternalStorageDirectory().getPath()
			+ "/iwantme/head/";
	private Configuration configuration;
	private String headImageNameString = "";
	private boolean isSuccess=false;
	private GotyeUser user_s;
	private GotyeAPI api;
	private String  headPath;
	public UpLoadHeadActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploadimage);
		api = GotyeAPI.getInstance();
		api.addListerer(this);
		initConfiguration(UpLoadHeadActivity.this,"UpLoadHeadActivity");
		headImageView = (ImageView) findViewById(R.id.edit_headimage);
		add_headimage_start_btn=(Button)findViewById(R.id.add_headimage_start_btn);
		headImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				new PhotoPopupWindow(UpLoadHeadActivity.this, arg0,
						new IPhotoChoice() {

							@Override
							public void choiceTakePhotos() {

								startActivityForResult(
										PhotoUtils.choiceTakePhotos(Uri
												.fromFile(new File(
														FilePath
																+ SharePreferenceUtils
																		.getIntance(
																				UpLoadHeadActivity.this)
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

		});
		
		add_headimage_start_btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if (isSuccess) {
					SharePreferenceUtils.getIntance(UpLoadHeadActivity.this).saveHeadImgUserName(Common.URL_IMG + headImageNameString+".jpg");
					Intent intent =new Intent(UpLoadHeadActivity.this,NewMainTabActivity.class);
					startActivity(intent);
					finish();
				}else
				{
				UIHelper.ToastMessage(UpLoadHeadActivity.this, "请先上传头像");
					
				}
				
			}
		});
	}
	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration=getConfiguration();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == PhotoUtils.NONE){
			return;
		}
			
		// 拍照
		if (requestCode == PhotoUtils.PHOTOHRAPH) {
			// 设置文件保存路径这里放在跟目录下
			File picture = new File(FilePath + SharePreferenceUtils
					.getIntance(
							UpLoadHeadActivity.this)
					.getCurrentUserName() + ".jpg");
			startActivityForResult(PhotoUtils.startPhotoZoom(Uri.fromFile(picture)), PhotoUtils.PHOTORESOULT);
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PhotoUtils.PHOTOZOOM) {
			
			startActivityForResult(PhotoUtils.startPhotoZoom(data.getData()), PhotoUtils.PHOTORESOULT);
		}
		// 处理结果
		if (requestCode == PhotoUtils.PHOTORESOULT) {
			try {
				
				Bundle extras = data.getExtras();
				Uri selectedImage = data.getData();
				if (selectedImage != null) {
					String path = FileUtil.uriToPath(this, selectedImage);
					setPicture(path);
				}
				if (extras != null) {
					Log.v("here", "heada");
					Bitmap photo = extras.getParcelable("data");
					byte[] imgByte = ImgAction.BitmaptoBytes(photo);
					// ====将图片保存到本地=====
					headImageNameString = SharePreferenceUtils
							.getIntance(
									UpLoadHeadActivity.this)
							.getCurrentUserName();
					String fileName = ImgAction.bytesToFile(imgByte,
							headImageNameString);// 临时文件名为username
					final String filePath = Environment
							.getExternalStorageDirectory().getPath()
							+ "/iwantme/head/";
					DefineDisplayView.showLoadingDialog(UpLoadHeadActivity.this);
					new Thread() {
						public void run() {

							FtpLoadTool.getInstance(new UploadInterface() {

								@Override
								public void onSuccess(int arg0, String arg1) {
									Message message=new Message();
									message.what=1;
									handler.sendMessage(message);
								}

								@Override
								public void onFailure(Throwable error,
										String content) {

								}
							}).ftpUpload(filePath,headImageNameString + ".jpg");
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
			ArrayList<ImageBean> imageBeans=new ArrayList<ImageBean>();
			ImageBean imageBean = new ImageBean();
			imageBean.setHeadimg(Common.URL_IMG + headImageNameString+".jpg");
			imageBean.setUsername(SharePreferenceUtils
					.getIntance(
							UpLoadHeadActivity.this)
					.getCurrentUserName());
			imageBeans.add(imageBean);
			ImageListBean listBean=new ImageListBean();
			listBean.setArray(imageBeans);
			configuration.setClassName(ImageListBean.class);
			configuration.setViewDataObject(listBean);
			new MyselfInfoController(configuration).addHeadImg();
		}
		
		
	};
	
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		ImageLoader.getInstance().displayImage(
				Common.URL_IMG + headImageNameString+".jpg",
				headImageView,
				InitDisplayImageOptions.getInstance().getHeadOptions(false,
						false, 0),
				InitDisplayImageOptions.getInstance()
				
				.getImageLoadingListener());
		UIHelper.ToastMessage(UpLoadHeadActivity.this, "头像上传成功,请点击开启");
		// 登录的时候要传入登录监听，当重复登录时会直接返回登录状态
		isSuccess=true;
	    /*GotyeAPI.getInstance().addListerer(UpLoadHeadActivity.this);
		int i = GotyeAPI.getInstance().login(SharePreferenceUtils.getIntance(UpLoadHeadActivity.this).getCurrentUserName(), null);
		// 根据返回的code判断
		if (i == GotyeStatusCode.CODE_OK) {
			// 已经登陆
			onLogin(i, null);
		}*/
	
	}
	
	
	private static final String CONFIG = "login_config";

	public void saveUser(String name, String password) {
		SharedPreferences sp = getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("username", name);
		edit.putString("password", password);
		edit.commit();
	}

	public static String[] getUser(Context context) {
		SharedPreferences sp = context.getSharedPreferences(CONFIG,
				Context.MODE_PRIVATE);
		String name = sp.getString("username", null);
		String password = sp.getString("password", null);
		String[] user = new String[2];
		user[0] = name;
		user[1] = password;
		return user;
	}
	@Override
	protected void onDestroy() {
		// 移除监听
		GotyeAPI.getInstance().removeListener(this);
		super.onDestroy();
	}

	@Override
	public void onLogin(int code, GotyeUser user) {
		ProgressDialogUtil.dismiss();
		// 判断登陆是否成功
		if (code == GotyeStatusCode.CODE_OK) {
			Intent toService = new Intent(this, GotyeService.class);
			startService(toService);
			user_s = api.getCurrentLoginUser();
			api.requestUserInfo(user_s.name, true);
			GotyeUser forModify=new GotyeUser(user_s.getName());
			Log.v("NICK", SharePreferenceUtils.getIntance(UpLoadHeadActivity.this).getCurrentNickName());
			forModify.setNickname(SharePreferenceUtils.getIntance(UpLoadHeadActivity.this).getCurrentNickName());
			forModify.setInfo(user_s.getInfo());
			forModify.setGender(user_s.getGender());
			forModify.setName(user_s.getName());
			headPath=FilePath + SharePreferenceUtils
					.getIntance(
							UpLoadHeadActivity.this)
					.getCurrentUserName() + ".jpg";
			Bitmap smaillBit = BitmapUtil.getSmallBitmap(headPath, 50, 50);
			headPath = BitmapUtil.saveBitmapFile(smaillBit);
			int code1 =api.modifyUserInfo(forModify, "");
			Log.v("modify-code", code1+"");
		/*	Log.v("start", "###################");
			Log.v("name", user_s.getName());
			Log.v("nickname", SharePreferenceUtils.getIntance(UpLoadHeadActivity.this).getCurrentNickName());
			Log.v("info", user_s.getInfo());
			Log.v("setGender", user_s.getGender().toString());
			Log.v("headpath", headPath);
			Log.v("end", "###################");*/
			Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
			//this.finish();
		} else {
			// 失败,可根据code定位失败原因
			Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
		}
	}
	boolean hasRequest = false;
	
	private void setUserInfo(GotyeUser user) {
		if (user.getIcon() == null && !hasRequest) {
			hasRequest = true;
			api.requestUserInfo(user.name, true);
		} else {
		}
	}
	@Override
	public void onLogout(int code) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onRequestUserInfo(int code, GotyeUser user) {
		Log.v("onRequestUserInfo", "here");
		setUserInfo(user);
	}

	@Override
	public void onModifyUserInfo(int code, GotyeUser user) {
		Log.v("onModifyUserInfo", "here");
		if (code == 0) {
			UIHelper.ToastMessage(UpLoadHeadActivity.this, "头像上传成功,请点击开启");
		} else {
			ToastUtil.show(UpLoadHeadActivity.this, "修改失败!");
		}
	}
	private void setPicture(String path) {
		Log.v("setPicture", "here");
	File f = new File(PathUtil.getAppFIlePath());
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		File file = new File(PathUtil.getAppFIlePath()
				+ System.currentTimeMillis() + "jpg");
		if (file.exists()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Bitmap smaillBit = BitmapUtil.getSmallBitmap(path, 50, 50);
		headPath = BitmapUtil.saveBitmapFile(smaillBit);
		Log.v("headPath", headPath+"");
	}
	
}
