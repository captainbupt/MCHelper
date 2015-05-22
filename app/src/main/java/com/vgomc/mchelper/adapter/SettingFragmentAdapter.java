package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.view.setting.ChannelSingleView;
import com.vgomc.mchelper.view.setting.SystemInfoView;

import java.util.ArrayList;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class SettingFragmentAdapter extends MyBaseAdapter {

    private Context mContext;

    public SettingFragmentAdapter(Context context) {
        super(context);
        mList = new ArrayList<>();
        mList.add(new SystemInfoView(mContext));
        mList.add(new ChannelSingleView(mContext));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new SystemInfoView(mContext);
    }
}
