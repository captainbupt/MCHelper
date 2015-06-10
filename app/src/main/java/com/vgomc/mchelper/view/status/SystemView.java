package com.vgomc.mchelper.view.status;

import android.content.Context;
import android.view.View;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.utility.TimeUtil;

import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class SystemView extends BaseCollapsibleView {
    public SystemView(Context context, OnClickListener synchronizeListener) {
        super(context);
        setTitle(R.string.status_system);
        setContentView(new SystemContentView(mContext, synchronizeListener));
    }

    public void setData(String version, String serial, long time) {
        ((SystemContentView) mContentView).setData(version, serial, time);
    }

    static class SystemContentView extends BaseCollapsibleContentView {

        private TextView mVersionTextView;
        private TextView mSerialTextView;
        private TextView mTimeTextView;
        private Button mSynchronizeButton;

        public SystemContentView(Context context, OnClickListener synchronizeListener) {
            super(context);
            initView(synchronizeListener);
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

        private void initView(OnClickListener synchronizeListener) {
            mVersionTextView = (TextView) findViewById(R.id.tv_view_status_system_version);
            mSerialTextView = (TextView) findViewById(R.id.tv_view_status_system_serial);
            mTimeTextView = (TextView) findViewById(R.id.tv_view_status_system_time);
            mSynchronizeButton = (Button) findViewById(R.id.btn_view_status_system_synchronize);
            mSynchronizeButton.setOnClickListener(synchronizeListener);
        }

    }
}
