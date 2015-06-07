package com.vgomc.mchelper.transmit.bluetooth;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.bluetooth.CurrentData;
import com.vgomc.mchelper.Entity.bluetooth.Unlock;
import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by weizhouh on 6/3/2015.
 */
public class BlueToothSeriveProvider {
    public static Configuration doReadConfiguration() {
        return null;
    }

    public static void doWriteConfiguration(Configuration configuration) {

    }

    public static void doGetCurrentData(Context context, final Handler handler) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        for (int ii = 1; ii <= CurrentData.COUNT_TABLE; ii++) {
            entities.add(new CurrentData(ii));
        }
        doSendMessage(context, entities, new OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                List[] dataList = new List[CurrentData.COUNT_TABLE];
                for (int ii = 0; ii < CurrentData.COUNT_TABLE; ii++) {
                    dataList[ii] = ((CurrentData) bluetoothEntities.get(ii)).variableDataList;
                }
                Message.obtain(handler, 0, dataList).sendToTarget();
            }
        });
    }

    private static boolean isTransacting = false;
    private static Unlock unlock = new Unlock();

    interface OnBluetoothCompletedListener {
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
        bluetoothEntities.add(0, new Unlock());
        BluetoothHelper.initBluetooth(context);
        BluetoothHelper.setOnReceivedMessageListener(new SequenceOnReceivedMessageListener(context, bluetoothEntities, listener));
        isTransacting = BluetoothHelper.sendMessage(bluetoothEntities.get(0).getRequest());
    }

    static class SequenceOnReceivedMessageListener implements BluetoothHelper.OnReceivedMessageListener {

        private static final Pattern OK_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "OK" + BaseBluetoothEntity.SEPERATOR);
        private static final Pattern ERROR_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "ER:[-]?[0-9]+" + BaseBluetoothEntity.SEPERATOR);

        private OnBluetoothCompletedListener mCompletedListener;
        private List<BaseBluetoothEntity> mBluetoothEntities;
        private Context mContext;
        private int mIndex;
        private StringBuffer mReceivedMessage;

        public SequenceOnReceivedMessageListener(Context context, List<BaseBluetoothEntity> bluetoothEntities, OnBluetoothCompletedListener completedListener) {
            this.mContext = context;
            mIndex = 0;
            mReceivedMessage = new StringBuffer();
            this.mBluetoothEntities = bluetoothEntities;
            this.mCompletedListener = completedListener;
        }

        @Override
        public void onReceivedMessage(String message) {
            mReceivedMessage.append(message);
            if (OK_PATTERN.matcher(mReceivedMessage).find()) {
                if(mBluetoothEntities.get(mIndex).parseOKResponse(mReceivedMessage.toString())) {
                    if (mIndex < mBluetoothEntities.size() - 1) {
                        BluetoothHelper.sendMessage(mBluetoothEntities.get(mIndex + 1).getRequest());
                        mIndex++;
                    } else {
                        mCompletedListener.onCompleted(mBluetoothEntities.subList(1, mBluetoothEntities.size()));
                        isTransacting = false;
                    }
                }else{
                    ToastUtil.showToast(mContext, R.string.tip_bluetooth_parse);
                    isTransacting = false;
                }
                mReceivedMessage.delete(0, mReceivedMessage.length());
            } else if (ERROR_PATTERN.matcher(mReceivedMessage).find()) {
                ToastUtil.showToast(mContext, R.string.tip_bluetooth_fail);
                ToastUtil.showToast(mContext, "Request: " + mBluetoothEntities.get(mIndex - 1).getRequest());
                ToastUtil.showToast(mContext, "Response: " + message);
                isTransacting = false;
                mReceivedMessage.delete(0, mReceivedMessage.length());
            }
        }
    }
}
