package com.vgomc.mchelper.adapter.system;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.entity.system.DataValue;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;

import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 6/22/2015.
 */
public class CloudAdapter extends MyBaseAdapter {
    public CloudAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.adapter_system_cloud_data, parent, false);
        DataValue dataValue = (DataValue) getItem(position);
        ((TextView) view.findViewById(R.id.tv_adapter_cloud_name)).setText(dataValue.varName);
        ((TextView) view.findViewById(R.id.tv_adapter_cloud_value)).setText(dataValue.value + dataValue.varUnit);
        ((TextView) view.findViewById(R.id.tv_adapter_cloud_time)).setText(dataValue.varDate);
        return view;
    }
}
