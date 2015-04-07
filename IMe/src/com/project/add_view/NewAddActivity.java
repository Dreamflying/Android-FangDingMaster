package com.project.add_view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.base.common.Common;
import com.base.model.Configuration;
import com.base.view.BaseActivity;
import com.project.add_controller.AddController;
import com.project.add_controller.MainGVAdapter;
import com.project.add_model.AddBean;
import com.project.add_model.AddListBean;
import com.project.add_model.PostImageBean;
import com.project.iwant.NewMainFragment;
import com.project.iwant.NewMainTabActivity;
import com.project.iwant.R;
import com.project.message_view.MessageActivity;
import com.project.message_view.MsgExpGv;
import com.project.message_view.MessageActivity.MyOnPageChangeListener;
import com.project.message_view.MessageActivity.MyViewPagerAdapter;
import com.project.message_view.MsgExpGv.IaddExp;
import com.project.myself_controlloer.MyselfInfoController;
import com.project.myself_model.PublishBean;
import com.project.myself_model.PublishListBean;
import com.project.myself_view.Constants;
import com.project.myself_view.MyselfInfoActivity;
import com.project.myself_view.NearbyActivity;
import com.project.myself_view.PublishActivity;
import com.utils.app.ScreenUtils;
import com.utils.app.StringUtils;
import com.utils.app.TimestampTool;
import com.utils.app.UIHelper;
import com.utils.app.Utility;
import com.utils.http.FtpLoadTool;
import com.utils.http.LocationUtils;
import com.utils.http.UploadInterface;
import com.utils.io.SharePreferenceUtils;
import com.utils.widght.DefineDisplayView;

/**
 * 主页面，可跳转至相册选择照片，并在此页面显示所选择的照片。 Created by hanj on 14-10-13.
 */
