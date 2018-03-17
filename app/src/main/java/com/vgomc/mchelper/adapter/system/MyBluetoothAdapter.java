package com.vgomc.mchelper.adapter.system;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.entity.system.Bluetooth;
import com.vgomc.mchelper.entity.system.DataValue;
import com.vgomc.mchelper.utility.MyBluetoothManager;

/**
 * Created by weizhou1 on 2018/3/10.
 */
public class MyBluetoothAdapter extends MyBaseAdapter {

    private View.OnClickListener mOnConnectListener;

    public MyBluetoothAdapter(Context context, View.OnClickListener onConnectListener) {
        super(context);
        this.mOnConnectListener = onConnectListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.adapter_system_bluetooth, parent, false);
        final Bluetooth dataValue = (Bluetooth) getItem(position);
        view.findViewById(R.id.btn_adapter_bluetooth_connect).setTag(dataValue);
        ((TextView) view.findViewById(R.id.tv_adapter_bluetooth_name)).setText(dataValue.getName());
        ((TextView) view.findViewById(R.id.tv_adapter_bluetooth_address)).setText(dataValue.getAddress());
        view.findViewById(R.id.btn_adapter_bluetooth_connect).setOnClickListener(mOnConnectListener);
        view.findViewById(R.id.btn_adapter_bluetooth_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et = new EditText(mContext);
                et.setText(dataValue.getName());
                new AlertDialog.Builder(mContext).setTitle("请输入名称").setView(et).setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataValue.setName(et.getText().toString());
                        MyBluetoothManager.addBluetooth(mContext, dataValue.getAddress(), dataValue.getName());
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", null).create().show();
            }
        });
        view.findViewById(R.id.btn_adapter_bluetooth_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext).setTitle("确认删除？").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyBluetoothManager.deleteBluetooth(mContext, dataValue.getAddress());
                        removeItem(position);
                    }
                }).setNegativeButton("取消", null).create().show();
            }
        });
        return view;
    }
}
