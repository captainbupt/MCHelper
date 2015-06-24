package com.vgomc.mchelper.fragment;

import android.content.DialogInterface;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.BatteryChannelEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.ChannelEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.DeviceParameterEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.GPRSParamEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.MeasurePlanEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.StorageTableEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.VariableEntity;
import com.vgomc.mchelper.entity.setting.Battery;
import com.vgomc.mchelper.entity.setting.Channel;
import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.RS485Channel;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.SettingFragmentAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseListFragment;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;

import java.io.File;
import java.util.List;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class SettingFragment extends BaseListFragment {

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        return new SettingFragmentAdapter(mContext);
    }

    public void updateData() {
        mFragmentAdapter.updateData();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }

    public void readSettingFromDevice() {
        new AlertDialog.Builder(mContext).setTitle(R.string.menu_action_bar_read_from_device_confirm)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BlueToothSeriveProvider.doReadConfiguration(mContext, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                            @Override
                            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                DeviceParameterEntity deviceParameterEntity = (DeviceParameterEntity) bluetoothEntities.get(0);
                                ChannelEntity channelEntity = (ChannelEntity) bluetoothEntities.get(1);
                                VariableEntity variableEntity = (VariableEntity) bluetoothEntities.get(2);
                                BatteryChannelEntity batteryChannelEntity = (BatteryChannelEntity) bluetoothEntities.get(3);
                                MeasurePlanEntity measurePlanEntity = (MeasurePlanEntity) bluetoothEntities.get(4);
                                StorageTableEntity storageTableEntity = (StorageTableEntity) bluetoothEntities.get(5);
                                GPRSParamEntity gprsParamEntity = (GPRSParamEntity) bluetoothEntities.get(6);
                                Configuration configuration = Configuration.getInstance();
                                configuration.name = deviceParameterEntity.name;
                                configuration.password = deviceParameterEntity.key;
                                configuration.timeZone = deviceParameterEntity.zone;
                                configuration.bluetoothTimeOn = deviceParameterEntity.bluetoothTime > 0;
                                configuration.bluetoothTime = deviceParameterEntity.bluetoothTime;
                                RS485Channel rs485Channel = (RS485Channel) configuration.channelMap.get(Channel.SUBJECT_RS485);
                                rs485Channel.slaveAddress = deviceParameterEntity.index;
                                rs485Channel.baudRate = deviceParameterEntity.rating;
                                rs485Channel.setProtocol(deviceParameterEntity.protocol);
                                configuration.channelMap.put(Channel.SUBJECT_RS485, rs485Channel);
                                for (int ii = 0; ii < channelEntity.channelArray.length; ii++) {
                                    Channel channelResult = channelEntity.channelArray[ii];
                                    Channel channel = configuration.channelMap.get(channelResult.subject);
                                    if (channel == null) {
                                        channel = channelResult;
                                    } else {
                                        channel.batteryName = channelResult.batteryName;
                                        channel.signalType = channelResult.signalType;
                                    }
                                    configuration.channelMap.put(channel.subject, channel);
                                }
                                for (int ii = 0; ii < batteryChannelEntity.batteryArray.length; ii++) {
                                    Battery batteryResult = batteryChannelEntity.batteryArray[ii];
                                    Battery battery = null;
                                    int position = 0;
                                    for (int jj = 0; jj < configuration.batteryList.size(); jj++) {
                                        Battery tmpBattery = (Battery) configuration.batteryList.get(jj);
                                        if (tmpBattery.subject.equals(batteryResult.subject)) {
                                            battery = tmpBattery;
                                            position = jj;
                                        }
                                    }
                                    if (battery != null) {
                                        battery.startTime = batteryResult.startTime;
                                        battery.liveTime = batteryResult.liveTime;
                                        battery.mode = batteryResult.mode;
                                        battery.isOrder = false;
                                        configuration.batteryList.set(position, battery);
                                    }
                                }
                                configuration.variableManager.clear();
                                for (int ii = 0; ii < variableEntity.variableArray.length; ii++) {
                                    configuration.variableManager.setVariable(variableEntity.variableArray[ii]);
                                }
                                for (int ii = 0; ii < measurePlanEntity.measuringArray.length && ii < configuration.measuringList.size(); ii++) {
                                    measurePlanEntity.measuringArray[ii].setVariableData(measurePlanEntity.ids[ii]);
                                    configuration.measuringList.set(ii, measurePlanEntity.measuringArray[ii]);
                                }
                                for (int ii = 0; ii < storageTableEntity.storageArray.length && ii < configuration.measuringList.size(); ii++) {
                                    configuration.storageList.set(ii, storageTableEntity.storageArray[ii]);
                                }
                                configuration.network = gprsParamEntity.network;
                                Configuration.setInstance(configuration);
                                updateData();
                            }
                        });
                    }
                }).setNegativeButton(R.string.dialog_cancel, null).show();
    }

    public void writeSettingToDevice() {
        new AlertDialog.Builder(mContext).setTitle(R.string.menu_action_bar_write_to_device_confirm).setMessage("写入配置将改变设备运行方式，确定要修改？")
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BlueToothSeriveProvider.doWriteConfiguration(mContext, Configuration.getInstance(), new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                            @Override
                            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                showToast(R.string.menu_action_bar_write_to_file_success);
                            }
                        });
                    }
                }).setNegativeButton(R.string.dialog_cancel, null).show();
    }

    public void readSettingFromFile() {
        final String[] configurationFiles = FileServiceProvider.getConfigurationFileNames(mContext);
        if (configurationFiles == null || configurationFiles.length == 0) {
            showToast(R.string.menu_action_bar_read_from_file_empty);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setTitle(R.string.menu_action_bar_read_from_file_choice)
                .setItems(configurationFiles, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Configuration configuration = FileServiceProvider.readObjectFromFile(FileServiceProvider.getExternalConfigurationPath(mContext) + File.separator + configurationFiles[which]);
                        if (configuration == null) {
                            showToast(R.string.menu_action_bar_read_from_file_fail);
                        } else {
                            showToast(R.string.menu_action_bar_read_from_file_success);
                            Configuration.setInstance(configuration);
                            updateData();
                        }
                    }
                }).setNegativeButton(R.string.dialog_cancel, null).show();
    }

    public void writeSettingToFile() {
        final EditText et = new EditText(mContext);

        new AlertDialog.Builder(mContext).setTitle(R.string.menu_action_bar_write_to_file_input)
                .setView(et)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String input = et.getText().toString();
                        if (input.equals("")) {
                            showToast(R.string.menu_action_bar_write_to_file_input_empty);
                        } else {
                            String path = FileServiceProvider.getExternalConfigurationPath(mContext) + File.separator + input + FileServiceProvider.SUFFIX_CONFIGURATION;
                            File file = new File(path);
                            if (file.exists()) {
                                showReplaceDialog(path);
                            } else {
                                if (FileServiceProvider.writeObjectToFile(Configuration.getInstance(), path)) {
                                    showToast(R.string.menu_action_bar_write_to_file_success);
                                } else {
                                    showToast(R.string.menu_action_bar_write_to_file_fail);
                                }
                            }
                        }
                    }
                }).setNegativeButton("取消", null).show();
    }

    private void showReplaceDialog(final String path) {
        new AlertDialog.Builder(mContext).setTitle(R.string.menu_action_bar_write_to_file_existed)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileServiceProvider.writeObjectToFile(Configuration.getInstance(), path);
                        showToast(R.string.menu_action_bar_write_to_file_success);
                    }
                }).setNegativeButton(R.string.dialog_cancel, null).create().show();
    }

}
