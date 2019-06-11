package com.wwzs.component.commonsdk.view;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;

import com.wwzs.component.commonsdk.R;


public class MyProgressDialog extends Dialog {

    public MyProgressDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        setContentView(R.layout.public_progress_view);
        setCanceledOnTouchOutside(false);
    }

}
