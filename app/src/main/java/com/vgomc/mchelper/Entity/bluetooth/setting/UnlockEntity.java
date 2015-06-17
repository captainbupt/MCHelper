package com.vgomc.mchelper.Entity.bluetooth.setting;

import android.content.Context;
import android.content.DialogInterface;

import com.vgomc.mchelper.Entity.bluetooth.BaseBluetoothEntity;
import com.vgomc.mchelper.Entity.setting.Channel;
import com.vgomc.mchelper.R;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.EditText;

/**
 * Created by weizhouh on 6/7/2015.
 */
public class UnlockEntity extends BaseBluetoothSettingEntity {

    public static String password = "";

    public interface OnPasswordConfirmListener {
        void onPasswordConfirm();
    }

    public UnlockEntity(Context context, final OnPasswordConfirmListener onPasswordConfirmListener) {
        final EditText editText = new EditText(context);
        editText.setText(password);
        new AlertDialog.Builder(context).setTitle("请输入密码").setView(editText).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password = editText.getText().toString();
                onPasswordConfirmListener.onPasswordConfirm();
            }
        }).setNegativeButton(R.string.dialog_cancel, null).show();
    }

    @Override
    public String getRequest() {
        return "AT+UNLOCK=" + password;
    }

}
