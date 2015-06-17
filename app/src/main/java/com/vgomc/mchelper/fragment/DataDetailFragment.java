package com.vgomc.mchelper.fragment;

import android.content.DialogInterface;
import android.view.View;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.bluetooth.inquiry.CurrentDataEntity;
import com.vgomc.mchelper.Entity.data.VariableData;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.data.DataDetailAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseListFragment;
import com.vgomc.mchelper.transmit.bluetooth.BlueToothSeriveProvider;
import com.vgomc.mchelper.utility.ToastUtil;
import com.vgomc.mchelper.view.data.CurrentDataView;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class DataDetailFragment extends BaseListFragment {

    public static final String KEY_FIRST = "first";
    public static final String KEY_LAST = "last";
    public static final String KEY_POSITION = "position";
    private DataDetailAdapter adapter = null;

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        boolean isFirst = getArguments().getBoolean(KEY_FIRST, false);
        boolean isLast = getArguments().getBoolean(KEY_LAST, false);
        final int position = getArguments().getInt(KEY_POSITION, 0);
        adapter = new DataDetailAdapter(mContext, isFirst, isLast, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(mContext);
                editText.setText("" + 0);
                new AlertDialog.Builder(mContext).setTitle("请设置积累值").setView(editText).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        float value = 0.0f;
                        try {
                            value = Float.parseFloat(editText.getText().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.showToast(mContext, "输入非法，请重试");
                        }
                        VariableData variableData = (VariableData) CurrentDataView.mVariableDataLists[0].get(position);
                        final int varId = Integer.parseInt(variableData.name.split("-")[0]);
                        BlueToothSeriveProvider.doSetAccumulate(mContext, varId, value, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                            @Override
                            public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                BlueToothSeriveProvider.doGetCurrentData(mContext, varId, new BlueToothSeriveProvider.OnBluetoothCompletedListener() {
                                    @Override
                                    public void onCompleted(List<BaseBluetoothEntity> bluetoothEntities) {
                                        List<Object> newData = new ArrayList<>();
                                        for (int ii = 0; ii < CurrentDataView.mVariableDataLists.length; ii++) {
                                            VariableData variableData1 = ((CurrentDataEntity) bluetoothEntities.get(ii)).variableDataList.get(0);
                                            newData.add(variableData1);
                                            CurrentDataView.mVariableDataLists[ii].set(position, variableData1);
                                        }
                                        adapter.setList(newData);
                                    }
                                });
                            }
                        });
                    }
                }).setNegativeButton(R.string.dialog_cancel, null).show();
            }
        });
        List<Object> variableDatas = new ArrayList<>();
        for (int jj = 0; jj < CurrentDataView.mVariableDataLists.length; jj++) {
            variableDatas.add(CurrentDataView.mVariableDataLists[jj].get(position));
        }
        adapter.setList(variableDatas);
        return adapter;
    }
}
