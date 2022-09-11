package com.terdfgdfjieyiji.jghrstyfghtertry.page.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MyToastUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.RemindDialog;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.SPUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MainUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.StatusBarUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.SwitchButtonView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemSettingActivity extends AppCompatActivity {

    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.switch_btn)
    SwitchButtonView switchBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    ImageView backImg;
    @BindView(R.id.logout_btn)
    TextView logoutBtn;

    private String phone;

    private boolean isPush;
    private RemindDialog remindDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        backImg.setOnClickListener(v -> finish());
        titleTv.setText("设置");
        phone = SPUtil.getString("phone");
        phoneTv.setText(phone.replace(phone.substring(3, 7), "****"));
        isPush = SPUtil.getBool("push");
        switchBtn.setChecked(isPush);
        switchBtn.setmOnCheckedChangeListener(new SwitchButtonView.OnCheckedChangeListener() {
            @Override
            public void OnCheckedChanged(boolean isChecked) {
                SPUtil.saveBool("push", isChecked);
                MyToastUtil.showShort(isChecked ? "开启成功" : "关闭成功");
            }
        });
        logoutBtn.setOnClickListener(v -> {
            remindDialog = new RemindDialog(this, "温馨提示", "确定退出当前登录", false);
            remindDialog.setBtnClickListener(new RemindDialog.BtnClickListener() {
                @Override
                public void leftClicked() {
                    remindDialog.dismiss();
                }

                @Override
                public void rightClicked() {
                    remindDialog.dismiss();
                    SPUtil.saveString("phone", "");
                    MainUtil.startActivity(SystemSettingActivity.this, LoginActivity.class, null);
                    finish();
                }
            });
            remindDialog.show();
            remindDialog.setBtnStr("取消", "退出");
        });
    }
}
