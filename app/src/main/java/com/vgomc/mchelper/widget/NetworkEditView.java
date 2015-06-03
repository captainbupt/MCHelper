package com.vgomc.mchelper.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.vgomc.mchelper.Entity.setting.Configuration;
import com.vgomc.mchelper.Entity.setting.Network;
import com.vgomc.mchelper.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.RadioButton;

/**
 * Created by weizhouh on 5/29/2015.
 */

public class NetworkEditView extends LinearLayout {

    private Context mContext;
    private RadioGroup mModeRadioGroup;
    private RadioButton mModeAutoRadioButton;
    private RadioButton mModeAlwaysRadioButton;
    private TimeEditView mTimeEditView;
    private EditText mAddressEditText;
    private EditText mPortEditText;
    private LinearLayout mContentLayout;

    private Network mNetwork;

    public NetworkEditView(Context context) {
        super(context);
        this.mContext = context;
        this.mNetwork = Configuration.getInstance().network;
        setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_setting_network_edit, this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        mModeRadioGroup = (RadioGroup) findViewById(R.id.rg_view_setting_network_edit_mode);
        mModeAutoRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_network_edit_mode_auto);
        mModeAlwaysRadioButton = (RadioButton) findViewById(R.id.rb_view_setting_network_edit_mode_always);
        mTimeEditView = (TimeEditView) findViewById(R.id.tev_view_setting_network_edit_time);
        mContentLayout = (LinearLayout) findViewById(R.id.ll_view_setting_network_edit_content);
        mAddressEditText = (EditText) findViewById(R.id.et_view_setting_network_edit_address);
        mPortEditText = (EditText) findViewById(R.id.et_view_setting_network_edit_port);
    }

    private void initListener() {
        mModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_view_setting_network_edit_mode_auto) {
                    mContentLayout.setVisibility(View.VISIBLE);
                } else {
                    mContentLayout.setVisibility(View.GONE);
                }
            }
        });

        mTimeEditView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final MyTimePickerView view = new MyTimePickerView(mContext, MyTimePickerView.MODE_HMS, mTimeEditView.getTime(), 32767000, 1);
                new AlertDialog.Builder(mContext).setTitle(R.string.setting_network_time).setView(view).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long time = view.getTime();
                        mTimeEditView.setTime(time);
                    }
                }).create().show();
                return true;
            }
        });
        mPortEditText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final MyBigNumberPicker numberPicker = new MyBigNumberPicker(mContext, 65535, 1, 5, Integer.parseInt(mPortEditText.getText().toString()));
                    new AlertDialog.Builder(mContext).setTitle(R.string.setting_network_port).setView(numberPicker).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int port = numberPicker.getValue();
                            mPortEditText.setText(port + "");
                        }
                    }).create().show();
                }
                return true;
            }
        });
    }

    private void initData() {
        if (mNetwork.isAlwaysOn) {
            mModeAlwaysRadioButton.setChecked(true);
            mContentLayout.setVisibility(View.GONE);
        } else {
            mModeAutoRadioButton.setChecked(true);
            mContentLayout.setVisibility(View.VISIBLE);
        }
        mTimeEditView.setTime(mNetwork.time);
        mTimeEditView.setMode(MyTimePickerView.MODE_HMS);
        mAddressEditText.setText(mNetwork.address);
        mPortEditText.setText(mNetwork.port + "");
    }

    public Network getNetwork() {
        mNetwork.isAlwaysOn = mModeRadioGroup.getCheckedRadioButtonId() == R.id.rb_view_setting_network_edit_mode_always;
        mNetwork.time = mTimeEditView.getTime();
        mNetwork.address = mAddressEditText.getText().toString();
        mNetwork.port = Integer.parseInt(mPortEditText.getText().toString());
        return mNetwork;
    }

}
