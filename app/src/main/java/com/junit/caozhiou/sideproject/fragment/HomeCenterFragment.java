package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.util.FragmentTabUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeCenterFragment extends Fragment {

    private View contentView;

    @Bind(R.id.rg_tab)
    RadioGroup rg_tab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<Fragment>();
    FragmentTabUtils fragmentTabUtils;


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
            ButterKnife.bind(this, contentView);
            initview();

        }
        return contentView;


    }

    private void initview() {
        fragments.add(new TabInfoFragment());
        fragments.add(new FunctionsFragment());
        fragments.add(new OneFragment());
        fragments.add(new MineFragment());

        fragmentTabUtils = new FragmentTabUtils(viewPager, getChildFragmentManager(), fragments, rg_tab);

    }


}
