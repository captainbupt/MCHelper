package com.vgomc.mchelper.view.data;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.CurrentDataEntity;
import com.vgomc.mchelper.Entity.data.VariableData;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.activity.data.DataDetailActivity;
import com.vgomc.mchelper.adapter.data.CurrentDataAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.NoScrollListView;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.ListView;

import java.util.List;

/**
 * Created by weizhouh on 6/5/2015.
 */
public class CurrentDataView extends BaseCollapsibleView {

    public static List[] mVariableDataLists;

    private Button mRefreshButton;
    private CurrentDataContentView mContentView;

    public CurrentDataView(Context context) {
        super(context);
        setTitle(R.string.data_current);
        mContentView = new CurrentDataContentView(context);
        setContentView(mContentView);
        initView();
        initListener();
    }

    private void initView() {
        mRefreshButton = new Button(mContext);
        LinearLayout.LayoutParams lp = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT;
        mRefreshButton.setLayoutParams(lp);
        mRefreshButton.setText("刷新");
        addTitleView(mRefreshButton);
    }

    private void initListener() {
        mRefreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BlueToothSeriveProvider.doGetCurrentData(mContext, -1, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                    @Override
                    public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                        mVariableDataLists = new List[CurrentDataEntity.COUNT_TABLE];
                        for (int ii = 0; ii < CurrentDataEntity.COUNT_TABLE; ii++) {
                            mVariableDataLists[ii] = ((CurrentDataEntity) bluetoothEntities.get(ii)).variableDataList;
                        }
                        mContentView.setVariableData(mVariableDataLists);
                    }
                });
            }
        });
    }

    class CurrentDataContentView extends BaseCollapsibleContentView {

        private NoScrollListView mDataListView;
        private CurrentDataAdapter mDataAdapter;

        public CurrentDataContentView(Context context) {
            super(context);
            mDataListView = (NoScrollListView) findViewById(R.id.nslv_data_current);
            mDataAdapter = new CurrentDataAdapter(mContext);
            mDataListView.setAdapter(mDataAdapter);
            initListener();
        }

        public void setVariableData(List[] dataList) {
            mDataAdapter.setList(dataList[0]);
        }

        private void initListener() {
            mDataListView.setOnItemClickListener(new NoScrollListView.OnNoScrollItemClickListener() {
                @Override
                public void onItemClick(View v, Object item, int position, long id) {
                    Intent intent = new Intent(mContext, DataDetailActivity.class);
                    intent.putExtra(DataDetailActivity.KEY_POSITION, position);
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_data_current;
        }

        @Override
        protected void updateData() {

        }
    }


}
