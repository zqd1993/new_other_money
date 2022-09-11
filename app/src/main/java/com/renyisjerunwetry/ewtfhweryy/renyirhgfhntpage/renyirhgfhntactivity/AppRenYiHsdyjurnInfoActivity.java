package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.MainRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnStatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppRenYiHsdyjurnInfoActivity extends AppCompatActivity {

    @BindView(R.id.app_version_info_tv)
    TextView appVersionInfoTv;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_yi_dai_erjr_ryntu_app_info);
        ButterKnife.bind(this);
        RenYiHsdyjurnStatusBarUtil.setTransparent(this, false);
        titleTv.setText("关于我们");
        appVersionInfoTv.setText("当前版本号：v" + MainRenYiHsdyjurnUtil.getAppVersion(this));
        backImg.setOnClickListener(v ->{
            finish();
        });
    }
}
