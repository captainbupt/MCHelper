package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.DatePicker;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.utility.ToastUtil;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class HistoryDataView extends BaseCollapsibleView {
    public HistoryDataView(Context context) {
        super(context);
        setTitle(R.string.data_history);
        setContentView(new HistoryDataContentView(context));
    }

    class HistoryDataContentView extends BaseCollapsibleContentView {

        private TextView mDownloadTextView;
        private TextView mBackupTextView;
        private TextView mClearTextView;

        public HistoryDataContentView(Context context) {
            super(context);
            initView();
            initListener();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_history;
        }

        private void initView() {
            mDownloadTextView = (TextView) findViewById(R.id.tv_view_data_history_download);
            mBackupTextView = (TextView) findViewById(R.id.tv_view_data_history_memory);
            mClearTextView = (TextView) findViewById(R.id.tv_view_data_history_clear);
        }

        private void initListener() {
            mDownloadTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext).setTitle(R.string.data_history_download_tip).setPositiveButton(R.string.data_history_download_all, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doDownload(mContext, true, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                @Override
                                public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                    ToastUtil.showToast(mContext, R.string.data_history_download_success);
                                }
                            });
                        }
                    }).setNeutralButton(R.string.data_history_download_new, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doDownload(mContext, false, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                @Override
                                public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                    ToastUtil.showToast(mContext, R.string.data_history_download_success);
                                }
                            });
                        }
                    }).setNegativeButton(R.string.dialog_cancel, null).show();
                }
            });
            mBackupTextView.setOnClickListener(new OnClickListener() {
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
            mClearTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar c = Calendar.getInstance();
                    final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                            c.set(year, monthOfYear + 1, dayOfMonth);
                            BlueToothSeriveProvider.doClearHistory(mContext, c.getTimeInMillis(), new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                @Override
                                public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                    ToastUtil.showToast(mContext, R.string.data_history_clear_success);
                                }
                            });
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.show(((Activity) mContext).getSupportFragmentManager());
                }
            });
        }

        @Override
        protected void updateData() {

        }
    }
}
