package com.utils.db;

import java.util.ArrayList;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.project.iwant_or_ihave_model.FriendInfoBean;
import com.project.message_model.CommentBean;
import com.project.message_model.FriendsBean;
import com.project.message_model.MessageBean;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.utils.io.StorageEnvironmentUtils;

/**
 * @description 数据库操作工具类 ：加入Xutils 工具中的dbUtil是类，实现ORM 机制。
 * @author Ly
 * @email 1522651962@qq.com
 * @time 2014-10-5下午3:17:32
 */
public class SQLDatebaseUtils {

	private static SQLDatebaseUtils sqlDatebaseUtils = new SQLDatebaseUtils();
	private static Context context;
	private final static String DB_NAME = "iwant";
	private final static String DB_DIR = StorageEnvironmentUtils.
			 getInternalPath() + "/iwantme/database";
	private static DbUtils dbUtils = null;

	private SQLDatebaseUtils() {
	}

	public static SQLDatebaseUtils getInstance(Context sqContext) {
		context = sqContext;
		return sqlDatebaseUtils;
	}

	/**
	 * @function 创建数据库
	 * @time 2014-10-5下午4:41:15 void
	 */
	public  void createDb() {
		Log.v("create", "db");
		if (dbUtils==null) {
			dbUtils = DbUtils.create(context);
		}
		dbUtils.configAllowTransaction(true);
		dbUtils.configDebug(true);
		try {
			// 创建 message 表
			dbUtils.createTableIfNotExist(MessageBean.class);
			dbUtils.createTableIfNotExist(CommentBean.class);
			dbUtils.createTableIfNotExist(FriendsBean.class);
			dbUtils.createTableIfNotExist(FriendInfoBean.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @function 获取dbutils
	 * @time 2014-10-5下午5:04:50
	 * @return DbUtils
	 */
	public  DbUtils getDbUtils() {
		if (dbUtils==null) {
			 createDb();
		}
		return dbUtils;
	}

	/**
	 * @function 获取未读信息数目
	 * @time 2014-10-13下午4:43:54
	 * @param username
	 * @return int
	 */
	public int getUnReadMsgNum(String username) {
		int mReadRow = 0;
		String sql = "SELECT toName FROM com_project_message_model_MessageBean where user='"
				+ username + "' and toUser!='' and readStatus='0'";
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				mReadRow++;
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return mReadRow;
	}
	
	public int getUnReadMsgNumForToUser(String username,String toUser){
		int mReadRow = 0;
		String sql = "SELECT toName FROM com_project_message_model_MessageBean where user='" + username
				+ "' and touser='" + toUser + "' and readStatus='0'";
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				mReadRow++;
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return mReadRow;
		
	}
	
	public int getUnReadCommentMsgNumForToUser(String username){
		int mReadRow = 0;
		String sql = "SELECT toName FROM com_project_message_model_CommentBean where user='" + username
				 + "' and readStatus='0'";
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				mReadRow++;
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return mReadRow;
		
	}
	
	

	/**进入聊天界面，根据聊天用户进行筛选内容
	 * @param username
	 * @param touser
	 * @return
	 */
	public ArrayList<MessageBean> getMsgList(String username,String touser){
	     ArrayList<MessageBean> messageBeans=new ArrayList<MessageBean>();
		String sql = "SELECT * FROM com_project_message_model_MessageBean where user='"
				+ username + "' and toUser='"+touser+"' ORDER BY time asc ";
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				MessageBean messageBean=new MessageBean();
				messageBean.setContent(cur.getString(cur.getColumnIndex("content")));
				messageBean.setId(cur.getInt(cur.getColumnIndex("id")));
				messageBean.setReadStatus(cur.getString(cur.getColumnIndex("readStatus")));
				messageBean.setStatus(cur.getString(cur.getColumnIndex("status")));
				messageBean.setToName(cur.getString(cur.getColumnIndex("toName")));
				messageBean.setToUser(cur.getString(cur.getColumnIndex("toUser")));
				messageBean.setTime(cur.getString(cur.getColumnIndex("time")));
				messageBean.setUser(cur.getString(cur.getColumnIndex("user")));
				messageBean.setHeadImage(cur.getString(cur.getColumnIndex("headImage")));
				messageBeans.add(messageBean);
				
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return messageBeans;
	}
	
	/**
	 * @param username
	 * @return
	 */
	public ArrayList<MessageBean> getDisticMessage(String username){
	     ArrayList<MessageBean> messageBeans=new ArrayList<MessageBean>();
		String sql = "SELECT *,count(distinct toUser)  FROM com_project_message_model_MessageBean where user='"
				+ username + "' GROUP BY toUser  ORDER BY time desc LIMIT 20" ;
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				MessageBean messageBean=new MessageBean();
				messageBean.setId(cur.getInt(cur.getColumnIndex("id")));
				messageBean.setContent(cur.getString(cur.getColumnIndex("content")));
				messageBean.setReadStatus(cur.getString(cur.getColumnIndex("readStatus")));
				messageBean.setStatus(cur.getString(cur.getColumnIndex("status")));
				messageBean.setToName(cur.getString(cur.getColumnIndex("toName")));
				messageBean.setToUser(cur.getString(cur.getColumnIndex("toUser")));
				messageBean.setTime(cur.getString(cur.getColumnIndex("time")));
				messageBean.setUser(cur.getString(cur.getColumnIndex("user")));
				messageBean.setHeadImage(cur.getString(cur.getColumnIndex("headImage")));
				messageBeans.add(messageBean);
				
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return messageBeans;
	}
	
	
	public void deleteMessage(String toUser){
		String sql = "delete from com_project_message_model_MessageBean where toUser='"+toUser+"'" ;
		MessageBean messageBean=new MessageBean();
		messageBean.setToUser(toUser);
		getDbUtils().getDatabase().execSQL(sql);
	}
	
	/**
	 * @param username
	 * @return
	 */
	public ArrayList<CommentBean> getDisticComment(String username){
	     ArrayList<CommentBean> messageBeans=new ArrayList<CommentBean>();
		/*String sql = "SELECT *,count(distinct toUser)  FROM com_project_message_model_CommentBean where user='"
				+ username + "' GROUP BY toUser  ORDER BY time desc LIMIT 20" ;*/
	     String sql = "SELECT * FROM com_project_message_model_CommentBean where user='"
					+ username + "' ORDER BY time desc " ;
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				CommentBean messageBean=new CommentBean();
				messageBean.setId(cur.getInt(cur.getColumnIndex("id")));
				messageBean.setContent(cur.getString(cur.getColumnIndex("content")));
				messageBean.setReadStatus(cur.getString(cur.getColumnIndex("readStatus")));
				messageBean.setStatus(cur.getString(cur.getColumnIndex("status")));
				messageBean.setToName(cur.getString(cur.getColumnIndex("toName")));
				messageBean.setToUser(cur.getString(cur.getColumnIndex("toUser")));
				messageBean.setTime(cur.getString(cur.getColumnIndex("time")));
				messageBean.setUser(cur.getString(cur.getColumnIndex("user")));
				messageBean.setDtid(cur.getString(cur.getColumnIndex("dtid")));
				messageBean.setHeadImage(cur.getString(cur.getColumnIndex("headImage")));
				messageBeans.add(messageBean);
				
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return messageBeans;
	}
	
	
	public ArrayList<FriendsBean> getFriendsBeans(String username){
		 ArrayList<FriendsBean> FriendsBean=new ArrayList<FriendsBean>();
			String sql = "SELECT *,count(distinct friendUserName)  FROM com_project_message_model_FriendsBean where username='"
					+ username +"' GROUP BY friendUserName" ;
			try {
				Cursor cur = getDbUtils().execQuery(sql);
				while (cur.moveToNext()) {
					FriendsBean messageBean=new FriendsBean();
					messageBean.setId(cur.getInt(cur.getColumnIndex("id")));
					messageBean.setFriendHeadImage(cur.getString(cur.getColumnIndex("friendHeadImage")));
					messageBean.setFriendNickName(cur.getString(cur.getColumnIndex("friendNickName")));
					messageBean.setFriendUserName(cur.getString(cur.getColumnIndex("friendUserName")));
					messageBean.setUsername(cur.getString(cur.getColumnIndex("username")));
					FriendsBean.add(messageBean);
				}
				cur.close();
			} catch (DbException e) {
				e.printStackTrace();
			}
			return FriendsBean;
		
	}
	
	public void changeMessageReadStatus(String toUser){
		String sql = "update  com_project_message_model_MessageBean set  readStatus=1 where toUser='"+toUser+"'" ;
		getDbUtils().getDatabase().execSQL(sql);
	}
	public void changeCommentMessageReadStatus(String username){
		String sql = "update  com_project_message_model_CommentBean set  readStatus=1 where user='"+username+"'" ;
		getDbUtils().getDatabase().execSQL(sql);
	}
	
	
	/**根据username 获取头像的路径
	 * @param username
	 * @return
	 */
	public ArrayList<FriendInfoBean> getAllFriends(){
		ArrayList<FriendInfoBean> friendInfoBeans=new ArrayList<FriendInfoBean>();
		String sql = "SELECT  *  FROM com_project_iwant_or_ihave_model_FriendInfoBean  GROUP BY username" ;
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				FriendInfoBean friendInfoBean=new FriendInfoBean();
				friendInfoBean.setHeadimage(cur.getString(cur.getColumnIndex("headimage")));
				friendInfoBean.setNickname(cur.getString(cur.getColumnIndex("nickname")));
				friendInfoBean.setIdo(cur.getString(cur.getColumnIndex("ido")));
				friendInfoBean.setUsername(cur.getString(cur.getColumnIndex("username")));
				friendInfoBeans.add(friendInfoBean);
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return friendInfoBeans;
	}
	
	/**根据username 获取头像的路径
	 * @param username
	 * @return
	 */
	public String getHeadImageByUsername(String username){
		
		String sql = "SELECT  headimage  FROM com_project_iwant_or_ihave_model_FriendInfoBean where username='"
				+ username +"'" ;
		String headImage=null;
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				headImage=cur.getString(cur.getColumnIndex("headimage"));
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return headImage;
	}
	
	/**根据username 获取昵称
	 * @param username
	 * @return
	 */
	public String getNickNameByUsername(String username){
		
		String sql = "SELECT  nickname  FROM com_project_iwant_or_ihave_model_FriendInfoBean where username='"
				+ username +"'" ;
		String nickName=null;
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				nickName=cur.getString(cur.getColumnIndex("nickname"));
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return nickName;
	}
	/**根据username 获取签名
	 * @param username
	 * @return
	 */
	public String getIdoByUsername(String username){
		
		String sql = "SELECT  ido  FROM com_project_iwant_or_ihave_model_FriendInfoBean where username='"
				+ username +"'" ;
		String ido=null;
		try {
			Cursor cur = getDbUtils().execQuery(sql);
			while (cur.moveToNext()) {
				ido=cur.getString(cur.getColumnIndex("ido"));
			}
			cur.close();
		} catch (DbException e) {
			e.printStackTrace();
		}
		return ido;
	}
	/**
	 * 用于用户注销用户删除数据库表数据
	 */
	public void deleteAllTable(){
		if (dbUtils==null) {
			getDbUtils();
		}
		try {
			dbUtils.dropTable(MessageBean.class);
			dbUtils.dropTable(CommentBean.class);
			dbUtils.dropTable(FriendsBean.class);
			dbUtils.dropTable(FriendInfoBean.class);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	/**
	 * 用于用户注销用户删除数据库表数据
	 */
	public void deleteDB(){
		if (dbUtils==null) {
			getDbUtils();
		}
		try {
			dbUtils.dropDb();
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
