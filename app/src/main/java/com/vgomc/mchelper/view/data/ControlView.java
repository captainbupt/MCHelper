package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.entity.bluetooth.inquiry.GetPhotoEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.TakePhotoEntity;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.FileUtil;
import com.vgomc.mchelper.utility.TimeUtil;
import com.vgomc.mchelper.utility.ToastUtil;

import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ControlView extends BaseCollapsibleView {

    public ControlView(Context context) {
        super(context);
        setTitle("控制");
        setContentView(new ControlContentView(mContext));
    }


    class ControlContentView extends BaseCollapsibleContentView {
        private Button mBackupButton;
        private Button mClearButton;
        private Button mPhotoHighButtion;
        private Button mPhotoMedButtion;
        private Button mPhotoLowButtion;

        public ControlContentView(Context context) {
            super(context);
            initView();
            initListener();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_control;
        }

        private void initView() {
            mBackupButton = (Button) findViewById(R.id.btn_view_data_control_backup);
            mClearButton = (Button) findViewById(R.id.btn_view_data_control_clear);
            mPhotoHighButtion = (Button) findViewById(R.id.btn_view_data_control_photo_high);
            mPhotoMedButtion = (Button) findViewById(R.id.btn_view_data_control_photo_med);
            mPhotoLowButtion = (Button) findViewById(R.id.btn_view_data_control_photo_low);
        }

        private void initListener() {
            mBackupButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext).setTitle(R.string.data_history_memory_backup_tip).setPositiveButton(R.string.data_history_memory_backup_all, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doBackup(mContext, true, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                @Override
                                public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                    ToastUtil.showToast(mContext, R.string.data_history_memory_backup_success);
                                }
                            });
                        }
                    }).setNeutralButton(R.string.data_history_memory_backup_record, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doBackup(mContext, false, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                @Override
                                public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                    ToastUtil.showToast(mContext, R.string.data_history_memory_backup_success);
                                }
                            });
                        }
                    }).setNegativeButton(R.string.dialog_cancel, null).show();
                }
            });
            mClearButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText editText = new EditText(mContext);
                    new AlertDialog.Builder(mContext).setTitle("请输入设备日期（格式：XXXX-XX-XX XX:XX:XX）").setView(editText).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String time = editText.getText().toString();
                            if (TextUtils.isEmpty(time)) {
                                ToastUtil.showToast(mContext, "不能为空");
                            } else {
                                BlueToothSeriveProvider.doClearHistory(mContext, time, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                    @Override
                                    public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                        ToastUtil.showToast(mContext, "删除成功！");
                                    }
                                });
                            }
                        }
                    }).setNegativeButton(R.string.dialog_cancel, null).show();
                }
            });
            mPhotoHighButtion.setOnClickListener(new TakePhotoOnClickListener(mContext, TakePhotoEntity.RESOLUTION_HIGH));
            mPhotoMedButtion.setOnClickListener(new TakePhotoOnClickListener(mContext, TakePhotoEntity.RESOLUTION_MED));
            mPhotoLowButtion.setOnClickListener(new TakePhotoOnClickListener(mContext, TakePhotoEntity.RESOLUTION_LOW));
        }

        @Override
        protected void updateData() {

        }
    }

    static class TakePhotoOnClickListener implements OnClickListener {

        private Context mContext;
        private int resolution;

        public TakePhotoOnClickListener(Context context, int resolution) {
            this.mContext = context;
            this.resolution = resolution;
        }

        @Override
        public void onClick(View v) {
            BlueToothSeriveProvider.takePhoto(mContext, resolution, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                @Override
                public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                    TakePhotoEntity entity = (TakePhotoEntity) bluetoothEntities.get(1);
                    BlueToothSeriveProvider.getPhoto(mContext, entity.address, entity.fileSize, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                        @Override
                        public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                            String path = FileServiceProvider.getExternalPhotoPath(mContext) + File.separator + TimeUtil.long2DigitTime(System.currentTimeMillis()) + FileServiceProvider.SUFFIX_PHOTO;
                            try {
                                FileOutputStream fos = new FileOutputStream(path);
                                for (BaseBluetoothEntity getPhotoEntity : bluetoothEntities) {
                                    fos.write(((GetPhotoEntity) getPhotoEntity).content.getBytes());
                                }
                                fos.close();
                                ToastUtil.showToast(mContext, "照片保存到：" + path);
                            } catch (IOException e) {
                                e.printStackTrace();
                                ToastUtil.showToast(mContext, "保存照片失败");
                            }

                        }
                    });
                }
            });
        }
    }
}
