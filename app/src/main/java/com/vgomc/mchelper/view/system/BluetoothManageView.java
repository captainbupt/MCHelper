package com.vgomc.mchelper.view.system;

import android.content.Context;
import android.view.View;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.system.MyBluetoothAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.entity.system.Bluetooth;
import com.vgomc.mchelper.transmit.bluetooth.BluetoothHelper;
import com.vgomc.mchelper.utility.MyBluetoothManager;
import com.vgomc.mchelper.widget.NoScrollListView;

/**
 * Created by weizhou1 on 2018/3/10.
 */

public class BluetoothManageView extends BaseCollapsibleView {
    public BluetoothManageView(Context context) {
        super(context);
        setTitle(R.string.system_operation_bluetooth);
        mContentView = new SystemOperationContentView(mContext);
        setContentView(mContentView);
    }

    class SystemOperationContentView extends BaseCollapsibleContentView {

        NoScrollListView mListView;
        MyBluetoothAdapter mAdapter;

        public SystemOperationContentView(Context context) {
            super(context);
            mListView = findViewById(R.id.nslv_system_bluetooth);
            mAdapter = new MyBluetoothAdapter(mContext, new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bluetooth bluetooth = (Bluetooth) view.getTag();
                    BluetoothHelper.connect(mContext, bluetooth.getAddress());
                }
            });
            mAdapter.setList(MyBluetoothManager.getBluetoothList(mContext));
            mListView.setAdapter(mAdapter);
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_system_bluetooth;
        }

        @Override
        protected void updateData() {
            mAdapter.setList(MyBluetoothManager.getBluetoothList(mContext));
        }
    }
}
