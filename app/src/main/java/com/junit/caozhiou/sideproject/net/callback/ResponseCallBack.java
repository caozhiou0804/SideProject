package com.junit.caozhiou.sideproject.net.callback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/5.
 */

public abstract class ResponseCallBack<T> {

    /**
     * 对返回数据进行操作的回调， UI线程
     */
    public abstract void onSuccess(T t, Call call, Response response);

//    /**
//     * 请求失败，响应错误，数据解析错误等，都会回调该方法， UI线程
//     */
//    public abstract void onError(Call call, Response response, Exception e);
//
//    /**
//     * 网络失败结束之前的回调
//     */
//    public abstract void parseError(Call call, Exception e);
}
