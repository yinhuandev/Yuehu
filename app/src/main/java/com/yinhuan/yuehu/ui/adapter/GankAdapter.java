package com.yinhuan.yuehu.ui.adapter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.databinding.ItemGankBinding;
import com.yinhuan.yuehu.mvp.bean.GankBean;
import com.yinhuan.yuehu.ui.activity.WebActivity;
import com.yinhuan.yuehu.util.ImageUtil;
import com.yinhuan.yuehu.util.LogUtil;

import java.util.List;

/**
 * Created by yinhuan on 2017/1/31.
 */

public class GankAdapter extends AnimRecyclerViewAdapter<GankAdapter.ViewHolder> {


    private List<GankBean> mData;

    public GankAdapter(List<GankBean> data){
        this.mData = data;
    }

    @Override
    public GankAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemGankBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_gank, parent, false);
        View itemView = binding.getRoot();
        return new GankAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GankAdapter.ViewHolder holder, int position) {
        GankBean bean = mData.get(position);
        holder.bindView(bean,position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ItemGankBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.getBinding(this.itemView);
        }

        public void bindView(final GankBean bean, int position){
            LogUtil.d("GankAdapter","BindView："+bean.getDesc());
            binding.setGankBean(bean);
            binding.executePendingBindings();
            if (bean.getImages() != null
                    && bean.getImages().size() > 0
                    && !TextUtils.isEmpty(bean.getImages().get(0))){
                binding.imgGank.setVisibility(View.VISIBLE);
                ImageUtil.getInstance().loadGif(bean.getImages().get(0),binding.imgGank);
            }else {
                binding.imgGank.setVisibility(View.GONE);
            }

            binding.llGank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //WebViewActivity.loadUrl(v.getContext(), bean.getUrl(), "加载中...");
                    Intent intent = WebActivity.newIntent(v.getContext(),bean.getUrl(),bean.getDesc());
                    v.getContext().startActivity(intent);
                }
            });

           // showItemAnim(binding.llGank, position);

        }
    }

    public void addAll(List<GankBean> data) {
        this.mData.addAll(data);
    }


    public void clear() {
        mData.clear();
    }

}
