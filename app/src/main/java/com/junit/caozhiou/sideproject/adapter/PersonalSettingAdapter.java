package com.junit.caozhiou.sideproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.entity.PersonalLeftData;

import java.util.List;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-12
 * Time: 14:20
 * Description:slidingMenu左边recyclerview适配
 */
public class PersonalSettingAdapter extends RecyclerView.Adapter<PersonalSettingAdapter.ViewHolder> {

    private Context ctx;
    private List<PersonalLeftData> dataList;

    private ItemClickListener clickListener;

    public PersonalSettingAdapter(Context ctx, List<PersonalLeftData> dataList, ItemClickListener clickListener) {
        this.dataList = dataList;
        this.ctx = ctx;
        this.clickListener=clickListener;
    }

    @Override
    public PersonalSettingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_setting, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PersonalSettingAdapter.ViewHolder holder, final int position) {
        PersonalLeftData data = dataList.get(position);
        if (null == data)
            return;
        holder.iv_setting_cion.setImageResource(data.getIcon_res());
        holder.tv_setting_title.setText(data.getSettingTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_setting_cion;
        private TextView tv_setting_title;
        private View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            iv_setting_cion = (ImageView) itemView.findViewById(R.id.iv_setting_cion);
            tv_setting_title = (TextView) itemView.findViewById(R.id.tv_setting_title);

        }
    }

    public interface ItemClickListener {

        void onItemClick(PersonalLeftData personalLeftData);
    }
}
