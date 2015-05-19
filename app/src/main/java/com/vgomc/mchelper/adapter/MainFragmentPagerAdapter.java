package com.vgomc.mchelper.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.fragment.DataFragment;
import com.vgomc.mchelper.fragment.SettingFragment;
import com.vgomc.mchelper.fragment.StatusFragment;
import com.vgomc.mchelper.fragment.SystemFragment;

import org.holoeverywhere.app.Fragment;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int MAX_COUNT = 4;

    private Fragment[] mFragmentArray;
    private Context mContext;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        mFragmentArray = new Fragment[MAX_COUNT];
        mFragmentArray[0] = new SettingFragment();
        mFragmentArray[1] = new StatusFragment();
        mFragmentArray[2] = new DataFragment();
        mFragmentArray[3] = new SystemFragment();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArray[position];
    }

    @Override
    public int getCount() {
        return mFragmentArray.length;
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