package com.utils.os;

/**上传接口
 * @author liyuan
 * @Description 
 * @Email 1522651962@qq.com
 * @date 2014年7月18日上午10:25:58
 */
public interface UploadInterface {
	
	public void onSuccess(int arg0, String arg1);
	public void onFailure(Throwable error, String content);

}
