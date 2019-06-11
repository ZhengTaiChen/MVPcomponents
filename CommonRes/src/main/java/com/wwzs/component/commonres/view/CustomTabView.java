package com.wwzs.component.commonres.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


import com.vondear.rxtool.RxTextTool;
import com.wwzs.component.commonres.R;
import com.wwzs.component.commonsdk.utils.ArmsUtils;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * 功能
 * 描述
 * Created By 郑小国 on 2018/12/4
 */
public class CustomTabView extends LinearLayout implements View.OnClickListener {

    private TextView tvLeftTitle;
    private TextView tvLeftNum;
    private View line;
    private TextView tvRightTitle;
    private TextView tvRightNum;
    private int selectedColor;
    private float selectedSize;
    private int unSelectedColor;
    private float unSelectedSize;
    private FragmentChangeManager fm;
    private OnTabSelectListener mListener;
    private String[] mTitles;

    public CustomTabView(Context context) {
        super(context);
    }

    public CustomTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.public_CustomTabView);
        selectedColor = attributes.getColor(R.styleable.public_CustomTabView_public_selected_text_color, Color.parseColor("#ffffff"));
        selectedSize = attributes.getDimension(R.styleable.public_CustomTabView_public_selected_text_size, 18);
        unSelectedColor = attributes.getColor(R.styleable.public_CustomTabView_public_unselected_text_color, Color.parseColor("#ffffff"));
        unSelectedSize = attributes.getDimension(R.styleable.public_CustomTabView_public_unselected_text_size, 14);
        unSelectedSize = ArmsUtils.px2sp(context, unSelectedSize);
        selectedSize = ArmsUtils.px2sp(context, selectedSize);
        tvLeftTitle.setTextSize(unSelectedSize);
        tvRightTitle.setTextSize(unSelectedSize);
        tvLeftNum.setTextSize(unSelectedSize);
        tvRightNum.setTextSize(unSelectedSize);
    }

    public CustomTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTabData(String[] mTitles, FragmentChangeManager fragmentChangeManager) {
        this.mTitles = mTitles;
        this.fm = fragmentChangeManager;
        setTabData(mTitles);
        setCurrentTab(0);
    }

    public void setTabData(String[] mTitles) {
        int maxLength = 0;
        for (String mTitle : mTitles) {
            if (mTitle.length() > maxLength) {
                maxLength = mTitle.length();
            }
        }

        tvLeftTitle.setText(mTitles[0]);
        tvLeftTitle.setMinEms(maxLength + 1);
        tvRightTitle.setText(mTitles[1]);
        tvRightTitle.setMinEms(maxLength + 1);
        tvRightTitle.setTextColor(unSelectedColor);
        tvLeftNum.setOnClickListener(this);
        tvLeftTitle.setOnClickListener(this);
        tvRightTitle.setOnClickListener(this);
        tvRightNum.setOnClickListener(this);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.public_view_tab, this);
        tvLeftTitle = this.findViewById(R.id.tv_left_title);
        tvLeftNum = this.findViewById(R.id.tv_left_num);
        line = this.findViewById(R.id.line);
        tvRightTitle = this.findViewById(R.id.tv_right_title);
        tvRightNum = this.findViewById(R.id.tv_right_num);

    }


    public void setLeftNum(String leftNum) {
        RxTextTool.getBuilder(mTitles[0].replace("/\\([^\\)]*\\)/g", ""))
                .append("("+leftNum+")")
                .setProportion(14f / 18f)
                .into(tvLeftTitle);
    }

    public View getLine() {
        return line;
    }

    public void setLine(View line) {
        this.line = line;
    }


    public void setRightNum(String rightNum) {
        RxTextTool.getBuilder(mTitles[1].replace("/\\([^\\)]*\\)/g", ""))
                .append("("+rightNum+")")
                .setProportion(14f / 18f)
                .into(tvRightTitle);

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_left_title || i == R.id.tv_left_num) {
            setCurrentTab(0);
        } else if (i == R.id.tv_right_title || i == R.id.tv_right_num) {
            setCurrentTab(1);
        }
    }

    public void setCurrentTab(int position) {
        if (position == 0) {
            if (fm != null)
                fm.setFragments(position);
            tvLeftTitle.setTextSize(selectedSize);
            tvLeftTitle.setTextColor(selectedColor);
            tvRightTitle.setTextSize(unSelectedSize);
            tvRightTitle.setTextColor(unSelectedColor);
            if (mListener != null)
                mListener.onTabSelect(position);
        } else {
            if (fm != null)
                fm.setFragments(position);
            tvLeftTitle.setTextSize(unSelectedSize);
            tvLeftTitle.setTextColor(unSelectedColor);
            tvRightTitle.setTextSize(selectedSize);
            tvRightTitle.setTextColor(selectedColor);
            if (mListener != null)
                mListener.onTabSelect(position);
        }

    }

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.mListener = listener;
    }

    public interface OnTabSelectListener {
        void onTabSelect(int position);
    }

    public static class FragmentChangeManager {
        private FragmentManager mFragmentManager;
        private int mContainerViewId;
        /**
         * Fragment切换数组
         */
        private ArrayList<Fragment> mFragments;
        /**
         * 当前选中的Tab
         */
        private int mCurrentTab;

        public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments) {
            this.mFragmentManager = fm;
            this.mContainerViewId = containerViewId;
            this.mFragments = fragments;
            initFragments();
        }

        /**
         * 初始化fragments
         */
        private void initFragments() {
            for (Fragment fragment : mFragments) {
                mFragmentManager.beginTransaction().add(mContainerViewId, fragment).hide(fragment).commit();
            }

            setFragments(0);
        }

        /**
         * 界面切换控制
         */
        public void setFragments(int index) {
            for (int i = 0; i < mFragments.size(); i++) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                Fragment fragment = mFragments.get(i);
                if (i == index) {
                    ft.show(fragment);
                } else {
                    ft.hide(fragment);
                }
                ft.commitAllowingStateLoss();
            }
            mCurrentTab = index;
        }

        public int getCurrentTab() {
            return mCurrentTab;
        }

        public Fragment getCurrentFragment() {
            return mFragments.get(mCurrentTab);
        }
    }
}
