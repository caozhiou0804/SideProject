package com.junit.caozhiou.sideproject.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.OpenItemAdapter;
import com.junit.caozhiou.sideproject.view.DividerDecoration;

public class OpenItemRecyclerViewFragment extends Fragment {
    private View contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contentView = inflater.inflate(R.layout.fragment_open_item_recycler_view, container, false);

        initView();
        return contentView;
    }

    private void initView() {
        // 实例化控件
        final RecyclerView recycler_view_open_item = (RecyclerView) contentView.findViewById(R.id.recycler_view_open_item);

        // 设置启动列表的修改动画效果(默认为关闭状态)
//        recycler_view_open_item.getItemAnimator().setSupportsChangeAnimations(true);
        // 设置动画时长
        recycler_view_open_item.getItemAnimator().setChangeDuration(300);
        recycler_view_open_item.getItemAnimator().setMoveDuration(300);

        // 实现RecyclerView实现竖向列表展示模式
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler_view_open_item.setLayoutManager(layoutManager);
//        recycler_view_open_item.addItemDecoration(new DividerDecoration(getActivity()));
        // 实例化数据适配器并绑定在控件上
        final OpenItemAdapter adapter = new OpenItemAdapter();
        recycler_view_open_item.setAdapter(adapter);
    }
}
