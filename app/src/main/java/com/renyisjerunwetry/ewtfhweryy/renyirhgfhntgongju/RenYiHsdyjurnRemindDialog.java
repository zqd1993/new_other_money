package com.renyisjerunwetry.ewtfhweryy.renyirhgfhntgongju;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.renyisjerunwetry.ewtfhweryy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RenYiHsdyjurnRemindDialog extends Dialog {
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.left_btn)
    TextView leftBtn;
    @BindView(R.id.right_btn)
    TextView rightBtn;
    @BindView(R.id.sure_btn)
    TextView sureBtn;
    @BindView(R.id.btn_ll)
    View btnLl;
    @BindView(R.id.one_btn_ll)
    View oneBtnLl;

    private BtnClickListener btnClickListener;
    private String titleStr, contentStr;
    private boolean showOneBtn;
    private Context context;

    public RenYiHsdyjurnRemindDialog(@NonNull Context context, String titleStr, String contentStr, boolean showOneBtn) {
        super(context, R.style.tran_dialog);
        this.titleStr = titleStr;
        this.contentStr = contentStr;
        this.showOneBtn = showOneBtn;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(context, R.layout.dialog_normal_ren_yi_dai_erjr_ryntu, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        titleTv.setText(titleStr);
        contentTv.setText(contentStr);

        if (showOneBtn) {
            btnLl.setVisibility(View.GONE);
            oneBtnLl.setVisibility(View.VISIBLE);
        }
        leftBtn.setOnClickListener(v -> {
            if (btnClickListener != null) {
                btnClickListener.leftClicked();
            }
        });
        rightBtn.setOnClickListener(v -> {
            if (btnClickListener != null) {
                btnClickListener.rightClicked();
            }
        });
        sureBtn.setOnClickListener(v -> {
            dismiss();
        });
    }

    public void setBtnStr(String leftStr, String rightStr) {
        leftBtn.setText(leftStr);
        rightBtn.setText(rightStr);
    }

    public void setBtnClickListener(BtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    public interface BtnClickListener {
        void leftClicked();

        void rightClicked();
    }
}
