package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.view.setting.BatteryView;
import com.vgomc.mchelper.view.setting.ChannelView;
import com.vgomc.mchelper.view.setting.SystemInfoView;

import org.holoeverywhere.widget.ListView;

import java.util.ArrayList;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class SettingFragmentAdapter extends MyBaseAdapter {

    private Context mContext;

    public SettingFragmentAdapter(Context context) {
        super(context);
        this.mContext = context;
        mList = new ArrayList<>();
        mList.add(new SystemInfoView(context));
        mList.add(new ChannelView(context));
        mList.add(new BatteryView(mContext));
    }

    public void updateData() {
        for (Object o : mList) {
            BaseCollapsibleView view = (BaseCollapsibleView) o;
            view.updateData();
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (View) getItem(position);
    }
}
