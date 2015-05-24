package com.vgomc.mchelper.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseFragment;

import org.holoeverywhere.LayoutInflater;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class DataFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_data, container, false);
        return rootView;
    }

    @Override
    protected void updateData() {

    }
}
