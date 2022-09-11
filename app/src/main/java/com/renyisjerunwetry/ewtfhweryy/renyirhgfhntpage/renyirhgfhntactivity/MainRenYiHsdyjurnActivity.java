package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntfragment.GoodsRenYiHsdyjurnFragment;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnStatusBarUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnMyToastUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntadapter.BaseRenYiHsdyjurnFragmentAdapter;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntfragment.HomePageRenYiHsdyjurnFragment;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntfragment.SetRenYiHsdyjurnFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRenYiHsdyjurnActivity extends AppCompatActivity {

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
        RenYiHsdyjurnStatusBarUtil.setTransparent(this, false);
        RenYiHsdyjurnStatusBarUtil.setLightMode(this);
        setContentView(R.layout.activity_main_ren_yi_dai_erjr_ryntu);
        ButterKnife.bind(this);
        homeViewPager.setUserInputEnabled(false);
        mFragments.add(new HomePageRenYiHsdyjurnFragment());
        mFragments.add(new GoodsRenYiHsdyjurnFragment());
        mFragments.add(new SetRenYiHsdyjurnFragment());

        homeViewPager.setAdapter(new BaseRenYiHsdyjurnFragmentAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
        initListener();
    }

    public void changePage(){
        homeViewPager.setCurrentItem(1, false);
        homepageTv.setTextColor(getResources().getColor(R.color.color_676767));
        homepageTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.kyuisd), null, null);
        productTv.setTextColor(getResources().getColor(R.color.colorPrimary));
        productTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.rthjfgjn), null, null);
        mineTv.setTextColor(getResources().getColor(R.color.color_676767));
        mineTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.nsryu), null, null);
    }

    private void initListener(){
        homepageTv.setOnClickListener(v -> {
            homeViewPager.setCurrentItem(0, false);
            homepageTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            homepageTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this, R.drawable.exfgj), null, null);
            productTv.setTextColor(getResources().getColor(R.color.color_676767));
            productTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.whxfjtyu), null, null);
            mineTv.setTextColor(getResources().getColor(R.color.color_676767));
            mineTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.nsryu), null, null);
        });
        productTv.setOnClickListener(v -> {
            changePage();
        });
        mineTv.setOnClickListener(v -> {
            homeViewPager.setCurrentItem(2, false);
            homepageTv.setTextColor(getResources().getColor(R.color.color_676767));
            homepageTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.kyuisd), null, null);
            productTv.setTextColor(getResources().getColor(R.color.color_676767));
            productTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.whxfjtyu), null, null);
            mineTv.setTextColor(getResources().getColor(R.color.colorPrimary));
            mineTv.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.gjdnsr), null, null);
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                RenYiHsdyjurnMyToastUtil.showShort("再按一次退出程序");
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