package com.vgomc.mchelper.fragment;

import com.vgomc.mchelper.adapter.DataFragmentAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseListFragment;


/**
 * Created by weizhouh on 5/18/2015.
 */
public class DataFragment extends BaseListFragment {

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        return new DataFragmentAdapter(mContext);
    }
}
