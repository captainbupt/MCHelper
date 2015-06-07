package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.view.data.CurrentDataView;
import com.vgomc.mchelper.view.data.HistoryDataView;
import com.vgomc.mchelper.view.status.ControlView;
import com.vgomc.mchelper.view.status.NetworkView;
import com.vgomc.mchelper.view.status.StatusView;
import com.vgomc.mchelper.view.status.StorageView;
import com.vgomc.mchelper.view.status.SystemView;

import java.util.ArrayList;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class DataFragmentAdapter extends BaseCollapseAdapter {

    public DataFragmentAdapter(Context context) {
        super(context);
        mList = new ArrayList<>();
        mList.add(new CurrentDataView(mContext));
        mList.add(new HistoryDataView(mContext));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (View) getItem(position);
    }
}
