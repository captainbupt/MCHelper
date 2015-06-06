package com.vgomc.mchelper.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.DataFragmentAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseFragment;
import com.vgomc.mchelper.base.BaseListFragment;

import org.holoeverywhere.LayoutInflater;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class DataFragment extends BaseListFragment {

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        return new DataFragmentAdapter(mContext);
    }
}
