package com.vgomc.mchelper.adapter.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.setting.Storage;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.utility.TimeUtil;

import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/30/2015.
 */
public class StorageChannelAdapter extends MyBaseAdapter {
    public StorageChannelAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.adapter_setting_storage);
        TextView subjectTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_storage_subject);
        TextView modeTextVoew = (TextView) view.findViewById(R.id.tv_adapter_setting_storage_mode);
        TextView beginTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_storage_begin);
        TextView endTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_storage_end);
        TextView intervalTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_storage_interval);
        TextView sendTextView = (TextView) view.findViewById(R.id.tv_adapter_setting_storage_send);
        LinearLayout contentLayout = (LinearLayout) view.findViewById(R.id.ll_adapter_setting_storage_content);
        Storage storage = (Storage) getItem(position);
        subjectTextView.append((position + 1) + "");
        if (storage.isOn) {
            modeTextVoew.setText("On");
            contentLayout.setVisibility(View.VISIBLE);
        } else {
            modeTextVoew.setText("Off");
            contentLayout.setVisibility(View.GONE);
        }
        int[] beginTimeArray = TimeUtil.long2timeArray(storage.beginTime);
        beginTextView.setText(beginTimeArray[0] + ":" + beginTimeArray[1]);
        int[] endTimeArray = TimeUtil.long2timeArray(storage.endTime);
        endTextView.setText(endTimeArray[0] + ":" + endTimeArray[1]);
        intervalTextView.setText(storage.interval + "");
        if (storage.isSend) {
            sendTextView.setText("On");
        } else {
            sendTextView.setText("Off");
        }
        return view;
    }
}
