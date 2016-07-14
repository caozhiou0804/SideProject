package com.junit.caozhiou.sideproject.activity;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-13
 * Time: 11:56
 * Description:
 */

import android.app.Activity;
import java.io.IOException;

import com.junit.caozhiou.sideproject.R;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SuppressLint("NewApi")
public class OkHttpTestActivity extends Activity implements OnClickListener {

    OkHttpClient mOkHttpClient = new OkHttpClient();
    String url = "https://www.baidu.com/";
    String result = "";
    TextView textView1;
    final int MSG_WHAT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);

        Button btnPost = (Button) findViewById(R.id.button1);
        btnPost.setOnClickListener(this);
        Button btnGet = (Button) findViewById(R.id.button2);
        btnGet.setOnClickListener(this);
        Button btnPostAsync = (Button) findViewById(R.id.button3);
        btnPostAsync.setOnClickListener(this);
        Button btnGetAsync = (Button) findViewById(R.id.button4);
        btnGetAsync.setOnClickListener(this);

        textView1 = (TextView) findViewById(R.id.textView1);
    }

    private void doPost() throws Exception {
        // TODO Auto-generated method stub
//        RequestBody formBody = new FormEncodingBuilder().add("platform", "android").add("name", "bug")
//                .add("subject", "XXXXXXXXXXXXXXX").build();

        //当写请求头的时候，使用 header(name, value) 可以设置唯一的name、value。如果已经有值，旧的将被移除，然后添加新的。使用 addHeader(name, value) 可以添加多值（添加，不移除已有的）。
//        Request request = new Request.Builder().url(url).header("User-Agent", "OkHttp Headers.java")
//                .addHeader("Accept", "application/json; q=0.5").addHeader("Accept", "application/vnd.github.v3+json")
//                .post(formBody).build();
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        Response response = mOkHttpClient.newCall(request).execute();// execute
        if (response.isSuccessful()) {
            System.out.println(response.code());
            //System.out.println(response.body().string());
            String body = response.body().string();
            System.out.println(body);
            result = "doPost|" + response.code() + "|" + body;
            mHandler.sendEmptyMessage(MSG_WHAT);

        }
    }

    private void doPostAsync() {
        // TODO Auto-generated method stub
//        RequestBody formBody = new FormEncodingBuilder().add("platform", "android").add("name", "bug")
//                .add("subject", "XXXXXXXXXXXXXXX").build();
//        Request request = new Request.Builder().url(url).header("User-Agent", "OkHttp Headers.java")
//                .addHeader("Accept", "application/json; q=0.5").addHeader("Accept", "application/vnd.github.v3+json")
//                .post(formBody).build();
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        // enqueue
        mOkHttpClient.newCall(request).enqueue(new Callback() {
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
                    result = "doPostAsync|" + response.code() + "|" + body;
                    mHandler.sendEmptyMessage(MSG_WHAT);
            }
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                // NOT UI Thread
//                if (response.isSuccessful()) {
//                    System.out.println(response.code());
//
//                    //System.out.println(response.body().string());
//                    String body = response.body().string();
//                    System.out.println(body);
//                    result = "doPostAsync|" + response.code() + "|" + body;
//                    mHandler.sendEmptyMessage(MSG_WHAT);
//                }
//
            }
        });

    }

    private void doGet() throws Exception {
        // TODO Auto-generated method stub
        Request request = new Request.Builder().url(url).build();
        Response response = mOkHttpClient.newCall(request).execute();// execute
        if (response.isSuccessful()) {
            System.out.println(response.code());
            //System.out.println(response.body().string());
            String body = response.body().string();
            System.out.println(body);
            result = "doGet|" + response.code() + "|" + body;
            mHandler.sendEmptyMessage(MSG_WHAT);
        }
    }

    private void doGetAsync() {
        // TODO Auto-generated method stub
        Request request = new Request.Builder().url(url).build();
        // enqueue
        mOkHttpClient.newCall(request).enqueue(new Callback() {
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
                    result = "doPostAsync|" + response.code() + "|" + body;
                    mHandler.sendEmptyMessage(MSG_WHAT);
                }
            }
//
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                // NOT UI Thread
//                if (response.isSuccessful()) {
//                    System.out.println(response.code());
//                    //System.out.println(response.body().string());
//                    String body = response.body().string();
//                    System.out.println(body);
//                    result = "doGetAsync|" + response.code() + "|" + body;
//                    mHandler.sendEmptyMessage(MSG_WHAT);
//                }
//            }
        });
    }




    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT:
                    textView1.setText(result);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

        ;
    };

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.button1:

                new Thread(new Runnable() {
                    public void run() {
                        try {
                            doPost();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            case R.id.button2:
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            doGet();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.button3:
                doPostAsync();
                break;
            case R.id.button4:
                doGetAsync();
                break;

            default:

                break;
        }

    }
}
