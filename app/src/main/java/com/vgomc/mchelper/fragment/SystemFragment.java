package com.vgomc.mchelper.fragment;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.SystemFragmentAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseFragment;
import com.vgomc.mchelper.base.BaseListFragment;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class SystemFragment extends BaseListFragment {

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        return new SystemFragmentAdapter(mContext);
    }

    public void updateDate() {
        if (mFragmentAdapter != null) {
            mFragmentAdapter.updateData();
        }
    }
}
