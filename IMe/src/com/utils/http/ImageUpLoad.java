package com.utils.http;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.http.Header;
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

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.request.CreateBucketRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;
import com.base.common.Common;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * @description 图片上传两种方式：1. 利用百度BCS SDK进行图片上传到百度个人云存储 2.二进制流上传 3. Base64流
 *              AsyncHttpClient上传
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-7上午10:47:55
 */
public class ImageUpLoad {
	static String host = "bcs.duapp.com";
	static String accessKey = "22r3LWoc89CteM7YcjPo89jn";
	static String secretKey = "6Wknnb85mVKD4eXPMVutIV4NOLljkRKT";
	static String bucket = "iwantuplad";
	// ----------------------------------------
	static String object_imagename = "/test";

	/**
	 * @function 实例化
	 * @time 2014-10-7上午11:45:08 void
	 */
	public static boolean initBcsSdk(File file,String objname) {
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		try {
			// ------------object-------------
			return putObjectByFile(baiduBCS, file,objname);
			// ------------common------------------
			// generateUrl(BaiduBCS baiduBCS);
		} catch (BCSServiceException e) {
			
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean putObjectByFile(BaiduBCS baiduBCS, File file,String objname) {
		PutObjectRequest request = new PutObjectRequest(bucket, objname, file);
		baiduBCS.putBucketPolicy(bucket, X_BS_ACL.PublicReadWrite);
		ObjectMetadata metadata = new ObjectMetadata();
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		Log.v("x-bs-request-id: ", response.getRequestId() + "");
		if (response!=null) {
			return true;
		}
		return false;
	}

	/**
	 * @function 利用SYnc HTTP 上传图片 base64
	 * @time 2014-10-7下午1:11:11
	 * @param cont
	 * @param photodata
	 * @param regData
	 *            void
	 */
	public static void uploadImage(final Context cont, Bitmap photodata,
			String regData) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// 将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
			photodata.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			baos.close();
			byte[] buffer = baos.toByteArray();
			System.out.println("图片的大小：" + buffer.length);
			// 将图片的字节流数据加密成base64字符输出
			String photo = Base64.encodeToString(buffer, 0, buffer.length,
					Base64.DEFAULT);

			// photo=URLEncoder.encode(photo,"UTF-8");
			RequestParams params = new RequestParams();
			params.put("photo", photo);
			params.put("name", "woshishishi");// 传输的字符数据
			String url = "http://10.0.2.2:8080/IC_Server/servlet/RegisterServlet1";

			AsyncHttpClient client = new AsyncHttpClient();
			client.post(url, params, new AsyncHttpResponseHandler() {
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {

				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @function 二进制流上传图片
	 * @time 2014-10-7下午1:20:47
	 * @param userName
	 * @param strimg
	 * @param mNow
	 * @param url
	 *            void
	 */
	public static void uploadImage(String userName, String strimg, int mNow,
			String url) {

		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpPost httpPost = new HttpPost(url);
		MultipartEntity postEntity = new MultipartEntity();
		String fileName = userName;
		ContentBody cbFileName;
		try {
			cbFileName = new StringBody(fileName);
			File file = new File(strimg);
			ContentBody cbFileData = new FileBody(file, "image/jpg");
			postEntity.addPart("fileName", cbFileName);
			postEntity.addPart("fileData", cbFileData);

			httpPost.setEntity(postEntity);
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity);
					if (result.equals(Common.RESPONSE_SUCCESS)) {
						// ========上传失败==========
					} else {
						// ========上传成功==========
					}
				} else {
					// ========上传失败==========
					Log.v("失败", "上传失败，请检查网络");
				}
			}
		} catch (Exception e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

}
