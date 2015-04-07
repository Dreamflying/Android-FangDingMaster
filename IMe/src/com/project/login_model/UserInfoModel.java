package com.project.login_model;

import android.content.Context;

import com.lidroid.xutils.exception.DbException;
import com.utils.db.SQLDatebaseUtils;

/**
 *  @description 用户信息model 类，操作本地数据库
 *  @author Ly
 *  @email 1522651962@qq.com
 *  @time 2014-10-5下午5:01:44
 */
public class UserInfoModel {
	
	private Context context;
	public UserInfoModel(Context context){
		this.context=context;
	}
	
	/**
	 *@function  创建数据库
	 * @time 2014-10-5下午5:07:41
	 * @param userInfoBean
	 * void
	 */
	public void createUserInfoTable(UserInfoBean userInfoBean){
		try {
			SQLDatebaseUtils.getInstance(context).getDbUtils().save(userInfoBean);
		} catch (DbException e) {
			e.printStackTrace();
		}
		
	}
	
    

}
