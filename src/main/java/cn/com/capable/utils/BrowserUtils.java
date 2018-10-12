package cn.com.capable.utils;

import javax.servlet.http.HttpServletRequest;

//浏览器工具类
public class BrowserUtils {

    private final static String IE11 = "rv:11.0";
    private final static String IE10 = "MSIE 10.0";
    private final static String IE9 = "MSIE 9.0";
    private final static String IE8 = "MSIE 8.0";
    private final static String IE7 = "MSIE 7.0";
    private final static String IE6 = "MSIE 6.0";
    private final static String MAXTHON = "Maxthon";
    private final static String QQ = "QQBrowser";
    private final static String GREEN = "GreenBrowser";
    private final static String SE360 = "360SE";
    private final static String FIREFOX = "Firefox";
    private final static String OPERA = "Opera";
    private final static String CHROME = "Chrome";
    private final static String SAFARI = "Safari";
    private final static String OTHER = "其它";

    //判断是否为IE浏览器
    public static boolean isIE(HttpServletRequest request){
        if(request.getHeader("USER-AGENT").toLowerCase().indexOf("msie")>0||
                request.getHeader("USER-AGENT").toLowerCase().indexOf("rv:11.0")>0) // IE11输出 ：MOZILLA/5.0 (WINDOWS NT 6.1; WOW64; TRIDENT/7.0; RV:11.0) LIKE GECKO
            return true;
        return false;
    }

    //获取IE版本
    public static Double getIEVersion(HttpServletRequest request){
        Double version = 0.0;
        if (getBrowserType(request,IE6))
            version = 6.0;
        else if (getBrowserType(request,IE7))
            version = 7.0;
        else if (getBrowserType(request,IE8))
            version = 8.0;
        else if (getBrowserType(request,IE9))
            version = 9.0;
        else if (getBrowserType(request,IE10))
            version = 10.0;
        else if (getBrowserType(request,IE11))
            version = 11.0;
        return version;
    }

    //获取浏览器类型
    public static String getBrowserType(HttpServletRequest request){
        String browserType = "";
        if (getBrowserType(request, IE11)) {
            browserType = "IE11";
        }
        if (getBrowserType(request, IE10)) {
            browserType = "IE10";
        }
        if (getBrowserType(request, IE9)) {
            browserType = "IE9";
        }
        if (getBrowserType(request, IE8)) {
            browserType = "IE8";
        }
        if (getBrowserType(request, IE7)) {
            browserType = "IE7";
        }
        if (getBrowserType(request, IE6)) {
            browserType = "IE6";
        }
        if (getBrowserType(request, FIREFOX)) {
            browserType = "Firefox";
        }
        if (getBrowserType(request, SAFARI)) {
            browserType ="Safari";
        }
        if (getBrowserType(request, CHROME)) {
            browserType = "Chrome";
        }
        if (getBrowserType(request, OPERA)) {
            browserType = "Opera";
        }
        if (getBrowserType(request, "Camino")) {
            browserType = "Camino";
        }
        return browserType;
    }




    private static boolean getBrowserType(HttpServletRequest request,String browserType){
        if(request.getHeader("USER-AGENT").toLowerCase().indexOf(browserType)>0)
            return true;
        return false;
    }


}
