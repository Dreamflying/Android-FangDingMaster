package com.utils.os;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import com.utils.io.StorageEnvironmentUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

/**
 * 图片上传下载类
 * 
 * @version 1.0
 * @created 2014-7-10
 */

public class ImgAction {
	private static String TAG = "ImgAction";

	/*
	 * 上传头像到服务器？
	 */
	public static String setHead(Context content, Uri uri, String username) {
		Bitmap bitmap = null;
		try {
			bitmap = MediaStore.Images.Media.getBitmap(
					content.getContentResolver(), uri);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// =====头像不用太大，切割一下========
		bitmap = zoomImage(bitmap, 150, 150);
		// =====先将切割后的头像保存到本地，然后再上传文件到服务器=====
		byte[] imgByte = BitmaptoBytes(bitmap);
		String fileName = null;
		try {
			// ====将图片保存到本地=====
			fileName = bytesToFile(imgByte, username);// 临时文件名为username
		} catch (IOException e) {
			e.printStackTrace();
		}
		// =======开始上传==========
		getHead(username, fileName ,"url");
		// ===返回文件地址，方便页面显示====
		return fileName;
	}

	/*
	 * 上传头像
	 */
	private static void getHead(final String userName, final String strimg,final String urlString) {
		new Thread() {
			@Override
			public void run() {
				HttpClient httpClient = new DefaultHttpClient();
				httpClient.getParams().setParameter(
						CoreProtocolPNames.PROTOCOL_VERSION,
						HttpVersion.HTTP_1_1);
				HttpPost httpPost = new HttpPost(urlString);
				MultipartEntity postEntity = new MultipartEntity();
				String fileName = userName;
				ContentBody cbFileName;
				try {
					cbFileName = new StringBody(fileName);
					// 文件用FileBody，并指定文件类型
					File file = new File(strimg);
					ContentBody cbFileData = new FileBody(file, "image/jpg");

					// 把上面创建的这些Body全部加到Entity里面去。
					// 注意他们的key，这些key在Struts2服务器端Action的代码里必须保持一致！！
					postEntity.addPart("fileName", cbFileName);
					postEntity.addPart("fileData", cbFileData);

					httpPost.setEntity(postEntity);
					// 下面这句话就把数据提交到服务器去了
					HttpResponse response = httpClient.execute(httpPost);
					Log.v("返回值：", "" + response.getStatusLine().getStatusCode());
					if (response.getStatusLine().getStatusCode() == 200) {
						HttpEntity entity = response.getEntity();
						if (entity != null) {
							String result = EntityUtils.toString(entity);
							if (result.equals("1")) {
								// ========上传成功==========
								// 提示更新头像成功，然后将之前缓存的文件删除。。。。网络畅通的话基本不会失败
								Log.v(TAG, "头像上传成功");
							} else {
								// ========上传成功==========
								Log.v(TAG, "头像上传失败");
							}
						} else {
							// ========上传失败==========
							Log.v(TAG, "头像上传失败");
						}
					}
				} catch (Exception e) {
					// Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	/*
	 * 将图片切割成规定像素大小
	 */
	private static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// ======获取这个图片的宽和高======
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// ===创建操作图片用的matrix对象===
		Matrix matrix = new Matrix();
		// =======计算宽高缩放率=========
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// ========缩放图片动作=========
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	/*
	 * 将bitmap图片转换成byte[]二进制流
	 */
	public static byte[] BitmaptoBytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/*
	 * 将bytep[]二进制流图片文件保存在本地
	 */
	public static String bytesToFile(byte[] bs, String name) throws IOException {
	
		String fileName = StorageEnvironmentUtils.getSDCardPath() + "/iwantme/head/" + name
				+ ".jpg";
		File file=new File(StorageEnvironmentUtils.getSDCardPath() + "/iwantme/head/");
		if (!file.exists()) {
			file.mkdirs();
		 }
		
			FileOutputStream out = new FileOutputStream(new File(fileName));
			out.write(bs);
			out.flush();
			out.close();
			
		return fileName;
	}

	/*
	 * 获得圆形图片
	 */
	public static Bitmap GetRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Bitmap bitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap2);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, width, height);
		final RectF rectF = new RectF(rect);
		paint.setColor(color);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawRoundRect(rectF, 40.1f, 40.1f, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return bitmap2;
	}
	//=============当前正在更新的缓存============
	/*
	 * 判定本地图片是否存在
	 */
	public static boolean IsImg(String mImgName){
		 try{
			 String path = StorageEnvironmentUtils.getSDCardPath();
             File f=new File(path + "/iwantme/head/"+mImgName+".jpg");
             if(!f.exists()){
            	 Log.v("图片存在","图片存在");
                     return false;
                     
             }
             f.createNewFile();
             
     }catch (Exception e) {
             // TODO: handle exception
             return false;
     }
		 Log.v("图片不存在","图片不存在");
     return true;
	}
	/*
	 * 下载图片
	 */
	public void SaveImg(final String mImgName){
		new Thread() {
			public void run() {
		Log.v("正在下载图片","正在下载图片");
		Bitmap bitmap = null;  
        try  
        {  Log.v("图片不存在","图片不存在0");
            //初始化一个URL对象  
            URL url = new URL("url");  
            //获得HTTPConnection网络连接对象  
            Log.v("图片不存在","图片不存在1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
            connection.setConnectTimeout(5*1000);  
            connection.setDoInput(true);  
            connection.connect();  
            //得到输入流  
            Log.v("图片不存在","图片不存在2");
            InputStream is = connection.getInputStream();  
            Log.i("TAG", "*********inputstream**"+is);  
            bitmap = BitmapFactory.decodeStream(is);  
            Log.i("TAG", "*********bitmap****"+bitmap);  
            String path = Environment.getExternalStorageDirectory().getPath();
            try {
            	   FileOutputStream out=new FileOutputStream(path + "/me/head/"+mImgName+".jpg");
            	   bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            	   out.flush();
            	   out.close();
            	  } catch (Exception e) {
            	   // TODO Auto-generated catch block
            	   e.printStackTrace();
            	  }
            Log.v("TAG", "图片写入本地成功");  
            //关闭输入流  
            is.close();  
            //关闭连接  
            connection.disconnect();  
        } catch (Exception e)  
        {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }
			}
			}.start();
	}
}
