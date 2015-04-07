package com.base.view;

import android.view.View;

/**
 * fragment 基类接口
 * 
 * @author Ly
 * @time 2014年5月6日下午12:59:14
 * @email liyuan6522151@gmail.com
 */
public interface IBaseFragment {
	/** 装载UI,获取id资源 */
	public void initView(View view);

	/** 注册监听事件 */
	public void setListener();

	/** 绑定界面的数据或者是属性 */
	public void bingDefaultView();

	/** 装载configuration 配置类 */
	public void initConfiguration(BaseFragment fragment);

}
