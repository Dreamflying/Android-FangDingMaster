package com.utils.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.base.common.Common;
import com.base.model.Configuration;
import com.utils.io.FileUtils;
import com.utils.io.FileUtilsUpdateProgressBar;


/**
 * 下载文件
 * 
 * @author Ly
 * @time 2014年5月31日下午12:25:05
 * @email liyuan6522151@gmail.com
 */
public class FileRequest {

	private Configuration mConfiguration;
	private int fileSize;
	private int updateSize;
	private boolean isProgressBar; 

	public FileRequest(Configuration configuration) {
		this.mConfiguration = configuration;
	}

	/**
	 * 开始下载文件
	 * 
	 * @param isProgressBar
	 */
	public void startDownLoadFile(boolean isProgressBar) {
		this.isProgressBar=isProgressBar;
		new Thread() {
			public void run() {
				Message msgMessage = new Message();
				switch (downLoadFile()) {
				case Common.DOWNLOADFILE_SUCCESS:
					msgMessage.what = Common.DOWNLOADFILE_SUCCESS;
					fileHandler.sendMessage(msgMessage);
					break;
				case Common.DOWNLOADFILE_ERROR:
					msgMessage.what = Common.DOWNLOADFILE_ERROR;
					fileHandler.sendMessage(msgMessage);
					break;
				default:
					break;
				}

			}

		}.start();

	}

	/**
	 * 下载文件
	 */
	public int downLoadFile() {
		try {
			InputStream is = null;
			FileUtils fileUtils = new FileUtils();
			// inputStream = 上个从网络上获得的输入流
			is = getInputStreamFromUrl(mConfiguration.getUrl());
			if (is != null) {
				File resultFile = fileUtils.writeSDCARDFromInputSteam(
						mConfiguration.getFilePath(),
						mConfiguration.getFileName(), is,
						new FileUtilsUpdateProgressBar() {

							@Override
							public void updateProgressBar(int updatelength) {
								updateSize = updatelength;
								if (isProgressBar) {
									Message msgMessage = new Message();
									msgMessage.what = Common.DOWNLOADFILE_SUCCESS;
									fileHandler.sendMessage(msgMessage);
								}
							}

							@Override
							public void startProgressBar(int updatelength) {
								// TODO Auto-generated method stub

							}

							@Override
							public void endProgressBar(int updatelength) {
								// TODO Auto-generated method stub

							}
						});
				if (resultFile == null) {
					// 下载出错
					return Common.DOWNLOADFILE_ERROR;
				}
			} else {
				return Common.DOWNLOADFILE_ERROR;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Common.DOWNLOADFILE_SUCCESS;

	}

	/**
	 * 获取网络流
	 * 
	 * @param newUrl
	 * @return
	 */
	public InputStream getInputStreamFromUrl(String newUrl) {
		URL url = null;
		HttpURLConnection httpURLConnection = null;
		InputStream is = null;
		try {
			url = new URL(newUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			fileSize = httpURLConnection.getContentLength();
			is = httpURLConnection.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return is;
	}

	Handler fileHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int percent = updateSize * 100 / fileSize;
			switch (msg.what) {
			case Common.DOWNLOADFILE_SUCCESS:
				Log.v("file", " down file is success");
				mConfiguration.getControl_modelRequest().requestFileSuccess(
						"file is success", percent);
				break;
			case Common.DOWNLOADFILE_ERROR:
				Log.v("file", " down file is error");
				mConfiguration.getControl_modelRequest().requestFileError(
						"file is error");
				break;
			default:
				break;
			}
		}

	};
}
