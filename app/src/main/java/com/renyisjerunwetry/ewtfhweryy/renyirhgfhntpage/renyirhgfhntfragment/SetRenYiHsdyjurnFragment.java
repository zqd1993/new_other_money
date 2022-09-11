package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity.RenYiHsdyjurnCancellationActivity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.BaseRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.ConfigRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.MineRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnRemindDialog;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.MainRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnMyToastUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.SPRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.ObserverRenYiHsdyjurnManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.RenYiHsdyjurnRetrofitManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity.AgreementRenYiHsdyjurnActivity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity.AppRenYiHsdyjurnInfoActivity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity.RenYiHsdyjurnSystemSettingActivity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntadapter.RenYiHsdyjurnMineItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SetRenYiHsdyjurnFragment extends BaseRenYiHsdyjurnFragment {

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.mail_tv)
    TextView mail_tv;

    private Bundle bundle;
    private List<MineRenYiHsdyjurnEntity> mList;
    private RenYiHsdyjurnMineItemAdapter renYiHsdyjurnMineItemAdapter;
    private RenYiHsdyjurnRemindDialog renYiHsdyjurnRemindDialog;
    private ClipboardManager clipboard;
    private ClipData clipData;
    private String mailStr;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_ren_yi_dai_erjr_ryntu_set;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mList = new ArrayList<>();
        String phone = SPRenYiHsdyjurnUtil.getString("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        MineRenYiHsdyjurnEntity entity = new MineRenYiHsdyjurnEntity();
        entity.setImgRes(R.drawable.zfdh);
        entity.setItemTv("关于我们");
        MineRenYiHsdyjurnEntity entity1 = new MineRenYiHsdyjurnEntity();
        entity1.setImgRes(R.drawable.lpguy);
        entity1.setItemTv("隐私协议");
        MineRenYiHsdyjurnEntity entity2 = new MineRenYiHsdyjurnEntity();
        entity2.setImgRes(R.drawable.wrhx);
        entity2.setItemTv("注册协议");
        MineRenYiHsdyjurnEntity entity4 = new MineRenYiHsdyjurnEntity();
        entity4.setImgRes(R.drawable.pytisa);
        entity4.setItemTv("系统设置");
        MineRenYiHsdyjurnEntity entity5 = new MineRenYiHsdyjurnEntity();
        entity5.setImgRes(R.drawable.ghjd);
        entity5.setItemTv("注销账户");
        mList.add(entity);
        mList.add(entity1);
        mList.add(entity2);
        mList.add(entity4);
        mList.add(entity5);
        renYiHsdyjurnMineItemAdapter = new RenYiHsdyjurnMineItemAdapter(getActivity(), mList);
        renYiHsdyjurnMineItemAdapter.setHasStableIds(true);
        renYiHsdyjurnMineItemAdapter.setOnItemClickListener(new RenYiHsdyjurnMineItemAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                if (position == 0) {
                    MainRenYiHsdyjurnUtil.startActivity(getActivity(), AppRenYiHsdyjurnInfoActivity.class, null);
                } else if (position == 1) {
                    bundle = new Bundle();
                    bundle.putString("title", "隐私协议");
                    bundle.putString("url", RenYiHsdyjurnRetrofitManager.getYsxy());
                    MainRenYiHsdyjurnUtil.startActivity(getActivity(), AgreementRenYiHsdyjurnActivity.class, bundle);
                } else if (position == 2) {
                    bundle = new Bundle();
                    bundle.putString("title", "注册协议");
                    bundle.putString("url", RenYiHsdyjurnRetrofitManager.getZcxy());
                    MainRenYiHsdyjurnUtil.startActivity(getActivity(), AgreementRenYiHsdyjurnActivity.class, bundle);
                } else if (position == 3) {
                    MainRenYiHsdyjurnUtil.startActivity(getActivity(), RenYiHsdyjurnSystemSettingActivity.class, null);
                } else if (position == 4) {
                    MainRenYiHsdyjurnUtil.startActivity(getActivity(), RenYiHsdyjurnCancellationActivity.class, null);
                }
            }
        });
        rvy.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvy.setHasFixedSize(true);
        rvy.setAdapter(renYiHsdyjurnMineItemAdapter);
        mail_tv.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(mailStr)) {
                clipData = ClipData.newPlainText(null, mailStr);
                clipboard.setPrimaryClip(clipData);
                RenYiHsdyjurnMyToastUtil.showShort("复制成功");
            }
        });
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getCompanyInfo();
    }

    private void getCompanyInfo() {
        RenYiHsdyjurnRetrofitManager.getRetrofitManager().getApiService().getCompanyInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverRenYiHsdyjurnManager<BaseRenYiHsdyjurnEntity<ConfigRenYiHsdyjurnEntity>>() {

                    @Override
                    public void onSuccess(BaseRenYiHsdyjurnEntity<ConfigRenYiHsdyjurnEntity> baseRenYiHsdyjurnEntity) {
                        if (baseRenYiHsdyjurnEntity == null) {
                            return;
                        }
                        ConfigRenYiHsdyjurnEntity configRenYiHsdyjurnEntity = baseRenYiHsdyjurnEntity.getData();
                        if (configRenYiHsdyjurnEntity != null) {
                            mailStr = configRenYiHsdyjurnEntity.getGsmail();
                            mail_tv.setText(mailStr);
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }
}
