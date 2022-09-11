package com.terdfgdfjieyiji.jghrstyfghtertry.page.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.BaseEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.ConfigEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.MineEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.RemindDialog;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MainUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MyToastUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.SPUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.ObserverManager;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.RetrofitManager;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.activity.AgreementActivity;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.activity.AppInfoActivity;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.activity.CancellationActivity;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.activity.SystemSettingActivity;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.adapter.MineItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SetFragment extends BaseFragment{

    @BindView(R.id.rvy)
    RecyclerView rvy;
    @BindView(R.id.phone_tv)
    TextView phoneTv;

    private Bundle bundle;
    private List<MineEntity> mList;
    private MineItemAdapter mineItemAdapter;
    private RemindDialog remindDialog;
    private ClipboardManager clipboard;
    private ClipData clipData;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_set;
    }

    @Override
    public void initData() {
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        mList = new ArrayList<>();
        String phone = SPUtil.getString("phone");
        if (!TextUtils.isEmpty(phone) && phone.length() > 10) {
            phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        }
        MineEntity entity = new MineEntity();
        entity.setImgRes(R.drawable.fjyuims);
        entity.setItemTv("关于我们");
        MineEntity entity1 = new MineEntity();
        entity1.setImgRes(R.drawable.ewrgbfgjnhsr);
        entity1.setItemTv("隐私协议");
        MineEntity entity2 = new MineEntity();
        entity2.setImgRes(R.drawable.ewgdfh);
        entity2.setItemTv("注册协议");
        MineEntity entity3 = new MineEntity();
        entity3.setImgRes(R.drawable.ergbfgjng);
        entity3.setItemTv("投诉邮箱");
        MineEntity entity4 = new MineEntity();
        entity4.setImgRes(R.drawable.mfgdrt);
        entity4.setItemTv("系统设置");
        MineEntity entity5 = new MineEntity();
        entity5.setImgRes(R.drawable.nmsfhdzt);
        entity5.setItemTv("注销账户");
        mList.add(entity);
        mList.add(entity1);
        mList.add(entity2);
        mList.add(entity3);
        mList.add(entity4);
        mList.add(entity5);
        mineItemAdapter = new MineItemAdapter(getActivity(), mList);
        mineItemAdapter.setHasStableIds(true);
        mineItemAdapter.setOnItemClickListener(new MineItemAdapter.OnItemClickListener() {
            @Override
            public void itemClicked(int position) {
                if (position == 0){
                    MainUtil.startActivity(getActivity(), AppInfoActivity.class, null);
                } else if (position == 1){
                    bundle = new Bundle();
                    bundle.putString("title", "隐私协议");
                    bundle.putString("url", RetrofitManager.getYsxy());
                    MainUtil.startActivity(getActivity(), AgreementActivity.class, bundle);
                } else if (position == 2){
                    bundle = new Bundle();
                    bundle.putString("title", "注册协议");
                    bundle.putString("url", RetrofitManager.getZcxy());
                    MainUtil.startActivity(getActivity(), AgreementActivity.class, bundle);
                } else if (position == 3){
                    getCompanyInfo();
                } else if (position == 4){
                    MainUtil.startActivity(getActivity(), SystemSettingActivity.class, null);
                } else if (position == 5){
                    MainUtil.startActivity(getActivity(), CancellationActivity.class, null);
                }
            }
        });
        rvy.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvy.setHasFixedSize(true);
        rvy.setAdapter(mineItemAdapter);
    }

    @Override
    public void initListener() {

    }

    private void getCompanyInfo() {
        RetrofitManager.getRetrofitManager().getApiService().getCompanyInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseEntity<ConfigEntity>>() {

                    @Override
                    public void onSuccess(BaseEntity<ConfigEntity> baseEntity) {
                        if (baseEntity == null) {
                            return;
                        }
                        ConfigEntity configEntity = baseEntity.getData();
                        if (configEntity != null) {
                            remindDialog = new RemindDialog(getActivity(), "温馨提示", configEntity.getGsmail(), false);
                            remindDialog.setBtnClickListener(new RemindDialog.BtnClickListener() {
                                @Override
                                public void leftClicked() {
                                    remindDialog.dismiss();
                                }

                                @Override
                                public void rightClicked() {
                                    clipData = ClipData.newPlainText(null, configEntity.getGsmail());
                                    clipboard.setPrimaryClip(clipData);
                                    MyToastUtil.showShort("复制成功");
                                    remindDialog.dismiss();
                                }
                            });
                            remindDialog.show();
                            remindDialog.setBtnStr("取消", "复制");
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
