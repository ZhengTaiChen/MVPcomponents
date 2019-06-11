package com.wwzs.component.commonsdk.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wwzs.component.commonsdk.R;
import com.wwzs.component.commonsdk.core.RouterHub;


/**
 * Created by David.Li on 2016/3/6.
 */
public class SpUtils {

    /**
     * 判断是否已登录
     *
     * @return
     */
    public static boolean judgeIsSign(Activity mActivity, int requestCode) {
        String userid = DataHelper.getStringSF(mActivity, "uid");
        if (TextUtils.isEmpty(userid) && userid.equals("")) {        //先判断是否登录
//            if (-1 == requestCode)
//                ARouter.getInstance()
//                        .build(RouterHub.APP_A0_SIGNINACTIVITY)
//                        .withTransition(R.anim.push_buttom_in, R.anim.push_buttom_out)
//                        .navigation();
//            else
                ARouter.getInstance()
                    .build(RouterHub.APP_A0_SIGNINACTIVITY)
                    .withTransition(R.anim.push_buttom_in, R.anim.push_buttom_out)
                    .navigation(mActivity, requestCode);

            return false;
        }
        return true;
    }

    /**
     * 判断是否已认证
     *
     * @return
     */
    public static boolean judgeIsAuth(Context context) {
        String coid = DataHelper.getStringSF(context, "coid");
        return !TextUtils.isEmpty(coid);
    }


}
