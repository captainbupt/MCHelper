package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.entity.setting.Channel;
import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.RS485Channel;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.activity.setting.RS485ChannelActivity;
import com.vgomc.mchelper.activity.setting.SDIChannelActivity;
import com.vgomc.mchelper.activity.setting.SHTChannelActivity;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.NoScrollListView;

import android.widget.TextView;

import java.util.ArrayList;

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
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_RS485));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_SDI));
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.adapter_setting_channel_multiple, null);
        TextView subjectTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_channel_multiple_subject);
        TextView sensorTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_channel_multiple_sensor_count);
        TextView variableTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_channel_multiple_variable_count);
        final NoScrollListView variableListView = (NoScrollListView) view.findViewById(R.id.nslv_adapter_setting_channel_multiple_variables);
        final Channel channel = (Channel) getItem(position);
        subjectTextView.setText(channel.subject);
        sensorTextView.setText("传感器" + channel.getSensorCount() + "个");
        variableTextView.setText("变量" + channel.getVariableCount() + "个");
        variableListView.setAdapter(new ChannelVariableAdapter(mContext, channel.getVariable()));
        view.setOnClickListener(new View.OnClickListener() {
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
        return view;
    }

    private void toEditActivity(int type) {
        if (type == Channel.TYPE_SHT) {
            mContext.startActivity(new Intent(mContext, SHTChannelActivity.class));
        } else if (type == Channel.TYPE_RS485) {
            mContext.startActivity(new Intent(mContext, RS485ChannelActivity.class));
        } else if (type == Channel.TYPE_SDI) {
            /*if (((RS485Channel) Configuration.getInstance().channelMap.get(Channel.SUBJECT_RS485)).mode == RS485Channel.TYPE_MODE_SLAVE) {
                ToastUtil.showToast(mContext, "RS485为从机模式，无法设置SDI通道");
                return;
            }*/
            mContext.startActivity(new Intent(mContext, SDIChannelActivity.class));
        }
    }
}
