package com.wys.mvpcomponents.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.wwzs.component.commonsdk.di.component.AppComponent;

import com.wys.mvpcomponents.di.module.MainModule;
import com.wys.mvpcomponents.mvp.contract.MainContract;

import com.wwzs.component.commonsdk.di.scope.ActivityScope;
import com.wys.mvpcomponents.mvp.ui.activity.MainActivity;


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
@ActivityScope
@Component(modules = MainModule.class, dependencies = AppComponent.class)
public interface MainComponent {
    void inject(MainActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MainComponent.Builder view(MainContract.View view);

        MainComponent.Builder appComponent(AppComponent appComponent);

        MainComponent build();
    }
}