package com.project.iwant_or_ihave_controller;

import com.base.common.Common;
import com.base.controller.Controller;
import com.base.model.Configuration;

/**
 * @description 我要controller
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-10下午10:21:23
 */
public class IwantController extends Controller {
	private String GET_IWANT_DO = "GetPost.do";
	private String GET_IHAVE_DO = "GetPost.do";
	private String APPLY_USERSELECTION = "satisfyDemand.do";
	private String SEARCH_INFO = "PostSearch.do";
	private String getOwnPost = "getOwnPost.do";
	private String removePost = "removePost.do";
	private String getMatchContent = "GetMatchContent.do";
	private String getPostByDistance = "GetPostByDistance.do";
	private String getPostBySex = "GetPostBySex.do";
	private String ignorePost="ignorePost.do";
	private String getMatchNumber="GetMatchNumber.do";
	private String wishSearch="WishSearch.do";

	public IwantController(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @function 获取want
	 * @time 2014-10-10下午10:22:46 void
	 */
	public void getIwantInfo() {
		this.startRequest(GET_IWANT_DO, Common.POST);
	}

	/**
	 * @function 获取have
	 * @time 2014-10-10下午10:22:46 void
	 */
	public void getIhaveInfo() {
		this.startRequest(GET_IHAVE_DO, Common.POST);
	}

	/**
	 * @function 申请
	 * @time 2014-10-14下午2:11:57 void
	 */
	public void applyUserSelection() {
		this.startRequest(APPLY_USERSELECTION, Common.POST);
	}

	/**
	 * @function 搜索信息
	 * @time 2014-10-15上午2:03:26 void
	 */
	public void searchInfo() {
		this.startRequest(SEARCH_INFO, Common.POST);
	}

	/**
	 * 获取自己的我有我要
	 */
	public void getOwnPost() {

		this.startRequest(getOwnPost, Common.POST);
	}

	/**
	 * 删除自己的我有我要信息
	 */
	public void removePost() {
		this.startRequest(removePost, Common.POST);
	}

	public void getMatchContent() {
		this.startRequest(getMatchContent, Common.POST);
	}

	public void getMatchNumber(){
		
		this.startRequest(getMatchNumber, Common.POST);
	}
	
	public void getPostDistance() {
		this.startRequest(getPostByDistance, Common.POST);
	}

	public void getPostBySex() {
		this.startRequest(getPostBySex, Common.POST);
	}
	public void ignorePost(){
		this.startRequest(ignorePost, Common.POST);
	}
	public void wishSearch(){
		
		this.startRequest(wishSearch, Common.POST);
	}
}
