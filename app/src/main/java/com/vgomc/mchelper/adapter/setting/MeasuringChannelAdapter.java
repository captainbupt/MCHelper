package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.entity.setting.Measuring;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.utility.TimeUtil;

import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by weizhouh on 5/30/2015.
 */
public class MeasuringChannelAdapter extends MyBaseAdapter {
    public MeasuringChannelAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.adapter_setting_measuring, parent, false);
        TextView subjectTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_measuring_subject);
        TextView modeTextVoew = (TextView) view.findViewById(R.id.tv_adapter_setting_measuring_mode);
        TextView beginTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_measuring_begin);
        TextView endTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_measuring_end);
        TextView intervalTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_measuring_interval);
        TextView variableTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_measuring_variable);
        LinearLayout contentLayout = (LinearLayout) view.findViewById(R.id.ll_adapter_setting_measuring_content);
        Measuring measuring = (Measuring) getItem(position);
        subjectTextView.append((position + 1) + "");
        if (measuring.isOn) {
            modeTextVoew.setText("On");
            contentLayout.setVisibility(View.VISIBLE);
        } else {
            modeTextVoew.setText("Off");
            contentLayout.setVisibility(View.GONE);
        }
        int[] beginTimeArray = TimeUtil.long2timeArray(measuring.beginTime);
        beginTextView.setText(beginTimeArray[0] + ":" + beginTimeArray[1]);
        int[] endTimeArray = TimeUtil.long2timeArray(measuring.endTime);
        endTextView.setText(endTimeArray[0] + ":" + endTimeArray[1]);
        intervalTextView.setText(measuring.interval + "");
        variableTextView.setText(measuring.getVariableNames(","));
        return view;
    }
}
