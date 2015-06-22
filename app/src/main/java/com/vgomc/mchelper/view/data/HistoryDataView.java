package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.CurrentDataEntity;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.data.HistoryAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.NoScrollListView;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class HistoryDataView extends BaseCollapsibleView {

    private Button mRefreshButton;
    private HistoryDataContentView mContentView;

    public HistoryDataView(Context context) {
        super(context);
        setTitle(R.string.data_history);
        mContentView = new HistoryDataContentView(context);
        setContentView(mContentView);
        initView();
        initListener();
    }

    private void initView() {
        mRefreshButton = new Button(mContext);
        LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT;
        mRefreshButton.setLayoutParams(lp);
        mRefreshButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_size_medium));
        mRefreshButton.setText("下载");
        addTitleView(mRefreshButton);
    }

    private void initListener() {
        mRefreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext).setTitle(R.string.data_history_download_tip).setPositiveButton(R.string.data_history_download_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BlueToothSeriveProvider.doDownload(mContext, true, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                            @Override
                            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                ToastUtil.showToast(mContext, R.string.data_history_download_success);
                                mContentView.updateHistoryData();
                            }
                        });
                    }
                }).setNeutralButton(R.string.data_history_download_new, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BlueToothSeriveProvider.doDownload(mContext, false, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                            @Override
                            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                ToastUtil.showToast(mContext, R.string.data_history_download_success);
                                mContentView.updateHistoryData();
                            }
                        });
                    }
                }).setNegativeButton(R.string.dialog_cancel, null).show();
            }
        });
    }

    class HistoryDataContentView extends BaseCollapsibleContentView {

        private NoScrollListView mContentListView;
        private HistoryAdapter mHistoryAdapter;

        public HistoryDataContentView(Context context) {
            super(context);
            initView();
            initListener();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_history;
        }

        private void initView() {
            mContentListView = (NoScrollListView) findViewById(R.id.nslv_view_data_history);
            mHistoryAdapter = new HistoryAdapter(mContext);
            mContentListView.setAdapter(mHistoryAdapter);
            updateHistoryData();
        }

        private void initListener() {

        }

        public void updateHistoryData() {
            File file = new File(FileServiceProvider.getExternalRecordPath(mContext));
            File[] recordFiles = file.listFiles();
            List<Object> records = new ArrayList<>();
            for (int ii = 0; ii < recordFiles.length; ii++) {
                records.add(recordFiles[ii]);
            }
            mHistoryAdapter.setList(records);
        }

        @Override
        protected void updateData() {

        }
    }
}
