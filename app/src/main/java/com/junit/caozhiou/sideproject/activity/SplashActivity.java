package com.junit.caozhiou.sideproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.base.BaseFragmentActivity;
import com.junit.caozhiou.sideproject.util.CharacterParser;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class SplashActivity extends BaseFragmentActivity {


    private VideoView surface_view;

    // 汉子转换成拼音
    private CharacterParser characterParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        surface_view = (VideoView) findViewById(R.id.surface_view);
        Vitamio.isInitialized(getApplicationContext());
//        characterParser = CharacterParser.getInstance();
//        String pinyin = characterParser
//                .getSelling("欢迎光临");
        playVedio();
//        intentHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent =new Intent(SplashActivity.this,HomeActivity.class);
//                startActivity(intent);
//
//            }
//        },500);
    }

//    private Handler intentHandler = new Handler();

    private void playVedio() {
        String path = Environment.getExternalStorageDirectory() + "/DCIM/Camera/test.mp4";

        if (path == "") {
            // Tell the user to provide a media file URL/path.
            Toast.makeText(SplashActivity.this, "Please edit VideoViewDemo Activity, and set path" + " variable to your media file URL/path", Toast.LENGTH_LONG).show();
            return;
        } else {
            /*
			 * Alternatively,for streaming media you can use
			 * mVideoView.setVideoURI(Uri.parse(URLstring));
			 */
            surface_view.setVideoPath(path);
            final MediaController controller = new MediaController(this);
            surface_view.setMediaController(controller);
            surface_view.requestFocus();
            surface_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                }
            });
            surface_view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            surface_view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(controller.isShowing()){

                        controller.hide();
                    }

                    return true;
                }
            });


        }
    }

}
