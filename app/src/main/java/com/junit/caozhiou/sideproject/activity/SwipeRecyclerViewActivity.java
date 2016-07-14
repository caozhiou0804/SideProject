package com.junit.caozhiou.sideproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.SwipeRecyclerViewAdapter1;
import com.junit.caozhiou.sideproject.adapter.swipe.expandRecyclerviewadapter.StickyRecyclerHeadersDecoration;
import com.junit.caozhiou.sideproject.view.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SwipeRecyclerViewActivity extends Activity implements SwipeRecyclerViewAdapter1.OnDeleteItemListener {

    private RecyclerView recycler_view_swipe;

    private SwipeRecyclerViewAdapter1 swipeRecyclerViewAdapter;

    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recycler_view);

        initView();

    }

    private void initView() {
        recycler_view_swipe = (RecyclerView) findViewById(R.id.recycler_view_swipe);
        int orientation = LinearLayoutManager.VERTICAL;
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, false);
        recycler_view_swipe.setLayoutManager(layoutManager);
        initData();
        swipeRecyclerViewAdapter = new SwipeRecyclerViewAdapter1(SwipeRecyclerViewActivity.this, datas, this);
        recycler_view_swipe.setAdapter(swipeRecyclerViewAdapter);
//        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
//        recycler_view_swipe.addItemDecoration(headersDecor);
        recycler_view_swipe.addItemDecoration(new DividerDecoration(this));
    }

    private void initData() {
        datas = new ArrayList<String>();
        for (int i = 0; i < 15; i++) {
            datas.add("item" + i);
        }
    }

    @Override
    public void deleteItem(int position) {
        datas.remove(datas.get(position));
        swipeRecyclerViewAdapter.notifyDataSetChanged();
    }

}
