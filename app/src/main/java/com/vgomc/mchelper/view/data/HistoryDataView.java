package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.data.HistoryAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.transmit.file.FileServiceProvider;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.NoScrollListView;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HistoryDataView extends BaseCollapsibleView {

    public static final String[] COLUMN_NAME = {"实时值", "平均值", "最大值", "最小值", "时段积累值", "永久积累值", "最大值时间", "最小值时间"};
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
                final boolean[] columnList = new boolean[COLUMN_NAME.length];
                for (int i = 0; i < columnList.length; i++) {
                    columnList[i] = false;
                }
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).setTitle(R.string.data_history_download_tip)
                        .setMultiChoiceItems(COLUMN_NAME, null, null)
                        .setPositiveButton(R.string.data_history_download_all, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                BlueToothSeriveProvider.doDownload(mContext, true, columnList, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
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
                                BlueToothSeriveProvider.doDownload(mContext, false, columnList, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                    @Override
                                    public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                        ToastUtil.showToast(mContext, R.string.data_history_download_success);
                                        mContentView.updateHistoryData();
                                    }
                                });
                            }
                        }).setNegativeButton(R.string.dialog_cancel, null).show();
                alertDialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (i == 2 && !alertDialog.getListView().isItemChecked(i)) {
                            alertDialog.getListView().setItemChecked(6, false);
                            columnList[6] = false;
                        }
                        if (i == 6 && alertDialog.getListView().isItemChecked(i)) {
                            alertDialog.getListView().setItemChecked(2, true);
                            columnList[2] = true;
                        }
                        if (i == 3 && !alertDialog.getListView().isItemChecked(i)) {
                            alertDialog.getListView().setItemChecked(7, false);
                            columnList[7] = false;
                        }
                        if (i == 7 && alertDialog.getListView().isItemChecked(i)) {
                            alertDialog.getListView().setItemChecked(3, true);
                            columnList[3] = true;
                        }
                        columnList[i] = alertDialog.getListView().isItemChecked(i);
                    }
                });
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
            mContentListView.setOnItemLongClickListener(new NoScrollListView.OnNoScrollItemLongClcikListener() {
                @Override
                public void onItemClick(View v, final Object item, int position, long id) {
                    new AlertDialog.Builder(mContext).setItems(new String[]{"发送", "删除"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final File file = (File) item;
                            if (i == 0) {
                                Intent share = new Intent(Intent.ACTION_SEND);
                                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                                share.setType("*/*");//此处可发送多种文件
                                mContext.startActivity(Intent.createChooser(share, "Share"));
                            } else if (i == 1) {
                                new AlertDialog.Builder(mContext)
                                        .setTitle("确认删除：" + file.getName())
                                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                boolean result = file.delete();
                                                if(result){
                                                    updateHistoryData();
                                                }else{
                                                    ToastUtil.showToast(mContext, "删除文件失败！");
                                                }
                                            }
                                        }).setNegativeButton("取消", null).show();
                            }
                        }
                    }).show();
                }
            });
        }

        public void updateHistoryData() {
            File file = new File(FileServiceProvider.getExternalRecordPath(mContext));
            File[] recordFiles = file.listFiles();
            List<Object> records = new ArrayList<>();
            if (recordFiles != null) {
                for (File recordFile : recordFiles) {
                    if (recordFile.isFile() && recordFile.getName().endsWith(".csv")) {
                        records.add(recordFile);
                    } else if (recordFile.isDirectory()) {
                        File[] childFiles = recordFile.listFiles();
                        for (File childFile : childFiles) {
                            if (childFile.isFile() && childFile.getName().endsWith(".csv")) {
                                records.add(childFile);
                            }
                        }
                    }
                }
            }
            mHistoryAdapter.setList(records);
        }

        @Override
        protected void updateData() {

        }
    }
}
