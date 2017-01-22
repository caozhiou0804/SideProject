package com.junit.caozhiou.sideproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.entity.AnyEvent;
import com.junit.caozhiou.sideproject.entity.UploadPhotoBean;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;
import com.junit.caozhiou.sideproject.util.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class UploadFileActivity extends AppCompatActivity {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            //设置超时，不设置可能会报异常
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();
    private Button upload, download;
    private ProgressBar uploadProgress, downloadProgeress;

    private String file_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        file_path = getIntent().getStringExtra("file_path");
        initView();
    }

    private void initView() {
        uploadProgress = (ProgressBar) findViewById(R.id.upload_progress);
        downloadProgeress = (ProgressBar) findViewById(R.id.download_progress);
        upload = (Button) findViewById(R.id.upload);
        download = (Button) findViewById(R.id.download);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();

            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new AnyEvent("nothing is impossible!"));
            }
        });
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            MyToast.show(UploadFileActivity.this, e+"", 1500);
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            UploadPhotoBean uploadPhotoBean = gson.fromJson(response, UploadPhotoBean.class);
            if ("0".equals(uploadPhotoBean.getStatus())) {
                EventBus.getDefault().post(new AnyEvent(uploadPhotoBean.getPicUrl()));
            } else {
                MyToast.show(UploadFileActivity.this, uploadPhotoBean.getMessage(), 1500);
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
            int pro = (int) (progress * 100);
            uploadProgress.setProgress(pro);
        }
    }

    private void upload() {
        File file = new File(file_path);
        String url = "http://192.168.111.2:8080/WebApp/Userfeature/uploadFile";
        OkHttpUtils.post()//
                .addFile("myfile", file.getName(), file)//
                .url(url)
                .id(100)//
//                .params(params)//
//                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());
    }
}
