package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.view.MyScollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 当前类注释:第一个Fragment
 */
public class OneFragment extends Fragment {

    @Bind(R.id.sv_one)
    MyScollView sv_one;

    @Bind(R.id.layout_header_image)
    ImageView layout_header_image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.one_frag_layout, container, false);
        ButterKnife.bind(this, mView);
        initview();
        return mView;
    }

    private void initview() {
        sv_one.setZoomRatio(MyScollView.ZOOM_X2);
        sv_one.setParallaxImageView(layout_header_image);
    }
}
