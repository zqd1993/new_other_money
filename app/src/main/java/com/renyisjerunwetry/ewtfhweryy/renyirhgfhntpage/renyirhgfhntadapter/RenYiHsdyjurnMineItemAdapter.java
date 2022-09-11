package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.MineRenYiHsdyjurnEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RenYiHsdyjurnMineItemAdapter extends RecyclerView.Adapter<RenYiHsdyjurnMineItemAdapter.MineItemHolder> {

    private Context context;

    private List<MineRenYiHsdyjurnEntity> mList;

    private OnItemClickListener onItemClickListener;

    public RenYiHsdyjurnMineItemAdapter(Context context, List<MineRenYiHsdyjurnEntity> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public MineItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_mine_item_ren_yi_dai_erjr_ryntu, parent, false);
        return new MineItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RenYiHsdyjurnMineItemAdapter.MineItemHolder holder, int position) {
        MineRenYiHsdyjurnEntity model = mList.get(position);
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
