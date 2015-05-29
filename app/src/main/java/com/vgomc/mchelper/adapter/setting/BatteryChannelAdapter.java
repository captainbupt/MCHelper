package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.Battery;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.utility.TimeUtil;

import org.holoeverywhere.widget.FrameLayout;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;

import java.util.Calendar;

/**
 * Created by weizhouh on 5/21/2015.
 */
public class BatteryChannelAdapter extends MyBaseAdapter {

    private boolean isOrder;

    public BatteryChannelAdapter(Context context, boolean isOrder) {
        super(context);
        this.isOrder = isOrder;
    }

    public void setIsOrder(boolean isOrder) {
        this.isOrder = isOrder;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Battery battery = (Battery) getItem(position);
        View view = mInflater.inflate(R.layout.adapter_setting_battery);
        TextView nameTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_battery_subject);
        TextView modeTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_battery_mode);
        TextView patternTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_battery_pattern);
        TextView beginTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_battery_time_begin);
        TextView lastTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_battery_time_last);
        LinearLayout detailLayout = (LinearLayout) view.findViewById(R.id.ll_adapter_setting_battery_detail);
        nameTextView.setText(battery.subject);
        if (battery.isAlwaysOn) {
            modeTextView.setText(R.string.setting_battery_channel_mode_always);
            detailLayout.setVisibility(View.GONE);
        } else {
            modeTextView.setText(R.string.setting_battery_channel_mode_auto);
            detailLayout.setVisibility(View.VISIBLE);
            patternTextView.setText(battery.isOrder ? R.string.setting_battery_pattern_order : R.string.setting_battery_pattern_custom);
            Calendar startCalendar = TimeUtil.long2calendar(battery.startTime);
            beginTextView.setText(String.format("%2d分%2d秒%3d毫秒", startCalendar.get(Calendar.MINUTE), startCalendar.get(Calendar.SECOND), startCalendar.get(Calendar.MILLISECOND)));
            Calendar liveCalendar = TimeUtil.long2calendar(battery.liveTime);
            lastTextView.setText(String.format("%2d分%2d秒%3d毫秒", startCalendar.get(Calendar.MINUTE), startCalendar.get(Calendar.SECOND), startCalendar.get(Calendar.MILLISECOND)));
        }
        return view;
    }
}
