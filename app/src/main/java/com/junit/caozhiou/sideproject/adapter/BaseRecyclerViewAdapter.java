package com.junit.caozhiou.sideproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;

/**
 * 作者：lubote on 2016/6/22 15:02
 * 邮箱：nj.caozhiou@dhjt.com
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.ViewHolder>{

    //一个RecyclerView的Item加载是有顺序的，类似于Activity的生命周期（姑且这么叫），具体可以对adapter的每个方法进行重写打下日志进行查看，具体大致为：
    // getItemViewType(获取显示类型，返回值可在onCreateViewHolder中拿到，以决定加载哪种ViewHolder)
    // onCreateViewHolder(加载ViewHolder的布局)
    // onViewAttachedToWindow（当Item进入这个页面的时候调用）
    //onBindViewHolder(将数据绑定到布局上，以及一些逻辑的控制就写这啦)
    //   onViewDetachedFromWindow（当Item离开这个页面的时候调用）
    //   onViewRecycled(当Item被回收的时候调用)


    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view){
            super(view);
        }
    }


}
