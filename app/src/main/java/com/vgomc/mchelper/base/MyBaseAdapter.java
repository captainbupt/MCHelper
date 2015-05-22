package com.vgomc.mchelper.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by weizhouh on 5/21/2015.
 */
public abstract class MyBaseAdapter extends BaseAdapter {

    protected List<Object> mList;
    protected Context mContext;
    protected LayoutInflater mInflater;

    public MyBaseAdapter(Context context){
        this.mContext = context;
        this.mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
    }

    public void setList(List<Object> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addItem(Object item) {
        if (mList == null)
            mList = new ArrayList<>();
        mList.add(item);
        notifyDataSetChanged();
    }

    public void addList(List<Object> list) {
        if (mList == null) {
            mList = list;
        }
        if (list != null) {
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
