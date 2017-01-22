package com.junit.caozhiou.sideproject.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.PersonalInfoActivity;
import com.junit.caozhiou.sideproject.activity.SettingActivity;
import com.junit.caozhiou.sideproject.adapter.PersonalSettingAdapter;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.entity.PersonInfoEvent;
import com.junit.caozhiou.sideproject.entity.PersonalLeftData;
import com.junit.caozhiou.sideproject.entity.UserDataBean;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.ImageUtils;

import net.qiujuer.genius.blur.StackBlur;

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
    @Bind(R.id.ll_left_bg)
    LinearLayout ll_left_bg;
    @Bind(R.id.rl_personal_info)
    RelativeLayout rl_personal_info;

    @Bind(R.id.recycler_view_personal_setting)
    RecyclerView recycler_view_personal_setting;
    @Bind(R.id.sdv_person_icon)
    SimpleDraweeView sdv_person_icon;
    @Bind(R.id.tv_user_name)
    TextView tv_user_name;
    @Bind(R.id.tv_level)
    TextView tv_level;

    private PersonalSettingAdapter personalSettingAdapter;
    private List<PersonalLeftData> datas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_person_left, container, false);
        ButterKnife.bind(this, view);
        initView();
        //注册
        if (null != EventBus.getDefault())
            EventBus.getDefault().register(this);
        return view;


    }

    protected void initView() {
        UserDataBean userDataBean = MyApplication.getInstance().getUserDataBean();
        Bitmap newBitmap = StackBlur.blur(ImageUtils.drawableToBitmap(getResources().getDrawable(R.drawable.jr1)), 30, false);
        BitmapDrawable drawable = new BitmapDrawable(getResources(), newBitmap);
        ll_left_bg.setBackground(drawable);
        if (null != userDataBean) {
            if (!TextUtils.isEmpty(userDataBean.getHead_picurl())) {
                sdv_person_icon.setImageURI(Uri.parse(userDataBean.getHead_picurl()));
                //本地图片

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            InputStream in = new BufferedInputStream(new URL(userDataBean.getHead_picurl()).openStream(), 1024);
//                            ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
//                            BufferedOutputStream out = new BufferedOutputStream(dataStream, 1024);
//                            copy(in, out);
//                            out.flush();
//                            byte[] data = dataStream.toByteArray();
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                            Bitmap newBitmap = StackBlur.blur(bitmap, 20, false);
//                            BitmapDrawable drawable= new BitmapDrawable(getResources(), newBitmap);
//                            ll_left_bg.setBackground(drawable);
//                            data = null;
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
            }
            if (!TextUtils.isEmpty(userDataBean.getUsername())) {
                tv_user_name.setText(userDataBean.getUsername());
            }
        }

        initLayoutManger();
        intiLeftData();
        personalSettingAdapter = new PersonalSettingAdapter(getActivity(), datas, new PersonalSettingAdapter.ItemClickListener() {
            @Override
            public void onItemClick(PersonalLeftData personalLeftData) {
                Intent desIntent = new Intent(getActivity(), personalLeftData.getDesActivity());
                startActivity(desIntent);
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
                        UserDataBean user = new Gson().fromJson(response, UserDataBean.class);

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

    @OnClick(R.id.rl_personal_info)
    public void person_info(View view) {
        Intent intent = new Intent(getActivity(),
                PersonalInfoActivity.class);
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
    private void intiLeftData() {
        datas = new ArrayList<>();
        PersonalLeftData data1 = new PersonalLeftData(R.drawable.mine_setting, "探探", SettingActivity.class);
        PersonalLeftData data2 = new PersonalLeftData(R.drawable.mine_setting, "设置", SettingActivity.class);
        PersonalLeftData data3 = new PersonalLeftData(R.drawable.mine_setting, "设置", SettingActivity.class);
        datas.add(data1);
        datas.add(data2);
        datas.add(data3);

    }

    @Subscribe
    public void onEventMainThread(PersonInfoEvent event) {
        if (!TextUtils.isEmpty(event.getType()) && PersonInfoEvent.UPDATE_INFO.equals(event.getType()))
            initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
