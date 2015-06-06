package com.vgomc.mchelper.fragment;

import com.vgomc.mchelper.adapter.data.DataDetailAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseListFragment;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class DataDetailFragment extends BaseListFragment {

    public static final String KEY_FIRST = "first";
    public static final String KEY_LAST = "last";

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        boolean isFirst = getArguments().getBoolean(KEY_FIRST, false);
        boolean isLast = getArguments().getBoolean(KEY_LAST, false);
        return new DataDetailAdapter(mContext, isFirst, isLast);
    }
}
