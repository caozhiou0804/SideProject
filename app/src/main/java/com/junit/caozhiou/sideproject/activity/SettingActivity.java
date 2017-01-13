package com.junit.caozhiou.sideproject.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

import com.junit.caozhiou.sideproject.R;

public class SettingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //拿到屏幕的大小
        int width = this.getWindowManager().getDefaultDisplay().getWidth();

        Window window = this.getWindow();
        LayoutParams params = window.getAttributes();
        params.width = width;
        this.getWindow().setAttributes(params);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getY() > 0 && event.getY() < 50 && event.getX() > getWindow().getAttributes().width - 50) {
            this.finish();
            return super.onTouchEvent(event);
        } else {
            return false;
        }

    }


}
