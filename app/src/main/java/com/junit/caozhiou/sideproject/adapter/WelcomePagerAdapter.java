package com.junit.caozhiou.sideproject.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;

import java.util.List;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-11
 * Time: 16:41
 * Description:
 */
public class WelcomePagerAdapter extends PagerAdapter {

    private List<String> datas;

    private Context ctx;

    public WelcomePagerAdapter(Context ctx, List<String> datas) {
        this.datas = datas;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        // return images.length;
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_welcome_pager, null);
        TextView tv_welcome_title = (TextView) view.findViewById(R.id.tv_welcome_title);
        tv_welcome_title.setText(datas.get(position));
        container.addView(view);
        return view;
    }
}
