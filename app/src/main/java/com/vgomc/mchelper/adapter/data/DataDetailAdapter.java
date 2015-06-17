package com.vgomc.mchelper.adapter.data;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.Entity.data.VariableData;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.MyBaseAdapter;
import com.vgomc.mchelper.view.data.DataDetailTitleView;
import com.vgomc.mchelper.view.data.DateDetailTableView;

import java.util.ArrayList;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class DataDetailAdapter extends BaseCollapseAdapter {

    private boolean isFirst;
    private boolean isLast;
    private View.OnClickListener setAccumulateListener;

    public DataDetailAdapter(Context context, boolean isFirst, boolean isLast, View.OnClickListener setAccumulateListener) {
        super(context);
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.setAccumulateListener = setAccumulateListener;
    }

    @Override
    public void updateData() {

    }

    @Override
    public int getCount() {
        return super.getCount() + 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            VariableData variableData = (VariableData) getItem(1);
            return new DataDetailTitleView(mContext, isFirst, isLast, variableData.name);
        } else {
            VariableData variableData = (VariableData) getItem(position - 1);
            return new DateDetailTableView(mContext, position, variableData, setAccumulateListener);
        }
    }
}
