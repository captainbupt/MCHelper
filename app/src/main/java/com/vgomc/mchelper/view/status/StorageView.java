package com.vgomc.mchelper.view.status;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.widget.ProgressBar;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 5/31/2015.
 */
public class StorageView extends BaseCollapsibleView {
    public StorageView(Context context) {
        super(context);
        setTitle(R.string.status_storage);
        setContentView(new SystemContentView(mContext));
    }

    class SystemContentView extends BaseCollapsibleContentView {

        private ProgressBar mMemoryProgressBar;
        private ProgressBar mSDCardProgressBar;
        private TextView mMemoryTextView;
        private TextView mSDCardTextView;

        public SystemContentView(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_status_storage;
        }

        @Override
        protected void updateData() {
            mMemoryProgressBar = (ProgressBar) findViewById(R.id.pb_view_status_storage_memory);
            mSDCardProgressBar = (ProgressBar) findViewById(R.id.pb_view_status_storage_sdcard);
            mMemoryTextView = (TextView) findViewById(R.id.tv_view_status_storage_memory);
            mSDCardTextView = (TextView) findViewById(R.id.tv_view_status_storage_sdcard);
        }

        private void initView() {

        }

        private void intListener() {

        }
    }
}
