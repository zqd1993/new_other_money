package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntpage.renyirhgfhntactivity;

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

import com.renyisjerunwetry.ewtfhweryy.RenYiHsdyjurnApp;
import com.renyisjerunwetry.ewtfhweryy.R;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.BaseRenYiHsdyjurnEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntentity.RenYiHsdyjurnLoginRespEntity;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnCountDownTimer;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnMyToastUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.SPRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.MainRenYiHsdyjurnUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju.RenYiHsdyjurnStatusBarUtil;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.ObserverRenYiHsdyjurnManager;
import com.renyisjerunwetry.ewtfhweryy.renyirhgfhntnet.RenYiHsdyjurnRetrofitManager;
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

public class LoginRenYiHsdyjurnActivity extends RxAppCompatActivity {

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
        RenYiHsdyjurnStatusBarUtil.setTransparent(this, false);
        setContentView(R.layout.activity_login_ren_yi_dai_erjr_ryntu);
        ButterKnife.bind(this);
        getIp();
        isChecked = SPRenYiHsdyjurnUtil.getBool("is_agree_check");
        isNeedVerification = SPRenYiHsdyjurnUtil.getBool("is_code_register");
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
                bundle.putString("url", RenYiHsdyjurnRetrofitManager.getZcxy());
                MainRenYiHsdyjurnUtil.startActivity(LoginRenYiHsdyjurnActivity.this, AgreementRenYiHsdyjurnActivity.class, bundle);
            }
        };
        if (text.length > 1) {
            spannableString.setSpan(clickableSpan, text[0].length(), text[0].length() + text[1].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#157ff9")), text[1].length(), text[2].length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (text.length > 1) {
            ClickableSpan clickableSpan1 = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    bundle = new Bundle();
                    bundle.putString("title", "隐私协议");
                    bundle.putString("url", RenYiHsdyjurnRetrofitManager.getYsxy());
                    MainRenYiHsdyjurnUtil.startActivity(LoginRenYiHsdyjurnActivity.this, AgreementRenYiHsdyjurnActivity.class, bundle);
                }
            };
            int startNum = spannableString.length() - text[2].length();
            int endNum = spannableString.length();
            spannableString.setSpan(clickableSpan1, startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#157ff9")), startNum, endNum, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        loginRemindTv.setText(spannableString);
        loginRemindTv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void initListener(){
        getverificationTv.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)){
                RenYiHsdyjurnMyToastUtil.showShort("电话号码不能为空");
                return;
            }
            if (!MainRenYiHsdyjurnUtil.isMobile(mobileStr)) {
                RenYiHsdyjurnMyToastUtil.showShort("请输入正确的电话号码");
                return;
            }
            getVerification(mobileStr);
        });
        loginBtn.setOnClickListener(v -> {
            mobileStr = mobileEt.getText().toString().trim();
            verificationStr = verificationEt.getText().toString().trim();
            if (TextUtils.isEmpty(mobileStr)){
                RenYiHsdyjurnMyToastUtil.showShort("电话号码不能为空");
                return;
            }
            if (!MainRenYiHsdyjurnUtil.isMobile(mobileStr)) {
                RenYiHsdyjurnMyToastUtil.showShort("请输入正确的电话号码");
                return;
            }
            if (TextUtils.isEmpty(verificationStr) && isNeedVerification){
                RenYiHsdyjurnMyToastUtil.showShort("验证码不能为空");
                return;
            }
            if (!remindCb.isChecked()){
                RenYiHsdyjurnMyToastUtil.showShort("请阅读用户协议及隐私政策");
                return;
            }
            if (!isOaid){
                DeviceIdentifier.register(RenYiHsdyjurnApp.getInstance());
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
        RenYiHsdyjurnRetrofitManager.getRetrofitManager().getApiService().sendVerifyCode(phone).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverRenYiHsdyjurnManager<BaseRenYiHsdyjurnEntity>() {

                    @Override
                    public void onSuccess(BaseRenYiHsdyjurnEntity baseRenYiHsdyjurnEntity) {
                        if (baseRenYiHsdyjurnEntity != null && !TextUtils.isEmpty(baseRenYiHsdyjurnEntity.getMsg())) {
                            RenYiHsdyjurnMyToastUtil.showShort("验证码" + baseRenYiHsdyjurnEntity.getMsg());
                            if (baseRenYiHsdyjurnEntity.getMsg().contains("成功")) {
                                RenYiHsdyjurnCountDownTimer mRenYiHsdyjurnCountDownTimerJieJie = new RenYiHsdyjurnCountDownTimer(getverificationTv, 60000, 1000);
                                mRenYiHsdyjurnCountDownTimerJieJie.start();
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
        RenYiHsdyjurnRetrofitManager.getRetrofitManager().getApiService()
                .login(mobileStr,verificationStr, "", ip, oaidStr, "OAID").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new ObserverRenYiHsdyjurnManager<BaseRenYiHsdyjurnEntity<RenYiHsdyjurnLoginRespEntity>>() {

                    @Override
                    public void onSuccess(BaseRenYiHsdyjurnEntity<RenYiHsdyjurnLoginRespEntity> baseRenYiHsdyjurnEntity) {
                        if (baseRenYiHsdyjurnEntity == null) {
                            return;
                        }
                        if (baseRenYiHsdyjurnEntity != null && baseRenYiHsdyjurnEntity.getCode() == 0) {
                            if (baseRenYiHsdyjurnEntity.getData() != null) {
                                SPRenYiHsdyjurnUtil.saveString("phone", mobileStr);
                                SPRenYiHsdyjurnUtil.saveString("token", baseRenYiHsdyjurnEntity.getData().getToken());
                                MainRenYiHsdyjurnUtil.startActivity(LoginRenYiHsdyjurnActivity.this, MainRenYiHsdyjurnActivity.class, null);
                                finish();
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseRenYiHsdyjurnEntity.getMsg())) {
                                RenYiHsdyjurnMyToastUtil.showShort(baseRenYiHsdyjurnEntity.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onFail(Throwable throwable) {
                        RenYiHsdyjurnMyToastUtil.showShort("登陆失败");
                    }

                    @Override
                    public void onFinish() {

                    }
                });
    }

}
