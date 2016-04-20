package com.vgomc.mchelper.view.status;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.utility.TimeUtil;
import com.vgomc.mchelper.utility.ToastUtil;

import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class SystemView extends BaseCollapsibleView {
    public SystemView(Context context, OnClickListener synchronizeListener) {
        super(context);
        setTitle(R.string.status_system);
        Button button = new Button(context);
        button.setText("刷新");
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_medium));
        button.setOnClickListener(synchronizeListener);
        addTitleView(button);
        setContentView(new SystemContentView(mContext));
    }

    public void setData(String version, String serial, long time) {
        ((SystemContentView) mContentView).setData(version, serial, time);
    }

    static class SystemContentView extends BaseCollapsibleContentView {

        private TextView mVersionTextView;
        private TextView mSerialTextView;
        private TextView mTimeTextView;
        private Button mSynchronizeButton;

        public SystemContentView(Context context) {
            super(context);
            initView();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_system;
        }

        @Override
        protected void updateData() {

        }

        public void setData(String version, String serial, long time) {
            mVersionTextView.setText(version);
            mSerialTextView.setText(serial);
            mTimeTextView.setText(TimeUtil.long2DeviceTime(time));
        }

        private void initView() {
            mVersionTextView = (TextView) findViewById(R.id.tv_view_status_system_version);
            mSerialTextView = (TextView) findViewById(R.id.tv_view_status_system_serial);
            mTimeTextView = (TextView) findViewById(R.id.tv_view_status_system_time);
            mSynchronizeButton = (Button) findViewById(R.id.btn_view_status_system_synchronize);
            mSynchronizeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlueToothSeriveProvider.doSetTime(mContext, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                        @Override
                        public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                            ToastUtil.showToast(mContext, "同步成功!");
                        }
                    });
                }
            });
        }

    }
}
