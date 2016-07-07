package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;

import java.util.ArrayList;
import java.util.List;

public class HomeCenterFragment extends Fragment {

    private TabLayout tab_layout_home_center;

    private ViewPager viewpager_home_center;
    private View contentView;
    private List<Fragment> fragments;
    private CNKFixedPagerAdapter mPagerAdater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == contentView) {
            contentView = inflater.inflate(R.layout.fragment_home_center, container, false);
            initViews();
            initData();

        }
        return contentView;
    }

    private void initViews() {
        tab_layout_home_center = (TabLayout) contentView.findViewById(R.id.tab_layout_home_center);
        viewpager_home_center = (ViewPager) contentView.findViewById(R.id.viewpager_home_center);

        tab_layout_home_center.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = mPagerAdater.getTabView(tab.getPosition());
                TextView tv = (TextView)view.findViewById(R.id.textView);
                tv.setTextColor(getActivity().getResources().getColor(R.color.white));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = mPagerAdater.getTabView(tab.getPosition());
                TextView tv = (TextView)view.findViewById(R.id.textView);
                tv.setTextColor(getActivity().getResources().getColor(R.color.pink_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData() {
        String[] titles = new String[]{"RecyclerView", "多功能", "我"};
        fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OneFragment oneFragment = new OneFragment();
            Bundle bundle = new Bundle();
            bundle.putString("extra", titles[i]);
            oneFragment.setArguments(bundle);
            if (i == 0) {
                fragments.add(new TabInfoFragment());
            } else if(i==1){
                fragments.add(new FunctionsFragment());
            }
            else{
                fragments.add(oneFragment);
            }
        }
        //创建Fragment的 ViewPager 自定义适配器
        mPagerAdater = new CNKFixedPagerAdapter(getChildFragmentManager(), getActivity());
        //设置显示的标题
        mPagerAdater.setTitles(titles);
        //设置需要进行滑动的页面Fragment
        mPagerAdater.setFragments(fragments);

        viewpager_home_center.setAdapter(mPagerAdater);
        tab_layout_home_center.setupWithViewPager(viewpager_home_center);


        //设置Tablayout
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        tab_layout_home_center.setTabMode(TabLayout.MODE_FIXED);
        //设置自定义Tab--加入图标的demo
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = tab_layout_home_center.getTabAt(i);
            tab.setCustomView(mPagerAdater.getTabView(i));

        }


        }


}
