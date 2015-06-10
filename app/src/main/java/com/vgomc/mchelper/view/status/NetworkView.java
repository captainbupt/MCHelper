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
        setContentView(new NetworkContentView(mContext));
    }

    public void setData(String status, String network, int strength, int errorRate, String error, int onTime, int waitTime, int retryTime) {
        ((NetworkContentView) mContentView).setData(status, network, strength, errorRate, error, onTime, waitTime, retryTime);
    }

    class NetworkContentView extends BaseCollapsibleContentView {

        private TextView mStatusTextView;
        private TextView mNameTextView;
        private TextView mStrengthTextView;
        private TextView mErrorRateTextView;
        private TextView mErrorFlagTextView;
        private TextView mOnTimeTextView;
        private TextView mWaitTimeTextView;
        private TextView mRetryTimeTextView;

        public NetworkContentView(Context context) {
            super(context);
            initView();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_network;
        }

        @Override
        protected void updateData() {

        }

        public void setData(String status, String network, int strength, int errorRate, String error, int onTime, int waitTime, int retryTime) {
            mStatusTextView.setText(status);
            mNameTextView.setText(network);
            mStrengthTextView.setText(strength + "");
            mErrorRateTextView.setText(errorRate + "");
            mErrorFlagTextView.setText(error);
            mOnTimeTextView.setText(onTime + "秒");
            mWaitTimeTextView.setText(waitTime + "秒");
            mRetryTimeTextView.setText(retryTime + "");
        }

        private void initView() {
            mStatusTextView = (TextView) findViewById(R.id.tv_view_status_network_status);
            mNameTextView = (TextView) findViewById(R.id.tv_view_status_network_name);
            mStrengthTextView = (TextView) findViewById(R.id.tv_view_status_network_strength);
            mErrorRateTextView = (TextView) findViewById(R.id.tv_view_status_network_error_rate);
            mErrorFlagTextView = (TextView) findViewById(R.id.tv_view_status_network_error_flag);
            mOnTimeTextView = (TextView) findViewById(R.id.tv_view_status_network_on_time);
            mWaitTimeTextView = (TextView) findViewById(R.id.tv_view_status_network_wait_time);
            mRetryTimeTextView = (TextView) findViewById(R.id.tv_view_status_network_retry_time);
        }

    }
}
