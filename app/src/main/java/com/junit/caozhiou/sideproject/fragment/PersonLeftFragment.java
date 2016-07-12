package com.junit.caozhiou.sideproject.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.PersonalSettingAdapter;
import com.junit.caozhiou.sideproject.entity.PersonalSettingData;
import com.junit.caozhiou.sideproject.entity.ScreenBean;
import com.junit.caozhiou.sideproject.view.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

public class PersonLeftFragment extends Fragment {

    private View contentView;
    private Button btn_go_to_tv;
    private Button btn_go_video_list;
    private ScreenBean screenBean;
    private SwipeItemLayout item_contact_swipe_root;
    private TextView tv_delete, tv_share;

    private RecyclerView recycler_view_personal_setting;

    private PersonalSettingAdapter personalSettingAdapter;

    private List<PersonalSettingData> datas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == contentView) {
            contentView = inflater.inflate(R.layout.fragment_person_left, container, false);
        }
        initView();
        return contentView;
    }

    private void initView() {
        recycler_view_personal_setting = (RecyclerView) contentView.findViewById(R.id.recycler_view_personal_setting);

        initLayoutManger();
        intiData();
        personalSettingAdapter = new PersonalSettingAdapter(getActivity(), datas, new PersonalSettingAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),position+"",Toast.LENGTH_LONG).show();
            }
        });
        recycler_view_personal_setting.setAdapter(personalSettingAdapter);
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
    private void intiData() {
        datas = new ArrayList<>();

        for (int i = 0; i < 8; i++) {

            PersonalSettingData data = new PersonalSettingData();
            data.setIcon_res(R.drawable.mine_setting);
            data.setSettingTitle("设置项"+i);
            datas.add(data);
        }
    }
}
