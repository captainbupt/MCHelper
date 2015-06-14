package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.DeviceParameterEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.DeviceStatusEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.DeviceTimeEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.GPRSStatusEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.MemoryStatusEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.SDCardStatusEntity;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.view.status.ControlView;
import com.vgomc.mchelper.view.status.NetworkView;
import com.vgomc.mchelper.view.status.StatusView;
import com.vgomc.mchelper.view.status.StorageView;
import com.vgomc.mchelper.view.status.SystemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 5/19/2015.
 */
public class StatusFragmentAdapter extends BaseCollapseAdapter {

    public StatusFragmentAdapter(Context context, View.OnClickListener synchronizeListener) {
        super(context);
        mList = new ArrayList<>();
        mList.add(new SystemView(context, synchronizeListener));
        mList.add(new StatusView(context));
        mList.add(new StorageView(context));
        mList.add(new NetworkView(context));
        mList.add(new ControlView(context));
    }

    public void setData(List<BaseBluetoothEntity> entityList) {
        DeviceParameterEntity deviceParameterEntity = (DeviceParameterEntity) entityList.get(0);
        DeviceStatusEntity deviceStatusEntity = (DeviceStatusEntity) entityList.get(1);
        DeviceTimeEntity deviceTimeEntity = (DeviceTimeEntity) entityList.get(2);
        MemoryStatusEntity memoryStatusEntity = (MemoryStatusEntity) entityList.get(3);
        SDCardStatusEntity sdCardStatusEntity = (SDCardStatusEntity) entityList.get(4);
        GPRSStatusEntity gprsStatusEntity = (GPRSStatusEntity) entityList.get(5);
        ((SystemView) mList.get(0)).setData(deviceParameterEntity.model + "/" + deviceParameterEntity.version, deviceParameterEntity.uid, deviceTimeEntity.time);
        ((StatusView) mList.get(1)).setData(deviceStatusEntity.getBattery(), deviceStatusEntity.getFlagRepresentation(), deviceStatusEntity.getGPS());
        ((StorageView) mList.get(2)).setData(memoryStatusEntity.used, memoryStatusEntity.total, sdCardStatusEntity.total - sdCardStatusEntity.free, sdCardStatusEntity.total);
        ((NetworkView) mList.get(3)).setData(GPRSStatusEntity.STATUS_REPRESENTATION[gprsStatusEntity.status], gprsStatusEntity.netName, gprsStatusEntity.strength, gprsStatusEntity.errorRate, gprsStatusEntity.getFlagRepresentation(), gprsStatusEntity.onTime, gprsStatusEntity.waitTime, gprsStatusEntity.retryTimes);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return (View) getItem(position);
    }
}
