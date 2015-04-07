package com.base.controller;

import android.graphics.Bitmap;
import android.util.Log;

import com.base.common.Common;
import com.base.model.Configuration;
import com.base.model.ResponseResult;
import com.utils.http.JsonHelper;
import com.utils.http.JsonRequest;


/**父类Controller
 * @author Ly
 * @time 2014年5月6日下午12:52:47
 * @email liyuan6522151@gmail.com
 */
public class Controller implements IControlModelRequest {
	
	private Configuration configuration;
	private String API_URL = Common.URL_SERVER;
	public Controller(Configuration configuration) {
		this.configuration = configuration;
		this.configuration.setControl_modelRequest(this);
	}

	public Configuration getConfiguration() {
		return configuration;
	}
	/**
	 * 开始请求
	 * 
	 * @param url
	 */
	public void startRequest(String url) {
		configuration.setUrl(API_URL+url);
		new JsonRequest(configuration).requestPostMethod();
	}

	/**
	 * 开始请求
	 * 
	 * @param url
	 * @param requestmethod
	 */
	public void startRequest(String url, int requestmethod) {
		configuration.setUrl(API_URL+url);
		switch (requestmethod) {
		case Common.POST:
			new JsonRequest(configuration).requestPostMethod();
			break;
		case Common.GET:
			new JsonRequest(configuration).requestGetMethod();
			break;
		}

	}

	/**数据检查
	 * @param jsonString
	 */
	public void checkDownLoadData(String jsonString) {
		ResponseResult result = (ResponseResult) JsonHelper.jsonToObject(
				jsonString, ResponseResult.class);
		Log.v("code", result.getCode() + result.getMessage());
		if (result != null) {
			Log.v("code", result.getCode() + result.getMessage());
			if (result.getCode() != null) {

				if (result.getCode().equals(Common.REQUEST_NORMAL_CODE)) {
					Object object = null;
					if (configuration.getClassName() != null) {
						object = JsonHelper.jsonToObject(jsonString,
								configuration.getClassName());
					}

					if (object != null) {
						configuration.getView_ControlRequst().requestSuccess(
								object);
					} else {
						Log.v("object", "is null");
						configuration.getView_ControlRequst()
								.requestDataIsNull(result.getMessage());
					}

				}
				if (result.getCode().equals(Common.REQUEST_ERROR_CODE)) {
					configuration.getView_ControlRequst().requestErrorCode(
							result.getMessage());
				}
                if (result.getCode().equals(Common.RESPONSE_SUCCESS)) {
                	configuration.getView_ControlRequst().responseSuccess(result.getMessage());
				}
                
                if (result.getCode().equals(Common.RESPONSE_FAILURE)) {
                	configuration.getView_ControlRequst().responseFailure(result.getMessage());
				}
			}
		}else {
			configuration.getView_ControlRequst()
			.requestDataIsNull("null");
			Log.d("result-object", "null");
		}
	}

	@Override
	public void requestSuccess(String jsonString) {
		this.checkDownLoadData(jsonString);
	}

	@Override
	public void requestTimeout(String timeoutString) {
		configuration.getView_ControlRequst().requestTimeout(timeoutString);

	}

	@Override
	public void requestServerError(String serverError) {
		configuration.getView_ControlRequst().requestServerError(serverError);

	}

	@Override
	public void requestFileSuccess(String filemessage,int updateprogressBar) {
		configuration.getView_ControlRequst().requestFileSuccess(filemessage,updateprogressBar);
		
	}

	@Override
	public void requestFileError(String iserror) {
		configuration.getView_ControlRequst().requestFileError(iserror);
		
	}

	@Override
	public void requestImageSuccess(Bitmap imageBitmap) {
		configuration.getView_ControlRequst().requestImageSuccess(imageBitmap);
		
	}

	@Override
	public void requestImageFailure(String error) {
		configuration.getView_ControlRequst().requestImageFailure(error);
		
	}
}
