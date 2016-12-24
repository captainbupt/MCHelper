package com.vgomc.mchelper.transmit.bluetooth;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.BatteryChannelEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.BatteryStatusEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.BluetoothStatusEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.ChannelEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.CurrentDataEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.DeviceParameterEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.DeviceStatusEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.DeviceTimeEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.DownloadInquiryEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.GPRSParamEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.GPRSStatusEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.GetPhotoEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.MeasurePendEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.MeasurePlanEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.MemoryStatusEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.SDCardStatusEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.StorageTableEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.TakePhotoEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.VariableEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.AccumulateSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.BackupSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.BatterySettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.BatteryStatusSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.BluetoothStatusSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.ChannelSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.ClearEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.DeviceParamSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.DownloadingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.GPRSParamSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.GPRSSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.MeasuringSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.MeasuringStatusSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.RestartEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.SaveEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.StorageSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.TimeSettingEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.UnlockEntity;
import com.vgomc.mchelper.entity.bluetooth.setting.VariableSettingChannel;
import com.vgomc.mchelper.entity.setting.Battery;
import com.vgomc.mchelper.entity.setting.Channel;
import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.Measuring;
import com.vgomc.mchelper.entity.setting.RS485Channel;
import com.vgomc.mchelper.entity.setting.Storage;
import com.vgomc.mchelper.entity.setting.Variable;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.TimeUtil;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.MyProgressDialog;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BlueToothSeriveProvider {
    private static boolean isTransacting = false;
    private static MyProgressDialog mProgressDialog;

    public interface OnBluetoothCompletedListener {
        void onCompleted(List<BaseBluetoothEntity> bluetoothEntities);
    }

    private static void doSendMessage(final Context context, final List<BaseBluetoothEntity> bluetoothEntities, final OnBluetoothCompletedListener listener) {
        if (isTransacting) {
            ToastUtil.showToast(context, R.string.tip_bluetooth_busy);
            return;
        }
        if (bluetoothEntities == null || bluetoothEntities.size() == 0) {
            ToastUtil.showToast(context, R.string.tip_bluetooth_empty);
            return;
        }
        bluetoothEntities.add(0, new UnlockEntity(context, new UnlockEntity.OnPasswordConfirmListener() {
            @Override
            public void onPasswordConfirm() {
                BluetoothHelper.initBluetooth(context);
                BluetoothHelper.setOnReceivedMessageListener(new SequenceOnReceivedMessageListener(context, bluetoothEntities, listener));
                isTransacting = BluetoothHelper.sendMessage(bluetoothEntities.get(0).getRequest());
                mProgressDialog = new MyProgressDialog(context);
                mProgressDialog.setTitle(R.string.tip_bluetooth_transferring);
                mProgressDialog.setMessage(context.getResources().getString(R.string.tip_bluetooth_connecting));
                mProgressDialog.setCancelable(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setMax(bluetoothEntities.size());
                mProgressDialog.show();
                overtimeRunnable.setContext(context);
                handler.removeCallbacks(overtimeRunnable);
                handler.postDelayed(overtimeRunnable, 30000);
            }
        }));
    }

    private static void cancelTransaction() {
        BluetoothHelper.setOnReceivedMessageListener(null);
        handler.removeCallbacks(overtimeRunnable);
        isTransacting = false;
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    static Handler handler = new Handler();

    static OvertimeRunnable overtimeRunnable = new OvertimeRunnable();

    static class OvertimeRunnable implements Runnable {

        private Context mContext;

        public void setContext(Context context) {
            this.mContext = context;
        }

        @Override
        public void run() {
            if (mContext != null) {
                ToastUtil.showToast(mContext, R.string.tip_bluetooth_overtime);
            }
            BluetoothHelper.OnReceivedMessageListener listener = BluetoothHelper.getOnReceivedMessageListener();
            if (listener != null && listener instanceof SequenceOnReceivedMessageListener) {
                ((SequenceOnReceivedMessageListener) listener).retry();
            } else {
                cancelTransaction();
            }
        }
    }

    static class SequenceOnReceivedMessageListener implements BluetoothHelper.OnReceivedMessageListener {

        private static final Pattern OK_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "OK" + BaseBluetoothEntity.SEPERATOR);
        private static final Pattern ERROR_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "ER:[-]?[0-9]+" + BaseBluetoothEntity.SEPERATOR);

        private OnBluetoothCompletedListener mCompletedListener;
        private List<BaseBluetoothEntity> mBluetoothEntities;
        private Context mContext;
        private int mIndex;
        private ArrayList<Byte> mReceivedMessageBytes;
        private int retryTime = 0;

        public SequenceOnReceivedMessageListener(Context context, List<BaseBluetoothEntity> bluetoothEntities, OnBluetoothCompletedListener completedListener) {
            this.mContext = context;
            mIndex = 0;
            mReceivedMessageBytes = new ArrayList<>();
            this.mBluetoothEntities = bluetoothEntities;
            this.mCompletedListener = completedListener;
            retryTime = 0;
        }


        @Override
        public void onReceivedMessage(byte[] messageBytes, int length) {
            if (length == 0 || messageBytes.length == 0) {
                return;
            }
            for (int ii = 0; ii < length && ii < messageBytes.length; ii++) {
                mReceivedMessageBytes.add(messageBytes[ii]);
            }
            byte[] buffer = new byte[mReceivedMessageBytes.size()];
            for (int ii = 0; ii < mReceivedMessageBytes.size(); ii++) {
                buffer[ii] = mReceivedMessageBytes.get(ii);
            }
            String mReceivedMessage = new String(buffer, Charset.forName("GBK"));
            Matcher errorMatcher = ERROR_PATTERN.matcher(mReceivedMessage);
            if (OK_PATTERN.matcher(mReceivedMessage).find()) {
                if (mBluetoothEntities.get(mIndex).parseOKResponse(mReceivedMessage, buffer)) {
                    sendMessage();
                    retryTime = 0;
                } else {
                    new AlertDialog.Builder(mContext).setTitle(R.string.tip_bluetooth_parse).setMessage(mReceivedMessage).show();
                    cancelTransaction();
                }
                //mReceivedMessage.delete(0, mReceivedMessage.length());
                mReceivedMessageBytes.clear();
            } else if (errorMatcher.find()) {
                String errorResponse = errorMatcher.group();
                boolean result = mBluetoothEntities.get(mIndex).parseErrorCode(mContext, Integer.parseInt(errorResponse.replace(BaseBluetoothEntity.SEPERATOR, "").replace("ER:", "")));
                if (result) {
                    cancelTransaction();
                } else {
                    sendMessage();
                    retryTime = 0;
                }
                mReceivedMessageBytes.clear();
            }
        }

        public void retry() {
            if (retryTime < 2) {
                handler.removeCallbacks(overtimeRunnable);
                ToastUtil.showToast(mContext, "正在重试" + (retryTime + 1) + "次");
                retryTime++;
                if (mIndex > 0) {
                    mIndex--;
                }
                mBluetoothEntities.add(mIndex + 1, new UnlockEntity(mContext));
                sendMessage();
            } else {
                cancelTransaction();
            }
        }

        @Override
        public void onError() {
            ToastUtil.showToast(mContext, "蓝牙连接失败，请重试");
            if (retryTime < 2) {
                handler.removeCallbacks(overtimeRunnable);
                ToastUtil.showToast(mContext, "正在重试" + (retryTime + 1) + "次");
                retryTime++;
                if (!((Activity) mContext).isFinishing())
                    new AlertDialog.Builder(mContext).setTitle("蓝牙连接失败，请重启采集器的蓝牙，确保蓝牙工作后重试").setPositiveButton("点击重试", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mIndex > 0) {
                                mIndex--;
                            }
                            mBluetoothEntities.add(mIndex + 1, new UnlockEntity(mContext));
                            sendMessage();
                        }
                    }).setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelTransaction();
                        }
                    }).setCancelable(false).show();
            } else {
                cancelTransaction();
            }
        }

        private void sendMessage() {
            handler.removeCallbacks(overtimeRunnable);
            handler.postDelayed(overtimeRunnable, 30000);
            if (mIndex < mBluetoothEntities.size() - 1) {
                if (mBluetoothEntities.get(mIndex).getClass().equals(UnlockEntity.class)) {
                    mBluetoothEntities.remove(mIndex);
                    mIndex--;
                }
                String request = mBluetoothEntities.get(mIndex + 1).getRequest();
                mProgressDialog.setMessage(mContext.getResources().getString(R.string.tip_bluetooth_sending) + request);
                mProgressDialog.setProgress(mIndex);
                BluetoothHelper.sendMessage(request);
                mIndex++;
            } else {
                cancelTransaction();
                mCompletedListener.onCompleted(mBluetoothEntities);
            }
        }
    }

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

    public static void doWriteConfiguration(Context context, Configuration configuration, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new DeviceParamSettingEntity(configuration.name, configuration.timeZone, configuration.bluetoothTime, configuration.password, (RS485Channel) configuration.channelMap.get(Channel.SUBJECT_RS485)));
        for (int ii = 1; ii < Channel.SUBJECTS.length; ii++) { // 不发送SHT
            entities.add(new ChannelSettingEntity(configuration.channelMap.get(Channel.SUBJECTS[ii])));
        }
        for (Variable variable : configuration.variableManager.getAllVariableList()) {
            entities.add(new VariableSettingChannel(variable));
        }
        for (Object o : configuration.batteryList) {
            entities.add(new BatterySettingEntity((Battery) o));
        }
        for (int ii = 0; ii < configuration.measuringList.size(); ii++) {
            entities.add(new MeasuringSettingEntity(ii + 1, (Measuring) configuration.measuringList.get(ii)));
        }
        for (int ii = 0; ii < configuration.storageList.size(); ii++) {
            entities.add(new StorageSettingEntity(ii + 1, (Storage) configuration.storageList.get(ii)));
        }
        entities.add(new GPRSParamSettingEntity(configuration.network));
        entities.add(new SaveEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doGetCurrentStatus(Context context, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new DeviceParameterEntity());
        entities.add(new DeviceStatusEntity());
        entities.add(new DeviceTimeEntity());
        entities.add(new MemoryStatusEntity());
        entities.add(new SDCardStatusEntity());
        entities.add(new GPRSStatusEntity());
        entities.add(new MeasurePendEntity());
        entities.add(new BluetoothStatusEntity());
        entities.add(new BatteryStatusEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doGetCurrentData(Context context, int varId, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        for (int ii = 1; ii <= CurrentDataEntity.COUNT_TABLE; ii++) {
            if (varId == -1)
                entities.add(new CurrentDataEntity(ii));
            else
                entities.add(new CurrentDataEntity(ii, varId));
        }
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doSetMeasuringStatus(Context context, int time, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new MeasuringStatusSettingEntity(time));
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doSetBatteryStatus(Context context, String subject, int time, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new BatteryStatusSettingEntity(subject, time));
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doSetNetworkStatus(Context context, boolean isOn, boolean isNormal, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new GPRSSettingEntity(isOn, isNormal));
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doSetBluetoothStatus(Context context, boolean isOn, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new BluetoothStatusSettingEntity(isOn));
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doReset(Context context, boolean isHard, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new RestartEntity(isHard));
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doClearHistory(Context context, String time, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new ClearEntity(time));
        entities.add(new SaveEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doSetTime(Context context, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new TimeSettingEntity(Calendar.getInstance().getTimeInMillis()));
        entities.add(new SaveEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doSetAccumulate(Context context, int varId, float value, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new AccumulateSettingEntity(varId, value));
        entities.add(new SaveEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doGetDeviceInfo(Context context, OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new DeviceParameterEntity());
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }

    public static void doDownload(final Context context, boolean isAll, final OnBluetoothCompletedListener onBluetoothCompletedListener) {
        List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new DownloadInquiryEntity(isAll));
        entities.add(new DeviceParameterEntity());
        doSendMessage(context, entities, new OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                long time = Calendar.getInstance().getTimeInMillis();
                int start = ((DownloadInquiryEntity) bluetoothEntities.get(0)).start;
                int count = ((DownloadInquiryEntity) bluetoothEntities.get(0)).count;
                String unid = ((DeviceParameterEntity) bluetoothEntities.get(1)).uid;
                String name = ((DeviceParameterEntity) bluetoothEntities.get(1)).name;
                String path = FileServiceProvider.getExternalRecordPath(context);
                File file = new File(path + File.separator + unid);
                if (!file.exists() || !file.isDirectory())
                    file.mkdir();
                String fileName = unid + File.separator + TimeUtil.long2DigitTime(time);
                try {
                    FileServiceProvider.saveRecord(context, fileName, "设备编号:" + unid + "\n设备名称:" + name + "\n下载日期:" + TimeUtil.long2DeviceTime(time));
                } catch (IOException e) {
                    e.printStackTrace();
                    ToastUtil.showToast(context, "写入文件失败，请重试");
                    return;
                }
                List<BaseBluetoothEntity> entities = new ArrayList<>();
                entities.add(new VariableEntity(context, fileName));
                for (int ii = 1; ii <= count; ii++) {
                    entities.add(new DownloadingEntity(context, start, ii, 1, fileName));
                }
                entities.add(new SaveEntity());
                doSendMessage(context, entities, onBluetoothCompletedListener);
            }
        });
    }

    public static void doBackup(final Context context, final boolean isAll, final OnBluetoothCompletedListener onBluetoothCompletedListener) {
        final List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new UnlockEntity(context, new UnlockEntity.OnPasswordConfirmListener() {
            @Override
            public void onPasswordConfirm() {
                entities.add(new BackupSettingEntity(isAll));
                entities.add(new SaveEntity());
                if (isTransacting) {
                    ToastUtil.showToast(context, R.string.tip_bluetooth_busy);
                    return;
                }
                BluetoothHelper.initBluetooth(context);
                BluetoothHelper.setOnReceivedMessageListener(new BackupOnReceiveMessageListener(context, entities, onBluetoothCompletedListener));
                isTransacting = BluetoothHelper.sendMessage(entities.get(0).getRequest());
                mProgressDialog = new MyProgressDialog(context);
                mProgressDialog.setTitle(R.string.tip_bluetooth_transferring);
                mProgressDialog.setMessage(context.getResources().getString(R.string.tip_bluetooth_connecting));
                mProgressDialog.setCancelable(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.show();
            }
        }));
    }

    static class BackupOnReceiveMessageListener implements BluetoothHelper.OnReceivedMessageListener {

        private static final Pattern SEQUENCE_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "[A-Za-z:0-9]*" + BaseBluetoothEntity.SEPERATOR);

        private OnBluetoothCompletedListener mCompletedListener;
        private List<BaseBluetoothEntity> mBluetoothEntities;
        private Context mContext;
        private int mIndex;
        private ArrayList<Byte> mReceivedMessageBytes;

        public BackupOnReceiveMessageListener(Context context, List<BaseBluetoothEntity> bluetoothEntities, OnBluetoothCompletedListener completedListener) {
            this.mContext = context;
            mIndex = 0;
            mReceivedMessageBytes = new ArrayList<>();
            this.mBluetoothEntities = bluetoothEntities;
            this.mCompletedListener = completedListener;
        }


        @Override
        public void onReceivedMessage(byte[] messageBytes, int length) {
            if (length == 0 || messageBytes.length == 0) {
                return;
            }
            for (int ii = 0; ii < length && ii < messageBytes.length; ii++) {
                mReceivedMessageBytes.add(messageBytes[ii]);
            }
            byte[] buffer = new byte[mReceivedMessageBytes.size()];
            for (int ii = 0; ii < mReceivedMessageBytes.size(); ii++) {
                buffer[ii] = mReceivedMessageBytes.get(ii);
            }
            String mReceivedMessage = new String(buffer, Charset.forName("GBK"));
            Matcher sequenceMatcher = SEQUENCE_PATTERN.matcher(mReceivedMessage);
            while (sequenceMatcher.find()) {
                String sequence = sequenceMatcher.group().replace(BaseBluetoothEntity.SEPERATOR, "");
                byte[] sequenceByte = sequence.getBytes(Charset.forName("GBK"));
                for (int jj = 0; jj < sequenceByte.length + 4; jj++) {
                    mReceivedMessageBytes.remove(0);
                }
                try {
                    if (sequence.startsWith("START:")) {
                        mProgressDialog.setMax(Integer.parseInt(sequence.replace("START:", "")));
                    } else if (sequence.startsWith("OK")) {
                        if (mBluetoothEntities.get(mIndex).parseOKResponse(mReceivedMessage.toString(), buffer)) {
                            sendMessage();
                        } else {
                            new AlertDialog.Builder(mContext).setTitle(R.string.tip_bluetooth_parse).setMessage(mReceivedMessage).show();
                            cancelTransaction();
                        }
                    } else if (sequence.startsWith("ER:")) {
                        mBluetoothEntities.get(mIndex).parseErrorCode(mContext, Integer.parseInt(sequence.replace("ER:", "")));
                        cancelTransaction();
                    } else {
                        mProgressDialog.setProgress(Integer.parseInt(sequence));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    new AlertDialog.Builder(mContext).setTitle(R.string.tip_bluetooth_parse).setMessage(mReceivedMessage).show();
                    cancelTransaction();
                }
            }
        }

        @Override
        public void onError() {
            cancelTransaction();
        }

        private void sendMessage() {
            if (mIndex < mBluetoothEntities.size() - 1) {
                String request = mBluetoothEntities.get(mIndex + 1).getRequest();
                BluetoothHelper.sendMessage(request);
                mProgressDialog.setMessage(mContext.getResources().getString(R.string.tip_bluetooth_sending) + request);
                mIndex++;
            } else {
                cancelTransaction();
                mCompletedListener.onCompleted(mBluetoothEntities.subList(1, mBluetoothEntities.size()));
            }
        }
    }

    public static void takePhoto(final Context context, final int resolution, final OnBluetoothCompletedListener onBluetoothCompletedListener) {
        final List<BaseBluetoothEntity> entities = new ArrayList<>();
        entities.add(new UnlockEntity(context, new UnlockEntity.OnPasswordConfirmListener() {
            @Override
            public void onPasswordConfirm() {
                entities.add(new TakePhotoEntity(resolution));
                if (isTransacting) {
                    ToastUtil.showToast(context, R.string.tip_bluetooth_busy);
                    return;
                }
                BluetoothHelper.initBluetooth(context);
                BluetoothHelper.setOnReceivedMessageListener(new TakePhotoOnReceivedMessageListener(context, entities, onBluetoothCompletedListener));
                isTransacting = BluetoothHelper.sendMessage(entities.get(0).getRequest());
                mProgressDialog = new MyProgressDialog(context);
                mProgressDialog.setTitle(R.string.tip_bluetooth_transferring);
                mProgressDialog.setMessage(context.getResources().getString(R.string.tip_bluetooth_connecting));
                mProgressDialog.setCancelable(false);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.show();
            }
        }));
    }

    static class TakePhotoOnReceivedMessageListener implements BluetoothHelper.OnReceivedMessageListener {

        private static final Pattern OK_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "OK" + BaseBluetoothEntity.SEPERATOR);
        private static final Pattern ERROR_PATTERN = Pattern.compile(BaseBluetoothEntity.SEPERATOR + "ER:[-]?[0-9]+" + BaseBluetoothEntity.SEPERATOR);

        private OnBluetoothCompletedListener mCompletedListener;
        private List<BaseBluetoothEntity> mBluetoothEntities;
        private Context mContext;
        private int mIndex;
        private ArrayList<Byte> mReceivedMessageBytes;
        private boolean startingPhoto;

        public TakePhotoOnReceivedMessageListener(Context context, List<BaseBluetoothEntity> bluetoothEntities, OnBluetoothCompletedListener completedListener) {
            this.mContext = context;
            mIndex = 0;
            mReceivedMessageBytes = new ArrayList<>();
            this.mBluetoothEntities = bluetoothEntities;
            this.mCompletedListener = completedListener;
            this.startingPhoto = false;
        }

        @Override
        public void onReceivedMessage(byte[] messageBytes, int length) {
            if (length == 0 || messageBytes.length == 0) {
                return;
            }
            for (int ii = 0; ii < length && ii < messageBytes.length; ii++) {
                mReceivedMessageBytes.add(messageBytes[ii]);
            }
            byte[] buffer = new byte[mReceivedMessageBytes.size()];
            for (int ii = 0; ii < mReceivedMessageBytes.size(); ii++) {
                buffer[ii] = mReceivedMessageBytes.get(ii);
            }
            String mReceivedMessage = new String(buffer, Charset.forName("GBK"));
            Matcher errorMatcher = ERROR_PATTERN.matcher(mReceivedMessage);
            if (OK_PATTERN.matcher(mReceivedMessage).find()) {
                if (!(mBluetoothEntities.get(mIndex) instanceof TakePhotoEntity) || // 用默认方式处理解锁
                        startingPhoto) { // 已经开始拍照
                    if (mBluetoothEntities.get(mIndex).parseOKResponse(mReceivedMessage.toString(), buffer)) {
                        sendMessage();
                    } else {
                        new AlertDialog.Builder(mContext).setTitle(R.string.tip_bluetooth_parse)
                                .setMessage(mBluetoothEntities.get(mIndex).getClass().getName() + "\n" + mReceivedMessage).show();
                        cancelTransaction();
                    }
                    mReceivedMessageBytes.clear();
                } else { // 处理拍照事件
                    startingPhoto = true;
                    // 清空之前的OK
                    mReceivedMessageBytes = new ArrayList<>();
                    mProgressDialog.setMessage("拍照中，请稍候...");
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                }
            } else if (errorMatcher.find()) {
                String errorResponse = errorMatcher.group();
                boolean result = mBluetoothEntities.get(mIndex).parseErrorCode(mContext, Integer.parseInt(errorResponse.replace(BaseBluetoothEntity.SEPERATOR, "").replace("ER:", "")));
                if (result) {
                    cancelTransaction();
                } else {
                    sendMessage();
                }
                mReceivedMessageBytes.clear();
            }
        }

        @Override
        public void onError() {
            cancelTransaction();
        }


        private void sendMessage() {
            handler.removeCallbacks(overtimeRunnable);
            handler.postDelayed(overtimeRunnable, 50000);
            if (mIndex < mBluetoothEntities.size() - 1) {
                if (mBluetoothEntities.get(mIndex).getClass().equals(UnlockEntity.class)) {
                    mBluetoothEntities.remove(mIndex);
                    mIndex--;
                }
                String request = mBluetoothEntities.get(mIndex + 1).getRequest();
                mProgressDialog.setMessage(mContext.getResources().getString(R.string.tip_bluetooth_sending) + request);
                mProgressDialog.setProgress(mIndex);
                BluetoothHelper.sendMessage(request);
                mIndex++;
            } else {
                cancelTransaction();
                mCompletedListener.onCompleted(mBluetoothEntities);
            }
        }
    }

    public static void getPhoto(final Context context, long address, long size, final OnBluetoothCompletedListener onBluetoothCompletedListener) {
        final List<BaseBluetoothEntity> entities = new ArrayList<>();
        long target = address + size;
        for (long tmp = address; tmp < target; tmp += 2048) {
            entities.add(new GetPhotoEntity(tmp, Math.min(2048, target - tmp)));
        }
        doSendMessage(context, entities, onBluetoothCompletedListener);
    }
}
