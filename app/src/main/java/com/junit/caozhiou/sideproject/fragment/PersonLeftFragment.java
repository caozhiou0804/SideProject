package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.adapter.PersonalSettingAdapter;
import com.junit.caozhiou.sideproject.base.BaseFragment;
import com.junit.caozhiou.sideproject.entity.CityBean;
import com.junit.caozhiou.sideproject.entity.PersonalSettingData;
import com.junit.caozhiou.sideproject.net.OkHttpUtil;
import com.junit.caozhiou.sideproject.net.RequestManager;
import com.junit.caozhiou.sideproject.net.callback.ResponseCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class PersonLeftFragment extends BaseFragment {

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
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_left, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
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
//        OkGo.get("http://192.168.111.2:8080/WebApp/Userfeature/userLogin?username=111&password=21323")//
//                .execute(new AbsCallback<BaseEntity>() {
//                    @Override
//                    public void onSuccess(BaseEntity o, Call call, Response response) {
//                        Log.d("",o.getMessage());
//                    }
//
//                    @Override
//                    public BaseEntity convertSuccess(Response response) throws Exception {
//                        JsonReader jsonReader = new JsonReader(response.body().charStream());
//                        return new Gson().fromJson(jsonReader,BaseEntity.class);
//                    }
//                });
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("page", "1");
        params.put("row", "10");
        String url = "http://192.168.111.2:8080/WebApp/Userfeature/getCityList";
        OkHttpUtil.request1(url, RequestManager.MEDIA_TYPE_MARKDOWN, params, new ResponseCallBack<CityBean>() {
            @Override
            public void onSuccess(CityBean cityBean, Call call, Response response) {
                Log.d("qqqqqqqqqqqqqqqqqqq",cityBean.getMessage());
            }
        });


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
        personalSettingAdapter.notifyDataSetChanged();
    }

}
