package com.junit.caozhiou.sideproject.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.SimpleRecyclerViewAdapter;
import com.junit.caozhiou.sideproject.entity.VideoData;
import com.junit.caozhiou.sideproject.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通recycleView
 */
public class SimpleRecycleViewFragment extends Fragment {


    private View contentView;

    private RecyclerView recycler_view_simple;

    private LinearLayoutManager mLayoutManager;

    private SimpleRecyclerViewAdapter mAdapter;

    private List<VideoData> videoDatas = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == contentView) {

            contentView = inflater.inflate(R.layout.fragment_simple_recycle_view, container, false);
        }
        initData();
        initView();

        return contentView;
    }

    private void initData() {
        videoDatas = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory() + "/DCIM/Camera/test.mp4";
//        String path ="/sdcard/DCIM/Camera/test.mp4";
        for (int i = 1; i < 6; i++) {
            VideoData data = new VideoData();
            data.setVideoTitle("标题" + i);
            data.setVideoTime("2:30");
            data.setVideoPath(path);
            videoDatas.add(data);
        }
    }

    private void initView() {
        recycler_view_simple = (RecyclerView) contentView.findViewById(R.id.recycler_view_simple);
        recycler_view_simple.setHasFixedSize(true);
        //1.LinearLayoutManager 线性布局类型
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler_view_simple.setLayoutManager(mLayoutManager);
        //添加默认的动画效果
        recycler_view_simple.setItemAnimator(new DefaultItemAnimator());
        //添加分隔线
        recycler_view_simple.addItemDecoration(new RecycleViewDivider(getActivity(), OrientationHelper.VERTICAL));
        mAdapter = new SimpleRecyclerViewAdapter(getActivity(), new SimpleRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "点击了第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        }, videoDatas);
        recycler_view_simple.setAdapter(mAdapter);

    }

}
