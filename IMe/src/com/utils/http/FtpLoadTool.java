package com.utils.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.base.common.FtpClientBean;

import android.R.integer;
import android.os.Environment;
import android.util.Log;


/**备份文件工具
 * @author liyuan
 * @Description 
 * @Email 1522651962@qq.com
 * @date 2014年7月18日上午9:58:05
 */
public class FtpLoadTool {
	
	private static FtpLoadTool updateInfo=new FtpLoadTool();
	private static UploadInterface uploadInterface;
	 String hostName = FtpClientBean.ftp_url;
	 String userName = FtpClientBean.ftp_username;
	 String password = FtpClientBean.ftp_password;
	 String remoteDir = FtpClientBean.ftp_remoteDir;
	 String remotePath="";
	 int port=FtpClientBean.ftp_port;
	   /**
	     * 通过ftp上传文件
	     * @param url ftp服务器地址 如： 192.168.1.110
	     * @param port 端口如 ： 21
	     * @param username  登录名
	     * @param password   密码
	     * @param remotePath  上到ftp服务器的磁盘路径
	     * @param fileNamePath  要上传的文件路径
	     * @param fileName      要上传的文件名
	     * @return
	     */
	
	
	private FtpLoadTool(){
		
	}
	
	public static FtpLoadTool getInstance(UploadInterface uploadInterface){
		FtpLoadTool.uploadInterface=uploadInterface;
		if (updateInfo==null) {
			updateInfo=new FtpLoadTool();
		}
		return updateInfo;
	}
	
 
    /**
     *@function  
     * @time 2014-10-15下午6:03:09
     * @param fileNamePath
     * @return
     * int
     */
    public int ftpUpload(String fileNamePath,String fileName) { 
    FTPClient ftpClient = new FTPClient(); 
     FileInputStream fis = null; 
     int returnMessage = 0; 
     try { 
         ftpClient.connect(hostName, port); 
         boolean loginResult = ftpClient.login(userName, password); 
         
         int returnCode = ftpClient.getReplyCode(); 
         if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {// 如果登录成功  
             ftpClient.makeDirectory(remoteDir); 
             // 设置上传目录  
             ftpClient.changeWorkingDirectory(remotePath); 
             ftpClient.setBufferSize(1024); 
             ftpClient.setControlEncoding("UTF-8"); 
             ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
             ftpClient.enterLocalPassiveMode(); 
                     fis = new FileInputStream(fileNamePath+fileName); 
             ftpClient.storeFile(fileName, fis); 
              
             returnMessage = 1;   //上传成功     
             uploadInterface.onSuccess(1, "");
             Log.i("returnMessage", "上传成功        ");
         } else {// 如果登录失败  
             returnMessage = 0; 
             uploadInterface.onFailure(null, "");
             Log.i("returnMessage", "上传失败        ");
             } 
                  
     
     } catch (IOException e) { 
         e.printStackTrace(); 
         throw new RuntimeException("FTP客户端出错！", e); 
     } finally { 
         //IOUtils.closeQuietly(fis);  
     try { 
         ftpClient.disconnect(); 
     } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("关闭FTP连接发生异常！", e); 
        } 
     } 
     return returnMessage; 
    } 

    
    public int ftpDownLoad(){
    	 FTPClient ftpClient = new FTPClient();
    	    String hostName = FtpClientBean.ftp_url;
    	    String userName = FtpClientBean.ftp_username;
    	    String password = FtpClientBean.ftp_password;
    	    String remoteDir = FtpClientBean.ftp_remoteDir;
    	    final String path = Environment.getExternalStorageDirectory().getPath()
					+ "/PaoPaoSMSBackup/message.xml";
    	    try {
    	      ftpClient.connect(hostName, FtpClientBean.ftp_port);
    	      ftpClient.setControlEncoding("UTF-8");
    	      boolean loginResult= ftpClient.login(userName, password);
    	      ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
    	      int returnCode=ftpClient.getReplyCode();
    	      if (loginResult && FTPReply.isPositiveCompletion(returnCode)) {
    	      FTPFile[] files = ftpClient.listFiles(remoteDir);
    	      for (int i = 0; i < files.length; i++) {
    	        System.out.println(files[i].getName());
    	        Log.i("name", files[i].getName());
    	      }
    	      File file = new File(path);
    	      FileOutputStream fos = new FileOutputStream(file);
    	      ftpClient.retrieveFile(remoteDir + "/message.xml", fos);
    	      fos.close();
    	      }else {
				return 1;
			}
    	     
    	    } catch (SocketException e) {
    	      e.printStackTrace();
    	    } catch (IOException e) {
    	      e.printStackTrace();
    	    }
    		return 0;
    	
    
    }
}
