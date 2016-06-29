package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.WaterFallAdapter;
import com.junit.caozhiou.sideproject.entity.WaterFallData;
import com.junit.caozhiou.sideproject.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持拖拽的recyclerView
 */
public class WaterFallRecyclerViewFragment extends Fragment {


    private View contentView;

    private RecyclerView recycler_view_waterfall;

    private List<WaterFallData> waterFallDatas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == contentView) {
            contentView = inflater.inflate(R.layout.fragment_water_fall_recycler_view, container, false);
        }
        initView();
        return contentView;
    }

    private void initView() {
        recycler_view_waterfall = (RecyclerView) contentView.findViewById(R.id.recycler_view_waterfall);
        //设置layoutManager
        recycler_view_waterfall.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //设置adapter
        initData();
        WaterFallAdapter adapter = new WaterFallAdapter(waterFallDatas);
        recycler_view_waterfall.setAdapter(adapter);
        //设置item之间的间隔
//        RecycleViewDivider recycleViewDivider = new RecycleViewDivider(getActivity(),
//                OrientationHelper.HORIZONTAL);
//        recycler_view_waterfall.addItemDecoration(recycleViewDivider);
    }

    private void initData() {
        waterFallDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WaterFallData fallData = new WaterFallData();
            fallData.setTitle("标题" + i);
            if(i/3==0){

                fallData.setImg(R.drawable.welcom);
            }else{
                fallData.setImg(R.drawable.unionpay);
            }
            waterFallDatas.add(fallData);
        }
    }
}
