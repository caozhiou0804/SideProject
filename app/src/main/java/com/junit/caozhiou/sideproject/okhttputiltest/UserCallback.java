package com.junit.caozhiou.sideproject.okhttputiltest;

import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.okhttputil.callback.Callback;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class UserCallback extends Callback<User>
{
    @Override
    public User parseNetworkResponse(Response response, int id) throws IOException
    {
        String string = response.body().string();
        User user = new Gson().fromJson(string, User.class);
        return user;
    }


}
