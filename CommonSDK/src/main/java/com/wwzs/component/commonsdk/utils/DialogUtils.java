package com.wwzs.component.commonsdk.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.DrawableRes;
import android.text.TextUtils;

import com.vondear.rxui.view.dialog.RxDialogSureCancel;


public class DialogUtils {
    public static void callPhone(Context mContext, String phone, @DrawableRes int resid) {
        RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(mContext);
        rxDialogSureCancel.getTitleView().setBackgroundResource(resid);
        rxDialogSureCancel.getContentView().setText("拨打：" + (TextUtils.isEmpty(phone) ? "01053325565" : phone));
        rxDialogSureCancel.getSureView().setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + (TextUtils.isEmpty(phone) ? "01053325565" : phone)));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
        rxDialogSureCancel.getCancelView().setOnClickListener(v -> rxDialogSureCancel.cancel());
        rxDialogSureCancel.show();
    }


    public static void showSuccess(Context context, String content) {
        RxDialogSureCancel sureCancel = new RxDialogSureCancel(context);
        sureCancel.setTitle("预约提醒");
        sureCancel.setContent(content);

    }
}
