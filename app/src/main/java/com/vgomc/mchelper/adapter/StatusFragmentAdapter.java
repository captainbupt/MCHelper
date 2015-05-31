package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.view.setting.BatteryView;
import com.vgomc.mchelper.view.setting.ChannelView;
import com.vgomc.mchelper.view.setting.MeasuringView;
import com.vgomc.mchelper.view.setting.NetworkView;
import com.vgomc.mchelper.view.setting.StorageView;
import com.vgomc.mchelper.view.setting.SystemInfoView;

import java.util.ArrayList;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class StatusFragmentAdapter extends BaseCollapseAdapter {

    private Context mContext;

    public StatusFragmentAdapter(Context context) {
        super(context);
        this.mContext = context;
        mList = new ArrayList<>();
        ChannelView channelView = new ChannelView(context);
        channelView.hideContent();
        mList.add(channelView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (View) getItem(position);
    }
}
