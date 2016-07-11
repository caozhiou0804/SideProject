package com.junit.caozhiou.sideproject.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.base.BaseFragmentActivity;
import com.junit.caozhiou.sideproject.util.ScreenUtil;

public class SplashActivity extends BaseFragmentActivity {

    private ImageView iv_splash01;
    private ImageView iv_splash02;
    private ImageView iv_splash03;
    private ImageView iv_splash04;
    private static final int ANIM_LAST_TIME = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);
    }

    private Handler intentHandler = new Handler();

    private void initView() {
        iv_splash01 = (ImageView) findViewById(R.id.iv_splash01);
        iv_splash02 = (ImageView) findViewById(R.id.iv_splash02);
        iv_splash03 = (ImageView) findViewById(R.id.iv_splash03);
        iv_splash04 = (ImageView) findViewById(R.id.iv_splash04);
        initAnim();
    }

    private void initAnim() {
        iv_splash01.setVisibility(View.INVISIBLE);
        iv_splash02.setVisibility(View.INVISIBLE);
        iv_splash03.setVisibility(View.INVISIBLE);
        iv_splash04.setVisibility(View.INVISIBLE);
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_splash01.setVisibility(View.VISIBLE);
                startTranslationAnim(iv_splash01);
            }
        }, 0);
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_splash02.setVisibility(View.VISIBLE);
                startTranslationAnim(iv_splash02);
            }
        }, ANIM_LAST_TIME);
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_splash03.setVisibility(View.VISIBLE);
                startTranslationAnim(iv_splash03);
            }
        }, ANIM_LAST_TIME * 2);
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iv_splash04.setVisibility(View.VISIBLE);
                startTranslationAnim(iv_splash04);
            }
        }, ANIM_LAST_TIME * 3);
        intentHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initAnim();
            }
        }, ANIM_LAST_TIME * 4);
    }

    private void startTranslationAnim(ImageView imageView) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.3f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.3f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(ANIM_LAST_TIME);
        set.start();


    }
}
