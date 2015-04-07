package com.test;

import java.util.ArrayList;

import com.project.login_model.UserInfoBean;
import com.project.login_model.UserInfoListBean;
import com.utils.http.JsonHelper;

public class Test {

	/**
	 *@function  
	 * @time 2014-10-5下午8:41:12
	 * @param args
	 * void
	 */
	public static void main(String[] args) {
		UserInfoListBean userInfoListBean=new UserInfoListBean();
	    ArrayList<UserInfoBean> list=new ArrayList<UserInfoBean>();
	    UserInfoBean userInfoBean=new UserInfoBean();
	    userInfoBean.setUsername("ss");
	    userInfoBean.setPassword("ss");
	    userInfoListBean.setArray(list);
	    System.out.println(JsonHelper.objectToStringTree(userInfoListBean));

	}

}
