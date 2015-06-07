package com.vgomc.mchelper.adapter.data;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.vgomc.mchelper.Entity.data.VariableData;
import com.vgomc.mchelper.activity.data.DataDetailActivity;
import com.vgomc.mchelper.fragment.DataDetailFragment;

import org.holoeverywhere.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weizhouh on 6/6/2015.
 */
public class DataDetailFragmentAdapter extends FragmentPagerAdapter {

    private List<DataDetailFragment> mFragmentList;
    private FragmentManager mFragmentManager;

    public DataDetailFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
        mFragmentList = new ArrayList<>();
    }

    public void setList(List[] dataLists) {
        for (int ii = 0; ii < dataLists[0].size(); ii++) {
            DataDetailFragment fragment;
            if (ii < mFragmentList.size()) {
                fragment = mFragmentList.get(ii);
            } else {
                fragment = new DataDetailFragment();
            }
            Bundle bundle = new Bundle();
            if (ii == 0) {
                bundle.putBoolean(DataDetailFragment.KEY_FIRST, true);
            } else if (ii == dataLists[0].size() - 1) {
                bundle.putBoolean(DataDetailFragment.KEY_LAST, true);
            }
            bundle.putSerializable(DataDetailFragment.KEY_POSITION, ii);
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        if (i < 0 || i >= mFragmentList.size())
            return null;
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
