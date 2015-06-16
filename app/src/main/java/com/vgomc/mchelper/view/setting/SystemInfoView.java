package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;

import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.MyBigNumberPicker;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/20/2015.
 */
public class SystemInfoView extends BaseCollapsibleView {

    public SystemInfoView(Context context) {
        super(context);
        setContentView(new SystemInfoContentView(context));
        setTitle(R.string.setting_system_info_title);
    }


    public class SystemInfoContentView extends BaseCollapsibleContentView {
        private EditText mNameEditText;
        private EditText mPasswordEditText;
        private TextView mTimeZoneTextView;
        private EditText mBluetoothTimeEditText;
        private TextView mBluetoothTimeUnitTextView;
        private Switch mBluetoothTimeSwitch;

        public SystemInfoContentView(Context context) {
            super(context);
            initView();
            updateData();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_system_info;
        }

        @Override
        protected void updateData() {
            mNameEditText.setText(Configuration.getInstance().name);
            mPasswordEditText.setText(Configuration.getInstance().password);
            mTimeZoneTextView.setText(Configuration.getInstance().timeZone + "");
            mBluetoothTimeEditText.setText(Configuration.getInstance().bluetoothTime + "");
            mBluetoothTimeSwitch.setChecked(Configuration.getInstance().bluetoothTimeOn);
            if (Configuration.getInstance().bluetoothTimeOn) {
                showBluetooth();
            } else {
                hideBluetooth();
            }
        }

        private void initView() {
            mNameEditText = (EditText) findViewById(R.id.et_setting_system_info_name);
            mPasswordEditText = (EditText) findViewById(R.id.et_setting_system_info_password);
            mTimeZoneTextView = (TextView) findViewById(R.id.tv_setting_system_info_zone);
            mBluetoothTimeEditText = (EditText) findViewById(R.id.et_setting_system_info_bluetooth_time);
            mBluetoothTimeUnitTextView = (TextView) findViewById(R.id.tv_setting_system_info_bluetooth_time_unit);
            mBluetoothTimeSwitch = (Switch) findViewById(R.id.sw_setting_system_info_bluetooth_time);
            mBluetoothTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        showBluetooth();
                    } else {
                        hideBluetooth();
                    }
                }
            });
            mBluetoothTimeEditText.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        final MyBigNumberPicker numberPicker = new MyBigNumberPicker(mContext, 1440, 1, 4, Configuration.getInstance().bluetoothTime);
                        new AlertDialog.Builder(mContext).setTitle(R.string.setting_system_info_content_bluetooth_time).setView(numberPicker)
                                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int time = numberPicker.getValue();
                                        mBluetoothTimeEditText.setText(time + "");
                                        Configuration.getInstance().bluetoothTime = time;
                                    }
                                }).create().show();
                    }
                    return true;
                }
            });
            mNameEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Configuration.getInstance().name = s.toString();
                }
            });
            mPasswordEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    Configuration.getInstance().password = s.toString();
                }
            });
        }

        private void hideBluetooth() {
            mBluetoothTimeEditText.setVisibility(View.GONE);
            mBluetoothTimeUnitTextView.setVisibility(View.GONE);
            Configuration.getInstance().bluetoothTimeOn = false;
            Configuration.getInstance().bluetoothTime = 0;
        }

        private void showBluetooth() {
            mBluetoothTimeEditText.setVisibility(View.VISIBLE);
            mBluetoothTimeUnitTextView.setVisibility(View.VISIBLE);
            Configuration.getInstance().bluetoothTimeOn = true;
            Configuration.getInstance().bluetoothTime = Integer.parseInt(mBluetoothTimeEditText.getText().toString());
        }
    }
}
