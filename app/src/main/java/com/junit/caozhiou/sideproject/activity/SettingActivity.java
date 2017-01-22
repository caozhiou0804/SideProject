package com.junit.caozhiou.sideproject.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.AnyEvent;
import com.junit.caozhiou.sideproject.entity.UploadPhotoBean;
import com.junit.caozhiou.sideproject.entity.UserDataBean;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.ImageUtils;
import com.junit.caozhiou.sideproject.util.MyToast;
import com.junit.caozhiou.sideproject.util.PreferenceUtil;
import com.junit.caozhiou.sideproject.view.multi_image_selector.MultiImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

public class SettingActivity extends AppCompatActivity {

    @Bind(R.id.iv_head_setting)
    ImageView iv_head_setting;
    @Bind(R.id.btn_loginOut)
    Button btn_loginOut;


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
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        intView();
    }

    private void intView() {

        UserDataBean userDataBean = MyApplication.getInstance().getUserDataBean();
        if (null != userDataBean) {
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
            if (!TextUtils.isEmpty(userDataBean.getBirth_day())) {
                tv_user_age_personal.setText(userDataBean.getBirth_day());
                tv_user_xingzuo_personal.setText(userDataBean.getBirth_day());
            }
//            if(!TextUtils.isEmpty(userDataBean.getUsername())){
//                tv_user_name_personal.setText(userDataBean.getUsername());
//            }

        }
    }

    @OnClick(R.id.btn_loginOut)
    public void loginOut(View view) {//退出登录
        PreferenceUtil.putString(Constant.USERNAME, null);
        PreferenceUtil.putString(Constant.PASSWORD, null);
        MyApplication.getInstance().setUserDataBean(null);

        Intent intent = new Intent(SettingActivity.this, LoginEnterenceActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.iv_head_setting)
    public void openPhotoAlbum(View view) {
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
        Intent intent = new Intent(SettingActivity.this,
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
                    iv_head_setting.setImageBitmap(imageBitmap);
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
            MyToast.show(SettingActivity.this, e + "", 1500);
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            UploadPhotoBean uploadPhotoBean = gson.fromJson(response, UploadPhotoBean.class);
            if (("0").equals(uploadPhotoBean.getStatus())) {
                EventBus.getDefault().post(new AnyEvent(uploadPhotoBean.getPicUrl()));
            } else {
                MyToast.show(SettingActivity.this, uploadPhotoBean.getMessage(), 1500);
            }
//            switch (id) {
//                case 100:
//                    Toast.makeText(UploadFileActivity.this, "http", Toast.LENGTH_SHORT).show();
//                    break;
//                case 101:
//                    Toast.makeText(UploadFileActivity.this, "https", Toast.LENGTH_SHORT).show();
//                    break;
//            }
        }

        @Override
        public void inProgress(final float progress, long total, int id) {
//            int pro = (int) (progress * 100);
//            uploadProgress.setProgress(pro);
        }
    }
}
