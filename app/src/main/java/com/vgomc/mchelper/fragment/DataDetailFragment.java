package com.vgomc.mchelper.fragment;

import com.vgomc.mchelper.Entity.data.VariableData;
import com.vgomc.mchelper.adapter.data.DataDetailAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseListFragment;
import com.vgomc.mchelper.view.data.CurrentDataView;

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

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        boolean isFirst = getArguments().getBoolean(KEY_FIRST, false);
        boolean isLast = getArguments().getBoolean(KEY_LAST, false);
        int position = getArguments().getInt(KEY_POSITION, 0);
        DataDetailAdapter adapter = new DataDetailAdapter(mContext, isFirst, isLast);
        List<Object> variableDatas = new ArrayList<>();
        for (int jj = 0; jj < CurrentDataView.mVariableDataLists.length; jj++) {
            variableDatas.add(CurrentDataView.mVariableDataLists[jj].get(position));
        }
        adapter.setList(variableDatas);
        return adapter;
    }
}
