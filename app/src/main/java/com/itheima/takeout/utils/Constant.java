package com.itheima.takeout.utils;

/**
 * Created by Teacher on 2016/9/2.
 */
public interface Constant {
    //192.168.199.215
    //192.168.82.250
    String replace_img_url = "192.168.82.250";
    // http://localhost:8080/   TakeoutService    /login?username="itheima"&password="bj"

//    String BASEURL="http://10.0.2.2:8080/";
    String BASEURL="http://192.168.82.250:8080/";
    // 登陆
    String LOGIN="TakeoutService/login";
    // http://localhost:8080/TakeoutService/home
    String HOME="TakeoutService/home";
    // http://localhost:8080/TakeoutService/goods?sellerId=1
    String GOODS = "TakeoutService/goods";
    //    http://localhost:8080/TakeoutService/address?userId=2163&&&&&&
    String ADDRESS="TakeoutService/address";

    String ORDER = "TakeoutService/order";
    String PAY="TakeoutService/pay";

    // 短信登陆的分类值
    int LOGIN_TYPE_SMS=2;

    String LAT ="Lat";
    String LNG ="Lng";

}
