package com.utils.http;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.base.common.Common;
import com.base.controller.IControlModelRequest;
import com.base.model.Configuration;
/**json请求通信类
 * @author Ly
 * @time 2014年5月6日下午1:11:34
 * @email liyuan6522151@gmail.com
 */
public class JsonRequest {

	private IControlModelRequest control_modelRequest;
	private RequestQueue queue;
	private Configuration vo;
	public  static String PUTDATA_JSONKEY = "data";
	public static String TOKEN_KEY="token";
	private Object object;
	public JsonRequest(Configuration vo) {
		this.vo=vo;
		this.control_modelRequest=vo.getControl_modelRequest();
		this.queue=vo.getRequestQueue();
		this.object=vo.getViewDataObject();
	}

	@SuppressWarnings("unchecked")
	public void requestGetMethod(){
		
		queue.add(new JsonObjectRequest(Method.GET, vo.getUrl(), null,
				new Listener() {
					@Override
					public void onResponse(Object arg0) {
						Log.v("arg0=", arg0.toString());
							control_modelRequest.requestSuccess(arg0.toString());	
					} 
				}, new  ErrorListener(){

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (arg0 instanceof TimeoutError) {
							control_modelRequest.requestTimeout(arg0.toString());
						}
						if(arg0 instanceof ServerError){
							control_modelRequest.requestServerError(arg0.toString());
						}
					}
					
					
				}

				).setTag(Common.RequstTag).setRetryPolicy(new DefaultRetryPolicy(6000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
		queue.start();
		
		
		
	}
	
	
	public void  requestPostMethod(){
		Log.v("request", "request");
		Log.v("url", vo.getUrl());
		StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST,
				vo.getUrl(), new Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.v("response", response.toString());
						// response
						control_modelRequest.requestSuccess(response
								.toString());
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					/*	Log.e("LOGIN-ERROR", error.getMessage(), error);
						byte[] htmlBodyBytes = error.networkResponse.data;
						Log.e("LOGIN-ERROR", new String(htmlBodyBytes), error);*/

						if (error instanceof TimeoutError) {
							Log.v("time out", "TimeoutError");
							control_modelRequest.requestTimeout(error.toString());
						}
						if(error instanceof ServerError){
							Log.v("server", "ServerError");
							control_modelRequest.requestServerError(error.toString());
						}
					}
				}) {

			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				Log.v("json=", JsonHelper.objectToStringTree(object));
				params.put(PUTDATA_JSONKEY,JsonHelper.objectToStringTree(object));
				//params.put(TOKEN_KEY,Common.TOKEN_STRING);
				return params;
			}

		};
		queue.add(stringRequest);
		
	}
}
