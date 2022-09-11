package com.terdfgdfjieyiji.jghrstyfghtertry.page.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.BaseEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.GoodsEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.TokenEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.SPUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MainUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.ObserverManager;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.RetrofitManager;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.activity.MainActivity;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.activity.ShowGoodsActivity;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.adapter.GoodsItemAdapter;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class HomePageFragment extends BaseFragment {

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
    private GoodsEntity topEntity;
    private GoodsItemAdapter mGoodsItemAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_page;
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
            ((MainActivity)getActivity()).changePage();
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                aindex();
            }
        });
    }

    private void aindex() {
        token = SPUtil.getString("token");
        TokenEntity entity = new TokenEntity();
        entity.setToken(token);
        RequestBody body = MainUtil.createBody(new Gson().toJson(entity));
        RetrofitManager.getRetrofitManager().getApiService().aindex(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseEntity<List<GoodsEntity>>>() {

                    @Override
                    public void onSuccess(BaseEntity<List<GoodsEntity>> baseEntity) {
                        if (baseEntity == null) {
                            return;
                        }
                        if (baseEntity.getData() != null && baseEntity.getData().size() > 0) {
                            mGoodsItemAdapter = new GoodsItemAdapter(getActivity(), baseEntity.getData());
                            mGoodsItemAdapter.setHasStableIds(true);
                            mGoodsItemAdapter.setOnItemClickListener(new GoodsItemAdapter.OnItemClickListener() {
                                @Override
                                public void itemClicked(GoodsEntity goodsEntity) {
                                    productClick(goodsEntity);
                                }
                            });
                            rvy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            rvy.setHasFixedSize(true);
                            rvy.setAdapter(mGoodsItemAdapter);
                        }
                        if (baseEntity.getTop() != null) {
                            renshuTv.setText(String.valueOf(baseEntity.getTop().getNum()));
                            monthTv.setText(baseEntity.getTop().getFan_time().substring(0, 2));
                            mianxiTv.setText(String.valueOf(baseEntity.getTop().getDisplay()));
                            eduTv.setText(String.valueOf(baseEntity.getTop().getMax_money()));
                            topEntity = baseEntity.getTop();
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

    private void productClick(GoodsEntity model) {
        if (model != null) {
            webBundle = new Bundle();
            webBundle.putString("url", model.getUrls());
            webBundle.putString("title", model.getTitle());
            MainUtil.startActivity(getActivity(), ShowGoodsActivity.class, webBundle);
        }
    }
}
