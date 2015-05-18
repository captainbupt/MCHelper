package com.vgomc.mchelper.factory;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.vgomc.mchelper.fragment.DataFragment;
import com.vgomc.mchelper.fragment.SettingFragment;
import com.vgomc.mchelper.fragment.StatusFragment;
import com.vgomc.mchelper.fragment.SystemFragment;

/**
 * Created by weizhouh on 5/18/2015.
 */
public class MainFragmentFactory {
    public static final int MAX_COUNT = 4;
    private Context mContext;
    private Fragment mFragmentArray[];

    public MainFragmentFactory(Context context){
        this.mContext = context;
        mFragmentArray = new Fragment[MAX_COUNT];
        mFragmentArray[0] = new SettingFragment();
        mFragmentArray[1] = new StatusFragment();
        mFragmentArray[2] = new DataFragment();
        mFragmentArray[3] = new SystemFragment();
    }

    public Fragment getFragmentByPosition(int position){
        if(position<0||position>=mFragmentArray.length){
            return null;
        }
        return mFragmentArray[position];
    }

}
