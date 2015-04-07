package com.utils.io;

/**更新进度条
 * @author Ly
 * @time 2014年6月2日下午9:46:32
 * @email liyuan6522151@gmail.com
 */
public interface FileUtilsUpdateProgressBar {
	
	/**
	 * @param updatelength
	 */
	public void updateProgressBar(int updatelength);
	
	/**
	 * @param updatelength
	 */
	public void startProgressBar(int updatelength);
	
	/**
	 * @param updatelength
	 */
	public void endProgressBar(int updatelength);

}
