package com.junit.caozhiou.sideproject.net;

/**
 * Created by Administrator on 2016/12/30.
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.junit.caozhiou.sideproject.entity.CityBean;
import com.junit.caozhiou.sideproject.net.callback.ResponseCallBack;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil<T> {


    /**
     * okHttp post异步请求
     *
     * @param url       接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     * @return
     */
    public static void request(String url, MediaType mediaType, HashMap<String, String> paramsMap, Callback callBack) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            RequestBody body = RequestBody.create(mediaType, params);
//            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);

            Request request = new Request.Builder().url(url).header("User-Agent", "OkHttp Headers.java")
                    .addHeader("Accept", "application/json; q=0.5").addHeader("Accept", "application/vnd.github.v3+json")
                    .post(body).build();

            RequestManager.getInstance().mOkHttpClient.newCall(request).enqueue(callBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * okHttp post异步请求
     *
     * @param url              接口地址
     * @param paramsMap        请求参数
     * @param responseCallBack 请求返回数据回调
     * @return
     */
    public static <T> void request1(String url, MediaType mediaType, HashMap<String, String> paramsMap, final ResponseCallBack<T> responseCallBack) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            RequestBody body = RequestBody.create(mediaType, params);
//            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);

            Request request = new Request.Builder().url(url).header("User-Agent", "OkHttp Headers.java")
                    .addHeader("Accept", "application/json; q=0.5").addHeader("Accept", "application/vnd.github.v3+json")
                    .post(body).build();

            RequestManager.getInstance().mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String code = call.toString();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {


                    if (response.isSuccessful()) {
                        int code = response.code();

                        String responseString = response.body().string();
                        Class<T> classOfT =null;

                        Gson gson = new Gson();
                        JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
                        CityBean result = gson.fromJson(jsonObject, CityBean.class);
                        T t=null;
                        responseCallBack.onSuccess(t, call, response);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFailureWithCode(String errorCode) {
        String errorMsg = "";

        if (errorCode.equals(ERROR_CODE_NET)) {
            errorMsg = "无法连接到服务器";
        } else if (errorCode.equals(ERROR_CODE_NO_PEER_CER)) {
            errorMsg = "缺少可信证书";
        } else if (errorCode.equals(ERROR_CODE_FROM_JSON_TO_OBJECT)) {
            errorMsg = "对象转换失败";
        } else if (errorCode.equals(ERROR_CODE_JSON_PARSE)) {
            errorMsg = "对象解析失败";
        } else if (errorCode.equals(ERROR_CODE_RESULT_KEY_NOT_CORRECT)) {
            errorMsg = "结果字段key不匹配";
        } else {
            errorMsg = "未知错误";
        }
    }

    /**
     * 服务器结果设置
     *
     * @author tongxu_li
     *         Copyright (c) 2014 Shanghai P&C Information Technology Co., Ltd.
     */
    public interface ServiceResultManager {
        /**
         * 设置状态码key
         */
        public String getStatusKey();

        /**
         * 设置状态信息key
         */
        public String getMessgaeKey();

        /**
         * 设置结果集key
         */
        public String getResultKey();

        /**
         * 设置成功状态码
         */
        public String getSuccessStatus();
    }


    // 网络连接错误
    private static final String ERROR_CODE_NET = "-900";
    // 无可信证书
    private static final String ERROR_CODE_NO_PEER_CER = "-901";

    // Json字符串转对象错误
    private static final String ERROR_CODE_FROM_JSON_TO_OBJECT = "-800";
    // Json字符串解析错误
    private static final String ERROR_CODE_JSON_PARSE = "-801";

    // 结果字段key不匹配
    private static final String ERROR_CODE_RESULT_KEY_NOT_CORRECT = "-700";

}