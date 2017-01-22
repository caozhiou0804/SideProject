package com.junit.caozhiou.sideproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.LoginEvent;
import com.junit.caozhiou.sideproject.entity.UserBean;
import com.junit.caozhiou.sideproject.log.L;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.MyToast;
import com.junit.caozhiou.sideproject.util.PreferenceUtil;
import com.junit.caozhiou.sideproject.view.cloudview.CloudProgressDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private String username;
    private String password;
    @Bind(R.id.input_name)
    EditText input_name;
    @Bind(R.id.input_password)
    EditText input_password;
    @Bind(R.id.btn_login)
    Button btn_login;

    private CloudProgressDialog cloudProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        cloudProgressDialog = new CloudProgressDialog(LoginActivity.this, "让数据飞一会儿..");
    }

    @OnClick(R.id.btn_login)
    public void register(View view) {
        if (!validate())
            return;
        reqLogin();
    }

    /**
     * 获取位置信息并登录
     */
    private void reqLogin() {
        MyApplication.getInstance().startLocationService(mListener);
    }
    int count_login = 1;
    int count_loc = 1;
    boolean isFirstIn =true;
    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (isFirstIn) {
                if (null != location && location.getLocType() != BDLocation.TypeServerError) {

                    String url = Constant.SERVER_IP + "Userfeature/userLogin";
                    username = input_name.getText().toString();
                    password = input_password.getText().toString();
                    String clientId = MyApplication.getInstance().getClientId();
                    OkHttpUtils
                            .post()
                            .url(url)
                            .addParams("phone", input_name.getText().toString())//
                            .addParams("password", input_password.getText().toString())//
                            .addParams("latitude", location.getLatitude() + "")
                            .addParams("longitude", location.getLongitude() + "")
                            .addParams("street", location.getStreet())
                            .addParams("clientId", clientId)//
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    L.d("Splash", e.toString());
//                                    cloudProgressDialog.cancel();
                                    MyApplication.getInstance().stopLocationService();
                                    MyToast.show(LoginActivity.this, e+"", 1500);
                                }

                                @Override
                                public void onResponse(String response, int id) {
//                                    cloudProgressDialog.cancel();
                                    Gson gson = new Gson();
                                    UserBean userBean = gson.fromJson(response, UserBean.class);
                                    if("0".equals(userBean.getStatus())){
                                        MyApplication.getInstance().setUserDataBean(userBean.getData());
                                        PreferenceUtil.putString(Constant.USERNAME, username);
                                        PreferenceUtil.putString(Constant.PASSWORD, password);
                                        PushManager.getInstance().bindAlias(getApplicationContext(), userBean.getData().getUserId());
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        EventBus.getDefault().post(new LoginEvent(LoginEvent.TYPE_LOGIN_SUCCESS));
                                        isFirstIn = false;
                                        MyApplication.getInstance().stopLocationService();
                                        finish();
                                    }else{
                                        MyToast.show(LoginActivity.this, userBean.getMessage(), 1500);
                                    }
                                }
                            });
                }

                L.d("LoginActivity", "登录请求了 " + count_login++ + " 次");
            }
            L.d("LoginActivity", "地理位置刷新了 " + count_loc++ + " 次");
        }

    };


    public boolean validate() {
        boolean valid = true;

        String name = input_name.getText().toString();
        String password = input_password.getText().toString();

        if (name.isEmpty() || name.length() < 11) {
            input_name.setError("请输入11位手机号");
            valid = false;
        } else {
            input_name.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            input_password.setError("请输入至少六位密码");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }
}

