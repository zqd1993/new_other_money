package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntfragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity.RenYiHsdyjurnShowGoodsActivity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.BaseRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.RenYiHsdyjurnGoodsEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.RenYiHsdyjurnTokenEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.SPRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.MainRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.ObserverRenYiHsdyjurnManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.RenYiHsdyjurnRetrofitManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntadapter.RenYiHsdyjurnGoodsGoodsItemAdapter;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class GoodsRenYiHsdyjurnFragment extends BaseRenYiHsdyjurnFragment {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rvy)
    RecyclerView rvy;

    private String token;
    private Bundle webBundle;
    private RenYiHsdyjurnGoodsGoodsItemAdapter mGoodsItemAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ren_yi_dai_erjr_ryntu_goods;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aindex();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        aindex();
    }

    private void aindex() {
        token = SPRenYiHsdyjurnUtil.getString("token");
        RenYiHsdyjurnTokenEntity entity = new RenYiHsdyjurnTokenEntity();
        entity.setToken(token);
        RequestBody body = MainRenYiHsdyjurnUtil.createBody(new Gson().toJson(entity));
        RenYiHsdyjurnRetrofitManager.getRetrofitManager().getApiService().aindex(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverRenYiHsdyjurnManager<BaseRenYiHsdyjurnEntity<List<RenYiHsdyjurnGoodsEntity>>>() {

                    @Override
                    public void onSuccess(BaseRenYiHsdyjurnEntity<List<RenYiHsdyjurnGoodsEntity>> baseRenYiHsdyjurnEntity) {
                        if (baseRenYiHsdyjurnEntity == null) {
                            return;
                        }
                        if (baseRenYiHsdyjurnEntity.getData() != null && baseRenYiHsdyjurnEntity.getData().size() > 0) {
                            mGoodsItemAdapter = new RenYiHsdyjurnGoodsGoodsItemAdapter(getActivity(), baseRenYiHsdyjurnEntity.getData());
                            mGoodsItemAdapter.setHasStableIds(true);
                            mGoodsItemAdapter.setOnItemClickListener(new RenYiHsdyjurnGoodsGoodsItemAdapter.OnItemClickListener() {
                                @Override
                                public void itemClicked(RenYiHsdyjurnGoodsEntity renYiHsdyjurnGoodsEntity) {
                                    productClick(renYiHsdyjurnGoodsEntity);
                                }
                            });
                            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvy.setHasFixedSize(true);
                            rvy.setAdapter(mGoodsItemAdapter);
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }

                    @Override
                    public void onFinish() {
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void productClick(RenYiHsdyjurnGoodsEntity model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putInt("tag", 3);
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            MainRenYiHsdyjurnUtil.startActivity(getActivity(), RenYiHsdyjurnShowGoodsActivity.class, webBundle);
        }
    }
}
