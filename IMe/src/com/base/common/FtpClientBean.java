package com.base.common;


/**连接FTP 服务器配置类
 * @author liyuan
 * @Description 
 * @Email 1522651962@qq.com
 * @date 2014年8月6日下午4:31:27
 */
public class FtpClientBean {
	
	/**
	 * ftp服务器的url 路径
	 */
	public static String ftp_url="121.40.104.223";
	/**
	 * 用户名
	 */
	public static String ftp_username="iwant";
	/**
	 * 密码
	 */
	public static String ftp_password="iwant";
	/**
	 * 端口
	 */
	public static int ftp_port=21;
	/**
	 * 远程文件路径
	 */
	public static String ftp_remoteDir="";
    /**
     * 下载成功
     */
    public final static int FTP_UPLOAD_SUCCESS=1;
    /**
     * 下载失败
     */
    public final static int FTP_UPLOAD_FAILURE=0;

}
