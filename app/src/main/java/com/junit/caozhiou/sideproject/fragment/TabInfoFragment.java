package com.junit.caozhiou.sideproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.TabLayout;

import com.junit.caozhiou.sideproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * :使用google支持库实现Tab标签
 */
public class TabInfoFragment extends Fragment {
    private String[] titles = new String[]{"附近的人"};
//            , "下拉刷新", "瀑布流", "grid拖拽", "list拖拽", "PinnedHeader", "展开功能", "在线教育", "互联网金融", "大公司", "专栏", "新产品"};
    private View mView;
    private TabLayout tab_layout;
    private ViewPager info_viewpager;
    private List<Fragment> fragments;
    private CNKFixedPagerAdapter mPagerAdater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.tab_info_fragment_layout, container, false);
            initViews();
            initValidata();
        }
        return mView;
    }

    private void initViews() {
        tab_layout = (TabLayout) mView.findViewById(R.id.tab_layout);
        info_viewpager = (ViewPager) mView.findViewById(R.id.info_viewpager);

    }

    private void initValidata() {
        fragments = new ArrayList<>();
//        for(int i=0;i<12;i++){
//            OneFragment oneFragment=new OneFragment();
//            Bundle bundle=new Bundle();
//            bundle.putString("extra",titles[i]);
//            oneFragment.setArguments(bundle);
//            fragments.add(oneFragment);
//        }
        for (int i = 0; i < titles.length; i++) {
            switch (i) {

                case 0:
                    NearByPersonFragment nearByPersonFragment = new NearByPersonFragment();
                    fragments.add(nearByPersonFragment);
                    break;
                case 1:
                    PullToRefreshRecyclerViewFragment pullToRefreshRecyclerViewFragment = new PullToRefreshRecyclerViewFragment();
                    fragments.add(pullToRefreshRecyclerViewFragment);
                    break;
                case 2:
                    WaterFallRecyclerViewFragment waterFallRecyclerViewFragment = new WaterFallRecyclerViewFragment();
                    fragments.add(waterFallRecyclerViewFragment);
                    break;
                case 3:
                    RecyclerGridFragment recyclerGridFragment = new RecyclerGridFragment();
                    fragments.add(recyclerGridFragment);
                    break;
                case 4:
                    RecyclerListFragment recyclerListFragment = new RecyclerListFragment();
                    fragments.add(recyclerListFragment);
                    break;
                case 5:
                    PinnedHeaderRecyclerViewFragment pinnedHeaderRecyclerViewFragment = new PinnedHeaderRecyclerViewFragment();
                    fragments.add(pinnedHeaderRecyclerViewFragment);
                    break;
                case 6:
                    OpenItemRecyclerViewFragment openItemRecyclerViewFragment = new OpenItemRecyclerViewFragment();
                    fragments.add(openItemRecyclerViewFragment);
                    break;
                default:
                    OneFragment oneFragment = new OneFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("extra", titles[i]);
                    oneFragment.setArguments(bundle);
                    fragments.add(oneFragment);
                    break;
            }
        }
        //创建Fragment的 ViewPager 自定义适配器
        mPagerAdater = new CNKFixedPagerAdapter(getChildFragmentManager(), getActivity());
        //设置显示的标题
        mPagerAdater.setTitles(titles);
        //设置需要进行滑动的页面Fragment
        mPagerAdater.setFragments(fragments);

        info_viewpager.setAdapter(mPagerAdater);

        tab_layout.setupWithViewPager(info_viewpager);

        //设置Tablayout
        //设置TabLayout模式 -该使用Tab数量比较多的情况
        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置自定义Tab--加入图标的demo
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tab_layout.getTabAt(i);
            tab.setCustomView(mPagerAdater.getTabView(i));
        }
    }
}
