package com.junit.caozhiou.sideproject.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.RefreshFootAdapter;
import com.junit.caozhiou.sideproject.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * 下拉刷新recyclerView
 */
public class PullToRefreshRecyclerViewFragment extends Fragment {


    private View contentView;

    private SwipeRefreshLayout swiperefreshlayout_pull_to_refresh;

    private RecyclerView recycler_view_pull_to_refresh;

    private LinearLayoutManager linearLayoutManager;

    private RefreshFootAdapter adapter;

    private int lastVisibleItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == contentView) {
            contentView = inflater.inflate(R.layout.fragment_pull_to_refresh_recycler_view, container, false);
        }
        initView();
        return contentView;
    }

    private void initView() {
        swiperefreshlayout_pull_to_refresh = (SwipeRefreshLayout) contentView.findViewById(R.id.swiperefreshlayout_pull_to_refresh);
        recycler_view_pull_to_refresh = (RecyclerView) contentView.findViewById(R.id.recycler_view_pull_to_refresh);

        //设置刷新时动画的颜色，可以设置4个
        swiperefreshlayout_pull_to_refresh.setProgressBackgroundColorSchemeResource(android.R.color.white);
        swiperefreshlayout_pull_to_refresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        swiperefreshlayout_pull_to_refresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler_view_pull_to_refresh.setLayoutManager(linearLayoutManager);
        //添加分隔线
        recycler_view_pull_to_refresh.addItemDecoration(new RecycleViewDivider(getActivity(), OrientationHelper.VERTICAL));
        recycler_view_pull_to_refresh.setAdapter(adapter = new RefreshFootAdapter(getActivity()));
        swiperefreshlayout_pull_to_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("czo", "invoke onRefresh...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<String> newDatas = new ArrayList<String>();
                        for (int i = 0; i < 5; i++) {
                            int index = i + 1;
                            newDatas.add("new item" + index);
                        }
                        adapter.addItem(newDatas);
                        swiperefreshlayout_pull_to_refresh.setRefreshing(false);
                        Toast.makeText(getActivity(), "更新了五条数据...", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }
        });
        //RecyclerView滑动监听
        recycler_view_pull_to_refresh.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()) {
                    adapter.changeMoreStatus(RefreshFootAdapter.LOADING_MORE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            List<String> newDatas = new ArrayList<String>();
                            for (int i = 0; i < 5; i++) {
                                int index = i + 1;
                                newDatas.add("more item" + index);
                            }
                            adapter.addMoreItem(newDatas);
                            adapter.changeMoreStatus(RefreshFootAdapter.PULLUP_LOAD_MORE);
                        }
                    }, 2500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


    }
}
