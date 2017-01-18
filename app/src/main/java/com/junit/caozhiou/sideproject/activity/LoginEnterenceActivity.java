package com.junit.caozhiou.sideproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.entity.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginEnterenceActivity extends FragmentActivity {

    @Bind(R.id.iv_outer)
    ImageView iv_outer;

    @Bind(R.id.btn_to_login)
    Button btn_to_login;

    @Bind(R.id.btn_to_registe)
    Button btn_to_registe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_enterence);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ButterKnife.bind(this);
        //注册
        if (null != EventBus.getDefault())
            EventBus.getDefault().register(this);
        iv_outer.startAnimation(rotaAnimation());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.btn_to_login)
    public void toLogin(View view) {
        Intent intent = new Intent(LoginEnterenceActivity.this,
                LoginActivity.class);
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        if (LoginEvent.TYPE_LOGIN_SUCCESS.equals(event.getType())) {
            finish();
        }

    }

    @OnClick(R.id.btn_to_registe)
    public void toRegiste(View view) {
        Intent intent = new Intent(LoginEnterenceActivity.this,
                RegisterActivity.class);
        startActivity(intent);

    }

    /**
     * 不间断旋转动画
     *
     * @return
     */
    private Animation rotaAnimation() {

        Animation anim = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        anim.setInterpolator(new LinearInterpolator());// 不停顿
        anim.setRepeatCount(-1);// 重复次数
        anim.setFillAfter(false);// 停在最后
        anim.setDuration(4000);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Toast.makeText(getActivity(), "开始了", 0).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Toast.makeText(getActivity(), "重复了", 0).show();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Toast.makeText(getActivity(), "结束了", 0).show();
            }
        });
        // 动画开始
        // bt.startAnimation(an);
        return anim;

    }
}
