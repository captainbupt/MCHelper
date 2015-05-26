package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.vgomc.mchelper.Entity.Battery;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;

import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.TextView;

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
        RadioGroup modeRadioGroup = (RadioGroup) view.findViewById(R.id.rg_adapter_setting_battery_mode);
        RadioButton autoModeRadioButton = (RadioButton) view.findViewById(R.id.rb_adapter_setting_battery_mode_order);
        RadioButton alwaysModeRadioButton = (RadioButton) view.findViewById(R.id.rb_adapter_setting_battery_mode_always);
        EditText beginTimeEditText = (EditText) view.findViewById(R.id.et_adapter_setting_battery_time_begin);
        TextView endTimeTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_battery_time_end);
        nameTextView.setText(battery.subject);
        if (battery.isAlwaysOn) {
            alwaysModeRadioButton.setChecked(true);
        } else {
            autoModeRadioButton.setChecked(true);
        }
        beginTimeEditText.setText(battery.startTime + "");
        endTimeTextView.setText(battery.getEndTime() + "");
        return view;
    }
}
