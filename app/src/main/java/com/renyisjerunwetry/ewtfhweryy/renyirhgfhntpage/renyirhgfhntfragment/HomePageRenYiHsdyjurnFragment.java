package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntadapter.RenYiHsdyjurnGoodsItemAdapter;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.BaseRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.RenYiHsdyjurnGoodsEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.RenYiHsdyjurnTokenEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.SPRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.MainRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.ObserverRenYiHsdyjurnManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.RenYiHsdyjurnRetrofitManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity.MainRenYiHsdyjurnActivity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity.RenYiHsdyjurnShowGoodsActivity;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class HomePageRenYiHsdyjurnFragment extends BaseRenYiHsdyjurnFragment {

    @BindView(R.id.edu_tv)
    TextView eduTv;
    @BindView(R.id.month_tv)
    TextView monthTv;
    @BindView(R.id.mianxi_tv)
    TextView mianxiTv;
    @BindView(R.id.renshu_tv)
    TextView renshuTv;
    @BindView(R.id.lingqu_sl)
    View lingquSl;
    @BindView(R.id.more_ll)
    View moreLl;
    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;

    private String token;
    private Bundle webBundle;
    private RenYiHsdyjurnGoodsEntity topEntity;
    private RenYiHsdyjurnGoodsItemAdapter mRenYiHsdyjurnGoodsItemAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page_ren_yi_dai_erjr_ryntu;
    }

    @Override
    public void onResume() {
        super.onResume();
        aindex();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        lingquSl.setOnClickListener(v -> {
            if (topEntity != null) {
                productClick(topEntity);
            }
        });
        moreLl.setOnClickListener(view -> {
            ((MainRenYiHsdyjurnActivity)getActivity()).changePage();
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aindex();
            }
        });
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
                            mRenYiHsdyjurnGoodsItemAdapter = new RenYiHsdyjurnGoodsItemAdapter(getActivity(), baseRenYiHsdyjurnEntity.getData());
                            mRenYiHsdyjurnGoodsItemAdapter.setHasStableIds(true);
                            mRenYiHsdyjurnGoodsItemAdapter.setOnItemClickListener(new RenYiHsdyjurnGoodsItemAdapter.OnItemClickListener() {
                                @Override
                                public void itemClicked(RenYiHsdyjurnGoodsEntity renYiHsdyjurnGoodsEntity) {
                                    productClick(renYiHsdyjurnGoodsEntity);
                                }
                            });
                            rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvy.setHasFixedSize(true);
                            rvy.setAdapter(mRenYiHsdyjurnGoodsItemAdapter);
                        }
                        if (baseRenYiHsdyjurnEntity.getTop() != null) {
                            renshuTv.setText(String.valueOf(baseRenYiHsdyjurnEntity.getTop().getNum()));
                            monthTv.setText(baseRenYiHsdyjurnEntity.getTop().getFan_time().substring(0, 2));
                            mianxiTv.setText(String.valueOf(baseRenYiHsdyjurnEntity.getTop().getDisplay()));
                            eduTv.setText(String.valueOf(baseRenYiHsdyjurnEntity.getTop().getMax_money()));
                            topEntity = baseRenYiHsdyjurnEntity.getTop();
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
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            MainRenYiHsdyjurnUtil.startActivity(getActivity(), RenYiHsdyjurnShowGoodsActivity.class, webBundle);
        }
    }
}
