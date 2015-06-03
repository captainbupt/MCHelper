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
public class SettingFragmentAdapter extends BaseCollapseAdapter {

    private Context mContext;

    public SettingFragmentAdapter(Context context) {
        super(context);
        this.mContext = context;
        mList = new ArrayList<>();
        mList.add(new SystemInfoView(context));
        ChannelView channelView = new ChannelView(context);
        channelView.hideContent();
        mList.add(channelView);
        BatteryView batteryView = new BatteryView(context);
        batteryView.hideContent();
        mList.add(batteryView);
        MeasuringView measuringView = new MeasuringView(context);
        measuringView.hideContent();
        mList.add(measuringView);
        StorageView storageView = new StorageView(context);
        storageView.hideContent();
        mList.add(storageView);
        NetworkView networkView = new NetworkView(context);
        networkView.hideContent();
        mList.add(networkView);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (View) getItem(position);
    }
}
