package com.vgomc.mchelper.view.setting;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.vgomc.mchelper.entity.setting.Configuration;
import com.vgomc.mchelper.entity.setting.Storage;
import com.vgomc.mchelper.R;
import com.vgomc.mchelper.adapter.setting.StorageChannelAdapter;
import com.vgomc.mchelper.base.BaseCollapsibleContentView;
import com.vgomc.mchelper.base.BaseCollapsibleView;
import com.vgomc.mchelper.widget.NoScrollListView;
import com.vgomc.mchelper.widget.StorageEditView;

import org.holoeverywhere.app.AlertDialog;

/**
 * Created by weizhouh on 5/21/2015.
 */
public class StorageView extends BaseCollapsibleView {
    public StorageView(Context context) {
        super(context);
        setTitle(R.string.setting_storage);
        setContentView(new StorageContentView(context));
    }

    private class StorageContentView extends BaseCollapsibleContentView {

        private NoScrollListView mChannelListView;
        private StorageChannelAdapter mStorageChannelAdapter;

        public StorageContentView(Context context) {
            super(context);
            initView();
            initListener();
        }

        @Override
        protected int getLayoutResId() {
            return R.layout.view_setting_storage;
        }

        @Override
        protected void updateData() {
            mStorageChannelAdapter.setList(Configuration.getInstance().storageList);
            mStorageChannelAdapter.notifyDataSetChanged();
        }

        private void initView() {
            mChannelListView = (NoScrollListView) findViewById(R.id.nslv_setting_storage);
            mStorageChannelAdapter = new StorageChannelAdapter(mContext);
            mChannelListView.setAdapter(mStorageChannelAdapter);
            mStorageChannelAdapter.setList(Configuration.getInstance().storageList);
        }

        private void initListener() {
            mChannelListView.setOnItemClickListener(new NoScrollListView.OnNoScrollItemClickListener() {
                @Override
                public void onItemClick(View v, Object item, final int position, long id) {
                    final StorageEditView editView = new StorageEditView(mContext, position);
                    new AlertDialog.Builder(mContext).setView(editView).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Storage storage = editView.getStorage();
                            Configuration.getInstance().storageList.set(position, storage);
                            mStorageChannelAdapter.setList(Configuration.getInstance().storageList);
                        }
                    }).create().show();
                }
            });
        }
    }
}
