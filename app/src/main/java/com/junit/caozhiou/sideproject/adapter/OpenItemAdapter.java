package com.junit.caozhiou.sideproject.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.junit.caozhiou.sideproject.R;

/**
 * User: caozhiou(1195002650@qq.com)
 * Date: 2016-07-14
 * Time: 10:01
 * Description:item展开适配
 */
public class OpenItemAdapter extends RecyclerView.Adapter<OpenItemAdapter.MainViewHolder> {

    // 为列表提供数据的数据集合
    final String[] contacts = new String[]{
            "Amanda M. Ormond",
            "Anquises Mejia Bustos",
            "Bellisima Goodchild",
            "Bodo Greenhand",
            "Brogan Allan",
            "Claudia Bosch",
            "Elisa Asmara",
            "Emile Barrientos",
            "Ermes Toscano",
            "Guarino Romani",
            "Heike Dresner",
            "Isobel Chamberlain",
            "Ja'rod Kahnrah",
            "Jessica L. Carrillo",
            "Joseph M. Parks",
            "Kabarann D'Ghor",
            "Karol Perea Paredes",
            "Kotkhe Varrin",
            "Kiera Pritchard",
            "Lavinia Sackville-Baggins",
            "Manville Dubois",
            "Marion Deslauriers",
            "Mewael Semere",
            "Negassi Girmay",
            "Numilen Sarabia Solano",
            "Ovidio Colombo",
            "Simone Hahn",
            "Stanley Ross",
            "Spencer Porter",
            "Tekle Ambessa",
            "Yasmin Alexander"
    };

    // 列表展开标识
    int opened = -1;

    /**
     * 绑定item布局
     *
     * @param parent
     * @param pos
     * @return
     */
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int pos) {
        return new MainViewHolder((ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_item_recycler_view, parent, false));
    }

    /**
     * 绑定数据到控件
     *
     * @param holder
     * @param pos
     */
    @Override
    public void onBindViewHolder(MainViewHolder holder, int pos) {
        final String contact = contacts[pos];
        holder.bind(pos, contact);
    }

    /**
     * 返回列表条数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return contacts.length;
    }

    /**
     * 实例化控件等操作
     */
    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // 标题
        public final TextView contactNameTV;
        // 隐藏的内容
        public final LinearLayout ll_hide_item;


        public final ImageView iv_open_item_arrow;

        // 实例化
        public MainViewHolder(ViewGroup itemView) {
            super(itemView);
            contactNameTV = ((TextView) itemView.findViewById(R.id.contactName));
            iv_open_item_arrow = ((ImageView) itemView.findViewById(R.id.iv_open_item_arrow));
            ll_hide_item = (LinearLayout) itemView.findViewById(R.id.ll_hide_item);
            itemView.setOnClickListener(this);
        }

        // 此方法实现列表的展开和关闭
        public void bind(int pos, String name) {
            contactNameTV.setText(name);

            if (pos == opened) {
                ll_hide_item.setVisibility(View.VISIBLE);
                iv_open_item_arrow.setImageResource(R.drawable.up_arrow);
            } else {
                ll_hide_item.setVisibility(View.GONE);
                iv_open_item_arrow.setImageResource(R.drawable.down_arrow);
            }


        }

        /**
         * 为item添加点击效果
         * (recyclerView是不提供onItemClickListener的。所以列表的点击事件需要我们自己来实现)
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (opened == getPosition()) {
                opened = -1;
                notifyItemChanged(getPosition());
            } else {
                int oldOpened = opened;
                opened = getPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
            }
        }
    }
}