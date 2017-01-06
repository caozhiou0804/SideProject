package com.junit.caozhiou.sideproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.LoginActivity;
import com.junit.caozhiou.sideproject.adapter.PersonalSettingAdapter;
import com.junit.caozhiou.sideproject.entity.PersonalSettingData;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonLeftFragment extends Fragment {

    @Bind(R.id.recycler_view_personal_setting)
    RecyclerView recycler_view_personal_setting;
    @Bind(R.id.sdv_person_icon)
    SimpleDraweeView sdv_person_icon;
    @Bind(R.id.tv_user_name)
    TextView tv_user_name;
    @Bind(R.id.tv_level)
    TextView tv_level;
    @Bind(R.id.tv_person_sign)
    TextView tv_person_sign;

    private PersonalSettingAdapter personalSettingAdapter;
    private List<PersonalSettingData> datas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_left, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;


    }

    protected void initView() {
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

}
