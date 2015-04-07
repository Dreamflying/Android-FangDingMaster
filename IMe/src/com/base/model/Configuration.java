package com.base.model;

import com.android.volley.RequestQueue;
import com.base.controller.IControlModelRequest;
import com.base.controller.IViewControlRequst;
import com.base.view.BaseActivity;


/**view -controller-model 流动数据类
 * @author Ly
 * @time 2014年5月6日下午1:01:36
 * @email liyuan6522151@gmail.com
 */
public class Configuration {
	
	private IControlModelRequest control_modelRequest;
	private IViewControlRequst view_ControlRequst;
	private String url;
	private RequestQueue requestQueue;
	private Class  className;
	private BaseActivity viewActivity;
	private String fileName;
    private String filePath;	

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	private static Configuration configuration;
	private Object viewDataObject;

	public Configuration() {

	}

	public static synchronized Configuration getIntance() {

		configuration = new Configuration();
		return configuration;

	}

	

	public Object getViewDataObject() {
		return viewDataObject;
	}

	public void setViewDataObject(Object viewDataObject) {
		this.viewDataObject = viewDataObject;
	}

	public Class getClassName() {
		return className;
	}

	public void setClassName(Class className) {
		this.className = className;
	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}

	public IControlModelRequest getControl_modelRequest() {
		return control_modelRequest;
	}

	public void setControl_modelRequest(
			IControlModelRequest control_modelRequest) {
		this.control_modelRequest = control_modelRequest;
	}

	public IViewControlRequst getView_ControlRequst() {
		return view_ControlRequst;
	}

	public void setView_ControlRequst(IViewControlRequst view_ControlRequst) {
		this.view_ControlRequst = view_ControlRequst;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public BaseActivity getViewActivity() {
		return viewActivity;
	}

	public void setViewActivity(BaseActivity viewActivity) {
		this.viewActivity = viewActivity;
	}
}
