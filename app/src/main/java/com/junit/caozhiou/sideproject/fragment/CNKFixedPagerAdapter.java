package com.junit.caozhiou.sideproject.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.application.FDApplication;
import com.junit.caozhiou.sideproject.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment，Viewpager的自定义适配器
 */
public class CNKFixedPagerAdapter extends FragmentStatePagerAdapter {
    //FragmentPagerAdapter因此适用于那些相对静态的页，数量也比较少的那种；如果需要处理有很多页，
// 并且数据动态性较大、占用内存较多的情况，应该使用FragmentStatePagerAdapter
//    http://www.cnblogs.com/lianghui66/p/3607091.html
    private String[] titles;
    private LayoutInflater mInflater;

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    private List<Fragment> fragments;
    private Context context;

    public CNKFixedPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container, position);
        } catch (Exception e) {

        }
        return fragment;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

    //此方法用来显示tab上的名字
//    @Override
//    public CharSequence getPageTitle(int position) {
//
//        return titles[position % titles.length];
//    }
    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    /**
     * 添加getTabView的方法，来进行自定义Tab的布局View
     *
     * @param position
     * @return
     */
    public View getTabView(int position) {
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.tab_item_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
//        if (position == 0) {
//            tv.setTextColor(context.getResources().getColor(R.color.white));
//        }else {
//            tv.setTextColor(context.getResources().getColor(R.color.pink_color));
//        }
        if (titles.length <= 4 && titles.length > 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ScreenUtil.getScreenWidth(context) / titles.length, LinearLayout.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
        }
        tv.setText(titles[position]);
        return view;
    }


}
