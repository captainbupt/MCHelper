package com.vgomc.mchelper.view.status;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.Switch;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class ControlView extends BaseCollapsibleView {
    public ControlView(Context context) {
        super(context);
        setTitle(R.string.status_control);
        setContentView(new SystemContentView(mContext));
    }

    class SystemContentView extends BaseCollapsibleContentView {

        private Switch mMeasuringSwitch;
        private Switch mNetworkSwitch;
        private Switch mBluetoothSwitch;
        private Button mCollectorButton;

        public SystemContentView(Context context) {
            super(context);

        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_control;
        }

        @Override
        protected void updateData() {

        }

        private void initView() {
            mMeasuringSwitch = (Switch) findViewById(R.id.sw_view_status_control_measuring);
            mNetworkSwitch = (Switch) findViewById(R.id.sw_view_status_control_network);
            mBluetoothSwitch = (Switch) findViewById(R.id.sw_view_status_control_bluetooth);
            mCollectorButton = (Button) findViewById(R.id.btn_view_status_control_collector);
        }

        private void intListener() {

        }
    }
}
