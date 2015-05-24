package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Activity.Setting.SHTChannelActivity;
import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.Entity.Configuration;
import com.vgomc.mchelper.Entity.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.widget.DefaultFormulaTextView;
import com.vgomc.mchelper.widget.NoScrollListView;

import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.datetimepicker.date.SimpleMonthAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class ChannelMultipleVariableAdapter extends MyBaseAdapter {

    public ChannelMultipleVariableAdapter(Context context) {
        super(context);
    }

    public void updateList() {
        mList = new ArrayList<>();
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_SHT));
        //mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_RS485));
        //mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_SDI));
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_setting_channel_multiple, null);
        }
        TextView subjectTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_multiple_subject);
        TextView sensorTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_multiple_sensor_count);
        TextView variableTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_multiple_variable_count);
        final NoScrollListView variableListView = (NoScrollListView) convertView.findViewById(R.id.nslv_adapter_setting_channel_multiple_variables);
        final Channel channel = (Channel) getItem(position);
        subjectTextView.setText(channel.subject);
        sensorTextView.setText("传感器" + channel.getSensorCount() + "个");
        variableTextView.setText("变量" + channel.getVariableCount() + "个");
        variableListView.setAdapter(new MultipleVariableAdapter(mContext, channel.variables));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (channel.getVariableCount() == 0) {
                    toEditActivity(channel.type);
                } else {
                    variableListView.setVisibility(variableListView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                }
            }
        });
        variableListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEditActivity(channel.type);
            }
        });
        return convertView;
    }

    private void toEditActivity(int type) {
        if (type == Channel.TYPE_SHT) {
            Intent intent = new Intent(mContext, SHTChannelActivity.class);
            mContext.startActivity(intent);
        }
    }

    class MultipleVariableAdapter extends MyBaseAdapter {

        public MultipleVariableAdapter(Context context, List<Variable> list) {
            super(context);
            mList = new ArrayList<>();
            for (Object o : list) {
                Variable variable = (Variable) o;
                if (variable.isVariableOn) {
                    mList.add(variable);
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.adapter_setting_channel_multiple_variable, null);
            }
            Variable variable = (Variable) getItem(position);
            TextView variableNameTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_multiple_variable_name);
            DefaultFormulaTextView defaultFormulaTextView = (DefaultFormulaTextView) convertView.findViewById(R.id.dftv_adapter_setting_channel_multiple_variable_formula);
            variableNameTextView.setText(variable.name);
            defaultFormulaTextView.setText(variable.factors);
            return convertView;
        }
    }
}
