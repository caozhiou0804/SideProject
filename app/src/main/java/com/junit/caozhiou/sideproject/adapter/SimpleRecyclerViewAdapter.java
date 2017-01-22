package com.junit.caozhiou.sideproject.adapter;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.entity.UserDataBean;
import com.junit.caozhiou.sideproject.util.DateUtil;
import com.junit.caozhiou.sideproject.util.ScreenUtil;
import com.junit.caozhiou.sideproject.view.SwipeItemLayout;
import com.junit.caozhiou.sideproject.view.viewpager.AdLoopView;
import com.junit.caozhiou.sideproject.view.viewpager.internal.ItemData;
import com.junit.caozhiou.sideproject.view.viewpager.internal.LoopData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 当前类注释:RecyclerView 数据自定义Adapter
 * 作者：lubote on 2016/6/21 09:49
 * 邮箱：nj.caozhiou@dhjt.com
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    private LayoutInflater mInflater;
    private List<UserDataBean> userBeanList = null;

    private Context context;


    private static final int TYPE_VIEWPAGER = 0x01;
    private static final int TYPE_GRIDVIEW = 0x02;
    private static final int TYPE3 = 0x03;
    private static final int TYPE4 = 0x04;
    /**
     * 当前处于打开状态的item
     */
    private List<SwipeItemLayout> mOpenedSil = new ArrayList<>();

    public SimpleRecyclerViewAdapter(Context context, OnRecyclerItemClickListener onRecyclerItemClickListener, List<UserDataBean> userBeanList) {
        this.mInflater = LayoutInflater.from(context);
        this.userBeanList = userBeanList;
        this.context = context;
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public int getItemCount() {
        return userBeanList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0) {
            type = TYPE_VIEWPAGER;
        }
//        else if (position > 0 && position <= 5) {
//            type = TYPE_GRIDVIEW;
//        } else if (position > 5 && position < 10) {
//            type = TYPE3;
//        }
        else
            type = TYPE_GRIDVIEW;
        return type;
    }

    /**
     * item显示类型
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_VIEWPAGER) {
            final View view = mInflater.inflate(R.layout.item_top_viewpager, parent, false);
            ViewPagerViewHolder viewHolder = new ViewPagerViewHolder(view);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onRecyclerItemClickListener != null) {
//                        onRecyclerItemClickListener.onItemClick(view, (int) view.getTag());
//                    }
//                }
//            });
            return viewHolder;
        } else if (viewType == TYPE_GRIDVIEW) {
            final View view = mInflater.inflate(R.layout.item_nearby_person, parent, false);
            FootViewHolder viewHolder = new FootViewHolder(view);
            return viewHolder;
        } else if (viewType == TYPE3) {
            final View view = mInflater.inflate(R.layout.item_drag_recycler_view, parent, false);
            FootViewHolder viewHolder = new FootViewHolder(view);
            return viewHolder;
        } else {
            final View view = mInflater.inflate(R.layout.item_waterfall, parent, false);
            FootViewHolder viewHolder = new FootViewHolder(view);
            return viewHolder;
        }
    }

    private LoopData loopData;
    private List<ItemData> itemDatas;

    private void intiViewPagerData() {
        itemDatas = new ArrayList<>();
        loopData = new LoopData();

        ItemData itemData1 = new ItemData();
        itemData1.setImgUrl("http://img4.imgtn.bdimg.com/it/u=3087959955,1627184380&fm=21&gp=0.jpg");
        itemData1.setLink("http://www.baidu.com");

        ItemData itemData2 = new ItemData();
        itemData2.setImgUrl("http://photo.enterdesk.com/2011-11-19/enterdesk.com-CD4E8814A52D88335DC2EAB725FBFF5D.jpg");
        itemData2.setLink("http://www.baidu.com");

        ItemData itemData3 = new ItemData();
        itemData3.setImgUrl("http://img0.imgtn.bdimg.com/it/u=3752951180,265738684&fm=21&gp=0.jpg");
        itemData3.setLink("http://www.baidu.com");

        ItemData itemData4 = new ItemData();
        itemData4.setImgUrl("http://img5.imgtn.bdimg.com/it/u=3310535289,2501327088&fm=21&gp=0.jpg");
        itemData4.setLink("http://www.baidu.com");

        itemDatas.add(itemData1);
        itemDatas.add(itemData2);
        itemDatas.add(itemData3);
        itemDatas.add(itemData4);

        loopData.setItems(itemDatas);

    }

    /**
     * viewpager数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
//        if (null != videoDatas && videoDatas.size() > 0) {
//            holder.tv_video_title.setText(videoDatas.get(position).getVideoTitle());
//            holder.tv_video_time.setText(videoDatas.get(position).getVideoTime());
//        }
//        holder.itemView.setTag(position);
        if (holder instanceof ViewPagerViewHolder) {
            ViewPagerViewHolder viewPagerViewHolder = (ViewPagerViewHolder) holder;
            intiViewPagerData();
            viewPagerViewHolder.adloop_act_adloopview.refreshData(loopData);
            viewPagerViewHolder.adloop_act_adloopview.startAutoLoop();
        } else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
//            final SwipeItemLayout swipeRoot = footViewHolder.swipeItemLayout;
           final UserDataBean userDataBean = userBeanList.get(position - 1);
            if (null != userDataBean) {
                if (!TextUtils.isEmpty(userDataBean.getUsername())) {//姓名
                    footViewHolder.tv_user_name_personal.setText(userDataBean.getUsername());
                }
                if (!TextUtils.isEmpty(userDataBean.getSex())) {//性别
                    if ("0".equals(userDataBean.getSex())) {//0 男 1 女
                        footViewHolder.iv_user_sex_personal.setImageResource(R.drawable.ic_sex_male);
                    } else {
                        footViewHolder.iv_user_sex_personal.setImageResource(R.drawable.ic_sex_female);
                    }
                }

                if (!TextUtils.isEmpty(userDataBean.getAge())) {//年龄
                    footViewHolder.tv_user_age_personal.setText(userDataBean.getAge());
                }
                if (!TextUtils.isEmpty(userDataBean.getXingzuo())) {//星座
                    footViewHolder.tv_user_xingzuo_personal.setText(userDataBean.getXingzuo());
                }
                String locName = "";//地理位置
                double locDistance = 0;//距离当前用户
                String active_time = "";
                if (!TextUtils.isEmpty(userDataBean.getStreet())) {
                    locName = userDataBean.getStreet();
                }

                if (!TextUtils.isEmpty(userDataBean.getActive_time())) {
                    long timeLast = DateUtil.getTimeMillisBetweenTwoDate(new Date(),
                            DateUtil.parseToDate(userDataBean.getActive_time(), DateUtil.FORMATER_YYYY_MM_DD_HH_MM_SS));//成员登录持续时间
                    if (timeLast > DateUtil.oneDayMillis) {
                        active_time = "超过一天";
                    } else if (timeLast > DateUtil.oneHourMillis && timeLast <= DateUtil.oneDayMillis) {
                        active_time = timeLast / DateUtil.oneHourMillis + "小时前活跃";
                    } else if (timeLast > DateUtil.oneMinuteMillis && timeLast <= DateUtil.oneHourMillis) {
                        active_time = timeLast / DateUtil.oneMinuteMillis + "分钟前活跃";
                    } else {
                        active_time = "刚刚在线";
                    }
                }
                if (!TextUtils.isEmpty(userDataBean.getLatitude()) && !TextUtils.isEmpty(userDataBean.getLongitude())
                        && !TextUtils.isEmpty(MyApplication.getInstance().getUserDataBean().getLongitude())
                        && !TextUtils.isEmpty(MyApplication.getInstance().getUserDataBean().getLatitude())) {
                    try {
                        double mLongitude = Double.parseDouble(MyApplication.getInstance().getUserDataBean().getLongitude());
                        double mLatitude = Double.parseDouble(MyApplication.getInstance().getUserDataBean().getLatitude());
                        double otherLongitude = Double.parseDouble(userDataBean.getLongitude());
                        double otherLatitude = Double.parseDouble(userDataBean.getLatitude());
                        locDistance = DistanceUtil.getDistance(new LatLng(otherLongitude, otherLatitude), new LatLng(mLongitude, mLatitude));
                        if (locDistance >= 1) {
                            footViewHolder.tv_user_location_personal.setText(locName + "(距离" + (int) locDistance + "千米), " + active_time);
                        } else {
                            footViewHolder.tv_user_location_personal.setText(locName + "(距离" + (int) (locDistance * 1000) + "米), " + active_time);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                footViewHolder.btn_user_age_personal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRecyclerItemClickListener.onFollowClick(userDataBean, position - 1);
                    }
                });
            }
        }


//            footViewHolder.item_contact_delete.setText("关注");

//            footViewHolder.item_contact_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onRecyclerItemClickListener.onFollowClick(userDataBean, position - 1);
//                    closeOpenedSwipeItemLayoutWithAnim();
//                }
//            });

//            swipeRoot.setDelegate(new SwipeItemLayout.SwipeItemLayoutDelegate() {
//                @Override
//                public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
//                    closeOpenedSwipeItemLayoutWithAnim();
//                    mOpenedSil.add(swipeItemLayout);
//                }
//
//                @Override
//                public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
//                    mOpenedSil.remove(swipeItemLayout);
//                }
//
//                @Override
//                public void onSwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {
//                    closeOpenedSwipeItemLayoutWithAnim();
//                }
//            });
//        }
//        startScaleAndTranslate(holder.itemView);
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (SwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

    private void startScaleAndTranslate(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 200, 0);
        AnimatorSet set = new AnimatorSet();
        set.play(scaleX).with(scaleY).with(animator);
        set.setDuration(400);
        set.start();
    }

    //
    private void startTranslateFromLeft(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", -ScreenUtil.getScreenWidth(context), 0);
        animator.setDuration(400);
        animator.start();
    }

    //
//    private void startTranslateFromRight(View view) {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", ScreenUtil.getScreenWidth(context), 0);
//        animator.setDuration(400);
//        animator.start();
//    }
//
    private void startTranslateFromBottom(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 200, 0);
        animator.setDuration(400);
        animator.start();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView videoImage;
        TextView videoNameText;
        ImageButton videoPlayBtn;
        ProgressBar mProgressBar;

        public ViewHolder(View view) {
            super(view);
            videoImage = (ImageView) view.findViewById(R.id.video_image);
            videoNameText = (TextView) view.findViewById(R.id.tv_video_title);
            videoPlayBtn = (ImageButton) view.findViewById(R.id.btn_play_video);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        }
    }

    /**
     * 底部FootView布局
     */
    public static class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        private AdLoopView adloop_act_adloopview;

        public ViewPagerViewHolder(View view) {
            super(view);
            adloop_act_adloopview = (AdLoopView) view.findViewById(R.id.adloop_act_adloopview);
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user_name_personal;//姓名
        private ImageView iv_user_sex_personal;//性别
        private TextView tv_user_age_personal;//年龄
        private TextView tv_user_xingzuo_personal;//星座
        private TextView tv_user_location_personal;//位置信息和活跃信息
       private Button btn_user_age_personal;
//        private SwipeItemLayout swipeItemLayout;

        public FootViewHolder(View view) {
            super(view);
            tv_user_name_personal = (TextView) view.findViewById(R.id.tv_user_name_personal);
            iv_user_sex_personal = (ImageView) view.findViewById(R.id.iv_user_sex_personal);
            tv_user_age_personal = (TextView) view.findViewById(R.id.tv_user_age_personal);
            tv_user_xingzuo_personal = (TextView) view.findViewById(R.id.tv_user_xingzuo_personal);
            tv_user_location_personal = (TextView) view.findViewById(R.id.tv_user_location_personal);
            btn_user_age_personal =  (Button) view.findViewById(R.id.btn_user_age_personal);
//            swipeItemLayout = (SwipeItemLayout) view.findViewById(R.id.item_contact_swipe_root);
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

        void onFollowClick(UserDataBean userDataBean, int position);
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
