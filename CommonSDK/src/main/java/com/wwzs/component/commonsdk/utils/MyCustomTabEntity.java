package com.wwzs.component.commonsdk.utils;

import com.flyco.tablayout.listener.CustomTabEntity;

public class MyCustomTabEntity implements CustomTabEntity {

    private String tabTitle = "";
    private int tabSelectedIcon;
    private int tabUnselectedIcon;

    public MyCustomTabEntity(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public MyCustomTabEntity(String tabTitle, int tabSelectedIcon, int tabUnselectedIcon) {
        this.tabTitle = tabTitle;
        this.tabSelectedIcon = tabSelectedIcon;
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }
}
