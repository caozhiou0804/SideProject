package com.junit.caozhiou.sideproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;
import com.junit.caozhiou.sideproject.entity.WaterFallData;

import java.util.List;

/**
 * 作者：lubote on 2016/6/22 09:41
 * 邮箱：nj.caozhiou@dhjt.com
 */

public class WaterFallAdapter extends RecyclerView.Adapter<WaterFallAdapter.MasonryView>{

    private List<WaterFallData> waterFallDatas;

    public WaterFallAdapter(List<WaterFallData> waterFallDatas) {
        this.waterFallDatas=waterFallDatas;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_waterfall, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {
        masonryView.imageView.setImageResource(waterFallDatas.get(position).getImg());
        masonryView.textView.setText(waterFallDatas.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return waterFallDatas.size();
    }

    public static class MasonryView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.img_waterfall );
            textView= (TextView) itemView.findViewById(R.id.tv_title_waterfall);
        }

    }

}