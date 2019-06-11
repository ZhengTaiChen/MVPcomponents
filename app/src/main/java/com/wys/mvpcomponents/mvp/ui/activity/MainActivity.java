package com.wys.mvpcomponents.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wwzs.component.commonsdk.base.BaseActivity;
import com.wwzs.component.commonsdk.di.component.AppComponent;
import com.wwzs.component.commonsdk.utils.ArmsUtils;

import com.wys.mvpcomponents.di.component.DaggerMainComponent;
import com.wys.mvpcomponents.mvp.contract.MainContract;
import com.wys.mvpcomponents.mvp.presenter.MainPresenter;

import com.wys.mvpcomponents.R;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/11/2019 10:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    protected void onEventRefresh(Message message) {

    }
}
