package com.wwzs.component.commonsdk.utils;

import com.wwzs.component.commonsdk.mvp.IView;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/**
 * 放置便于使用 RxJava 的一些工具方法
 * Created By 郑小国 on 2019/3/7
 */
public class RxUtils {

    private RxUtils() {
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return applySchedulers(view, false);
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view, boolean update) {
        return applySchedulers(view, update, 3, 5);
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(int marRetries, int retryDelaySecond) {
        return observable ->
                observable.subscribeOn(Schedulers.io())
                        .retryWhen(new RetryWithDelay(marRetries, retryDelaySecond))
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                ;
    }


    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view, boolean pullToRefresh, int marRetries, int retryDelaySecond) {
        return observable ->
                observable.subscribeOn(Schedulers.io())
                        .retryWhen(new RetryWithDelay(marRetries, retryDelaySecond))
                        .doOnSubscribe(disposable -> {
                            if (!pullToRefresh)
                                view.showLoading();//显示进度条
                        })
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doFinally(() -> {
                            view.hideLoading(pullToRefresh);//隐藏进度条
                        }).compose(RxLifecycleUtils.bindToLifecycle(view));
    }


}
