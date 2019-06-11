package com.wwzs.component.commonsdk.http;

public interface Api {


    String APP_DEFAULT_DOMAIN = "http://121.40.203.16/";

    String ERP_DEFAULT_DOMAIN = "http://120.26.112.117/"; //ERP地址

    //物业url旧版
    String PROPERTY_DEFAULT_DOMAIN_OLD = Api.PROPERTY_DEFAULT_DOMAIN + "wwzs/mobile/api/Appinterface.php";

    //物业url
    String PROPERTY_DEFAULT_DOMAIN = "http://www.shequyun.cc/";
    //天气
    String WEATHER_DEFAULT_DOMAIN = "http://v.juhe.cn/";

    String PROPERTY_DEFAULT_NAME = "appinterface";

    String APP_DOMAIN_NAME = "app";

    String ERP_DOMAIN_NAME = "erp";

    String WEATHER_DOMAIN_NAME = "weather";
}
