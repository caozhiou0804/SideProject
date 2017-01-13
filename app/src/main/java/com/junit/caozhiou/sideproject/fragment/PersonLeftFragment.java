package com.junit.caozhiou.sideproject.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.LoginActivity;
import com.junit.caozhiou.sideproject.adapter.PersonalSettingAdapter;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.entity.AnyEvent;
import com.junit.caozhiou.sideproject.entity.PersonalSettingData;
import com.junit.caozhiou.sideproject.entity.UserDataBean;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

public class PersonLeftFragment extends Fragment {

    @Bind(R.id.recycler_view_personal_setting)
    RecyclerView recycler_view_personal_setting;
    @Bind(R.id.sdv_person_icon)
    SimpleDraweeView sdv_person_icon;
    @Bind(R.id.tv_user_name)
    TextView tv_user_name;
    @Bind(R.id.tv_level)
    TextView tv_level;

    private PersonalSettingAdapter personalSettingAdapter;
    private List<PersonalSettingData> datas;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_left, container, false);
        ButterKnife.bind(this, view);
        UserDataBean userBean = MyApplication.getInstance().getUserDataBean();
        initView(userBean);
        //注册
        if (null != EventBus.getDefault())
            EventBus.getDefault().register(this);
        return view;


    }

    protected void initView(UserDataBean userDataBean) {
        if(null!=userDataBean){
            if(!TextUtils.isEmpty(userDataBean.getHead_picurl())){
                sdv_person_icon.setImageURI(Uri.parse(userDataBean.getHead_picurl()));
            }
            if(!TextUtils.isEmpty(userDataBean.getUsername())){
                tv_user_name.setText(userDataBean.getUsername());
            }
        }

        initLayoutManger();
        intiSettingData();
        personalSettingAdapter = new PersonalSettingAdapter(getActivity(), datas, new PersonalSettingAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), position + "", Toast.LENGTH_LONG).show();
            }
        });
        recycler_view_personal_setting.setAdapter(personalSettingAdapter);
    }

    /**
     * 请求获取个人信息
     */
    private void requestPersonalInfo() {
        String url = "";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {

                    }

                    @Override
                    public void onAfter(int id) {

                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UserDataBean user = new Gson().fromJson(response,UserDataBean.class);

//                        switch (id) {
//                            case 100:
//                                Toast.makeText(OkHttpTestActivity.this, "http", Toast.LENGTH_SHORT).show();
//                                break;
//                            case 101:
//                                Toast.makeText(OkHttpTestActivity.this, "https", Toast.LENGTH_SHORT).show();
//                                break;
//                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {

                    }
                });
    }

    @OnClick(R.id.sdv_person_icon)
    public void person_icon(View view) {
        Toast.makeText(getActivity(), "sdv_person_icon", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(),
                LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 初始化LayoutManger（控制recyclerview方向显示）
     */
    private void initLayoutManger() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler_view_personal_setting.setLayoutManager(linearLayoutManager);
    }

    /**
     * 初始化数据
     */
    private void intiSettingData() {
        datas = new ArrayList<>();

        for (int i = 0; i < 8; i++) {

            PersonalSettingData data = new PersonalSettingData();
            data.setIcon_res(R.drawable.mine_setting);
            data.setSettingTitle("设置项" + i);
            datas.add(data);
        }
    }

    @Subscribe
    public void onEventMainThread(AnyEvent event) {

        MyToast.show(getActivity(), event.getDiscribe(), MyToast.LENGTH_LONG);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
