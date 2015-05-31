package com.vgomc.mchelper.view.status;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class SystemView extends BaseCollapsibleView {
    public SystemView(Context context) {
        super(context);
        setTitle(R.string.status_system);
        setContentView(new SystemContentView(mContext));
    }

    class SystemContentView extends BaseCollapsibleContentView {

        private TextView mVersionTextView;
        private TextView mSerialTextView;
        private TextView mTimeTextView;
        private Button mSynchronizeButton;

        public SystemContentView(Context context) {
            super(context);

        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_system;
        }

        @Override
        protected void updateData() {

        }

        private void initView() {
            mVersionTextView = (TextView) findViewById(R.id.tv_view_status_system_version);
            mSerialTextView = (TextView) findViewById(R.id.tv_view_status_system_serial);
            mTimeTextView = (TextView) findViewById(R.id.tv_view_status_system_time);
            mSynchronizeButton = (Button) findViewById(R.id.btn_view_status_system_synchronize);
        }

        private void intListener() {

        }
    }
}
