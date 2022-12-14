package com.terdfgdfjieyiji.jghrstyfghtertry.page.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.GoodsEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.RetrofitManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodsItemAdapter extends RecyclerView.Adapter<GoodsItemAdapter.GoodsItemHolder> {

    private Context context;

    private List<GoodsEntity> mList;

    private OnItemClickListener onItemClickListener;

    public GoodsItemAdapter(Context context, List<GoodsEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public GoodsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_goods_item, parent, false);
        return new GoodsItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsItemAdapter.GoodsItemHolder holder, int position) {
        GoodsEntity model = mList.get(position);
        holder.monthTv.setText("1-" + model.getFan_time());
        holder.eduTv.setText(String.valueOf(model.getMax_money()));
        holder.monthTv.setText(model.getFan_time().substring(0, 2));
        holder.productNameTv.setText(model.getTitle());
        holder.dayMoneyTv.setText(model.getDay_money());
        if (!TextUtils.isEmpty(RetrofitManager.API_BASE_URL)) {
            Glide.with(context).load(RetrofitManager.API_BASE_URL + model.getImgs()).into(holder.productImg);
        }
        holder.clickView.setOnClickListener(v -> {
            if (onItemClickListener != null){
                onItemClickListener.itemClicked(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GoodsItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.product_name_tv)
        TextView productNameTv;
        @BindView(R.id.edu_tv)
        TextView eduTv;
        @BindView(R.id.day_money_tv)
        TextView dayMoneyTv;
        @BindView(R.id.month_tv)
        TextView monthTv;
        @BindView(R.id.click_view)
        View clickView;

        public GoodsItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClicked(GoodsEntity goodsEntity);
    }

}
