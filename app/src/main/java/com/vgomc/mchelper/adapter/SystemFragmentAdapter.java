package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.view.system.SystemCloudView;
import com.vgomc.mchelper.view.system.SystemOperationView;

import java.util.ArrayList;

/**
 * Created by weizhouh on 6/4/2015.
 */
public class SystemFragmentAdapter extends BaseCollapseAdapter {


    public SystemFragmentAdapter(Context context) {
        super(context);
        mList = new ArrayList<>();
        mList.add(new SystemOperationView(mContext));
        mList.add(new SystemCloudView(mContext));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (View) getItem(position);
    }
}
