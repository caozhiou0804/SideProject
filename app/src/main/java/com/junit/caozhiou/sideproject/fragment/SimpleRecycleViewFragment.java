package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.SimpleRecyclerViewAdapter;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.UserDataBean;
import com.junit.caozhiou.sideproject.entity.UserListBean;
import com.junit.caozhiou.sideproject.log.L;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.MyToast;
import com.junit.caozhiou.sideproject.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 普通recycleView
 */
public class SimpleRecycleViewFragment extends Fragment {


    private View contentView;

    private RecyclerView recycler_view_simple;

    private LinearLayoutManager mLayoutManager;

    private SimpleRecyclerViewAdapter mAdapter;

    private List<UserDataBean> userBeanList = new ArrayList<UserDataBean>();

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
        initView();

        return contentView;
    }

    private void reqUserList() {
        String url = Constant.SERVER_IP + "Userfeature/getAroundUsers";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.d("Splash", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        UserListBean userListBean = gson.fromJson(response, UserListBean.class);
                        userBeanList.addAll(userListBean.getData());
                        mAdapter.notifyDataSetChanged();
                    }
                });
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
            @Override
            public void onFollowClick(UserDataBean userDataBean, int position) {
                Toast.makeText(getActivity(), "用户" + userDataBean.getPhone() , Toast.LENGTH_SHORT).show();

                String userId_send = MyApplication.getInstance().getUserDataBean().getUserId();
                String userId_accept = userDataBean.getUserId();
                sendLoveMsg(userId_send,userId_accept);
            }
        }, userBeanList);
        recycler_view_simple.setAdapter(mAdapter);
        reqUserList();
    }

    /**
     * 发送示爱信息接口
     * @param userId_send
     * @param userId_accept
     */
    private void sendLoveMsg(String userId_send, String userId_accept) {
        String url = Constant.SERVER_IP+"Userfeature/sendFollows" ;
        OkHttpUtils
                .post()
                .url(url)
                .addParams("userId_send",userId_send)
                .addParams("userId_accept", userId_accept)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.d("Splash", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        UserListBean userBean = gson.fromJson(response, UserListBean.class);
                        MyToast.show(getActivity(),userBean.getMessage(),MyToast.LENGTH_LONG);
                    }
                });
    }

}
