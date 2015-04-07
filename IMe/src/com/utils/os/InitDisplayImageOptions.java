package com.utils.os;

import android.annotation.SuppressLint;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.project.adpter.AnimateFirstDisplayListener;
import com.project.iwant.R;

/**
 *  @description 获取 ImageOptions
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-4上午10:40:26
 */
public class InitDisplayImageOptions {
	
	
	private static InitDisplayImageOptions imageOptions=new InitDisplayImageOptions();
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener ;
	private  InitDisplayImageOptions(){
		
		
	}
	
	public static InitDisplayImageOptions getInstance(){
		return imageOptions;
	}
	
	/**
	 *@function  获取imageoptions
	 * @time 2014-10-4上午10:46:51
	 * @param isCacheInMemory
	 * @param isCacheOnDisk
	 * @param round
	 * @return
	 * DisplayImageOptions  
	 */
	public DisplayImageOptions getOptions(boolean isCacheInMemory,boolean isCacheOnDisk,int round){
		
		 options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.loding)
		.showImageForEmptyUri(R.drawable.loding)
		.showImageOnFail(R.drawable.loding)
		.cacheInMemory(isCacheInMemory)
		.cacheOnDisk(isCacheOnDisk)
		.bitmapConfig(Config.RGB_565)
		.considerExifParams(true)
		.displayer(new BitmapDisplayer() {
			
			@Override
			public void display(Bitmap arg0, ImageAware arg1, LoadedFrom arg2) {
				// TODO Auto-generated method stub
				//arg0=Bitmap.createBitmap(50,50,Config.RGB_565);
				arg1.setImageBitmap(arg0);
			}
		})
		.imageScaleType(ImageScaleType.EXACTLY)//设置图片缩放
		.build();
		return options;
		
	}
	
	public DisplayImageOptions getHeadOptions(boolean isCacheInMemory,boolean isCacheOnDisk,int round){
		
		 options = new DisplayImageOptions.Builder()
		/*.showImageOnLoading(R.drawable.logo_logo)*/
		.showImageForEmptyUri(R.drawable.gou1)
		/*.showImageOnFail(R.drawable.logo_logo)*/
		.cacheInMemory(isCacheInMemory)
		.cacheOnDisk(isCacheOnDisk)
		.bitmapConfig(Config.RGB_565)
		.considerExifParams(true)
		.displayer(new RoundedBitmapDisplayer(round))
		.imageScaleType(ImageScaleType.EXACTLY)//设置图片缩放
		.build();
		return options;
		
	}
	
	/**
	 *@function  获取下载图片监听对象
	 * @time 2014-10-4上午11:03:29
	 * @return
	 * ImageLoadingListener
	 */
	public ImageLoadingListener getImageLoadingListener(){
		animateFirstListener= new AnimateFirstDisplayListener();
		return animateFirstListener;
	}
	public static int calculateInSampleSize(BitmapFactory.Options options,  
            int reqWidth, int reqHeight) {  
          
        final int height = options.outHeight;  
        final int width = options.outWidth;  
        int inSampleSize = 1;  
  
        if (height > reqHeight || width > reqWidth) {  
  
            final int heightRatio = Math.round((float) height  
                    / (float) reqHeight);  
            final int widthRatio = Math.round((float) width / (float) reqWidth);  
  
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;  
        }  
  
        return inSampleSize;  
    }  
	
	public DisplayImageOptions getOptionsAuto(boolean isCacheInMemory,boolean isCacheOnDisk,int round){
		
		 options = new DisplayImageOptions.Builder()
		.cacheInMemory(isCacheInMemory)
		.cacheOnDisk(isCacheOnDisk)
		.bitmapConfig(Config.RGB_565)
		.considerExifParams(true)
		.displayer(new BitmapDisplayer() {
			
			@Override
			public void display(Bitmap arg0, ImageAware arg1, LoadedFrom arg2) {
				// TODO Auto-generated method stub
				//arg0=Bitmap.createBitmap(50,50,Config.RGB_565);
				arg1.setImageBitmap(arg0);
			}
		})
		.imageScaleType(ImageScaleType.EXACTLY)//设置图片缩放
		.build();
		return options;
		
	}
	public DisplayImageOptions getFirstOptions(boolean isCacheInMemory,boolean isCacheOnDisk,int round){
		
		 options = new DisplayImageOptions.Builder()
		.showImageForEmptyUri(R.drawable.loading)
		.showImageOnFail(R.drawable.first_going)
		.cacheInMemory(isCacheInMemory)
		.cacheOnDisk(isCacheOnDisk)
		.bitmapConfig(Config.RGB_565)
		.considerExifParams(true)
		.displayer(new BitmapDisplayer() {
			
			@Override
			public void display(Bitmap arg0, ImageAware arg1, LoadedFrom arg2) {
				// TODO Auto-generated method stub
				//arg0=Bitmap.createBitmap(50,50,Config.RGB_565);
				arg1.setImageBitmap(arg0);
			}
		})
		.imageScaleType(ImageScaleType.EXACTLY)//设置图片缩放
		.build();
		return options;
		
	}
}
