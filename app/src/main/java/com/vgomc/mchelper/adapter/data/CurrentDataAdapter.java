package com.vgomc.mchelper.adapter.data;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.entity.data.VariableData;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;

import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class CurrentDataAdapter extends MyBaseAdapter {
    public CurrentDataAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.adapter_data_current, null);
        TextView mNameTextView = (TextView) convertView.findViewById(R.id.tv_adapter_data_current_variable_name);
        TextView mValueTextView = (TextView) convertView.findViewById(R.id.tv_adapter_data_current_variable_value);
        VariableData data = (VariableData) getItem(position);
        mNameTextView.setText(data.name);
        mValueTextView.setText(data.currentValue + "");
        return convertView;
    }
}
