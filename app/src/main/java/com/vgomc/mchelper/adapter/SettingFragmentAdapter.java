package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.vgomc.mchelper.view.setting.SystemInfoView;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class SettingFragmentAdapter extends BaseAdapter {

    private Context mContext;

    public SettingFragmentAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new SystemInfoView(mContext);
    }
}
