package com.vgomc.mchelper.fragment;

import android.os.Bundle;

import org.holoeverywhere.LayoutInflater;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.StatusFragmentAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseFragment;
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
        BlueToothSeriveProvider.doGetCurrentStatus(mContext, handler);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            List<BaseBluetoothEntity> entities = (List<BaseBluetoothEntity>) msg.obj;
            ((StatusFragmentAdapter) mFragmentAdapter).setData(entities);
        }
    };
}
