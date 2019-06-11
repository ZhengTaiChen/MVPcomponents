package com.wwzs.component.commonsdk.base;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.wwzs.component.commonsdk.base.delegate.AppDelegate;
import com.wwzs.component.commonsdk.base.delegate.AppLifecycles;
import com.wwzs.component.commonsdk.di.component.AppComponent;
import com.wwzs.component.commonsdk.utils.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class BaseApplication extends MultiDexApplication implements App {

    // 获取到主线程的上下文
    public static BaseApplication mApplication;
    public static SharedPreferences SP;
    public static SharedPreferences.Editor EDIT;
    public static Map<Object, Long> mLong;
    private AppLifecycles mAppDelegate;


    @Keep
    @SophixEntry(BaseApplication.class)
    static class RealApplicationStub {
    }

    /**
     * 这里会在 {@link BaseApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
        this.mAppDelegate.attachBaseContext(base);


    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
        mApplication = this;
        mLong = new HashMap<>();
        SP = getSharedPreferences("userInfo", MODE_PRIVATE);
        EDIT = SP.edit();


    }

    /**
     * 在模拟环境中程序终止时会被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null)
            this.mAppDelegate.onTerminate(this);
    }

    /**
     * 将 {@link AppComponent} 返回出去, 供其它地方使用, {@link AppComponent} 接口中声明的方法所返回的实例, 在 {@link #getAppComponent()} 拿到对象后都可以直接使用
     *
     * @return AppComponent
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        Preconditions.checkState(mAppDelegate instanceof App, "%s must be implements %s", AppDelegate.class.getName(), App.class.getName());
        return ((App) mAppDelegate).getAppComponent();
    }

    private void initGreenDao() {


    }


}
