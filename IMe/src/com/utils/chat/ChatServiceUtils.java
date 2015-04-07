package com.utils.chat;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.lidroid.xutils.exception.DbException;
import com.project.message_model.MessageBean;
import com.utils.db.SQLDatebaseUtils;
import com.utils.io.SharePreferenceUtils;

/**
 * @description 聊天服务
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-7下午1:24:15
 */
public class ChatServiceUtils {
	
	
	/**聊天相关hanler 返回值
	 * **/
	public final static int PUBLISH_DYNAMIC_STATIC_CHANGE=1;
	public final static int PUBLISH_MAIN_TEXT=2;
	public final static int APPLY_UPDATE_MSG=3;
	public final static int APPLY_NIT_MSG=4; //通知信息
	
	/**聊天相关hanler 返回值2.0
	 * **/
	public final static int FLASH_MAIN_MESSAGE=1;
	public final static int FLUSH_MESSAGE=2;
	
	public static int SOUND_TIMES=0;// 铃声播放次数
	
	public  static boolean isMessageActivityPage=false;// 是否在message 界面，然后进行刷新
	public  static boolean isMessageFragment=false;// 是否在message 界面，然后进行刷新
	public  static boolean isMainActivityPage=false;// 是否在主界面，然后进行刷新
	public  static boolean isMyFragmentPage=false;// 是否在主界面，然后进行刷新
	
	
	public  static boolean isNewMainActivityPage=false;// 是否在主界面，然后进行刷新
	public  static boolean isMessageFragmentPage=false;// 是否在主界面，然后进行刷新
	
	
	private static ChatServiceUtils chatService = new ChatServiceUtils();
	
	/**chat 内容分类标记*/
	public static String APPLY="#apply#";//满足-申请
	public static String HAN="#han#";// 批阅-申请
	public static String COMMENT="#comment#";// 评论-申请
	
	

	private static Context context;

	private ChatServiceUtils() {

	}

	public static ChatServiceUtils getIntance(Context iContext) {
		context = iContext;
		return chatService;
	}

	
}
