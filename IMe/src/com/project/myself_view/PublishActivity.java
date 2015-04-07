package com.project.myself_view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.base.common.Common;
import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.adpter.PublishAdapter;
import com.project.iwant.R;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.MyselfInfoListBean;
import com.project.myself_model.PublishBean;
import com.project.myself_model.PublishListBean;
import com.utils.app.TimestampTool;
import com.utils.app.UIHelper;
import com.utils.http.FtpLoadTool;
import com.utils.http.UploadInterface;
import com.utils.io.SharePreferenceUtils;
import com.utils.io.StorageEnvironmentUtils;
import com.utils.os.BitmapTool;
import com.utils.os.ImgAction;
import com.utils.security.MD5;
import com.utils.widght.DefineDisplayView;

public class PublishActivity extends BaseActivity {
	private static List<String> mImgList = new ArrayList<String>();
	private static List<String> imagepaths = new ArrayList<String>();
	private LinearLayout setup_return_btns;
	private EditText et_public_content;
	private Button btn_publish;
	private static int flag_img = 0;
	private GridView gv_addimg;
	private static PublishAdapter gv_adapter;
	private static Context mPDContext;
	private static Activity mPDActivity;

	public static String FilePath = StorageEnvironmentUtils.getSDCardPath()
			+ "/iwantme/head/";
	public static final String IMAGE_UNSPECIFIED = "image/*";
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	final static CharSequence[] items = { "从相册上传", "手机拍照上传" };
	private static String mUsername = "test";
	private PublishListBean publishListBean = new PublishListBean();
	private Configuration configuration;
	private int flag_nearby_0_or_myinfo_1 = 0;
	private static List<String> imgname = new ArrayList<String>();
	private int cout=0;

	public PublishActivity() {
		super("");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImgList.clear();
		imgname.clear();
		setContentView(R.layout.activity_publish);
		Intent intent = getIntent();
		flag_nearby_0_or_myinfo_1 = intent.getIntExtra("flag", 0);
		initView();
		initConfiguration(PublishActivity.this, "PublishActivity");

	}

	@Override
	public void initView() {
		mUsername = SharePreferenceUtils.getIntance(PublishActivity.this)
				.getCurrentUserName();
		mPDContext = PublishActivity.this;
		mPDActivity = PublishActivity.this;
		gv_addimg = (GridView) findViewById(R.id.dynamic_publish_gv);
		gv_adapter = new PublishAdapter(mPDContext, mImgList);
		gv_addimg.setAdapter(gv_adapter);
		btn_publish = (Button) findViewById(R.id.dynamic_publish_btn);
		setup_return_btns = (LinearLayout) this
				.findViewById(R.id.setup_return_btns);
		setup_return_btns.setOnClickListener(new myOnClickListener());
		btn_publish.setOnClickListener(new myOnClickListener());
		TextView so_iteam_tv = (TextView) this.findViewById(R.id.so_iteam_tv);
		so_iteam_tv.setText("发布");
		et_public_content = (EditText) this
				.findViewById(R.id.dynamic_publish_et);
	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
		configuration.setClassName(MyselfInfoListBean.class);
	}

