package com.utils.widght;

/**
 * @author Ly
 * @time 2014年5月6日下午3:47:51
 * @email liyuan6522151@gmail.com
 */
public class CheckEditText {

	/**
	 * 检测编辑框是否为空 2014年8月6日下午5:10:35
	 * 
	 * @param editdata
	 * @return
	 */
	public static boolean checkEditTextData(String editdata) {
		if (editdata == null || editdata.equals("")) {
			return false;
		}
		return true;
	}

}
