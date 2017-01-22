package com.junit.caozhiou.sideproject.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
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
import com.junit.caozhiou.sideproject.service.LocationService;
import com.junit.caozhiou.sideproject.util.MyToast;
import com.junit.caozhiou.sideproject.util.PreferenceUtil;

import okhttp3.Call;

public class SplashActivity extends FragmentActivity {

    private static final int ANIM_LAST_TIME = 2500;
    private ImageView imageView_splash;
    private LocationService locationService;
    private boolean isFirstIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView_splash = (ImageView) findViewById(R.id.imageView_splash);
        startTranslationAnim(imageView_splash);
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);
        login();
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
                MyApplication.getInstance().stopLocationService();
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
        ObjectAnimator alpha = ObjectAnimator.ofFloat(imageView, "alpha", 1f, 0.5f);
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
            MyApplication.getInstance().startLocationService(mListener);
        }
    }

    //个推

    //百度地图初始化
    @Override
    protected void onStart() {
        super.onStart();
//        locationService = MyApplication.getInstance().locationService;
//        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
//        locationService.registerListener(mListener);
//        //注册监听
//        int type = 0;
//        if (type == 0) {
//            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        } else if (type == 1) {
//            locationService.setLocationOption(locationService.getOption());
//        }
//        locationService.start();// 定位SDK
    }

    /***
     * Stop location service
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
//        locationService.unregisterListener(mListener); //注销掉监听
//        locationService.stop(); //停止定位服务
        super.onStop();
    }

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
                    OkHttpUtils
                            .post()
                            .url(url)
                            .addParams("phone", PreferenceUtil.getString(Constant.USERNAME))//
                            .addParams("password", PreferenceUtil.getString(Constant.PASSWORD))//
                            .addParams("latitude", location.getLatitude() + "")
                            .addParams("longitude", location.getLongitude() + "")
                            .addParams("street", location.getStreet())
                            .build()
                            .execute(
                                    new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            L.d("Splash", e.toString());
                                            jumpPage(false);
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            Gson gson = new Gson();
                                            UserBean userBean = gson.fromJson(response, UserBean.class);
                                            if ("0".equals(userBean.getStatus())) {
                                                MyApplication.getInstance().setUserDataBean(userBean.getData());
                                                jumpPage(true);
                                            } else {
                                                MyToast.show(SplashActivity.this, userBean.getMessage(), 1500);
                                            }
                                        }
                                    });
                } else {
                    jumpPage(false);
                }
                isFirstIn = false;
                L.d("SplashActivity", "登录请求了 " + count_login++ + " 次");
            }
            L.d("SplashActivity", "地理位置刷新了 " + count_loc++ + " 次");
        }

    };
    int count_login = 1;
    int count_loc = 1;
}
