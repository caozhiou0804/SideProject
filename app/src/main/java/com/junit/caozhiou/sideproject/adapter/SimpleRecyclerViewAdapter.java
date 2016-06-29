package com.junit.caozhiou.sideproject.adapter;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.HomeActivity;
import com.junit.caozhiou.sideproject.activity.SplashActivity;
import com.junit.caozhiou.sideproject.entity.VideoData;
import com.junit.caozhiou.sideproject.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * 当前类注释:RecyclerView 数据自定义Adapter
 * 作者：lubote on 2016/6/21 09:49
 * 邮箱：nj.caozhiou@dhjt.com
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    private LayoutInflater mInflater;
    private List<VideoData> videoDatas = null;


    private Context context;

    public SimpleRecyclerViewAdapter(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener, List<VideoData> videoDatas) {
        this.mInflater = LayoutInflater.from(context);
        this.videoDatas = videoDatas;
        this.context = context;
//        for (int i = 0; i < 20; i++) {
//            int index = i + 1;
//            mTitles.add("item" + index);
//        }
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
        Vitamio.isInitialized(context);
    }

    /**
     * item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = mInflater.inflate(R.layout.item_simple_recycler_layout, parent, false);
        //这边可以做一些属性设置，甚至事件监听绑定
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
                }
            }
        });

        return viewHolder;
    }
private  int oldPos=-1;
    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (null != videoDatas && videoDatas.size() > 0) {
            holder.tv_video_title.setText(videoDatas.get(position).getVideoTitle());
            holder.tv_video_time.setText(videoDatas.get(position).getVideoTime());

            final MediaController controller = new MediaController(context);
            holder.btn_play_video.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Toast.makeText(context, "按钮" + position, Toast.LENGTH_LONG).show();

                        holder.surface_view_simple.setMediaController(controller);
                        holder.surface_view_simple.requestFocus();

                        holder.surface_view_simple.setVideoPath(videoDatas.get(position).getVideoPath());

                        holder.surface_view_simple.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {

                                // optional need Vitamio 4.0
                                mediaPlayer.setPlaybackSpeed(1.0f);

                            }
                        });


                }
            });

        }
        holder.itemView.setTag(position);

        startTranslateFromBottom(holder.v);
    }

    private void startScale(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY);
        set.setDuration(400);
        set.start();
    }

    private void startTranslateFromLeft(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -ScreenUtil.getScreenWidth(context), 0);
        animator.setDuration(400);
        animator.start();
    }

    private void startTranslateFromRight(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", ScreenUtil.getScreenWidth(context), 0);
        animator.setDuration(400);
        animator.start();
    }

    private void startTranslateFromBottom(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 200, 0);
        animator.setDuration(400);
        animator.start();
    }

    @Override
    public int getItemCount() {
        return videoDatas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_video_title, tv_video_time;
        private Button btn_play_video;
        private VideoView surface_view_simple;


        public View v;

        public ViewHolder(View view) {
            super(view);
            v = view;
            tv_video_title = (TextView) view.findViewById(R.id.tv_video_title);
            tv_video_time = (TextView) view.findViewById(R.id.tv_video_time);
            btn_play_video = (Button) view.findViewById(R.id.btn_play_video);
            surface_view_simple = (VideoView) view.findViewById(R.id.surface_view_simple);

        }
    }

    public interface OnRecyclerItemClickListener {
        /**
         * item view 回调方法
         *
         * @param view     被点击的view
         * @param position 点击索引
         */
        void onItemClick(View view, int position);
    }

//    //添加数据
//    public void addItem(String data, int position) {
//        mTitles.add(position, data);
//        notifyItemInserted(position);
//    }
//
//    //删除数据
//    public void removeItem(String data) {
//        int position = mTitles.indexOf(data);
//        mTitles.remove(position);
//        notifyItemRemoved(position);
//    }
}
