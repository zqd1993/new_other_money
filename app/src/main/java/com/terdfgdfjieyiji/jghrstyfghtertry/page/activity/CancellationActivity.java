package com.terdfgdfjieyiji.jghrstyfghtertry.page.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MyToastUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CancellationActivity extends AppCompatActivity {

    @BindView(R.id.commit_btn)
    TextView commitBtn;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.back_image)
    View backImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellation);
        StatusBarUtil.setTransparent(this, false);
        ButterKnife.bind(this);
        titleTv.setText("账号注销");
        backImg.setOnClickListener(v -> finish());
        commitBtn.setOnClickListener(v -> {
            MyToastUtil.showShort("注销已提交，请耐心等待..");
            finish();
        });
    }

}
