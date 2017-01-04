//package com.junit.caozhiou.sideproject.net;
//
//import android.content.Context;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//
///**
// * okHttp网络请求request封装
// * @Date 2017/1/4.
// * @Author caozhiou
// */
//
//public class OkHttpRequestHandler {
//
//    private static OkHttpClient mOkHttpClient;
//
//    static {
//        mOkHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
//                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
//                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
//                .build();
//
//
//    }
//    /**
//     * 表单get提交方式(无context)
//     * @param params 保存key:value的对象
//     */
//    public static void get(String url, YTRequestParams params, ResponseHandlerInterface responseHandler) {
//        mOkHttpClient.get(url, params, responseHandler);
//    }
//
//    /**
//     * 表单post提交方式(无context)
//     * @param params 保存key:value的对象
//     */
//    public static void post(String url, YTRequestParams params, ResponseHandlerInterface responseHandler) {
//        mOkHttpClient.post(url, params, responseHandler);
//    }
//
//    /**
//     * 表单post提交方式
//     * @param params 保存key:value的对象
//     */
//    public static void post(Context context, String url, YTRequestParams params, ResponseHandlerInterface responseHandler) {
//        mOkHttpClient.post(context, url, params, responseHandler);
//    }
//}
