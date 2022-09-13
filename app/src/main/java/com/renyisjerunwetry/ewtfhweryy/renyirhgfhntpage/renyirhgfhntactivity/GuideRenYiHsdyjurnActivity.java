package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.BaseRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.LoginRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.SPRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.MainRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnStatusBarUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnGuideDialog;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.ObserverRenYiHsdyjurnManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.RenYiHsdyjurnRetrofitManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuideRenYiHsdyjurnActivity extends RxAppCompatActivity {

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    private RenYiHsdyjurnGuideDialog renYiHsdyjurnGuideDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RenYiHsdyjurnStatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_ren_yi_dai_erjr_ryntu_guide);
        isAgree = SPRenYiHsdyjurnUtil.getBool("agree");
        loginPhone = SPRenYiHsdyjurnUtil.getString("phone");
        getHttpApi();
    }

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    private void showDialog() {
        renYiHsdyjurnGuideDialog = new RenYiHsdyjurnGuideDialog(this);
        renYiHsdyjurnGuideDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        renYiHsdyjurnGuideDialog.setOnListener(new RenYiHsdyjurnGuideDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                SPRenYiHsdyjurnUtil.saveBool("agree", true);
                renYiHsdyjurnGuideDialog.dismiss();
                MainRenYiHsdyjurnUtil.startActivity(GuideRenYiHsdyjurnActivity.this, LoginRenYiHsdyjurnActivity.class, null);
                finish();
            }

            @Override
            public void bottomBtnClicked() {
                finish();
            }

            @Override
            public void clickedZcxy() {
                bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", RenYiHsdyjurnRetrofitManager.getZcxy());
                MainRenYiHsdyjurnUtil.startActivity(GuideRenYiHsdyjurnActivity.this, AgreementRenYiHsdyjurnActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putString("title", "隐私协议");
                bundle.putString("url", RenYiHsdyjurnRetrofitManager.getYsxy());
                MainRenYiHsdyjurnUtil.startActivity(GuideRenYiHsdyjurnActivity.this, AgreementRenYiHsdyjurnActivity.class, bundle);
            }
        });
        renYiHsdyjurnGuideDialog.show();
    }

    private void getHttpApi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://wentree.oss-cn-hangzhou.aliyuncs.com/95/vryjd.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSON(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSON(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            String net = jsonObject.getString("data");//取得其名字的值，一般是字符串
            if (!TextUtils.isEmpty(net)) {
                RenYiHsdyjurnRetrofitManager.API_BASE_URL = net;
                SPRenYiHsdyjurnUtil.saveString("API_BASE_URL", net);
                Thread.sleep(1000);
                getGankData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getGankData() {
        RenYiHsdyjurnRetrofitManager.getRetrofitManager().getApiService().getGankData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverRenYiHsdyjurnManager<BaseRenYiHsdyjurnEntity<LoginRenYiHsdyjurnEntity>>() {

                    @Override
                    public void onSuccess(BaseRenYiHsdyjurnEntity<LoginRenYiHsdyjurnEntity> loginEntityBaseRenYiHsdyjurnEntity) {
                        if (loginEntityBaseRenYiHsdyjurnEntity == null) {
                            return;
                        }
                        LoginRenYiHsdyjurnEntity loginRenYiHsdyjurnEntity = loginEntityBaseRenYiHsdyjurnEntity.getData();
                        if (loginRenYiHsdyjurnEntity != null) {
                            SPRenYiHsdyjurnUtil.saveBool("is_agree_check", "0".equals(loginRenYiHsdyjurnEntity.getIs_agree_check()));
                            SPRenYiHsdyjurnUtil.saveBool("is_code_register", "0".equals(loginRenYiHsdyjurnEntity.getIs_code_register()));
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }

                    @Override
                    public void onFinish() {
                        toPage();
                    }
                });
    }

    private void toPage() {
        if (isAgree) {
            if (!TextUtils.isEmpty(loginPhone)) {
                MainRenYiHsdyjurnUtil.startActivity(this, MainRenYiHsdyjurnActivity.class, null);
            } else {
                MainRenYiHsdyjurnUtil.startActivity(this, LoginRenYiHsdyjurnActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
