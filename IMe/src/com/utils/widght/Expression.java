package com.utils.widght;

import java.lang.reflect.Field;

import com.project.iwant.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.util.Log;

public class Expression {
	public static String getCSStr(String str){
		String teStr=str;
		for(int i=1;i<64;i++){
			String numStr="";
			if(i<=9){
				numStr="0"+i;
			}else{
				numStr=""+i;
			}
			teStr =teStr.replaceAll("#"+numStr,"<img src='e_"+numStr+"'/>");
		}
		return teStr;
	}
	public static CharSequence getCS(final Context context,String str){
		String str1=getCSStr(str);
		CharSequence charSequence = Html.fromHtml(str1, new ImageGetter() {
 			@Override
 			public Drawable getDrawable(String source) {
 				// TODO Auto-generated method stub
 				// 获得系统资源的信息，比如图片信息
 				Drawable drawable = context.getResources().getDrawable(
 						getResourceId(source));
 				drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2,
							drawable.getIntrinsicHeight() / 2);
				return drawable;
 			}
 		}, null);
		return charSequence;
	}
	public static int getResourceId(String name) {
		try {
			// 根据资源的ID的变量名获得Field的对象,使用反射机制来实现的
			Field field = R.drawable.class.getField(name);
			// 取得并返回资源的id的字段(静态变量)的值，使用反射机制
			return Integer.parseInt(field.get(null).toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
}
