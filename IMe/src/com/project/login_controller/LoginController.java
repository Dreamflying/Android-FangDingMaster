package com.project.login_controller;

import com.base.common.Common;
import com.base.controller.Controller;
import com.base.model.Configuration;

/**
 * @description LoginController 包含登录，注册，修改密码等页面的处理
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-9下午1:32:26
 */
public class LoginController extends Controller {
	private String LOGIN_USERINFOMETHOD = "login.do";// 获取用户信息请求
	private String REGISTER_USERINFOMETHOD = "regist.do";// 用户注册
	private String MODIFY_PASSWORD = "modifypsd.do";// 修改密码
	private String UpdateUserInfo = "UpdateUserInfo.do";
	private String GetUserInfo = "GetUserInfo.do";

	public LoginController(Configuration configuration) {
		super(configuration);
	}

	/**
	 * @function 获取用户信息
	 * @time 2014-10-5下午5:19:24 void
	 */
	public void getUserInfo() {
		this.startRequest(LOGIN_USERINFOMETHOD, Common.POST);
	}

	/**
	 * @function 注册用户信息
	 * @time 2014-10-8下午6:45:41 void
	 */
	public void registerUserInfo() {
		this.startRequest(REGISTER_USERINFOMETHOD, Common.POST);

	}

	/**
	 * @function 修改密码
	 * @time 2014-10-14下午2:10:04 void
	 */
	public void modifyPassWord() {
		this.startRequest(MODIFY_PASSWORD, Common.POST);
	}

	/**
	 * 修改用户信息（现在的接口只是修改签名和昵称）
	 */
	public void modifyUserInfo() {

		this.startRequest(UpdateUserInfo, Common.POST);
	}
	
	
	public void getUserInfo_New(){
		this.startRequest(GetUserInfo, Common.POST);
		
	}

}
