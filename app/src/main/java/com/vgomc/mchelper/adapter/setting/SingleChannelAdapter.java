package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.Channel;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.widget.DefaultFromulaView;

import org.holoeverywhere.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/22/2015.
 */
public class SingleChannelAdapter extends MyBaseAdapter {

    public SingleChannelAdapter(Context context) {
        super(context);
        setList(generateList());
    }

    private List<Object> generateList() {
        List<Object> channelList = new ArrayList<>();
        channelList.add(new Channel("P2", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("P3", 1, 1, new String[]{"∑ÁÀŸ"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN0", 1, 1, new String[]{"∑ÁœÚ"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN1", 1, 1, new String[]{"Ã´—Ù∑¯…‰"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN2", 1, 1, new String[]{"Õ¡»¿ÀÆ∑÷"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN3", 1, 1, new String[]{"Õ¡»¿Œ¬∂»"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN4", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN5", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN6", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN7", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN8", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN9", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN10", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        channelList.add(new Channel("AN11", 1, 1, new String[]{"Ωµ”Í"}, new float[][]{new float[]{0.0f, 0.0f, 0.0f}}));
        return channelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adapter_setting_channel_single, null);
        }
        TextView subjectTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_single_subject);
        TextView variableTextView = (TextView) convertView.findViewById(R.id.tv_adapter_setting_channel_single_variable);
        DefaultFromulaView formulaViewTextView = (DefaultFromulaView) convertView.findViewById(R.id.tv_adapter_setting_channel_single_formula);
        Channel channel = (Channel) getItem(position);
        subjectTextView.setText(channel.subject);
        variableTextView.setText(channel.variableNames[0]);
        formulaViewTextView.setText(channel.factors[0]);
        return convertView;
    }
}
