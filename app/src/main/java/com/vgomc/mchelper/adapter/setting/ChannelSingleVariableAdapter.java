package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.setting.Channel;
import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.widget.DefaultFormulaTextView;

import org.holoeverywhere.widget.TextView;

import java.util.ArrayList;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class ChannelSingleVariableAdapter extends MyBaseAdapter {

    public ChannelSingleVariableAdapter(Context context) {
        super(context);
    }

    public void updateList() {
        mList = new ArrayList<>();
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_P2));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_P3));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN0));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN1));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN2));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN3));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN4));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN5));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN6));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN7));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN8));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN9));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN10));
        mList.add(Configuration.getInstance().channelMap.get(Channel.SUBJECT_AN11));
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_setting_channel_single, null);
        }
        TextView subjectTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_single_subject);
        TextView variableTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_single_variable);
        DefaultFormulaTextView formulaViewTextView = (DefaultFormulaTextView) convertView.findViewById(R.id.tv_adapter_setting_channel_single_formula);
        Channel channel = (Channel) getItem(position);
        subjectTextView.setText(channel.subject);
        variableTextView.setText(channel.variables.get(0).name);
        formulaViewTextView.setText(channel.variables.get(0).factors);
        return convertView;
    }
}
