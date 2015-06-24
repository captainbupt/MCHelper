package com.vgomc.mchelper.entity.bluetooth.setting;

import android.content.Context;
import android.content.DialogInterface;

import com.vgomc.mchelper.R;
import com.vgomc.mchelper.utility.SP;

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

    public UnlockEntity(Context context){
        password = SP.getStringSP(context, "default", "key", password);
    }

    public UnlockEntity(final Context context, final OnPasswordConfirmListener onPasswordConfirmListener) {
        password = SP.getStringSP(context, "default", "key", password);
        final EditText editText = new EditText(context);
        editText.setText(password);
        new AlertDialog.Builder(context).setTitle("请输入密码").setView(editText).setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                password = editText.getText().toString();
                SP.putStringSP(context, "default", "key", password);
                onPasswordConfirmListener.onPasswordConfirm();
            }
        }).setNegativeButton(R.string.dialog_cancel, null).show();
    }

    @Override
    public String getRequest() {
        return "AT+UNLOCK=" + password;
    }

}
