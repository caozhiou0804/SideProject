package com.junit.caozhiou.sideproject.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.entity.CityBean;
import com.junit.caozhiou.sideproject.entity.UserBean;
import com.junit.caozhiou.sideproject.net.OkHttpUtil;
import com.junit.caozhiou.sideproject.net.RequestManager;
import com.junit.caozhiou.sideproject.net.callback.ResponseCallBack;
import com.junit.caozhiou.sideproject.presenter.LoginPresenter;
import com.junit.caozhiou.sideproject.presenter.LoginView;
import com.junit.caozhiou.sideproject.presenter.back.LoginCallBack;
import com.junit.caozhiou.sideproject.presenter.impl.LoginPresenterImpl;
import com.junit.caozhiou.sideproject.util.ImageUtils;
import com.junit.caozhiou.sideproject.util.MyToast;
import com.junit.caozhiou.sideproject.view.multi_image_selector.MultiImageSelectorActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginView, LoginCallBack<UserBean> {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private EditText username, password;
    private Button login, clear, upload;
    private ImageView img;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    private void init() {
        loginPresenter = new LoginPresenterImpl(this, this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        img = (ImageView) findViewById(R.id.img);

        login = (Button) findViewById(R.id.login);
        clear = (Button) findViewById(R.id.clear);
        upload = (Button) findViewById(R.id.upload);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.loginOut();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toShowCamera();
            }
        });
    }

    @Override
    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void loginBack(UserBean userBean) {
        MyToast.show(this, userBean.getPassword(), 1000);
    }


    /**
     * 打开相册选择页面
     */
    private ArrayList<String> mSelectPath;

    private void toShowCamera() {
        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
        boolean showCamera = true;
        // int maxNum = picSizes;
        Intent intent = new Intent(LoginActivity.this,
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
        startActivityForResult(intent, 1111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1111:
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
                    img.setImageBitmap(imageBitmap);
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
    private void uploadFile(final String filepath) {
        // PubUtils.showTipDialog(UploadPhotoActivity.this, "图片上传中...");
//        String fileMimeType = PubUtils.getImgRealMimeType(PubUtils
//                .getFileFormat(filepath));
//        byte[] imgBytes = ImageUtils.File2Bytes(filepath);
        Intent i = new Intent(LoginActivity.this, UploadFileActivity.class);
        i.putExtra("file_path", filepath);
        startActivity(i);

    }

    private void doPostAsync(UserBean u) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", u.getUsername());
        params.put("password", u.getPassword());
        String url = "http://192.168.111.2:8080/WebApp/Userfeature/getCityList?row=10&page=1";
        OkHttpUtil.request1(url, RequestManager.MEDIA_TYPE_MARKDOWN, params, new ResponseCallBack<CityBean>() {
            @Override
            public void onSuccess(CityBean cityBean, Call call, Response response) {
                Log.d("qqqqqqqqqqqqqqqqqqq",cityBean.getMessage());
            }
        });
    }


}

