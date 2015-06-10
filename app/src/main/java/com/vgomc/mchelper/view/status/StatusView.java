package com.vgomc.mchelper.view.status;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class StatusView extends BaseCollapsibleView {
    public StatusView(Context context) {
        super(context);
        setTitle(R.string.status_status);
        setContentView(new StatusContentView(mContext));
    }

    public void setData(float[] batterys, String flagRepresentation, String gps) {
        ((StatusContentView) mContentView).setData(batterys, flagRepresentation, gps);
    }

    static class StatusContentView extends BaseCollapsibleContentView {

        private TextView mStatusTextView;
        private TextView mErrorCodeTextView;
        private TextView mLocationTextView;

        public StatusContentView(Context context) {
            super(context);
            initView();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_status;
        }

        @Override
        protected void updateData() {

        }

        public void setData(float[] batterys, String flagRepresentation, String gps) {
            mStatusTextView.setText(batterys[0] + "V , " + batterys[1] + "V , " + batterys[2] + "V");
            mErrorCodeTextView.setText(flagRepresentation);
            mLocationTextView.setText(gps);
        }

        private void initView() {
            mStatusTextView = (TextView) findViewById(R.id.tv_view_status_status_status);
            mErrorCodeTextView = (TextView) findViewById(R.id.tv_view_status_status_error_code);
            mLocationTextView = (TextView) findViewById(R.id.tv_view_status_status_location);
        }
    }
}
