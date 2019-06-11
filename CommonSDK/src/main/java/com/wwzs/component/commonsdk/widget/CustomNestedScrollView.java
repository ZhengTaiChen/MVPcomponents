package com.wwzs.component.commonsdk.widget;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;

public class CustomNestedScrollView extends NestedScrollView{
    private ScrollInterface scrollInterface;
    /**
     * 定义滑动接口 
     */
    public interface ScrollInterface {
        void onScrollChange(int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    public CustomNestedScrollView(Context context) {
        super(context);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (scrollInterface != null) {
            scrollInterface.onScrollChange(l, t, oldl, oldt);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public void setOnMyScrollChangeListener(ScrollInterface t) {
        this.scrollInterface = t;
    }
}
