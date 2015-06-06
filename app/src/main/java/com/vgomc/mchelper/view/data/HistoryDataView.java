package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.view.View;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.widget.TextView;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class HistoryDataView extends BaseCollapsibleView{
    public HistoryDataView(Context context) {
        super(context);
        setTitle(R.string.data_history);
        setContentView(new HistoryDataContentView(context));
    }

    class HistoryDataContentView extends BaseCollapsibleContentView{

        private TextView mDownloadTextView;
        private TextView mBackupTextView;
        private TextView mClearTextView;

        public HistoryDataContentView(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_history;
        }

        private void initView(){
            mDownloadTextView = (TextView) findViewById(R.id.tv_view_data_history_download);
            mBackupTextView = (TextView) findViewById(R.id.tv_view_data_history_memory);
            mClearTextView = (TextView) findViewById(R.id.tv_view_data_history_clear);
        }

        private void initListener(){
            mDownloadTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mBackupTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mClearTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        protected void updateData() {

        }
    }
}
