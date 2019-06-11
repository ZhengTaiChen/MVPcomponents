package com.wwzs.component.commonsdk.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wwzs.component.commonsdk.R;
import com.wwzs.component.commonsdk.core.RouterHub;

public class ARouterUtils {
    private ARouterUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 这个方法因为没有使用 {@link Activity}跳转
     * 所以 {@link ARouter} 会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link ARouter#getInstance()#navigation(Context, String)} 并传入 {@link Activity}
     *
     * @param path {@code path}
     */
    public static void navigation(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public static void navigationTransition(Context context, String path) {
        if (SpUtils.judgeIsSign((Activity) context, -1))
            if (!SpUtils.judgeIsAuth(context)) {
                ARouterUtils.navigation(RouterHub.APP_LIVEAUTHACTIVITY);
            } else {
            ARouter.getInstance().build(path)
                    .withTransition(R.anim.push_right_in, R.anim.push_right_out)
                    .navigation();
        }

    }

    /**
     * 带参数跳转
     *
     * @param path
     * @param bundle
     */
    public static void navigation(Context context, String path, Bundle bundle) {

        if (SpUtils.judgeIsSign((Activity) context, -1))
            if (!SpUtils.judgeIsAuth(context)) {
                ARouterUtils.navigation(RouterHub.APP_LIVEAUTHACTIVITY);
            } else {

            ARouter.getInstance()
                    .build(path)
                    .with(bundle)
                    .withTransition(R.anim.push_right_in, R.anim.push_right_out)
                    .navigation();
        }


    }

    public static void navigation(Activity activity, String path, Bundle bundle, int requestCode) {
        if (SpUtils.judgeIsSign(activity, -1))
            if (!SpUtils.judgeIsAuth(activity)) {
                ARouterUtils.navigation(RouterHub.APP_LIVEAUTHACTIVITY);
            } else {
                ARouter.getInstance()
                        .build(path)
                        .with(bundle)
                        .withTransition(R.anim.push_right_in, R.anim.push_right_out)
                        .navigation(activity, requestCode);
            }

    }

    /**
     * 获取Fragment
     *
     * @param path
     */
    public static Fragment newFragment(String path) {
        return (Fragment) ARouter.getInstance().build(path).navigation();
    }

    /**
     * 使用 {@link ARouter} 根据 {@code path} 跳转到对应的页面, 如果参数 {@code context} 传入的不是 {@link Activity}
     * {@link ARouter} 就会自动给 {@link android.content.Intent} 加上 Intent.FLAG_ACTIVITY_NEW_TASK
     * 如果不想自动加上这个 Flag 请使用 {@link Activity} 作为 {@code context} 传入
     *
     * @param context
     * @param path
     */
    public static void navigation(Context context, String path) {
        ARouter.getInstance().build(path).navigation(context);
    }

    public static void navigation(Activity activity, String path, int requestCode) {
        ARouter.getInstance().build(path).navigation(activity, requestCode);
    }

    /**
     * //添加下划线
     *
     * @param textView
     */
    public static void addTextviewUnderline(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        //抗锯齿
        textView.getPaint().setAntiAlias(true);
    }

}
