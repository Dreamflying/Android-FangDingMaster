package com.utils.widght;

import android.content.Context;
import android.widget.GridView;

public class TagsGridView extends GridView {


public TagsGridView(Context context) {
super(context);
// TODO Auto-generated constructor stub
}

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
int expandSpec=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
super.onMeasure(widthMeasureSpec, expandSpec);
}


}
