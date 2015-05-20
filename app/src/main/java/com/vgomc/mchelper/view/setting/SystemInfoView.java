package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.LinearLayout;

/**
 * Created by weizhouh on 5/20/2015.
 */
public class SystemInfoView extends BaseCollapsibleView {

    public SystemInfoView(Context context) {
        super(context);
        initContentView(new SystemInfoContentView(context));
        setTitle(R.string.setting_system_info_title);
    }


    public class SystemInfoContentView extends BaseCollapsibleContentView {
        public SystemInfoContentView(Context context) {
            super(context);
        }

        @Override
        protected void setLayoutResource() {
            setLayoutParams(new AbsListView.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.view_setting_system_info, this);
        }
    }
}
