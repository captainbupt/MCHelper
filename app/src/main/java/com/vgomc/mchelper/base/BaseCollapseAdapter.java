package com.vgomc.mchelper.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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
public abstract class BaseCollapseAdapter extends MyBaseAdapter {

    private Context mContext;

    public BaseCollapseAdapter(Context context) {
        super(context);
    }

    public void updateData() {
        for (Object o : mList) {
            BaseCollapsibleView view = (BaseCollapsibleView) o;
            view.updateData();
        }
        notifyDataSetChanged();
    }
}
