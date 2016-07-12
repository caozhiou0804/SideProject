package com.junit.caozhiou.sideproject.adapter;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import android.widget.VideoView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.HomeActivity;
import com.junit.caozhiou.sideproject.activity.SplashActivity;
import com.junit.caozhiou.sideproject.entity.VideoData;
import com.junit.caozhiou.sideproject.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private VideoView mVideoView;

    public SimpleRecyclerViewAdapter(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener, List<VideoData> videoDatas) {
        this.mInflater = LayoutInflater.from(context);
        this.videoDatas = videoDatas;
        this.context = context;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
//        Vitamio.isInitialized(context);
    }

    private int time;

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
        time++;
        Log.d("onCreateViewHolder", "加载" + time + "次");
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecyclerItemClickListener != null) {
                    onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
                }
            }
        });
        viewHolder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex = (int) view.getTag();
                playPosition = -1;
                notifyDataSetChanged();
            }
        });
        return viewHolder;
    }

    private int currentIndex = -1;
    private int playPosition = -1;
    private boolean isPaused = false;
    private boolean isPlaying = false;

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d("onBindViewHolder", "加载" + time + "次");
        if (null != videoDatas && videoDatas.size() > 0) {
//            holder.tv_video_title.setText(videoDatas.get(position).getVideoTitle());
//            holder.tv_video_time.setText(videoDatas.get(position).getVideoTime());
            playVideo(position, holder);
        }
        holder.itemView.setTag(position);
//        startTranslateFromBottom(holder.v);
    }

    /**
     * 播放视频
     *
     * @param position
     * @param holder
     */
    private void playVideo(int position, final ViewHolder holder) {
        if (currentIndex == position) {
            Log.d("currentIndex", currentIndex + "currentIndex == position");
            holder.videoPlayBtn.setVisibility(View.INVISIBLE);
            holder.videoImage.setVisibility(View.INVISIBLE);
            holder.videoNameText.setVisibility(View.INVISIBLE);

            if (isPlaying || playPosition == -1) {
                if (mVideoView != null) {
                    Log.d("4", mVideoView + "mVideoView!=null");

                    mVideoView.setVisibility(View.GONE);
                    mVideoView.stopPlayback();
                    holder.mProgressBar.setVisibility(View.GONE);
                }
            }
//            mVideoView = (VideoView) convertView
//                    .findViewById(R.id.videoview);
            mVideoView.setVisibility(View.VISIBLE);
//            mMediaCtrl.setAnchorView(mVideoView);
//            mMediaCtrl.setMediaPlayer(mVideoView);
//            mVideoView.setMediaController(mMediaCtrl);
            mVideoView.requestFocus();
            holder.mProgressBar.setVisibility(View.VISIBLE);
            if (playPosition > 0 && isPaused) {
                mVideoView.start();
                isPaused = false;
                isPlaying = true;
                holder.mProgressBar.setVisibility(View.GONE);
            } else {
                mVideoView.setVideoPath(videoDatas.get(position).getVideoPath());
                isPaused = false;
                isPlaying = true;
                System.out.println("播放新的视频");
            }
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mVideoView != null) {
                        mVideoView.seekTo(0);
                        mVideoView.stopPlayback();
                        currentIndex = -1;
                        isPaused = false;
                        isPlaying = false;
                        holder.mProgressBar.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }
                }
            });
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    holder.mProgressBar.setVisibility(View.GONE);
                    mVideoView.start();
                }
            });

        } else {
            holder.videoPlayBtn.setVisibility(View.VISIBLE);
            holder.videoImage.setVisibility(View.VISIBLE);
            holder.videoNameText.setVisibility(View.VISIBLE);
            holder.mProgressBar.setVisibility(View.GONE);
        }
    }

//    private void startScale(View view) {
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
//        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
//        AnimatorSet set = new AnimatorSet();
//        set.play(scaleX).with(scaleY);
//        set.setDuration(400);
//        set.start();
//    }
//
//    private void startTranslateFromLeft(View view) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -ScreenUtil.getScreenWidth(context), 0);
//        animator.setDuration(400);
//        animator.start();
//    }
//
//    private void startTranslateFromRight(View view) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", ScreenUtil.getScreenWidth(context), 0);
//        animator.setDuration(400);
//        animator.start();
//    }
//
//    private void startTranslateFromBottom(View view) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 200, 0);
//        animator.setDuration(400);
//        animator.start();
//    }

    @Override
    public int getItemCount() {
        return videoDatas.size();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView videoImage;
        TextView videoNameText;
        ImageButton videoPlayBtn;
        ProgressBar mProgressBar;
        public View v;

        public ViewHolder(View view) {
            super(view);
            v = view;
            videoImage = (ImageView) view.findViewById(R.id.video_image);
            videoNameText = (TextView) view.findViewById(R.id.tv_video_title);
            videoPlayBtn = (ImageButton) view.findViewById(R.id.btn_play_video);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
            mVideoView = (VideoView) view.findViewById(R.id.videoview);
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
