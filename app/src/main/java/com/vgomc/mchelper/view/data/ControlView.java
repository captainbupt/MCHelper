package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

/**
 * Created by weizhouh on 6/18/2015.
 */
public class ControlView extends BaseCollapsibleView {

    public ControlView(Context context) {
        super(context);
        setTitle("控制");
        setContentView(new ControlContentView(mContext));
    }


    class ControlContentView extends BaseCollapsibleContentView {
        private Button mBackupButton;
        private Button mClearButton;

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
        }

        @Override
        protected void updateData() {

        }
    }

}
