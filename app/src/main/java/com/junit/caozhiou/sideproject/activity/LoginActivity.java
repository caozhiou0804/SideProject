package com.junit.caozhiou.sideproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.app.MyApplication;
import com.junit.caozhiou.sideproject.constant.Constant;
import com.junit.caozhiou.sideproject.entity.UserBean;
import com.junit.caozhiou.sideproject.log.L;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.PreferenceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


//    // UI references.
//    private AutoCompleteTextView mEmailView;
//    private EditText mPasswordView;
//    private View mProgressView;
//    private View mLoginFormView;
//
//    private EditText username, password;
//    private Button login, clear, upload;
//    private ImageView img;
//    private LoginPresenter loginPresenter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        init();
//
//    }
//
//    private void init() {
//
//        username = (EditText) findViewById(R.id.username);
//        password = (EditText) findViewById(R.id.pass);
//        img = (ImageView) findViewById(R.id.img);
//
//        login = (Button) findViewById(R.id.login);
//        clear = (Button) findViewById(R.id.clear);
//        upload = (Button) findViewById(R.id.upload);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginPresenter.login();
//            }
//        });
//
//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginPresenter.loginOut();
//            }
//        });
//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toShowCamera();
//            }
//        });
//    }
//
//    @Override
//    public String getUsername() {
//        return username.getText().toString();
//    }
//
//    @Override
//    public String getPassword() {
//        return password.getText().toString();
//    }
//
//    @Override
//    public void loginBack(Object obj) {
//        Gson gson = new Gson();
//        UserBean userBean = gson.fromJson(obj.toString(), UserBean.class);
//        MyApplication.getInstance().setUserDataBean(userBean.getJson());
//        PreferenceUtil.putString(Constant.USERNAME, userBean.getJson().getUsername());
//        PreferenceUtil.putString(Constant.PASSWORD, userBean.getJson().getPassword());
//        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//        startActivity(intent);
//        finish();
//    }
//
//    /**
//     * 打开相册选择页面
//     */
//    private ArrayList<String> mSelectPath ;
//
//    private void toShowCamera() {
//        int selectedMode = MultiImageSelectorActivity.MODE_SINGLE;
//        boolean showCamera = true;
//        // int maxNum = picSizes;
//        Intent intent = new Intent(LoginActivity.this,
//                MultiImageSelectorActivity.class);
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA,
//                showCamera);
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
//        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE,
//                selectedMode);
//        if (mSelectPath != null && mSelectPath.size() > 0) {
//            intent.putExtra(
//                    MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST,
//                    mSelectPath);
//        }
//        startActivityForResult(intent, 1111);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case 1111:
//                if (resultCode == RESULT_OK) {
//                    mSelectPath = data
//                            .getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                    Bitmap imageBitmap = null;
//                    try {
//                        imageBitmap = ImageUtils.revitionImageSize(mSelectPath
//                                .get(0));
//                        uploadFile(mSelectPath
//                                .get(0));
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    img.setImageBitmap(imageBitmap);
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    /**
//     * 上传图片
//     *
//     * @param filepath
//     */
//    private void uploadFile(final String filepath) {
//        // PubUtils.showTipDialog(UploadPhotoActivity.this, "图片上传中...");
////        String fileMimeType = PubUtils.getImgRealMimeType(PubUtils
////                .getFileFormat(filepath));
////        byte[] imgBytes = ImageUtils.File2Bytes(filepath);
//        Intent i = new Intent(LoginActivity.this, UploadFileActivity.class);
//        i.putExtra("file_path", filepath);
//        startActivity(i);
//
//    }

    private static final String TAG = "LoginActivity";
    private String username;
    private String password;
    @Bind(R.id.input_name)
    EditText input_name;
    @Bind(R.id.input_password)
    EditText input_password;
    @Bind(R.id.btn_login)
    Button btn_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    public void register(View view) {
        if (!validate())
            return;
        reqLogin();
    }

    private void reqLogin() {
        String url = Constant.SERVER_IP + "Userfeature/userLogin";
        username = input_name.getText().toString();
        password = input_password.getText().toString();
        OkHttpUtils
                .post()
                .url(url)
                .addParams("phone", input_name.getText().toString())//
                .addParams("password", input_password.getText().toString())//
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        L.d("Splash", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        UserBean userBean = gson.fromJson(response, UserBean.class);
                        MyApplication.getInstance().setUserDataBean(userBean.getData());
                        PreferenceUtil.putString(Constant.USERNAME, username);
                        PreferenceUtil.putString(Constant.PASSWORD, password);
                        PushManager.getInstance().bindAlias(getApplicationContext(),userBean.getData().getUserId());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
//
//    @Override
//    public void onBackPressed() {
//        // disable going back to the MainActivity
//        moveTaskToBack(true);
//    }

    public boolean validate() {
        boolean valid = true;

        String name = input_name.getText().toString();
        String password = input_password.getText().toString();

        if (name.isEmpty() || name.length() < 11) {
            input_name.setError("请输入11位手机号");
            valid = false;
        } else {
            input_name.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            input_password.setError("请输入至少六位密码");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }
}

