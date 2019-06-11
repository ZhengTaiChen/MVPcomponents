package com.wwzs.component.commonsdk.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.AttributeSet;
import android.view.Gravity;
import android.view.InflateException;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.kingja.loadsir.core.LoadService;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.vondear.rxui.activity.AndroidBug5497Workaround;
import com.wwzs.component.commonsdk.base.delegate.IActivity;
import com.wwzs.component.commonsdk.http.imageloader.ImageLoader;
import com.wwzs.component.commonsdk.integration.cache.Cache;
import com.wwzs.component.commonsdk.integration.cache.CacheType;
import com.wwzs.component.commonsdk.integration.lifecycle.ActivityLifecycleable;
import com.wwzs.component.commonsdk.mvp.IPresenter;
import com.wwzs.component.commonsdk.mvp.IView;
import com.wwzs.component.commonsdk.utils.ArmsUtils;
import com.wwzs.component.commonsdk.view.MyProgressDialog;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;

import javax.inject.Inject;

import androidx.fragment.app.FragmentManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.wwzs.component.commonsdk.utils.Preconditions.checkNotNull;


public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView, IActivity, ActivityLifecycleable, CustomAdapt {
    public BaseActivity mActivity;
    protected HashMap<String, Object> dataMap;
    @Inject
    protected ImageLoader mImageLoader;
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    protected ImmersionBar mImmersionBar;

    //是否已经添加上了水印
    protected boolean isHasWater = false;
    //是否添加水印
    protected boolean isAddWater = true;

    protected MyProgressDialog mProgressDialog;

    protected LoadService loadService;

    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            int layoutResID = initView(savedInstanceState);
            //如果initView返回0,框架则不会调用setContentView(),当然也不会 Bind ButterKnife
            if (layoutResID != 0) {
                setContentView(layoutResID);
                //绑定到butterknife
                mUnbinder = ButterKnife.bind(this);
                AndroidBug5497Workaround.assistActivity(this);
            }
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        //初始化沉浸式
        if (isImmersionBarEnabled())
            initImmersionBar();
        dataMap = new HashMap<>();
        mActivity = this;
        mProgressDialog = new MyProgressDialog(mActivity);
        initData(savedInstanceState);
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        if (ImmersionBar.isSupportStatusBarDarkFont())
            mImmersionBar.statusBarDarkFont(false).init();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY)
            mUnbinder.unbind();
        this.mUnbinder = null;
        if (mPresenter != null)
            mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    /**
     * 消息更新
     *
     * @param message 更新内容
     */
    @Subscriber
    protected abstract void onEventRefresh(Message message);

    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading(boolean update) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.makeText(mActivity, message, Gravity.CENTER);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true} (默认为使用 {@code true}), Arms 会自动注册 EventBus
     */
    @Override
    public boolean useEventBus() {
        return true;
    }

    /**
     * 这个Activity是否会使用Fragment,框架会根据这个属性判断是否注册{@link FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回false,那意味着这个Activity不需要绑定Fragment,那你再在这个Activity中绑定继承于 {@link BaseFragment} 的Fragment将不起任何作用
     *
     * @return
     */
    @Override
    public boolean useFragment() {
        return true;
    }

    @Override
    public boolean isBaseOnWidth() {
        return true;
    }

    @Override
    public float getSizeInDp() {
        return 375;
    }
}
