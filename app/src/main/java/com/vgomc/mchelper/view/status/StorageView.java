package com.vgomc.mchelper.view.status;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class StorageView extends BaseCollapsibleView {
    public StorageView(Context context) {
        super(context);
        setTitle(R.string.status_storage);
        setContentView(new StorageContentView(mContext));
    }

    public void setData(float memoryUsed, float memoryTotal, float sdCardUsed, float sdCardTotal) {
        ((StorageContentView) mContentView).setData(memoryUsed, memoryTotal, sdCardUsed, sdCardTotal);
    }

    static class StorageContentView extends BaseCollapsibleContentView {

        private ProgressBar mMemoryProgressBar;
        private ProgressBar mSDCardProgressBar;
        private TextView mMemoryTextView;
        private TextView mSDCardTextView;

        public StorageContentView(Context context) {
            super(context);
            initView();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_storage;
        }

        @Override
        protected void updateData() {
        }

        public void setData(float memoryUsed, float memoryTotal, float sdCardUsed, float sdCardTotal) {
            mMemoryTextView.setText((memoryTotal - memoryUsed) + "KB可用，共" + memoryTotal + "KB");
            mSDCardTextView.setText((sdCardTotal - sdCardUsed) + "MB可用，共" + sdCardTotal + "MB");
            mMemoryProgressBar.setMax((int) memoryTotal);
            mMemoryProgressBar.setProgress((int) memoryUsed);
            mSDCardProgressBar.setMax((int) sdCardTotal);
            mSDCardProgressBar.setProgress((int) sdCardUsed);
        }

        private void initView() {
            mMemoryProgressBar = (ProgressBar) findViewById(R.id.pb_view_status_storage_memory);
            mSDCardProgressBar = (ProgressBar) findViewById(R.id.pb_view_status_storage_sdcard);
            mMemoryTextView = (TextView) findViewById(R.id.tv_view_status_storage_memory);
            mSDCardTextView = (TextView) findViewById(R.id.tv_view_status_storage_sdcard);

        }

    }
}
