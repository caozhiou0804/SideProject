
package com.junit.caozhiou.sideproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.base.BaseFragmentActivity;

public class MainMessageActivity extends BaseFragmentActivity {

    private Button button;
    private TextView titleText;

    private String intentText;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_message;
    }

    @Override
    protected void $setToolBar() {
        // 用父类的样式
        super.$setToolBar();
    }

    @Override
    protected void initView() {
        $setToolBar();
        button = $findViewById(R.id.button);
        titleText = $findViewById(R.id.tv_title);
    }

    @Override
    protected void setListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        titleText.setText("标题");
        Bundle bundle = $getIntentExtra();
        if(null != bundle)
            intentText = bundle.getString("loginKey");
        Toast.makeText(ctx,"Click:" + intentText,Toast.LENGTH_LONG).show();
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.button:
               Toast.makeText(ctx,"Click:" + intentText,Toast.LENGTH_LONG).show();
                break;
        }
    }
}