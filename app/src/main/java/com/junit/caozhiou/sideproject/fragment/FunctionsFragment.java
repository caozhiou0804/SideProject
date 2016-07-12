package com.junit.caozhiou.sideproject.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.activity.PlayVideoActivity;
import com.junit.caozhiou.sideproject.activity.PlayVideoListActivity;
import com.junit.caozhiou.sideproject.activity.SwipeRecyclerViewActivity;
import com.junit.caozhiou.sideproject.activity.WelcomeActivity;
import com.junit.caozhiou.sideproject.entity.ScreenBean;
import com.junit.caozhiou.sideproject.view.SwipeItemLayout;


public class FunctionsFragment extends Fragment {

    private View contentView;
    private Button btn_go_to_tv;
    private Button btn_go_video_list;
    private ScreenBean screenBean;
    private SwipeItemLayout item_contact_swipe_root;
    private TextView tv_delete, tv_share;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (null == contentView) {
            contentView = inflater.inflate(R.layout.fragment_functions, container, false);
        }
        initView();
        return contentView;
    }

    private void initView() {

        btn_go_to_tv = (Button) contentView.findViewById(R.id.btn_go_to_tv);
        btn_go_video_list = (Button) contentView.findViewById(R.id.btn_go_video_list);
        btn_go_to_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayVideoActivity.class);
                startActivity(intent);
            }
        });
        btn_go_video_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_list = new Intent(getActivity(), PlayVideoListActivity.class);
                startActivity(intent_list);
            }
        });

        item_contact_swipe_root = (SwipeItemLayout) contentView.findViewById(R.id.item_contact_swipe_root);

        item_contact_swipe_root.setSwipeAble(true);
        item_contact_swipe_root.setDelegate(new SwipeItemLayout.SwipeItemLayoutDelegate() {
            @Override
            public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
//                closeOpenedSwipeItemLayoutWithAnim();
//                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
//                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {
//                closeOpenedSwipeItemLayoutWithAnim();
            }
        });

        tv_delete = (TextView) contentView.findViewById(R.id.tv_delete);
        tv_share = (TextView) contentView.findViewById(R.id.tv_share);

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeOpenedSwipeItemLayoutWithAnim();
                startActivity(new Intent(getActivity(), SwipeRecyclerViewActivity.class));
            }
        });
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeOpenedSwipeItemLayoutWithAnim();
                startActivity(new Intent(getActivity(), WelcomeActivity.class));

            }
        });
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        item_contact_swipe_root.closeWithAnim();
    }
}