public class NewAddActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	private static EditText addContentEv;
	private Button addOkBtn;
	private Button add_camera, add_photo, add_express;
	private TextView addAddressTv;
	private Button returnBtn;
	private String path_camera;
	private static ViewPager viewPager;// 页卡内容
	private List<View> views;// Tab页面列表
	private int currIndex = 0;// 当前页卡编号
	private View exp1, exp2, exp3;// 各个页卡
	private InputMethodManager imm;
	private static Context mContext;
	private LinearLayout add_tab;
	private int flag = 0;
	/*
	 * 显示图片
	 */
	private static GridView mExpGv1, mExpGv2, mExpGv3;

	public NewAddActivity() {
		super("");
		// TODO Auto-generated constructor stub
	}

	private MainGVAdapter adapter;
	private ArrayList<String> imagePathList;
	private GridView imagesList;
	private String[] imageNamesList;
	private Configuration configuration;
	private AddListBean addListBean = new AddListBean();
	private PublishListBean publishListBean = new PublishListBean();
	private int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add);
		mContext = NewAddActivity.this;
		initConfiguration(NewAddActivity.this, "NewAddActivity");
		imm = (InputMethodManager) getSystemService(NewAddActivity.this.INPUT_METHOD_SERVICE);
		Intent intent = getIntent();
		flag = intent.getIntExtra("flag", 0);
		initView();
		setListener();
		InitViewPager();
		showExp();
	}

	@Override
	public void initConfiguration(BaseActivity viewActivity, String activityName) {
		super.initConfiguration(viewActivity, activityName);
		configuration = getConfiguration();
	}

	@Override
	public void initView() {
		super.initView();
		// 获取屏幕像素
		ScreenUtils.initScreen(this);
		Button selectImgBtn = (Button) findViewById(R.id.add_image);
		imagesList = (GridView) findViewById(R.id.main_gridView);
		addAddressTv = (TextView) this.findViewById(R.id.add_address_tv);
		returnBtn = ( Button) this.findViewById(R.id.add__title_back_ll);
		addContentEv = (EditText) this.findViewById(R.id.add_new_tv);
		addOkBtn = (Button) this.findViewById(R.id.add_ok_btn);
		add_tab = (LinearLayout) this.findViewById(R.id.add_tab);
		add_camera = (Button) this.findViewById(R.id.add_from_camera);
		add_photo = (Button) this.findViewById(R.id.add_from_photo);
		add_express = (Button) this.findViewById(R.id.add_express);

		addAddressTv.setText(LocationUtils.getAddress());
		imagePathList = new ArrayList<String>();
		adapter = new MainGVAdapter(this, imagePathList);
		imagesList.setAdapter(adapter);
		selectImgBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 跳转至最终的选择图片页面

			}
		});
		imagesList.setOnItemClickListener(this);
	}

	@Override
	public void setListener() {
		addOkBtn.setOnClickListener(this);
		returnBtn.setOnClickListener(this);
		add_camera.setOnClickListener(this);
		add_photo.setOnClickListener(this);
		add_express.setOnClickListener(this);
	}

	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager1);
		views = new ArrayList<View>();
		LayoutInflater inflater = getLayoutInflater();
		exp1 = inflater.inflate(R.layout.msg_exp1, null);
		exp2 = inflater.inflate(R.layout.msg_exp2, null);
		exp3 = inflater.inflate(R.layout.msg_exp3, null);
		views.add(exp1);
		views.add(exp2);
		views.add(exp3);
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mExpGv1 = (GridView) exp1.findViewById(R.id.msg_exp_gv1);
		mExpGv2 = (GridView) exp2.findViewById(R.id.msg_exp_gv2);
		mExpGv3 = (GridView) exp3.findViewById(R.id.msg_exp_gv3);
	}

	private void showExp() {
		MsgExpGv mMsgExpGv1 = new MsgExpGv(mContext, 0, new IaddExp() {

			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);

			}
		});
		MsgExpGv mMsgExpGv2 = new MsgExpGv(mContext, 1, new IaddExp() {

			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);

			}
		});
		MsgExpGv mMsgExpGv3 = new MsgExpGv(mContext, 2, new IaddExp() {

			@Override
			public void addIExp(String str, int positon) {
				addExp(str, positon);

			}
		});
		mExpGv1.setAdapter(mMsgExpGv1);
		mExpGv2.setAdapter(mMsgExpGv2);
		mExpGv3.setAdapter(mMsgExpGv3);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v("here", "here");
		switch (requestCode) {
		case 1:
			int code = data.getIntExtra("code", -1);
			if (code != 100) {
				return;
			}

			ArrayList<String> paths = data.getStringArrayListExtra("paths");
			// 添加，去重
			boolean hasUpdate = false;
			for (String path : paths) {
				if (!imagePathList.contains(path)) {
					// 最多9张
					if (imagePathList.size() == 9) {
						Utility.showToast(this, "最多可添加9张图片。");
						break;
					}
					Log.v("path=photo", path);
					imagePathList.add(path);
					hasUpdate = true;
				}
			}

			if (hasUpdate) {
				adapter.notifyDataSetChanged();
			}
			break;
		case 2:
			boolean hasUpdates = false;
			if (!imagePathList.contains(path_camera)) {
				// 最多9张
				if (imagePathList.size() == 9) {
					Utility.showToast(this, "最多可添加9张图片。");
					break;
				}
				Log.v("path=camera", path_camera);
				if (path_camera!=null&&!path_camera.equals("")&&isImage(path_camera)) {
					imagePathList.add(path_camera);
					hasUpdates = true;

				}
							}
			if (hasUpdates) {
				adapter.notifyDataSetChanged();
			}
			break;
		}
	}

	@Override
	public void requestSuccess(Object object) {
		super.requestSuccess(object);
		switch (flag) {
		case 1:// 动态
			MyselfInfoActivity.getUserInfo();
			break;
		case 2:// 隔壁
			NearbyActivity.getNearData();
			break;
		case 3:// 发布
			NewMainFragment.loadData(0);
			break;
		default:
			break;
		}
		finish();
		
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.add_ok_btn:
			if (!StringUtils.isEmpty(addContentEv.getText().toString())) {
				
			
			if (imagePathList.size() != 0) {

				imageNamesList = new String[imagePathList.size()];
				for (int i = 0; i < imagePathList.size(); i++) {
					final String[] imageNames = imagePathList.get(i).split("/");
					imageNamesList[i] = imageNames[imageNames.length - 1];
					final String paths = imagePathList.get(i).replace(
							imageNames[imageNames.length - 1], "");
					Log.v("imageNames", imageNames[imageNames.length - 1]);
					Log.v("paths", paths);
					new Thread() {
						public void run() {

							/** test ftp **/
							FtpLoadTool.getInstance(new UploadInterface() {

								@Override
								public void onSuccess(int arg0, String arg1) {
									count++;
									Log.v("here", "here");
									Message message = new Message();
									message.what = 1;
									handler.sendMessage(message);
								}

								@Override
								public void onFailure(Throwable error,
										String content) {

								}
							}).ftpUpload(paths,
									imageNames[imageNames.length - 1]);
						}
					}.start();
				}
			} else {
				switch (flag) {
				case 1:// 动态
					ArrayList<PublishBean> myselfInfoBeans = new ArrayList<PublishBean>();
						PublishBean myselfInfoBean = new PublishBean();
						myselfInfoBean.setUser(SharePreferenceUtils.getIntance(NewAddActivity.this).getCurrentUserName());
						myselfInfoBean.setAddress(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationAddress());
						myselfInfoBean.setTime(TimestampTool.getCurrentTime());
						myselfInfoBean.setContent(addContentEv.getText().toString());
						myselfInfoBeans.add(myselfInfoBean);
					publishListBean.setArray(myselfInfoBeans);
					configuration.setClassName(PublishListBean.class);
					configuration.setViewDataObject(publishListBean);
					new MyselfInfoController(configuration).addMyselfInfo();
					break;
				case 2:// 隔壁
					ArrayList<PublishBean> getbi = new ArrayList<PublishBean>();
					
						PublishBean myselfInfoBean2 = new PublishBean();
						myselfInfoBean2.setUser(SharePreferenceUtils.getIntance(NewAddActivity.this).getCurrentUserName());
						myselfInfoBean2.setAddress(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationAddress());
						myselfInfoBean2.setTime(TimestampTool.getCurrentTime());
						myselfInfoBean2.setLat(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationLat());
						myselfInfoBean2.setLng(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationLng());
						myselfInfoBean2.setContent(addContentEv.getText().toString());
						getbi.add(myselfInfoBean2);
					publishListBean.setArray(getbi);
					configuration.setClassName(PublishListBean.class);
					configuration.setViewDataObject(publishListBean);
					new MyselfInfoController(configuration).addNearByInfo();
					break;
				case 3:// 发布
					ArrayList<AddBean> addBeans = new ArrayList<AddBean>();
					AddBean addBean = new AddBean();
					addBean.setUsername(SharePreferenceUtils.getIntance(mContext)
							.getCurrentUserName());
					addBean.setAddress(addAddressTv.getText().toString());
					addBean.setAddtime((TimestampTool.getCurrentTime()) + "");
					addBean.setContent(addContentEv.getText().toString());
					addBean.setLat(LocationUtils.getLatitude());
					addBean.setLng(LocationUtils.getLongitude());
					addBean.setStatus(0);
					ArrayList<PostImageBean> imageBeans = new ArrayList<PostImageBean>();
					for (int i = 0; i < imagePathList.size(); i++) {
						PostImageBean imageBean = new PostImageBean();
						imageBean.setImg_url(Common.URL_IMG + imageNamesList[i]);
						imageBeans.add(imageBean);
					}
					addBean.setType(0);
					addBean.setMePostImgs(imageBeans);
					addBeans.add(addBean);
					addListBean.setArray(addBeans);
					configuration.setViewDataObject(addListBean);
					configuration.setClassName(AddListBean.class);
					new AddController(configuration).addPost();

					break;
				default:
					break;
				}
				
			}

			DefineDisplayView.showLoadingDialog(NewAddActivity.this);}else{
				UIHelper.ToastMessage(NewAddActivity.this, "说出你想要的吧");
			}
			break;
		case R.id.add__title_back_ll:
			imm.hideSoftInputFromWindow(addContentEv.getWindowToken(), 0);
			finish();
			break;
		case R.id.add_from_camera:
			getImageFromCamera();
			break;
		case R.id.add_from_photo:
			Intent intent = new Intent(NewAddActivity.this,
					PhotoWallActivity.class);
			startActivityForResult(intent, 1);
			break;
		case R.id.add_express:
			if (getFaceStatus()) {
				hideFace();
			} else {

				showFace();

			}
			break;
		default:
			break;
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (count == imagePathList.size()) {

				switch (flag) {
				case 1:// 动态
					ArrayList<PublishBean> myselfInfoBeans = new ArrayList<PublishBean>();
					for (int i = 0; i < imagePathList.size(); i++) {
						PublishBean myselfInfoBean = new PublishBean();
						myselfInfoBean.setUser(SharePreferenceUtils.getIntance(NewAddActivity.this).getCurrentUserName());
						myselfInfoBean.setAddress(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationAddress());
						myselfInfoBean.setTime(TimestampTool.getCurrentTime());
						myselfInfoBean.setContent(addContentEv.getText().toString());
						myselfInfoBean.setImg(Common.URL_IMG + imageNamesList[i]);
						myselfInfoBeans.add(myselfInfoBean);
					}
					publishListBean.setArray(myselfInfoBeans);
					configuration.setClassName(PublishListBean.class);
					configuration.setViewDataObject(publishListBean);
					new MyselfInfoController(configuration).addMyselfInfo();
					break;
				case 2:// 隔壁
					ArrayList<PublishBean> getbi = new ArrayList<PublishBean>();
					for (int i = 0; i < imagePathList.size(); i++) {
						PublishBean myselfInfoBean = new PublishBean();
						myselfInfoBean.setUser(SharePreferenceUtils.getIntance(NewAddActivity.this).getCurrentUserName());
						myselfInfoBean.setAddress(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationAddress());
						myselfInfoBean.setTime(TimestampTool.getCurrentTime());
						myselfInfoBean.setLat(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationLat());
						myselfInfoBean.setLng(SharePreferenceUtils.getIntance(
								NewAddActivity.this).getLocationLng());
						myselfInfoBean.setContent(addContentEv.getText().toString());
						myselfInfoBean.setImg(Common.URL_IMG + imageNamesList[i]);
						getbi.add(myselfInfoBean);
					}
					publishListBean.setArray(getbi);
					configuration.setClassName(PublishListBean.class);
					configuration.setViewDataObject(publishListBean);
					new MyselfInfoController(configuration).addNearByInfo();
					break;
				case 3:// 发布
					ArrayList<AddBean> addBeans = new ArrayList<AddBean>();
					AddBean addBean = new AddBean();
					addBean.setUsername(SharePreferenceUtils.getIntance(mContext)
							.getCurrentUserName());
					addBean.setAddress(addAddressTv.getText().toString());
					addBean.setAddtime((TimestampTool.getCurrentTime()) + "");
					addBean.setContent(addContentEv.getText().toString());
					addBean.setLat(LocationUtils.getLatitude());
					addBean.setLng(LocationUtils.getLongitude());
					addBean.setStatus(0);
					ArrayList<PostImageBean> imageBeans = new ArrayList<PostImageBean>();
					for (int i = 0; i < imagePathList.size(); i++) {
						PostImageBean imageBean = new PostImageBean();
						imageBean.setImg_url(Common.URL_IMG + imageNamesList[i]);
						imageBeans.add(imageBean);
					}
					addBean.setType(0);
					addBean.setMePostImgs(imageBeans);
					addBeans.add(addBean);
					addListBean.setArray(addBeans);
					configuration.setViewDataObject(addListBean);
					configuration.setClassName(AddListBean.class);
					new AddController(configuration).addPost();

					break;
				default:
					break;
				}
			}
		}

	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	protected void getImageFromCamera() {
		String state = Environment.getExternalStorageState();

		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent getImageByCamera = new Intent(
					"android.media.action.IMAGE_CAPTURE");
			String out_file_path = Environment.getExternalStorageDirectory()
					.getPath() + "/me/addimage/";
			File dir = new File(out_file_path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			path_camera = out_file_path + System.currentTimeMillis() + ".jpg";
			getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(path_camera)));
			getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(getImageByCamera, 2);
		} else {
			Toast.makeText(getApplicationContext(), "请确认已经插入SD卡",
					Toast.LENGTH_LONG).show();
		}
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			return mListViews.get(position);
		}
		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			currIndex = arg0;
		}
	}

	/*
	 * 添加表情
	 */
	public static void addExp(String str, int postion) {
		if (postion <= 21) {
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					mContext.getResources(), Constants.face1[postion - 1]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString(str);
			// 用ImageSpan对象替换face
			if (postion <= 9) {
				spannableString.setSpan(imageSpan, 0, 3,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			} else {
				spannableString.setSpan(imageSpan, 0, 3,
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			// 将随机获得的图像追加到EditText控件的最后
			addContentEv.append(spannableString);

		} else if (postion <= 43 && postion > 21) {
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					mContext.getResources(), Constants.face2[postion - 22]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString(str);
			// 用ImageSpan对象替换face
			spannableString.setSpan(imageSpan, 0, 3,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 将随机获得的图像追加到EditText控件的最后
			addContentEv.append(spannableString);

		} else {
			// 根据资源ID获得资源图像的Bitmap对象
			Bitmap bitmap = BitmapFactory.decodeResource(
					mContext.getResources(), Constants.face3[postion - 44]);
			// 根据Bitmap对象创建ImageSpan对象
			ImageSpan imageSpan = new ImageSpan(mContext, bitmap);
			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString(str);
			// 用ImageSpan对象替换face
			spannableString.setSpan(imageSpan, 0, 3,
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			// 将随机获得的图像追加到EditText控件的最后
			addContentEv.append(spannableString);

		}

		addContentEv.setText(addContentEv.getText());
	}

	private void showFace() {
		imm.hideSoftInputFromWindow(addContentEv.getWindowToken(), 0);
		viewPager.setVisibility(View.VISIBLE);
		Log.v("showface", "showface");
		// 强行隐藏输入法
	}

	private void hideFace() {
		viewPager.setVisibility(View.GONE);
	}

	private boolean getFaceStatus() {
		if (viewPager.getVisibility() == View.VISIBLE) {
			Log.v("showface", "true");
			return true;
		} else {
			Log.v("showface", "false");
			return false;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(imagePathList.size()!=0){
			imagePathList.remove(arg2);
			adapter.notifyDataSetChanged();
			
		}
		
	}
	/*
	 * 判定本地图片是否存在
	 */
	public static boolean isImage(String mImgName) {
		try {
			File f = new File(mImgName);
			if (!f.exists()) {
				Log.v("图片存在", "图片存在");
				return false;

			}
			f.createNewFile();

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		Log.v("图片不存在", "图片不存在");
		return true;
	}
}
