package com.yinhuan.yuehu.ui.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.databinding.ItemDailyBinding;
import com.yinhuan.yuehu.mvp.bean.DailyBean;
import com.yinhuan.yuehu.ui.activity.DailyDetailsActivity;
import com.yinhuan.yuehu.util.ImageUtil;

import java.util.List;

/**
 * Created by yinhuan on 2017/1/31.
 */

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    ItemDailyBinding binding;
    private List<DailyBean.StoriesBean> mData;

    public DailyAdapter(List<DailyBean.StoriesBean> data){
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(inflater, R.layout.item_daily, parent, false);
        View itemView = binding.getRoot();
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DailyBean.StoriesBean bean = mData.get(position);
        holder.bindView(bean);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ItemDailyBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.getBinding(itemView);
        }

        public void bindView(final DailyBean.StoriesBean bean){

            binding.setDailyBean(bean);
            binding.executePendingBindings();

            if (bean.getImages() != null){
                ImageUtil.loadGif(bean.getImages().get(0), binding.imgDaily);
            }
            binding.llDaily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DailyDetailsActivity.class);
                    intent.putExtra("daily_id", bean.getId());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public void addAll(List<DailyBean.StoriesBean> data) {
        this.mData.addAll(data);
    }


    public void clear() {
        mData.clear();
    }

}
