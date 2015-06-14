package com.vgomc.mchelper.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.adapter.StatusFragmentAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseListFragment;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;

import java.util.List;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class StatusFragment extends BaseListFragment {

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        return new StatusFragmentAdapter(mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });
    }

    private void updateData() {
        BlueToothSeriveProvider.doGetCurrentStatus(mContext, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                ((StatusFragmentAdapter) mFragmentAdapter).setData(bluetoothEntities);
            }
        });
    }
}
