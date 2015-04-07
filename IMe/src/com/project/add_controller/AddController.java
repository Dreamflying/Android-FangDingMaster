package com.project.add_controller;

import com.base.common.Common;
import com.base.controller.Controller;
import com.base.model.Configuration;

/**
 * @description 发布controller，
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-9下午2:31:54
 */
public class AddController extends Controller {

	private String ADD_Demand_Method = "addDemand.do";
	private String addPost = "addPost.do";
	private String findKeyword = "findKeyword.do";

	public AddController(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @function 增加发布信息
	 * @time 2014-10-9下午9:04:32 void
	 */
	public void addPublishInfo() {
		this.startRequest(ADD_Demand_Method, Common.POST);
	}

	public void addPost() {

		this.startRequest(addPost, Common.POST);
	}

	public void findKeyword() {
		this.startRequest(findKeyword, Common.POST);
	}
}