	private class myOnClickListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			if (arg0 == setup_return_btns) {
				finish();
			} else if (arg0 == btn_publish) {
				if (et_public_content.getText().toString().equals("")
						&& mImgList.size() == 0) {
					Toast.makeText(PublishActivity.this, "请您输入动态内容，或者选择要上传的图片",
							Toast.LENGTH_SHORT).show();
				} else {
					DefineDisplayView.showLoadingDialog(PublishActivity.this);
					ArrayList<PublishBean> myselfInfoBeans = new ArrayList<PublishBean>();
					final String filePath =StorageEnvironmentUtils.getSDCardPath()
							+ "/iwantme/head/";
					
					for (int i = 0; i < mImgList.size(); i++) {
						PublishBean myselfInfoBean = new PublishBean();
						myselfInfoBean.setUser(mUsername);
						myselfInfoBean.setAddress(SharePreferenceUtils.getIntance(
								PublishActivity.this).getLocationAddress());
						myselfInfoBean.setTime(TimestampTool.getCurrentTime());
						myselfInfoBean.setContent(et_public_content.getText()
								.toString());
						if (flag_nearby_0_or_myinfo_1 == 0) {
							myselfInfoBean.setLat(SharePreferenceUtils.getIntance(
									PublishActivity.this).getLocationLat());
							myselfInfoBean.setLng(SharePreferenceUtils.getIntance(
									PublishActivity.this).getLocationLng());
						}
						myselfInfoBean.setImg(Common.URL_IMG + imgname.get(i)
								+ ".jpg");
						myselfInfoBeans.add(myselfInfoBean);
						final int m = i;
						new Thread() {
							public void run() {

								Log.v("mImgList.size()", mImgList.get(m));

								/** test ftp **/
								FtpLoadTool.getInstance(new UploadInterface() {

									@Override
									public void onSuccess(int arg0, String arg1) {
										cout++;
									    Log.v("here", "here");
										Message message=new Message();
										message.what=1;
										handler.sendMessage(message);
									}

									@Override
									public void onFailure(Throwable error,
											String content) {

									}
								}).ftpUpload(filePath, imgname.get(m) + ".jpg");
							}

							/*
							 * File file=new File(mImgList.get(i));
							 * ImageUpLoad.initBcsSdk
							 * (file,"/"+MD5.GetMD5Code(mUsername
							 * +TimestampTool.getCurrentTime()+flag_img));
							 */

						}.start();

					}
					publishListBean.setArray(myselfInfoBeans);
					if (mImgList.size()!=0) {
					
					}else {
						PublishBean myselfInfoBean = new PublishBean();
						myselfInfoBean.setUser(mUsername);
						myselfInfoBean.setAddress(SharePreferenceUtils.getIntance(
								PublishActivity.this).getLocationAddress());
						myselfInfoBean.setTime(TimestampTool.getCurrentTime());
						myselfInfoBean.setContent(et_public_content.getText()
								.toString());
						if (flag_nearby_0_or_myinfo_1 == 0) {
							myselfInfoBean.setLat(SharePreferenceUtils.getIntance(
									PublishActivity.this).getLocationLat());
							myselfInfoBean.setLng(SharePreferenceUtils.getIntance(
									PublishActivity.this).getLocationLng());
						}
						myselfInfoBeans.add(myselfInfoBean);
						// 发布
						configuration.setViewDataObject(publishListBean);
						if (flag_nearby_0_or_myinfo_1 == 0) {
							new MyselfInfoController(configuration).addNearByInfo();
						} else {
							new MyselfInfoController(configuration).addMyselfInfo();
						}
					}
					

					// finish();
				}
			}
		}
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if (cout==mImgList.size()) {
				configuration.setViewDataObject(publishListBean);
				if (flag_nearby_0_or_myinfo_1 == 0) {
					new MyselfInfoController(configuration).addNearByInfo();
				} else {
					new MyselfInfoController(configuration).addMyselfInfo();
				}
				cout=0;
			}else {
				
				
			}
			
			
		};
		
	};
	
	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		if (cout!=0) {
			UIHelper.ToastMsg(PublishActivity.this, "发布图片失败请重试");
		}else {
			if (flag_nearby_0_or_myinfo_1 == 0) {
				NearbyActivity.getNearData();
			}else {
				MyselfInfoActivity.getUserInfo();
			}
			
			finish();
		}
		
	}

	@Override
	public void requestDataIsNull(String objectString) {
		super.requestDataIsNull(objectString);
	}

	/**
	 * 操作选择
	 * 
	 * @param items
	 */
	public static void imageChooseItem() {
		AlertDialog alert = new AlertDialog.Builder(mPDContext)
				.setTitle("请选择图片来源：")
				.setSingleChoiceItems(items, -1,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int item) {
								if (item == 0) {
									mImgList = new ArrayList<String>();
									Intent intent = new Intent(mPDContext,
											ChoicePicActivity.class);
									mPDActivity.startActivityForResult(intent,
											PHOTOZOOM);
									dialog.dismiss();
								}
								if (item == 1) {
									mImgList = new ArrayList<String>();
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									intent.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(FilePath
													+ mUsername + flag_img
													+ ".jpg")));
									mPDActivity.startActivityForResult(intent,
											PHOTOHRAPH);
									dialog.dismiss();
								}
							}
						}).create();
		alert.show();
	}

	public static void deleteImage(int position) {

		// 删除指定行，然后刷新
		mImgList.remove(position);
		gv_adapter.notifyDataSetChanged();
	}

	/**
	 * author：luoli
	 * 
	 * work for：图片处理
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTOHRAPH) {

			// 设置文件保存路径这里放在跟目录下
			File picture = new File(FilePath + mUsername + flag_img + ".jpg");
			startPhotoZoom(Uri.fromFile(picture));
			// uploadImg(Uri.fromFile(picture), null);
		}

		if (data == null)
			return;

		// 读取相册缩放图片
		if (requestCode == PHOTOZOOM) {
			// UIHelper.ToastMessage(mPDContext, "拍照完成");
			// startPhotoZoom(data.getData());
			ArrayList<String> list = (ArrayList<String>) data
					.getSerializableExtra("list");
			for (int i = 0; i < list.size(); i++) {
				flag_img = i + 1;
				uploadImg(null, new File(list.get(i)));
			}
		}
		// 处理结果
		if (requestCode == PHOTORESOULT) {
			try {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap photo = extras.getParcelable("data");
					byte[] imgByte = ImgAction.BitmaptoBytes(photo);
					flag_img = 1;
					String fileName = ImgAction.bytesToFile(imgByte, mUsername
							+ flag_img);
					uploadImg(fileName, null);
					// UIHelper.ToastMessage(mPDContext, "拍照完成");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 收缩图片
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 500);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTORESOULT);
	}

	protected void uploadImg(String path, File file) {
		Bitmap photo1;
		try {
			if (path != null || file != null) {
				if (path != null) {
					photo1 = BitmapTool.revitionImageSize(path);
				} else {
					photo1 = BitmapTool.revitionImageSize(file
							.getAbsolutePath());
				}
				byte[] imgByte = ImgAction.BitmaptoBytes(photo1);
				// // ====将图片保存到本地=====
				String picnameString = MD5.GetMD5Code(mUsername
						+ TimestampTool.getCurrentTime() + flag_img);
				String fileName = ImgAction.bytesToFile(imgByte, picnameString);// 临时文件名为username
				mImgList.add(fileName);
				imgname.add(picnameString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.e("图片列表", mImgList.toString());
		gv_adapter = new PublishAdapter(mPDContext, mImgList);
		gv_addimg.setAdapter(gv_adapter);
	}

}
