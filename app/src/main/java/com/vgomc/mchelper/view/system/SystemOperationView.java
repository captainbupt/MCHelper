package com.vgomc.mchelper.view.system;

import android.content.Context;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;

/**
 * Created by weizhouh on 6/4/2015.
 */
public class SystemOperationView extends BaseCollapsibleView {
    public SystemOperationView(Context context) {
        super(context);
        setTitle(R.string.system_operation);
        setContentView(new SystemOperationContentView(mContext));
    }

    class SystemOperationContentView extends BaseCollapsibleContentView {


        public SystemOperationContentView(Context context) {
            super(context);

        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_system_operation;
        }

        @Override
        protected void updateData() {

        }
    }
}
