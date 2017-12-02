package com.yunyou.yike.ui_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.yunyou.yike.R;
import com.yunyou.yike.entity.KeFu;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogCustomerservice extends Dialog {
    private Button mButton;
    private KeFu mKeFu;

    public DiaLogCustomerservice(Context context, KeFu keFu) {
        super(context, R.style.dialogWindowAnimPhoto);
        init(keFu);


    }

    /**
     * 初始化数据
     */
    private void init(KeFu keFu) {
        this.mKeFu = keFu;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custormvice);
        mButton = (Button) findViewById(R.id.dialog_kefu_tvbada);
        if (mKeFu != null && mKeFu.getData() != null) {
            mButton.setText(mKeFu.getData());
        }
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mKeFu != null && mKeFu.getData() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mKeFu.getData()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            }
        });
    }


    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.dialogWindowAnimPhoto);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


}
