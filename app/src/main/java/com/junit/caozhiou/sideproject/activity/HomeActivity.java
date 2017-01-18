package com.junit.caozhiou.sideproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.fragment.HomeCenterFragment;
import com.junit.caozhiou.sideproject.fragment.PersonLeftFragment;
import com.junit.caozhiou.sideproject.view.SlidingMenu;

public class HomeActivity extends AppCompatActivity{

    private SlidingMenu mSlidingMenu;

    private HomeCenterFragment homeCenterFragment;

    //    private TabInfoFragment tabInfoFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
//        tabInfoFragment = new TabInfoFragment();
        homeCenterFragment = new HomeCenterFragment();

        initSlidingMenu();
    }


    private void initSlidingMenu() {
        mSlidingMenu = (SlidingMenu) findViewById(R.id.slidingmenu);
        mSlidingMenu.setMenu(R.layout.sliding_left_frame);
        getSupportFragmentManager().beginTransaction().add(R.id.left_frame, new PersonLeftFragment()).commit();

        mSlidingMenu.setContent(R.layout.sliding_center_frame);

        mSlidingMenu.setSecondaryMenu(R.layout.sliding_right_frame);
//        if (getFragmentByTag(RightFragment.class) == null) {
//            getSupportFragmentManager().beginTransaction().add(R.id.right_frame, new RightFragment(), RightFragment.class.getName()).commit();
//        }

        if (homeCenterFragment != null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.center_frame,
                            homeCenterFragment).commit();
        }
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

    }

    public void toggleLeftSliding(boolean animate) {
        mSlidingMenu.toggle(animate);
    }
}


