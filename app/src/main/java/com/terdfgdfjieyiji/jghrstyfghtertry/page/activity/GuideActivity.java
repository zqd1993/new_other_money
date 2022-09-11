package com.terdfgdfjieyiji.jghrstyfghtertry.page.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import androidx.annotation.Nullable;

import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.BaseEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.LoginEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.SPUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MainUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.StatusBarUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.GuideDialog;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.ObserverManager;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.RetrofitManager;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuideActivity extends RxAppCompatActivity {

    private Bundle bundle;

    private boolean isAgree = false, isResume = false;

    private String loginPhone = "";

    private GuideDialog guideDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_guide);
        isAgree = SPUtil.getBool("agree");
        loginPhone = SPUtil.getString("phone");
        getHttpApi();
    }

    @Override
    protected void onResume() {
        isResume = true;
        super.onResume();
        new Handler().postDelayed(() -> isResume = false, 500);
    }

    private void showDialog() {
        guideDialog = new GuideDialog(this);
        guideDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && !isResume) {
                    finish();
                    return false;
                }
                return true;
            }
        });
        guideDialog.setOnListener(new GuideDialog.OnListener() {

            @Override
            public void topBtnClicked() {
                SPUtil.saveBool("agree", true);
                guideDialog.dismiss();
                MainUtil.startActivity(GuideActivity.this, LoginActivity.class, null);
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
                bundle.putString("url", RetrofitManager.getZcxy());
                MainUtil.startActivity(GuideActivity.this, AgreementActivity.class, bundle);
            }

            @Override
            public void clickedYsxy() {
                bundle = new Bundle();
                bundle.putString("title", "隐私协议");
                bundle.putString("url", RetrofitManager.getYsxy());
                MainUtil.startActivity(GuideActivity.this, AgreementActivity.class, bundle);
            }
        });
        guideDialog.show();
    }

    private void getHttpApi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://haoone.oss-cn-hangzhou.aliyuncs.com/vjyj.json")
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
                RetrofitManager.API_BASE_URL = net;
                SPUtil.saveString("API_BASE_URL", net);
                Thread.sleep(1000);
                getGankData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getGankData() {
        RetrofitManager.getRetrofitManager().getApiService().getGankData().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseEntity<LoginEntity>>() {

                    @Override
                    public void onSuccess(BaseEntity<LoginEntity> loginEntityBaseEntity) {
                        if (loginEntityBaseEntity == null) {
                            return;
                        }
                        LoginEntity loginEntity = loginEntityBaseEntity.getData();
                        if (loginEntity != null) {
                            SPUtil.saveBool("is_agree_check", "0".equals(loginEntity.getIs_agree_check()));
                            SPUtil.saveBool("is_code_register", "0".equals(loginEntity.getIs_code_register()));
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
                MainUtil.startActivity(this, MainActivity.class, null);
            } else {
                MainUtil.startActivity(this, LoginActivity.class, null);
            }
            finish();
        } else {
            showDialog();
        }
    }
}
