package com.terdfgdfjieyiji.jghrstyfghtertry.page.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.MineEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineItemAdapter extends RecyclerView.Adapter<MineItemAdapter.MineItemHolder> {

    private Context context;

    private List<MineEntity> mList;

    private OnItemClickListener onItemClickListener;

    public MineItemAdapter(Context context, List<MineEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MineItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_mine_item, parent, false);
        return new MineItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MineItemAdapter.MineItemHolder holder, int position) {
        MineEntity model = mList.get(position);
        holder.itemImg.setImageResource(model.getImgRes());
        holder.itemTitleTv.setText(model.getItemTv());
        holder.parentLl.setOnClickListener(v -> {
            if (onItemClickListener != null){
                onItemClickListener.itemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MineItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.item_title_tv)
        TextView itemTitleTv;
        @BindView(R.id.parent_ll)
        View parentLl;

        public MineItemHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void itemClicked(int position);
    }

}
