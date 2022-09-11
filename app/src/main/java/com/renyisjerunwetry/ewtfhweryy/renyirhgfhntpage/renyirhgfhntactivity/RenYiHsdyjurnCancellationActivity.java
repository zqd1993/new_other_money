package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnMyToastUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnStatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RenYiHsdyjurnCancellationActivity extends AppCompatActivity {

    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_yi_dai_erjr_ryntu_cancellation);
        RenYiHsdyjurnStatusBarUtil.setTransparent(this, false);
        ButterKnife.bind(this);
        titleTv.setText("账号注销");
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            RenYiHsdyjurnMyToastUtil.showShort("注销已提交，请耐心等待..");
            finish();
        });
    }

}
