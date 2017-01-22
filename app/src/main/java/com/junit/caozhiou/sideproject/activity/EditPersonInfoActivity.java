package com.junit.caozhiou.sideproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.PersonInfoEvent;
import com.junit.caozhiou.sideproject.entity.UserBean;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.MyProgressDialog;
import com.junit.caozhiou.sideproject.util.MyToast;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class EditPersonInfoActivity extends AppCompatActivity {

    @Bind(R.id.btn_edit_info)
    Button btn_edit_info;

    @Bind(R.id.et_my_info_name)
    EditText et_my_info_name;
    @Bind(R.id.et_my_info_sex)
    EditText et_my_info_sex;
    @Bind(R.id.et_my_info_birthday)
    EditText et_my_info_birthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_person_info);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_edit_info)
    public void editInfo(View view) {
        final MyProgressDialog dialog = new MyProgressDialog(this, R.style.dialog_tran);
        dialog.show();
        String url = Constant.SERVER_IP + "Userfeature/uploadUserInfo";
        String username = et_my_info_name.getText().toString();
        String sex = et_my_info_sex.getText().toString();
        String birth_day = et_my_info_birthday.getText().toString();
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId", MyApplication.getInstance().getUserDataBean().getUserId())//
                .addParams("username", username)
                .addParams("sex", sex)
                .addParams("birth_day", birth_day)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        MyToast.show(EditPersonInfoActivity.this, e + "", 1500);
                        dialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        UserBean userBean = gson.fromJson(response, UserBean.class);
                        if("0".equals(userBean.getStatus())) {
                            MyApplication.getInstance().setUserDataBean(userBean.getData());
                            PersonInfoEvent personInfoEvent = new PersonInfoEvent(PersonInfoEvent.UPDATE_INFO);
                            personInfoEvent.setUserDataBean(userBean.getData());
                            EventBus.getDefault().post(personInfoEvent);
                            finish();
                        }
                            MyToast.show(EditPersonInfoActivity.this, userBean.getMessage(), 1500);
                        dialog.dismiss();
                    }
                });
    }


}
