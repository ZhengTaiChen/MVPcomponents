package com.wwzs.component.commonsdk.entity;

import android.os.Message;

import com.wwzs.component.commonsdk.integration.AppManager;

import java.io.Serializable;

public class StatusBean implements Serializable {
    private String succeed;
    private int error_code;
    private String error_desc;

    public StatusBean(String succeed, int error_code, String error_desc) {
        this.succeed = succeed;
        this.error_code = error_code;
        this.error_desc = error_desc;
    }

    public boolean getSucceed() {
        return !"0".equals(succeed);
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    public int getError_code() {


        return error_code;

    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_desc() {
        if (error_code == 100) {
            Message msg = new Message();
            msg.what = 100;
            AppManager.post(msg);
        }

        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }
}
