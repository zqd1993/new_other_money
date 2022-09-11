package com.terdfgdfjieyiji.jghrstyfghtertry.page.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.StatusBarUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MyToastUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.adapter.BaseFragmentAdapter;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.fragment.HomePageFragment;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.fragment.SetFragment;
import com.terdfgdfjieyiji.jghrstyfghtertry.page.fragment.GoodsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.home_view_pager)
    ViewPager2 homeViewPager;
    @BindView(R.id.homepage_tv)
    TextView homepageTv;
    @BindView(R.id.product_tv)
    TextView productTv;
    @BindView(R.id.mine_tv)
    TextView mineTv;

    private long exitTime = 0;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        StatusBarUtil.setLightMode(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        homeViewPager.setUserInputEnabled(false);
        mFragments.add(new HomePageFragment());
        mFragments.add(new GoodsFragment());
        mFragments.add(new SetFragment());

        homeViewPager.setAdapter(new BaseFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
        initListener();
    }

    public void changePage(){
        homeViewPager.setCurrentItem(1, false);
        homepageTv.setTextColor(getResources().getColor(R.color.color_676767));
        homepageTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.yerynghj), null, null);
        productTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        productTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.rthfgj), null, null);
        mineTv.setTextColor(getResources().getColor(R.color.color_676767));
        mineTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.qdfg), null, null);
    }

    private void initListener(){
        homepageTv.setOnClickListener(v -> {
            homeViewPager.setCurrentItem(0, false);
            homepageTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            homepageTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.zbvnf), null, null);
            productTv.setTextColor(getResources().getColor(R.color.color_676767));
            productTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.mdghzdf), null, null);
            mineTv.setTextColor(getResources().getColor(R.color.color_676767));
            mineTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.qdfg), null, null);
        });
        productTv.setOnClickListener(v -> {
            changePage();
        });
        mineTv.setOnClickListener(v -> {
            homeViewPager.setCurrentItem(2, false);
            homepageTv.setTextColor(getResources().getColor(R.color.color_676767));
            homepageTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.yerynghj), null, null);
            productTv.setTextColor(getResources().getColor(R.color.color_676767));
            productTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.mdghzdf), null, null);
            mineTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            mineTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.mluptyuu), null, null);
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                MyToastUtil.showShort("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}