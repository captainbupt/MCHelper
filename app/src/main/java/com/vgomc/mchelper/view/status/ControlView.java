package com.vgomc.mchelper.view.status;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.setting.Battery;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.dialog.BigNumberPickerDialog;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.utility.TimeUtil;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.StatusControlView;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;

import java.util.List;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class ControlView extends BaseCollapsibleView {
    SystemContentView mContentView;

    public ControlView(Context context) {
        super(context);
        setTitle(R.string.status_control);
        mContentView = new SystemContentView(mContext);
        setContentView(mContentView);
    }

    public void setData(int measuringTime, int[] batteryTime, String bluetoothStatus) {
        mContentView.setData(measuringTime, batteryTime, bluetoothStatus);
    }

    class SystemContentView extends BaseCollapsibleContentView {
        private StatusControlView mMeasuringControlView;
        private StatusControlView[] mBatteryControlViews;
        private StatusControlView mNetworkControlView;
        private StatusControlView mBluetoothControlView;
        private Button mResetButton;

        public SystemContentView(Context context) {
            super(context);
            initView();
            initListener();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_control;
        }

        @Override
        protected void updateData() {

        }

        public void setData(int measuringTime, int[] batteryTime, String bluetoothStatus) {
            if (measuringTime == 0) {
                mMeasuringControlView.setStatus(getResources().getString(R.string.status_control_measuring_status_running));
            } else {
                mMeasuringControlView.setStatus(getResources().getString(R.string.status_control_measuring_status_running) + measuringTime);
            }
            for (int ii = 0; ii < mBatteryControlViews.length; ii++) {
                if (batteryTime[ii] == 0) {
                    mBatteryControlViews[ii].setStatus(getResources().getString(R.string.status_control_battery_status_closing));
                } else {
                    mBatteryControlViews[ii].setStatus(getResources().getString(R.string.status_control_battery_status_running) + TimeUtil.long2TimeString(batteryTime[ii] * 1000l));
                }
            }
            mBluetoothControlView.setStatus(bluetoothStatus);
        }

        private void initView() {
            mMeasuringControlView = (StatusControlView) findViewById(R.id.scv_view_status_control_measuring);
            mMeasuringControlView.initData(R.string.status_control_measuring, R.string.status_control_measuring_pause, R.string.status_control_measuring_start);
            mBatteryControlViews = new StatusControlView[Battery.SUBJECTS.length];
            mBatteryControlViews[0] = (StatusControlView) findViewById(R.id.scv_view_status_control_battery_1);
            mBatteryControlViews[1] = (StatusControlView) findViewById(R.id.scv_view_status_control_battery_2);
            mBatteryControlViews[2] = (StatusControlView) findViewById(R.id.scv_view_status_control_battery_3);
            mBatteryControlViews[3] = (StatusControlView) findViewById(R.id.scv_view_status_control_battery_4);
            mBatteryControlViews[4] = (StatusControlView) findViewById(R.id.scv_view_status_control_battery_5);
            mBatteryControlViews[5] = (StatusControlView) findViewById(R.id.scv_view_status_control_battery_6);
            mBatteryControlViews[6] = (StatusControlView) findViewById(R.id.scv_view_status_control_battery_7);
            for (int ii = 0; ii < mBatteryControlViews.length; ii++) {
                mBatteryControlViews[ii].initData(getResources().getString(R.string.status_control_battery) + Battery.SUBJECTS[ii], R.string.status_control_battery_pause, R.string.status_control_battery_start);
            }
            mNetworkControlView = (StatusControlView) findViewById(R.id.scv_view_status_control_network);
            mNetworkControlView.initData(R.string.status_control_network, R.string.status_control_network_close, R.string.status_control_network_start);
            mBluetoothControlView = (StatusControlView) findViewById(R.id.scv_view_status_control_bluetooth);
            mBluetoothControlView.initData(R.string.status_control_bluetooth, R.string.status_control_bluetooth_close, R.string.status_control_bluetooth_start);
            mResetButton = (Button) findViewById(R.id.btn_view_status_control_collector);
        }

        private BlueToothSeriveProvider.OnBluetoothCompletedListener mMeasuringListener = new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                ToastUtil.showToast(mContext, "设置成功");
            }
        };

        private BlueToothSeriveProvider.OnBluetoothCompletedListener mBatteryListener = new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                ToastUtil.showToast(mContext, "设置成功");
            }
        };

        private BlueToothSeriveProvider.OnBluetoothCompletedListener mNetworkListener = new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                ToastUtil.showToast(mContext, "设置成功");
            }
        };

        private BlueToothSeriveProvider.OnBluetoothCompletedListener mBluetoothListener = new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                ToastUtil.showToast(mContext, "设置成功");
            }
        };

        private BlueToothSeriveProvider.OnBluetoothCompletedListener mResetListener = new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
            @Override
            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                ToastUtil.showToast(mContext, "设置成功");
            }
        };

        private void initListener() {
            mMeasuringControlView.setListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final EditText editText = new EditText(mContext);
                    editText.setText("1");
                    editText.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                BigNumberPickerDialog.getBigNumberPickerDialog(mContext, 2, 1, 60, Integer.parseInt(editText.getText().toString()), editText, getResources().getString(R.string.status_control_measuring_tip)).show();
                            }
                            return true;
                        }
                    });
                    new AlertDialog.Builder(mContext).setTitle(R.string.status_control_measuring_tip).setView(editText).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doSetMeasuringStatus(mContext, Integer.parseInt(editText.getText().toString()), mMeasuringListener);
                        }
                    }).setNegativeButton(R.string.dialog_cancel, null).show();
                }
            }, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlueToothSeriveProvider.doSetMeasuringStatus(mContext, 0, mMeasuringListener);
                }
            });
            for (int ii = 0; ii < mBatteryControlViews.length; ii++) {
                final String subject = Battery.SUBJECTS[ii];
                mBatteryControlViews[ii].setListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BlueToothSeriveProvider.doSetBatteryStatus(mContext, subject, 0, mBatteryListener);
                    }
                }, new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText editText = new EditText(mContext);
                        editText.setText("1");
                        editText.setOnTouchListener(new OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (event.getAction() == MotionEvent.ACTION_UP) {
                                    BigNumberPickerDialog.getBigNumberPickerDialog(mContext, 10, 1, 2147483647, Integer.parseInt(editText.getText().toString()), editText, getResources().getString(R.string.status_control_battery_tip)).show();
                                }
                                return true;
                            }
                        });
                        new AlertDialog.Builder(mContext).setTitle(R.string.status_control_battery_tip).setView(editText).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BlueToothSeriveProvider.doSetBatteryStatus(mContext, subject, Integer.parseInt(editText.getText().toString()), mBatteryListener);
                            }
                        }).setNegativeButton(R.string.dialog_cancel, null).show();
                    }
                });
            }
            mNetworkControlView.setListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext).setTitle(R.string.status_control_network_close_tip).setPositiveButton(R.string.status_control_network_close_power, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doSetNetworkStatus(mContext, false, true, mNetworkListener);
                        }
                    }).setNeutralButton(R.string.status_control_network_close_tcp, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doSetNetworkStatus(mContext, false, false, mNetworkListener);
                        }
                    }).setNegativeButton(R.string.dialog_cancel, null).show();
                }
            }, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext).setTitle(R.string.status_control_network_start_tip).setPositiveButton(R.string.status_control_network_start_normal, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doSetNetworkStatus(mContext, true, true, mNetworkListener);
                        }
                    }).setNeutralButton(R.string.status_control_network_start_debug, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doSetNetworkStatus(mContext, true, false, mNetworkListener);
                        }
                    }).setNegativeButton(R.string.dialog_cancel, null).show();
                }
            });
            mBluetoothControlView.setListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlueToothSeriveProvider.doSetBluetoothStatus(mContext, false, mBluetoothListener);
                }
            }, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlueToothSeriveProvider.doSetBluetoothStatus(mContext, true, mBluetoothListener);
                }
            });

            mResetButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext).setTitle(R.string.status_control_collector_reset_tip).setPositiveButton(R.string.status_control_collector_reset_cold, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doReset(mContext, true, mResetListener);
                        }
                    }).setNeutralButton(R.string.status_control_collector_reset_warm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            BlueToothSeriveProvider.doReset(mContext, false, mResetListener);
                        }
                    }).setNegativeButton(R.string.dialog_cancel, null).show();
                }
            });

        }


    }
}
