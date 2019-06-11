package com.wwzs.component.commonsdk.widget;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;


public class XWEditText extends AppCompatEditText {
    private XWEditText mthis;

    public XWEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mthis = this;
    }

    public XWEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mthis = this;
    }

//	private PointF c

    public XWEditText(Context context) {
        super(context);
        mthis = this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_DOWN && canVerticalScroll(this)) {

            //通知父控件不要干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (e.getAction() == MotionEvent.ACTION_MOVE && canVerticalScroll(this)) {

            //通知父控件不要干扰
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (e.getAction() == MotionEvent.ACTION_UP) {

//			getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(e);
    }

    private boolean canVerticalScroll(EditText editText) {
        //滚动的距离
        int scrollY = editText.getScrollY();
        //控件内容的总高度
        int scrollRange = editText.getLayout().getHeight();
        //控件实际显示的高度
        int scrollExtent = editText.getHeight() - editText.getCompoundPaddingTop() - editText.getCompoundPaddingBottom();
        //控件内容总高度与实际显示高度的差值
        int scrollDifference = scrollRange - scrollExtent;

        if (scrollDifference == 0) {
            return false;
        }

        return (scrollY > 0) || (scrollY < scrollDifference - 1);
    }

}
