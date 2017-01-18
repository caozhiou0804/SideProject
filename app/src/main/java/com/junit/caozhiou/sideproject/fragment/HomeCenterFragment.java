package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.junit.caozhiou.sideproject.R;

import java.util.HashMap;
import java.util.Map;

public class HomeCenterFragment extends Fragment {

    private View contentView;
    private RelativeLayout rl_home_container;

    Fragment f = null;

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
            initData();
            initViews();

        }
        return contentView;


    }

    private void initViews() {
        rl_home_container = (RelativeLayout) contentView.findViewById(R.id.rl_home_container);
    }

    private Map<Integer, Fragment> fragmentMap = new HashMap<>();

    private void initData() {

    }

    public void changeTabFragment(int menuItemId, boolean isReselection) {
    }
}
