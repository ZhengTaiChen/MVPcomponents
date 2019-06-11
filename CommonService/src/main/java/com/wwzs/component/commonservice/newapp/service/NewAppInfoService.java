package com.wwzs.component.commonservice.newapp.service;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.wwzs.component.commonservice.newapp.bean.NewAppInfo;

public interface NewAppInfoService extends IProvider {
    NewAppInfo getInfo();

    void toCheckSpeakResult(String result);
}
