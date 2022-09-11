package com.terdfgdfjieyiji.jghrstyfghtertry.page.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.terdfgdfjieyiji.jghrstyfghtertry.App;
import com.terdfgdfjieyiji.jghrstyfghtertry.R;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.BaseEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.entity.LoginRespEntity;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.CountDownTimer;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MyToastUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.SPUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.MainUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.gongju.StatusBarUtil;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.ObserverManager;
import com.terdfgdfjieyiji.jghrstyfghtertry.net.RetrofitManager;
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends RxAppCompatActivity {

    @BindView(R.id.login_remind_tv)
    TextView loginRemindTv;
    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.verification_et)
    EditText verificationEt;
    @BindView(R.id.get_verification_tv)
    TextView getverificationTv;
    @BindView(R.id.login_btn)
    View loginBtn;
    @BindView(R.id.remind_cb)
    CheckBox remindCb;

    private Bundle bundle;
    private String mobileStr, verificationStr, oaidStr, ip = "";
    private boolean isNeedVerification = true, isChecked = false, isOaid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getIp();
        isChecked = SPUtil.getBool("is_agree_check");
        isNeedVerification = SPUtil.getBool("is_code_register");
        remindCb.setChecked(isChecked);
        setData();
        initListener();
    }

    private void getIp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://pv.sohu.com/cityjson")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONToIp(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONToIp(String jsonData) {
        String jsonStr = "";
        try {
            if (jsonData.contains("{") && jsonData.contains("}")) {
                jsonStr = jsonData.substring(jsonData.indexOf("{"), jsonData.indexOf("}") + 1);
            }
            JSONObject jsonObject = new JSONObject(jsonStr);//新建json对象实例
            ip = jsonObject.getString("cip");//取得其名字的值，一般是字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setData(){
        String[] text = {
                "我已阅读并同意",
                "《注册服务协议》",
                "《用户隐私协议》"
        };
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < text.length; i++) {
            stringBuffer.append(text[i]);
        }
        loginRemindTv.setText(stringBuffer.toString());
        SpannableString spannableString = new SpannableString(loginRemindTv.getText().toString().trim());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                bundle = new Bundle();
                bundle.putString("title", "注册协议");
                bundle.putString("url", RetrofitManager.getZcxy());
                MainUtil.startActivity(LoginActivity.this, AgreementActivity.class, bundle);
            }
        };
        if (text.length > 1) {
            spannableString.setSpan(clickableSpan, text[0].length(), text[0].length() + text[1].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0ccfe9")), text[1].length(), text[2].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (text.length > 1) {
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    bundle = new Bundle();
                    bundle.putString("title", "隐私协议");
                    bundle.putString("url", RetrofitManager.getYsxy());
                    MainUtil.startActivity(LoginActivity.this, AgreementActivity.class, bundle);
                }
            };
            int startNum = spannableString.length() - text[2].length();
            int endNum = spannableString.length();
            spannableString.setSpan(clickableSpan1, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#0ccfe9")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        loginRemindTv.setText(spannableString);
        loginRemindTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initListener(){
        getverificationTv.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)){
                MyToastUtil.showShort("电话号码不能为空");
                return;
            }
            if (!MainUtil.isMobile(mobileStr)) {
                MyToastUtil.showShort("请输入正确的电话号码");
                return;
            }
            getVerification(mobileStr);
        });
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)){
                MyToastUtil.showShort("电话号码不能为空");
                return;
            }
            if (!MainUtil.isMobile(mobileStr)) {
                MyToastUtil.showShort("请输入正确的电话号码");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification){
                MyToastUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()){
                MyToastUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(App.getInstance());
                isOaid = true;
            }
            DeviceID.getOAID(this, new IGetter() {
                @Override
                public void onOAIDGetComplete(String result) {
                    if (TextUtils.isEmpty(result)){
                        oaidStr = "";
                    } else {
                        int length = result.length();
                        if (length < 64){
                            for (int i = 0; i < 64 - length; i++){
                                result = result + "0";
                            }
                        }
                        oaidStr = result;
                    }
                    login(mobileStr, verificationStr, ip, oaidStr);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    login(mobileStr, verificationStr, ip, oaidStr);
                }
            });
        });
    }

    private void getVerification(String phone) {
        RetrofitManager.getRetrofitManager().getApiService().sendVerifyCode(phone).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseEntity>() {

                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        if (baseEntity != null && !TextUtils.isEmpty(baseEntity.getMsg())) {
                            MyToastUtil.showShort("验证码" + baseEntity.getMsg());
                            if (baseEntity.getMsg().contains("成功")) {
                                CountDownTimer mCountDownTimerJieJie = new CountDownTimer(getverificationTv, 60000, 1000);
                                mCountDownTimerJieJie.start();
                            }
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {

                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

    private void login(String mobileStr, String verificationStr, String ip, String oaidStr) {
        RetrofitManager.getRetrofitManager().getApiService()
                .login(mobileStr,verificationStr, "", ip, oaidStr, "OAID").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverManager<BaseEntity<LoginRespEntity>>() {

                    @Override
                    public void onSuccess(BaseEntity<LoginRespEntity> baseEntity) {
                        if (baseEntity == null) {
                            return;
                        }
                        if (baseEntity != null && baseEntity.getCode() == 0) {
                            if (baseEntity.getData() != null) {
                                SPUtil.saveString("phone", mobileStr);
                                SPUtil.saveString("token", baseEntity.getData().getToken());
                                MainUtil.startActivity(LoginActivity.this, MainActivity.class, null);
                                finish();
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseEntity.getMsg())) {
                                MyToastUtil.showShort(baseEntity.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        MyToastUtil.showShort("登陆失败");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

}
