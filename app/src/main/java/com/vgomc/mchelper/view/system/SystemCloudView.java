package com.vgomc.mchelper.view.system;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.vgomc.mchelper.entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.entity.bluetooth.inquiry.DeviceParameterEntity;
import com.vgomc.mchelper.entity.system.CloudData;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.system.CloudAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.widget.NoScrollListView;

import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.LinearLayout;

import java.util.List;

/**
 * Created by weizhouh on 6/22/2015.
 */
public class SystemCloudView extends BaseCollapsibleView {

    private Button mRefreshButton;
    private SystemCloudContentView mContentView;
    private ProgressDialog mProgressDialog;

    public SystemCloudView(Context context) {
        super(context);
        setTitle(R.string.system_operation_cloud);
        mContentView = new SystemCloudContentView(mContext);
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
        mRefreshButton.setText("刷新");
        addTitleView(mRefreshButton);
    }

    private void initListener() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle("加载中..");
        mProgressDialog.setCancelable(false);
        mRefreshButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BlueToothSeriveProvider.doGetDeviceInfo(mContext, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                    @Override
                    public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                        String serial = ((DeviceParameterEntity) bluetoothEntities.get(0)).uid;
                        System.out.println(serial);
                        HttpUtils http = new HttpUtils();
                        http.send(HttpRequest.HttpMethod.GET,
                                "http://api.vgomc.com/dataquery/deviceVariableData/getLastupdatedData/" + serial,
                                new RequestCallBack<String>() {

                                    @Override
                                    public void onSuccess(ResponseInfo<String> responseInfo) {
                                        mProgressDialog.dismiss();
                                        CloudData cloudData = new CloudData(responseInfo.result);
                                        if (!cloudData.code.equals("01")) {
                                            ToastUtil.showToast(mContext, cloudData.messages);
                                        } else {
                                            mContentView.setData(cloudData.result.dataValues);
                                        }
                                    }

                                    @Override
                                    public void onStart() {
                                        mProgressDialog.show();
                                    }

                                    @Override
                                    public void onFailure(HttpException error, String msg) {
                                        mProgressDialog.dismiss();
                                        ToastUtil.showToast(mContext, msg);
                                    }
                                });
                    }
                });
            }
        });
    }

    class SystemCloudContentView extends BaseCollapsibleContentView {

        NoScrollListView mListView;
        CloudAdapter mAdapter;

        public SystemCloudContentView(Context context) {
            super(context);
            mListView = (NoScrollListView) findViewById(R.id.nslv_system_cloud);
            mAdapter = new CloudAdapter(mContext);
            mListView.setAdapter(mAdapter);
        }

        public void setData(List<Object> data) {
            mAdapter.setList(data);
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_system_cloud;
        }

        @Override
        protected void updateData() {

        }
    }
}
