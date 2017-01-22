package com.junit.caozhiou.sideproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.BaseBean;
import com.junit.caozhiou.sideproject.log.L;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.MyToast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    @Bind(R.id.input_name)
    EditText input_name;
    @Bind(R.id.input_email)
    EditText input_email;
    @Bind(R.id.input_password)
    EditText input_password;
    @Bind(R.id.btn_registe)
    Button btn_registe;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_registe)
    public void register(View view) {
        if (!validate())
            return;
            reqRegiste();
    }
    /**
     * 注册帐号
     */
    private void reqRegiste() {
        String url = Constant.SERVER_IP + "Userfeature/registeMember";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("phone", input_name.getText().toString())//
                .addParams("password", input_password.getText().toString())//
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.d("Splash", e.toString());
                        MyToast.show(RegisterActivity.this, e+"", 1500);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        BaseBean baseBean = gson.fromJson(response,BaseBean.class);
                        if ("0".equals(baseBean.getStatus())) {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            MyToast.show(RegisterActivity.this, baseBean.getMessage(), 1500);
                        }

                    }
                });

    }


    public boolean validate() {
        boolean valid = true;

        String name = input_name.getText().toString();
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();

        if (name.isEmpty() || name.length() < 11) {
            input_name.setError("请输入11位手机号");
            valid = false;
        } else {
            input_name.setError(null);
        }

      /*  if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_email.setError("enter a valid email address");
            valid = false;
        } else {
            input_email.setError(null);
        }*/

        if (password.isEmpty() || password.length() < 6 ) {
            input_password.setError("请输入至少六位密码");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }
}
