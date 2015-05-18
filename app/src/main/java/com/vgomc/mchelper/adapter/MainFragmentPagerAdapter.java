package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.factory.MainFragmentFactory;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {

    private MainFragmentFactory mFactory;
    private Context mContext;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context, MainFragmentFactory factory) {
        super(fm);
        this.mContext = context;
        this.mFactory = factory;
    }

    @Override
    public Fragment getItem(int position) {
        return mFactory.getFragmentByPosition(position);
    }

    @Override
    public int getCount() {
        return MainFragmentFactory.MAX_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.tab_title_setting);
            case 1:
                return mContext.getString(R.string.tab_title_status);
            case 2:
                return mContext.getString(R.string.tab_title_data);
            case 3:
                return mContext.getString(R.string.tab_title_system);
        }
        return null;
    }
}