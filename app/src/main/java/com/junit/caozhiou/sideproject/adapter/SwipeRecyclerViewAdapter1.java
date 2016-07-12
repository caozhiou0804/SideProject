package com.junit.caozhiou.sideproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.util.MyDialog;
import com.junit.caozhiou.sideproject.view.SwipeItemLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-06
 * Time: 18:13
 * Description:侧滑删除recyclerView
 */


public class SwipeRecyclerViewAdapter1 extends RecyclerView.Adapter<SwipeRecyclerViewAdapter1.ViewHolder> {

    private List<SwipeItemLayout> mOpenedSil = new ArrayList<SwipeItemLayout>();
    private Context ctx;
    private List<String> datas;
    private OnDeleteItemListener deleteItemListener;

    public SwipeRecyclerViewAdapter1(Context ctx, List<String> datas, OnDeleteItemListener deleteItemListener) {
        this.ctx = ctx;
        this.datas = datas;
        this.deleteItemListener = deleteItemListener;
    }

    @Override
    public int getItemCount() {

        return datas.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_swipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SwipeItemLayout swipeRoot = holder.mRoot;
        holder.mName.setText(datas.get(position));
        swipeRoot.setSwipeAble(true);
        swipeRoot.close();
        swipeRoot.setDelegate(new SwipeItemLayout.SwipeItemLayoutDelegate() {
            @Override
            public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
                mOpenedSil.add(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
                mOpenedSil.remove(swipeItemLayout);
            }

            @Override
            public void onSwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {
                closeOpenedSwipeItemLayoutWithAnim();
            }
        });
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                datas.remove(datas.get(position));
                final MyDialog dialog = new MyDialog(ctx);
                dialog.onButton1Listener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        deleteItemListener.deleteItem(position);
                        notifyDataSetChanged();
                        Toast.makeText(ctx, position + "", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.onButton2Listener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                if (null != dialog)
                    dialog.show();


            }
        });
    }

    public void closeOpenedSwipeItemLayoutWithAnim() {
        for (SwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public SwipeItemLayout mRoot;
        public TextView mDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.item_contact_title);
            mRoot = (SwipeItemLayout) itemView.findViewById(R.id.item_contact_swipe_root);
            mDelete = (TextView) itemView.findViewById(R.id.item_contact_delete);


        }
    }

    public interface OnDeleteItemListener {
        void deleteItem(int position);
    }
}
