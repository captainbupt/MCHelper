package com.vgomc.mchelper.fragment;

import android.os.Bundle;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.ListView;

import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseFragment;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class SettingFragment extends BaseFragment {

    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        mListView = (ListView) rootView.findViewById(R.id.ll_fragment_setting);

        return rootView;
    }
}
