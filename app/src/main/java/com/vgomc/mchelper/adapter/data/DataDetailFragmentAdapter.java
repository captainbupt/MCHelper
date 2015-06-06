package com.vgomc.mchelper.adapter.data;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.vgomc.mchelper.Entity.data.VariableData;
import com.vgomc.mchelper.fragment.DataDetailFragment;

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

    private void setList(List<VariableData> dataList) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (DataDetailFragment fragment : mFragmentList) {
            transaction.remove(fragment);
        }
        transaction.commit();
        mFragmentList.clear();
        int ii = 0;
        for (VariableData variableData : dataList) {
            Bundle bundle = new Bundle();
            if (ii == 0) {
                bundle.putBoolean(DataDetailFragment.KEY_FIRST, true);
            } else if (ii == dataList.size() - 1) {
                bundle.putBoolean(DataDetailFragment.KEY_LAST, true);
            }
            DataDetailFragment fragment = new DataDetailFragment();
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
