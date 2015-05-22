package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;

import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/21/2015.
 */
public class BatteryChannelAdapter extends MyBaseAdapter {

    public static final String[] channelNames = new String[]{
      "3V1","SWV1","SWV2","SWV3","SWV4","SWV5"
    };

    private boolean isOrder;

    public BatteryChannelAdapter(Context context, boolean isOrder) {
        super(context);
        this.isOrder = isOrder;
    }

    public void setIsOrder(boolean isOrder){
        this.isOrder = isOrder;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }



    static class ViewHolder{
        TextView mNameTextView;
        Switch mModeSwitch;

        public ViewHolder(View view){
            mNameTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_battery_name);
            mModeSwitch = (Switch) view.findViewById(R.id.sw_setting_battery_pattern);
        }
    }
}
