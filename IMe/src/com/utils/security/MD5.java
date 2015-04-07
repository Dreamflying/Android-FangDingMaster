package com.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 * 
 * @author Reek_Lee
 * @time 2014-4-22 上午10:45:40
 * @email：1522651962@qq.com
 */
public class MD5 {

	/**
	 * 全局数组
	 */
	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 空构造函数
	 */
	public MD5() {
	}

	/**
	 * 返回形式为数字跟字符串 2014年8月6日下午5:09:36
	 * 
	 * @param bByte
	 * @return
	 */
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	/**
	 * 返回形式只为数字 2014年8月6日下午5:09:45
	 * 
	 * @param bByte
	 * @return
	 */
	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		System.out.println("iRet1=" + iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	/**
	 * 转换字节数组为16进制字串 2014年8月6日下午5:09:51
	 * 
	 * @param bByte
	 * @return
	 */
	private static String byteToString(byte[] bByte) {
		StringBuffer sBuffer = new StringBuffer();
		for (int i = 0; i < bByte.length; i++) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}

	/**
	 * 获取得到MD5 密文 2014年8月6日下午5:09:59
	 * 
	 * @param strObj
	 * @return
	 */
	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}

}
