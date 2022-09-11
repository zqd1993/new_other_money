package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnMyToastUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnRemindDialog;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnSwitchButtonView;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.SPRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.MainRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnStatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RenYiHsdyjurnSystemSettingActivity extends AppCompatActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    RenYiHsdyjurnSwitchButtonView switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone;

    private boolean isPush;
    private RenYiHsdyjurnRemindDialog renYiHsdyjurnRemindDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RenYiHsdyjurnStatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_ren_yi_dai_erjr_ryntu_setting);
        ButterKnife.bind(this);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SPRenYiHsdyjurnUtil.getString("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SPRenYiHsdyjurnUtil.getBool("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new RenYiHsdyjurnSwitchButtonView.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SPRenYiHsdyjurnUtil.saveBool("push", isChecked);
                RenYiHsdyjurnMyToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            renYiHsdyjurnRemindDialog = new RenYiHsdyjurnRemindDialog(this, "温馨提示", "确定退出当前登录", false);
            renYiHsdyjurnRemindDialog.setBtnClickListener(new RenYiHsdyjurnRemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    renYiHsdyjurnRemindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    renYiHsdyjurnRemindDialog.dismiss();
                    SPRenYiHsdyjurnUtil.saveString("phone", "");
                    MainRenYiHsdyjurnUtil.startActivity(RenYiHsdyjurnSystemSettingActivity.this, LoginRenYiHsdyjurnActivity.class, null);
                    finish();
                }
            });
            renYiHsdyjurnRemindDialog.show();
            renYiHsdyjurnRemindDialog.setBtnStr("取消", "退出");
        });
    }
}
