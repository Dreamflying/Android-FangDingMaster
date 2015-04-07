package com.utils.widght;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author LY
 * @time 2014-9-22上午10:39:33
 * @description  自定义listView 解决 srollview 冲突
 */
public class SrollViewListView  extends ListView {

	
	 public SrollViewListView(Context context) {
         super(context);
 }

 public SrollViewListView(Context context, AttributeSet attrs) {
         super(context, attrs);
 }

 public SrollViewListView(Context context, AttributeSet attrs, int defStyle) {
         super(context, attrs, defStyle);
 }

 @Override
 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
         int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                         MeasureSpec.AT_MOST);

         super.onMeasure(widthMeasureSpec, expandSpec);
 }
 
}
