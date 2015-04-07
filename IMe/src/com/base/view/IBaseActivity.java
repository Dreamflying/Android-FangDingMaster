package com.base.view;


/**Activity 基类接口
 *@author Reek_Lee
 *@time 2014-4-11 下午6:04:36
 *@Email 1522651962@qq.com
 */
public interface IBaseActivity {
	
	/** 装载UI,获取id资源 */
	public void initView();

	/** 注册监听事件 */
	public void setListener();
	

	/** 装载configuration 配置类*/
	public void initConfiguration(BaseActivity viewActivity,String activityName);

	/** 绑定界面的数据或者是属性 */
	public void bingDataForView();
	
	
	

}
