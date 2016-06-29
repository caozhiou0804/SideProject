package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;

/**
 * 当前类注释:第一个Fragment
 */
public class OneFragment  extends Fragment{
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView==null){
            mView=inflater.inflate(R.layout.one_frag_layout,container,false);
            TextView tv_content=(TextView)mView.findViewById(R.id.tv_content);
            Bundle bundle=getArguments();
            if(bundle!=null){
                tv_content.setText(bundle.getString("extra"));
            }

        }
        return mView;
    }
}
