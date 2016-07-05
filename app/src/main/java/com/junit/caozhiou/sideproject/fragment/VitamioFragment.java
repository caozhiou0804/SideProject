package com.junit.caozhiou.sideproject.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.HomeActivity;
import com.junit.caozhiou.sideproject.activity.PlayVideoActivity;
import com.junit.caozhiou.sideproject.activity.PlayVideoListActivity;
import com.junit.caozhiou.sideproject.activity.SplashActivity;
import com.junit.caozhiou.sideproject.entity.ScreenBean;
import com.junit.caozhiou.sideproject.util.LocUtil;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VitamioFragment extends Fragment {

    private View contentView;
    private Button btn_go_to_tv;
    private Button btn_go_video_list;
    private ScreenBean screenBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == contentView) {
            contentView = inflater.inflate(R.layout.fragment_vitamio, container, false);
        }
        initView();
        return contentView;
    }

    private void initView() {

        Vitamio.isInitialized(getActivity());
        btn_go_to_tv = (Button) contentView.findViewById(R.id.btn_go_to_tv);
        btn_go_video_list = (Button) contentView.findViewById(R.id.btn_go_to_tv);
        btn_go_to_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                startActivity(intent);
            }
        });
        btn_go_video_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_list = new Intent(getActivity(), PlayVideoListActivity.class);
                startActivity(intent_list);
            }
        });
    }

}
