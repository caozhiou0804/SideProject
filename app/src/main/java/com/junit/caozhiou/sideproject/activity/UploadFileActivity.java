package com.junit.caozhiou.sideproject.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.net.coreprogress.helper.ProgressHelper;
import com.junit.caozhiou.sideproject.net.coreprogress.listener.ProgressListener;
import com.junit.caozhiou.sideproject.net.coreprogress.listener.impl.UIProgressListener;
import com.junit.caozhiou.sideproject.okhttputil.OkHttpUtils;
import com.junit.caozhiou.sideproject.okhttputil.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        uploadProgress.setProgress(50);
//                    }
//                });
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
    }

    private void download() {
        //这个是非ui线程回调，不可直接操作UI
        final ProgressListener progressResponseListener = new ProgressListener() {
            @Override
            public void onProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("TAG", "bytesRead:" + bytesRead);
                Log.e("TAG", "contentLength:" + contentLength);
                Log.e("TAG", "done:" + done);
                if (contentLength != -1) {
                    //长度未知的情况下回返回-1
                    Log.e("TAG", (100 * bytesRead) / contentLength + "% done");
                }
                Log.e("TAG", "================================");
            }
        };


        //这个是ui线程回调，可直接操作UI
        final UIProgressListener uiProgressResponseListener = new UIProgressListener() {
            @Override
            public void onUIProgress(long bytesRead, long contentLength, boolean done) {
                Log.e("TAG", "bytesRead:" + bytesRead);
                Log.e("TAG", "contentLength:" + contentLength);
                Log.e("TAG", "done:" + done);
                if (contentLength != -1) {
                    //长度未知的情况下回返回-1
                    Log.e("TAG", (100 * bytesRead) / contentLength + "% done");
                }
                Log.e("TAG", "================================");
                //ui层回调
                downloadProgeress.setProgress((int) ((100 * bytesRead) / contentLength));
                //Toast.makeText(getApplicationContext(), bytesRead + " " + contentLength + " " + done, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUIStart(long bytesRead, long contentLength, boolean done) {
                super.onUIStart(bytesRead, contentLength, done);
                Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUIFinish(long bytesRead, long contentLength, boolean done) {
                super.onUIFinish(bytesRead, contentLength, done);
                Toast.makeText(getApplicationContext(), "end", Toast.LENGTH_SHORT).show();
            }
        };

        //构造请求
        final Request request1 = new Request.Builder()
                .url("http://121.41.119.107:81/test/1.doc")
                .build();

        //包装Response使其支持进度回调
        ProgressHelper.addProgressResponseListener(client, uiProgressResponseListener).newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "error ", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG", response.body().string());
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
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    Toast.makeText(UploadFileActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(UploadFileActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(final float progress, long total, int id) {
            int pro = (int) (progress * 100);
            Toast.makeText(UploadFileActivity.this, pro + "%", Toast.LENGTH_SHORT).show();
            uploadProgress.setProgress(pro);
        }
    }

    private void upload() {
        File file = new File(file_path);
        String cotent_disposition = "form-data; name=\"myfile\";filename=\"" + file.getName() + "\"";
        String url = "http://192.168.111.2:8080/WebApp/Userfeature/uploadFile";
//                "?file_name=" + file.getName() + "&file=" + file;
        OkHttpUtils.post()//
                .addFile("myfile", file.getName(), file)//
                .url(url)//
//                .params(params)//
//                .headers(headers)//
                .build()//
                .execute(new MyStringCallback());


//        //此文件必须在手机上存在，实际情况下请自行修改，这个目录下的文件只是在我手机中存在。
//
//
//        //这个是非ui线程回调，不可直接操作UI
//        final ProgressListener progressListener = new ProgressListener() {
//            @Override
//            public void onProgress(long bytesWrite, long contentLength, boolean done) {
//                Log.e("TAG", "bytesWrite:" + bytesWrite);
//                Log.e("TAG", "contentLength" + contentLength);
//                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
//                Log.e("TAG", "done:" + done);
//                Log.e("TAG", "================================");
//            }
//        };
//
//
//        //这个是ui线程回调，可直接操作UI
//        final UIProgressListener uiProgressRequestListener = new UIProgressListener() {
//            @Override
//            public void onUIProgress(long bytesWrite, long contentLength, boolean done) {
//                Log.e("TAG", "bytesWrite:" + bytesWrite);
//                Log.e("TAG", "contentLength" + contentLength);
//                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
//                Log.e("TAG", "done:" + done);
//                Log.e("TAG", "================================");
//                //ui层回调
//                double bytesWrite_d = (double) bytesWrite;
//                double contentLength_d = (double) contentLength;
//                double progress = (bytesWrite_d) / contentLength_d;
//
//                uploadProgress.setProgress((int) progress * 100);
//
//                //Toast.makeText(getApplicationContext(), bytesWrite + " " + contentLength + " " + done, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onUIStart(long bytesWrite, long contentLength, boolean done) {
//                super.onUIStart(bytesWrite, contentLength, done);
//                Toast.makeText(getApplicationContext(), "start", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUIFinish(long bytesWrite, long contentLength, boolean done) {
//                super.onUIFinish(bytesWrite, contentLength, done);
//                Toast.makeText(getApplicationContext(), "end", Toast.LENGTH_SHORT).show();
//            }
//        };
//        String cotent_disposition = "form-data; name=\"myfile\";filename=\"" + file.getName() + "\"";
//        //构造上传请求，类似web表单
//        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("hello", "android")
//                .addFormDataPart("photo", file.getName(), RequestBody.create(null, file))
//                .addPart(Headers.of("Content-Disposition", cotent_disposition), RequestBody.create(MediaType.parse(file.getName()), file))
//                .build();
//        String url = "http://192.168.111.2:8080/WebApp/Userfeature/uploadFile?file_name=" + file.getName() + "&file=" + file;
//        //进行包装，使其支持进度回调
//        final Request request = new Request.Builder().url(url).post(ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener)).build();
//        //开始请求
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("TAG", "error ", e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("TAG", response.body().string());
//                if (response.isSuccessful()) {
//                    System.out.println(response.code());
//
//                    //System.out.println(response.body().string());
//                    String body = response.body().string();
//                    System.out.println(body);
//                    Toast.makeText(getApplicationContext(), body, Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });

    }
}
