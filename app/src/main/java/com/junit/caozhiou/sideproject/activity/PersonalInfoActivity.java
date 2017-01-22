package com.junit.caozhiou.sideproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.PersonInfoEvent;
import com.junit.caozhiou.sideproject.entity.UserBean;
import com.junit.caozhiou.sideproject.entity.UserDataBean;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.DateUtil;
import com.junit.caozhiou.sideproject.util.ImageUtils;
import com.junit.caozhiou.sideproject.util.MyToast;
import com.junit.caozhiou.sideproject.view.multi_image_selector.MultiImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;


public class PersonalInfoActivity extends AppCompatActivity {

    @Bind(R.id.btn_to_edit_info)
    Button btn_to_edit_info;
    @Bind(R.id.sdv_my_info_icon)
    SimpleDraweeView sdv_my_info_icon;

    @Bind(R.id.tv_user_name_personal)
    TextView tv_user_name_personal;
    @Bind(R.id.iv_user_sex_personal)
    ImageView iv_user_sex_personal;
    @Bind(R.id.tv_user_age_personal)
    TextView tv_user_age_personal;
    @Bind(R.id.tv_user_xingzuo_personal)
    TextView tv_user_xingzuo_personal;
    @Bind(R.id.tv_user_location_personal)
    TextView tv_user_location_personal;

    private static final int CHOOSE_PHOTO = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);
        initView();
        //注册
        if (null != EventBus.getDefault())
            EventBus.getDefault().register(this);
    }

    private void initView() {

        UserDataBean userDataBean = MyApplication.getInstance().getUserDataBean();
        if (null != userDataBean) {

            if (!TextUtils.isEmpty(userDataBean.getHead_picurl())) {
                sdv_my_info_icon.setImageURI(Uri.parse(userDataBean.getHead_picurl()));
            }
            if (!TextUtils.isEmpty(userDataBean.getUsername())) {
                tv_user_name_personal.setText(userDataBean.getUsername());
            }
            if (!TextUtils.isEmpty(userDataBean.getSex())) {//0 男，1 女
                if ("0".equals(userDataBean.getSex())) {
                    iv_user_sex_personal.setImageResource(R.drawable.ic_sex_male);
                } else {
                    iv_user_sex_personal.setImageResource(R.drawable.ic_sex_male);
                }
            }
            if (!TextUtils.isEmpty(userDataBean.getAge())) {
                tv_user_age_personal.setText(userDataBean.getAge());
            }
            if (!TextUtils.isEmpty(userDataBean.getXingzuo())) {
                tv_user_xingzuo_personal.setText(userDataBean.getXingzuo());
            }
            if (!TextUtils.isEmpty(userDataBean.getActive_time())) {
                long timeLast = DateUtil.getTimeMillisBetweenTwoDate(new Date(),
                        DateUtil.parseToDate(userDataBean.getActive_time(), DateUtil.FORMATER_YYYY_MM_DD_HH_MM_SS));//成员登录持续时间
                String active_time = "";
                if (timeLast > DateUtil.oneDayMillis) {
                    active_time = "超过一天";
                } else if (timeLast > DateUtil.oneHourMillis && timeLast <= DateUtil.oneDayMillis) {
                    active_time = timeLast / DateUtil.oneHourMillis + "小时前活跃";
                } else if (timeLast > DateUtil.oneMinuteMillis && timeLast <= DateUtil.oneHourMillis) {
                    active_time = timeLast / DateUtil.oneMinuteMillis + "分钟前活跃";
                } else {
                    active_time = "刚刚在线";
                }

                tv_user_location_personal.setText("0米, " + active_time);
            }
        }
    }

    @OnClick(R.id.btn_to_edit_info)
    public void editInfo(View view) {
        Intent intent = new Intent(PersonalInfoActivity.this, EditPersonInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sdv_my_info_icon)
    public void updateHeadPhoto(View view) {
        toShowCamera();
    }

    /**
     * 打开相册选择页面
     */
    private ArrayList<String> mSelectPath;

    private void toShowCamera() {
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        boolean showCamera = true;
        // int maxNum = picSizes;
        Intent intent = new Intent(PersonalInfoActivity.this,
                MultiImageSelectorActivity.class);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
                showCamera);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
                selectedMode);
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(
                    MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST,
                    mSelectPath);
        }
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    mSelectPath = data
                            .getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    Bitmap imageBitmap = null;
                    try {
                        imageBitmap = ImageUtils.revitionImageSize(mSelectPath
                                .get(0));
                        uploadFile(mSelectPath
                                .get(0));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 上传图片
     *
     * @param filepath
     */
    private void uploadFile(String filepath) {
        Bitmap getbitm = ImageUtils.getBitMapFromFile(filepath);
        // 获取压缩后图片
        String path = ImageUtils.compressImage(getbitm, 200);

        if (getbitm != null && !getbitm.isRecycled()) {
            // 回收并且置为null
            getbitm.recycle();
            getbitm = null;
        }
        System.gc();
        File file = new File(path);
        String url = Constant.SERVER_IP + "Userfeature/uploadFile";
        String userId = "";
        if (null != MyApplication.getInstance().getUserDataBean()
                && !TextUtils.isEmpty(MyApplication.getInstance().getUserDataBean().getUserId())) {
            userId = MyApplication.getInstance().getUserDataBean().getUserId();
        }
        OkHttpUtils.post()//
                .addFile("myfile", file.getName(), file)//
                .url(url)
                .addParams("userId", userId)
                .id(100)//
//                .params(params)//
//                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }


    class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            MyToast.show(PersonalInfoActivity.this, e + "", 1500);
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            UserBean userBean = gson.fromJson(response, UserBean.class);
            if (("0").equals(userBean.getStatus())) {
                MyApplication.getInstance().setUserDataBean(userBean.getData());
                PersonInfoEvent personInfoEvent = new PersonInfoEvent(PersonInfoEvent.UPDATE_INFO);
                personInfoEvent.setUserDataBean(userBean.getData());
                EventBus.getDefault().post(personInfoEvent);
            }
            MyToast.show(PersonalInfoActivity.this, userBean.getMessage(), 1500);

        }

        @Override
        public void inProgress(final float progress, long total, int id) {
//            int pro = (int) (progress * 100);
//            uploadProgress.setProgress(pro);
        }
    }

    @Subscribe
    public void onEventMainThread(PersonInfoEvent event) {

        MyToast.show(PersonalInfoActivity.this, event.getType(), MyToast.LENGTH_LONG);
        if (!TextUtils.isEmpty(event.getType()) && PersonInfoEvent.UPDATE_INFO.equals(event.getType()))
            initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
