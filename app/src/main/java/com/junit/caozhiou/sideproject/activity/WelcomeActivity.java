package com.junit.caozhiou.sideproject.activity;

import android.animation.ArgbEvaluator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.WelcomePagerAdapter;
import com.junit.caozhiou.sideproject.view.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager_welcome;
    private CirclePageIndicator indicator_welcome;

    private List<String> datas;
    private int[] backgroundColors = new int[]{
            R.color.androidColorA,
            R.color.androidColorB,
            R.color.androidColorC,
            R.color.androidColorD,
            R.color.androidColorE,
            R.color.blue
    };
    private View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentView = LayoutInflater.from(this).inflate(R.layout.activity_welcome, null);
        setContentView(contentView);

        initView();
    }

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private void initView() {
        datas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            datas.add("item" + i);
        }
        viewPager_welcome = (ViewPager) findViewById(R.id.viewPager_welcome);
        indicator_welcome = (CirclePageIndicator) findViewById(R.id.indicator_welcome);

//        indicator_welcome.setViewPager(new ViewPagerWrapper(getContext(), viewPager), getPagesCount());
        viewPager_welcome.setAdapter(new WelcomePagerAdapter(WelcomeActivity.this, datas));
        indicator_welcome.setViewPager(viewPager_welcome, 6);

        viewPager_welcome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                int nextColorPosition = position + 1;
//                if (nextColorPosition >= 6) {
//                    nextColorPosition %= 6;
//                }
//                if (position < 5) {
//                    viewPager_welcome.setBackgroundColor(getResources().getColor((Integer) argbEvaluator.evaluate(positionOffset, backgroundColors[position], backgroundColors[nextColorPosition])));
//                } else if (position == 5) {
//                    viewPager_welcome.setBackgroundColor(getResources().getColor(backgroundColors[position]));
//                    if (contentView != null) {
//                        contentView.setAlpha(1 - positionOffset);
//                    }
//                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        viewPager_welcome.setPageTransformer(true, new FragmentTransformer());
    }


}
