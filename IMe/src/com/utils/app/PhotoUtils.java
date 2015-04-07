package com.utils.app;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;

/**
 * @description 操作本地相册类
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-8下午4:03:18
 */
public class PhotoUtils {
	public static final int NONE = 0;
	public static final int PHOTOHRAPH = 1;// 拍照
	public static final int PHOTOZOOM = 2; // 缩放
	public static final int PHOTORESOULT = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";
	
	/**
	 *@function  对选择的照片进行处理
	 * @time 2014-10-8下午4:16:18
	 * @param uri
	 * @param context
	 * @return
	 * Intent
	 */
	public static Intent startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 80);
		intent.putExtra("outputY", 80);
		intent.putExtra("return-data", true);
	    return intent;
	}
	
	/** 选择本地照片
	 *@function  
	 * @time 2014-10-8下午4:18:35
	 * @return
	 * Intent
	 */
	public static Intent choiceLocalPhotos(){
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
		return intent;
		
	}
	
	/**
	 *@function  照相
	 * @time 2014-10-8下午4:19:46
	 * @return
	 * Intent
	 */
	public static Intent choiceTakePhotos(Parcelable  par){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, par);
		return intent;
		
	}

}
