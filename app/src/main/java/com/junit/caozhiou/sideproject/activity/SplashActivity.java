package com.junit.caozhiou.sideproject.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.UserBean;
import com.junit.caozhiou.sideproject.log.L;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.service.DemoIntentService;
import com.junit.caozhiou.sideproject.service.DemoPushService;
import com.junit.caozhiou.sideproject.util.PreferenceUtil;

import okhttp3.Call;

public class SplashActivity extends FragmentActivity {

    private static final int ANIM_LAST_TIME = 3000;
    private ImageView imageView_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView_splash = (ImageView) findViewById(R.id.imageView_splash);
        login();
        startTranslationAnim(imageView_splash);
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
    }

    /**
     * 页面跳转
     *
     * @param isLogin 是否登录
     */
    private void jumpPage(final boolean isLogin) {
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLogin) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginEnterenceActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, ANIM_LAST_TIME);
    }

    private Handler intentHandler = new Handler();

    private void initView() {
        // initAnim();
    }

    private void startTranslationAnim(ImageView imageView) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 2f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.5f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.3f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(alpha).with(scaleY);
        set.setDuration(ANIM_LAST_TIME + 2000);
        set.start();
    }

    /**
     * 静默登录
     */
    private void login() {

        if (TextUtils.isEmpty(PreferenceUtil.getString(Constant.USERNAME))
                || TextUtils.isEmpty(PreferenceUtil.getString(Constant.PASSWORD))) {
            jumpPage(false);
        } else {
            String url = Constant.SERVER_IP + "Userfeature/userLogin";
            OkHttpUtils
                    .post()
                    .url(url)
                    .addParams("phone", PreferenceUtil.getString(Constant.USERNAME))//
                    .addParams("password", PreferenceUtil.getString(Constant.PASSWORD))//
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            L.d("Splash", e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            Gson gson = new Gson();
                            UserBean userBean = gson.fromJson(response, UserBean.class);
                            MyApplication.getInstance().setUserDataBean(userBean.getData());
                            jumpPage(true);
                        }
                    });
        }
    }

    //个推
}
