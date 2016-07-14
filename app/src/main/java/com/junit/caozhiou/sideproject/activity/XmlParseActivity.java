package com.junit.caozhiou.sideproject.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.util.PubUtil;
import com.junit.caozhiou.sideproject.util.XmlParserHandler;

public class XmlParseActivity extends Activity {

    private TextView tv_xml_file_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parse);
        tv_xml_file_content = (TextView) findViewById(R.id.tv_xml_file_content);
        XmlParserHandler handler = PubUtil.getAssetsFile(this, "province_data.xml");
        tv_xml_file_content.setText(handler.getDataList().toString());
    }
}
