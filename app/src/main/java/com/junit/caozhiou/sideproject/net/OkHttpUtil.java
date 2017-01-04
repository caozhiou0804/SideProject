package com.junit.caozhiou.sideproject.net;

/**
 * Created by Administrator on 2016/12/30.
 */

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtil {


    /**
     * okHttp post异步请求
     *
     * @param url 接口地址
     * @param paramsMap 请求参数
     * @param callBack  请求返回数据回调
     *
     * @return
     */
    public static void request(String url,MediaType mediaType, HashMap<String, String> paramsMap,Callback callBack) {
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
     * @param url 接口地址
     * @param paramsMap 请求参数
     * @param responseCallBack  请求返回数据回调
     *
     * @return
     */
    public static void request1(String url, MediaType mediaType, HashMap<String, String> paramsMap, final ResponseCallBack responseCallBack) {
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

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    responseCallBack.parseObj();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public interface ResponseCallBack<T>{

        T parseObj();

    }


}