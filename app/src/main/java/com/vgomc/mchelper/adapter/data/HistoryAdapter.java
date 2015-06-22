package com.vgomc.mchelper.adapter.data;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.widget.TimeEditView;

import org.holoeverywhere.widget.TextView;

import java.io.File;

/**
 * Created by weizhouh on 6/18/2015.
 */
public class HistoryAdapter extends MyBaseAdapter {
    public HistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.adapter_data_history, parent, false);
        File history = (File) getItem(position);
        ((TextView) view.findViewById(R.id.tv_adapter_history_name)).setText(history.getName());
        ((TextView) view.findViewById(R.id.tv_adapter_history_size)).setText((history.getTotalSpace() / 1024) + "KB");
        return view;
    }
}
