package com.vgomc.mchelper.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.ListView;

/**
 * Created by weizhouh on 5/18/2015.
 */
public abstract class BaseListFragment extends BaseFragment {

    protected ListView mListView;
    protected BaseCollapseAdapter mFragmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_collapse_list, container, false);
        mListView = (ListView) rootView.findViewById(R.id.lv_fragment_setting);
        mFragmentAdapter = getBaseCollapseAdapter();
        mListView.setAdapter(mFragmentAdapter);
        return rootView;
    }

    protected abstract BaseCollapseAdapter getBaseCollapseAdapter();
}
