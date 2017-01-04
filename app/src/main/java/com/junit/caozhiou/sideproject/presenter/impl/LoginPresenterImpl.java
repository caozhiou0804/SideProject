package com.junit.caozhiou.sideproject.presenter.impl;

import android.os.Handler;
import android.os.Message;

import com.junit.caozhiou.sideproject.entity.UserBean;
import com.junit.caozhiou.sideproject.net.OkHttpUtil;
import com.junit.caozhiou.sideproject.net.RequestManager;
import com.junit.caozhiou.sideproject.presenter.LoginPresenter;
import com.junit.caozhiou.sideproject.presenter.LoginView;
import com.junit.caozhiou.sideproject.presenter.back.LoginCallBack;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/29.
 */

public class LoginPresenterImpl implements LoginPresenter {
    private LoginView loginView;
    private LoginCallBack loginCallBack;

    public LoginPresenterImpl(LoginView loginView, LoginCallBack<UserBean> loginCallBack) {
        this.loginView = loginView;
        this.loginCallBack = loginCallBack;
    }


    @Override
    public void login() {
        String username = loginView.getUsername();
        String pass = loginView.getPassword();

        UserBean u = new UserBean();
        u.setUsername(username);
        u.setPassword(pass);

        try {
            doPostAsync(u);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loginOut() {

    }

    private void doPostAsync(UserBean u) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username",u.getUsername());
        params.put("password",u.getPassword());
        String url = "http://192.168.111.2:8080/WebApp/Userfeature/userLogin";
        OkHttpUtil.request(url, RequestManager.MEDIA_TYPE_JSON, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(response.code());

                    //System.out.println(response.body().string());
                    String body = response.body().string();
                    System.out.println(body);
                    Message msg = mHandler.obtainMessage();
                    msg.obj = body;
                    msg.what = 123;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            UserBean userBean = new UserBean();
            userBean.setPassword(msg.obj.toString());
            loginCallBack.loginBack(userBean);
        }
    };
}