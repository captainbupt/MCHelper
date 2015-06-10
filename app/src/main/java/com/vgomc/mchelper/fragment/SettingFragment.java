package com.vgomc.mchelper.fragment;

import android.os.Bundle;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.ListView;

import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.SettingFragmentAdapter;
import com.vgomc.mchelper.base.BaseCollapseAdapter;
import com.vgomc.mchelper.base.BaseFragment;
import com.vgomc.mchelper.base.BaseListFragment;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class SettingFragment extends BaseListFragment {

    @Override
    protected BaseCollapseAdapter getBaseCollapseAdapter() {
        return new SettingFragmentAdapter(mContext);
    }

    public void updateData() {
        mFragmentAdapter.updateData();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateData();
    }


}
