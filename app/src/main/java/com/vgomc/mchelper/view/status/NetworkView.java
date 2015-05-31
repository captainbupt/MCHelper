package com.vgomc.mchelper.view.status;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class NetworkView extends BaseCollapsibleView {
    public NetworkView(Context context) {
        super(context);
        setTitle(R.string.status_network);
        setContentView(new SystemContentView(mContext));
    }

    class SystemContentView extends BaseCollapsibleContentView {

        private TextView mStatusTextView;
        private TextView mNameTextView;
        private TextView mStrengthTextView;
        private TextView mErrorRateTextView;
        private TextView mErrorFlagTextView;

        public SystemContentView(Context context) {
            super(context);

        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_status;
        }

        @Override
        protected void updateData() {

        }

        private void initView() {
            mStatusTextView = (TextView) findViewById(R.id.tv_view_status_network_status);
            mNameTextView = (TextView) findViewById(R.id.tv_view_status_network_name);
            mStrengthTextView = (TextView) findViewById(R.id.tv_view_status_network_strength);
            mErrorRateTextView = (TextView) findViewById(R.id.tv_view_status_network_error_rate);
            mErrorFlagTextView = (TextView) findViewById(R.id.tv_view_status_network_error_flag);
        }

        private void intListener() {

        }
    }
}
