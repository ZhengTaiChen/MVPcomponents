/*
 * Copyright 2018 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wwzs.component.commonsdk.core;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wwzs.component.commonsdk.BuildConfig;
import com.wwzs.component.commonsdk.ImgaEngine.strategy.CommonGlideImageLoaderStrategy;
import com.wwzs.component.commonsdk.base.delegate.AppLifecycles;
import com.wwzs.component.commonsdk.di.module.GlobalConfigModule;
import com.wwzs.component.commonsdk.http.SSLSocketClient;
import com.wwzs.component.commonsdk.http.log.RequestInterceptor;
import com.wwzs.component.commonsdk.integration.ConfigModule;

import java.util.List;

import butterknife.ButterKnife;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

import static com.wwzs.component.commonsdk.http.Api.APP_DEFAULT_DOMAIN;
import static com.wwzs.component.commonsdk.http.Api.APP_DOMAIN_NAME;
import static com.wwzs.component.commonsdk.http.Api.ERP_DEFAULT_DOMAIN;
import static com.wwzs.component.commonsdk.http.Api.ERP_DOMAIN_NAME;
import static com.wwzs.component.commonsdk.http.Api.PROPERTY_DEFAULT_DOMAIN;
import static com.wwzs.component.commonsdk.http.Api.PROPERTY_DEFAULT_NAME;
import static com.wwzs.component.commonsdk.http.Api.WEATHER_DEFAULT_DOMAIN;
import static com.wwzs.component.commonsdk.http.Api.WEATHER_DOMAIN_NAME;


/**
 * ================================================
 * CommonSDK 的 GlobalConfiguration 含有有每个组件都可公用的配置信息, 每个组件的 AndroidManifest 都应该声明此 ConfigModule
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#3.3">ConfigModule wiki 官方文档</a>
 * Created by JessYan on 30/03/2018 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GlobalConfiguration implements ConfigModule {

    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        if (!BuildConfig.LOG_DEBUG) //Release 时,让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE);
        builder.baseurl(APP_DEFAULT_DOMAIN)
                .imageLoaderStrategy(new CommonGlideImageLoaderStrategy())
                .globalHttpHandler(new GlobalHttpHandlerImpl(context))
                .responseErrorListener(new ResponseErrorListenerImpl())
                .gsonConfiguration((context1, gsonBuilder) -> {//这里可以自己自定义配置Gson的参数
                    gsonBuilder

                            .disableHtmlEscaping()//防止特殊字符出现乱码
                            .serializeNulls()//支持序列化null的参数
                            .enableComplexMapKeySerialization();//支持将序列化key为object的map,默认只能序列化key为string的map
                })
                .retrofitConfiguration((context1, builder1) -> {
                    builder1.addConverterFactory(GsonConverterFactory.create());
                    builder1.addConverterFactory(ScalarsConverterFactory.create());
                })
                .okhttpConfiguration((context12, builder1) -> {
                    builder1.sslSocketFactory(SSLSocketClient.getSSLSocketFactory(), SSLSocketClient.getTrustManager());
                    builder1.hostnameVerifier(SSLSocketClient.getHostnameVerifier());
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl. 详细使用请方法查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    RetrofitUrlManager.getInstance().with(builder1);
                })
                .rxCacheConfiguration((context1, rxCacheBuilder) -> {//这里可以自己自定义配置RxCache的参数
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true);
                    return null;
                });
    }

    @Override
    public void injectAppLifecycle(Context context, List<AppLifecycles> lifecycles) {
        // AppDelegate.Lifecycle 的所有方法都会在基类Application对应的生命周期中被调用,所以在对应的方法中可以扩展一些自己需要的逻辑
        lifecycles.add(new AppLifecycles() {

            @Override
            public void attachBaseContext(@NonNull Context base) {
            }

            @Override
            public void onCreate(@NonNull Application application) {
                if (BuildConfig.LOG_DEBUG) {//Timber日志打印
                    Timber.plant(new Timber.DebugTree());
                    ButterKnife.setDebug(true);
                    ARouter.openLog();     // 打印日志
                    ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
                    RetrofitUrlManager.getInstance().setDebug(true);
                }
                ARouter.init(application); // 尽可能早,推荐在Application中初始化
                RetrofitUrlManager.getInstance().setDebug(BuildConfig.LOG_DEBUG);
                //将每个 BaseUrl 进行初始化,运行时可以随时改变 DOMAIN_NAME 对应的值,从而达到切换 BaseUrl 的效果
                RetrofitUrlManager.getInstance().putDomain(APP_DOMAIN_NAME, APP_DEFAULT_DOMAIN);
                RetrofitUrlManager.getInstance().putDomain(ERP_DOMAIN_NAME, ERP_DEFAULT_DOMAIN);
                RetrofitUrlManager.getInstance().putDomain(PROPERTY_DEFAULT_NAME, PROPERTY_DEFAULT_DOMAIN);
                RetrofitUrlManager.getInstance().putDomain(WEATHER_DOMAIN_NAME, WEATHER_DEFAULT_DOMAIN);


            }

            @Override
            public void onTerminate(@NonNull Application application) {

            }
        });
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycles) {
        lifecycles.add(new ActivityLifecycleCallbacksImpl());
    }

    @Override
    public void injectFragmentLifecycle(Context context, List<FragmentManager.FragmentLifecycleCallbacks> lifecycles) {
        lifecycles.add(new FragmentManager.FragmentLifecycleCallbacks() {


            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                // 在配置变化的时候将这个 Fragment 保存下来,在 Activity 由于配置变化重建是重复利用已经创建的Fragment。
                // https://developer.android.com/reference/android/app/Fragment.html?hl=zh-cn#setRetainInstance(boolean)
                // 在 Activity 中绑定少量的 Fragment 建议这样做,如果需要绑定较多的 Fragment 不建议设置此参数,如 ViewPager 需要展示较多 Fragment
                f.setRetainInstance(true);

            }
        });
    }
}
