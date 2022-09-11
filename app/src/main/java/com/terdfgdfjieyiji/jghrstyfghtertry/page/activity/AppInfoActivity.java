package com.terdfgdfjieyiji.jghrstyfghtertry.page.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MainUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppInfoActivity extends AppCompatActivity {

    @BindView(R.id.app_version_info_tv)
    TextView appVersionInfoTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparent(this, false);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + MainUtil.getAppVersion(this));
        backImg.setOnClickListener(v ->{
            finish();
        });
    }
}
