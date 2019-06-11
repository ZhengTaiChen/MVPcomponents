package com.wwzs.component.commonsdk.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle3.android.FragmentEvent;
import com.wwzs.component.commonsdk.base.delegate.IFragment;
import com.wwzs.component.commonsdk.http.imageloader.ImageLoader;
import com.wwzs.component.commonsdk.integration.cache.Cache;
import com.wwzs.component.commonsdk.integration.cache.CacheType;
import com.wwzs.component.commonsdk.integration.lifecycle.FragmentLifecycleable;
import com.wwzs.component.commonsdk.mvp.IPresenter;
import com.wwzs.component.commonsdk.mvp.IView;
import com.wwzs.component.commonsdk.utils.ArmsUtils;
import com.wwzs.component.commonsdk.view.MyProgressDialog;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.jessyan.autosize.internal.CustomAdapt;

import static com.wwzs.component.commonsdk.utils.Preconditions.checkNotNull;


public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView, IFragment, FragmentLifecycleable, CustomAdapt {
    protected ImageLoader mImageLoader;
    protected FragmentActivity mActivity;
    protected Map<String, Object> dataMap = new HashMap<>();
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null
    protected MyProgressDialog mProgressDialog;

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(getActivity()).cacheFactory().build(CacheType.FRAGMENT_CACHE);
        }
        return mCache;
    }


    @NonNull
    @Override
    public final Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView(inflater, container, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        mProgressDialog = new MyProgressDialog(mActivity);
        super.onCreate(savedInstanceState);

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
        if (!TextUtils.isEmpty(message)) {
            checkNotNull(message);
            ArmsUtils.makeText(getContext(), message, Gravity.CENTER);
        }


    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        killMyself();
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
