package com.project.add_view;

import java.util.ArrayList;

import com.project.iwant.R;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class SearchGridViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> list;

    public SearchGridViewAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(
                R.layout.new_item, null);
        TextView text = (TextView) view.findViewById(R.id.textview);
        text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        text.getPaint().setAntiAlias(true);
        text.setText(list.get(i));
        return view;
    }
}
