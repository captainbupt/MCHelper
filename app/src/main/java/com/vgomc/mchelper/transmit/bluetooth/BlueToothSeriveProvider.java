package com.vgomc.mchelper.transmit.bluetooth;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.BatteryChannelEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.ChannelEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.CurrentDataEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.DeviceParameterEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.DeviceStatusEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.DeviceTimeEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.GPRSParamEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.GPRSStatusEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.MeasurePlanEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.MemoryStatusEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.SDCardStatusEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.StorageTableEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.VariableEntity;
import com.vgomc.mchelper.Entity.bluetooth.setting.BaseBluetoothSettingEntity;
import com.vgomc.mchelper.Entity.bluetooth.setting.UnlockEntity;
import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.ProgressDialog;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by weizhouh on 6/3/2015.
 */
public class BlueToothSeriveProvider {
    public static void doReadConfiguration(Context context, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new DeviceParameterEntity());
        entities.add(new ChannelEntity());
        entities.add(new VariableEntity());
        entities.add(new BatteryChannelEntity());
        entities.add(new MeasurePlanEntity());
        entities.add(new StorageTableEntity());
        entities.add(new GPRSParamEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doWriteConfiguration(Configuration configuration) {

    }

    public static void doGetCurrentStatus(Context context, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new DeviceParameterEntity());
        entities.add(new DeviceStatusEntity());
        entities.add(new DeviceTimeEntity());
        entities.add(new MemoryStatusEntity());
        entities.add(new SDCardStatusEntity());
        entities.add(new GPRSStatusEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doGetCurrentData(Context context, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        for (int ii = 1; ii <= CurrentDataEntity.COUNT_TABLE; ii++) {
            entities.add(new CurrentDataEntity(ii));
        }
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    private static boolean isTransacting = false;
    private static ProgressDialog mProgressDialog;

    public interface OnBluetoothCompletedListener {
        void onCompleted(List<BaseBluetoothEntity> bluetoothEntities);
    }

    private static void doSendMessage(Context context, List<BaseBluetoothEntity> bluetoothEntities, OnBluetoothCompletedListener listener) {
        if (isTransacting) {
            ToastUtil.showToast(context, R.string.tip_bluetooth_busy);
            return;
        }
        if (bluetoothEntities == null || bluetoothEntities.size() == 0) {
            ToastUtil.showToast(context, R.string.tip_bluetooth_empty);
            return;
        }
        bluetoothEntities.add(0, new UnlockEntity(Configuration.getInstance().password));
        BluetoothHelper.initBluetooth(context);
        BluetoothHelper.setOnReceivedMessageListener(new SequenceOnReceivedMessageListener(context, bluetoothEntities, listener));
        isTransacting = BluetoothHelper.sendMessage(bluetoothEntities.get(0).getRequest());
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(R.string.tip_bluetooth_transferring);
        mProgressDialog.setMessage(context.getResources().getString(R.string.tip_bluetooth_connecting));
        mProgressDialog.show();
    }

    static class SequenceOnReceivedMessageListener implements BluetoothHelper.OnReceivedMessageListener {

        private static final Pattern OK_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "OK" + BaseBluetoothEntity.SEPERATOR);
        private static final Pattern ERROR_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "ER:[-]?[0-9]+" + BaseBluetoothEntity.SEPERATOR);

        private OnBluetoothCompletedListener mCompletedListener;
        private List<BaseBluetoothEntity> mBluetoothEntities;
        private Context mContext;
        private int mIndex;
        private ArrayList<Byte> mReceivedMessageBytes;

        public SequenceOnReceivedMessageListener(Context context, List<BaseBluetoothEntity> bluetoothEntities, OnBluetoothCompletedListener completedListener) {
            this.mContext = context;
            mIndex = 0;
            mReceivedMessageBytes = new ArrayList<>();
            this.mBluetoothEntities = bluetoothEntities;
            this.mCompletedListener = completedListener;
        }

        @Override
        public void onReceivedMessage(byte[] messageBytes, int length) {
            for (int ii = 0; ii < length; ii++) {
                mReceivedMessageBytes.add(messageBytes[ii]);
            }
            byte[] buffer = new byte[mReceivedMessageBytes.size()];
            for (int ii = 0; ii < mReceivedMessageBytes.size(); ii++) {
                buffer[ii] = mReceivedMessageBytes.get(ii);
            }
            String mReceivedMessage = new String(buffer, Charset.forName("GBK"));
            if (OK_PATTERN.matcher(mReceivedMessage).find()) {
                if (mBluetoothEntities.get(mIndex).parseOKResponse(mReceivedMessage.toString())) {
                    sendMessage();
                } else {
                    new AlertDialog.Builder(mContext).setTitle(R.string.tip_bluetooth_parse).setMessage(mReceivedMessage).show();
                    isTransacting = false;
                    mProgressDialog.dismiss();
                }
                //mReceivedMessage.delete(0, mReceivedMessage.length());
                mReceivedMessageBytes.clear();
            } else if (ERROR_PATTERN.matcher(mReceivedMessage).find()) {
                String errorResponse = ERROR_PATTERN.matcher(mReceivedMessage).group();
                boolean result = mBluetoothEntities.get(mIndex).parseErrorCode(mContext, Integer.parseInt(errorResponse.replace(BaseBluetoothEntity.SEPERATOR, "").replace("ER:", "")));
                if (result) {
                    isTransacting = false;
                    mProgressDialog.dismiss();
                } else {
                    sendMessage();
                }
                mReceivedMessageBytes.clear();
            }
        }

        private void sendMessage() {
            if (mIndex < mBluetoothEntities.size() - 1) {
                String request = mBluetoothEntities.get(mIndex + 1).getRequest();
                mProgressDialog.setMessage(mContext.getResources().getString(R.string.tip_bluetooth_sending) + request);
                BluetoothHelper.sendMessage(request);
                mIndex++;
            } else {
                mCompletedListener.onCompleted(mBluetoothEntities.subList(1, mBluetoothEntities.size()));
                isTransacting = false;
                mProgressDialog.dismiss();
            }
        }
    }
}
