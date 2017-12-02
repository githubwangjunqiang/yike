package com.yunyou.yike.ui_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.yunyou.yike.R;
import com.yunyou.yike.utils.To;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogSuggestions extends Dialog {
    private Button mButton;
    private EditText mEditTextContent;

    private Callback mCallback;

    public DiaLogSuggestions(Context context, Callback callback) {
        super(context, R.style.dialogWindowAnimPhoto);
        mCallback = callback;
    }


    public interface Callback {
        void onClick(String content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tousujianyi);
        mButton = (Button) findViewById(R.id.dialog_tousu_tvbada);
        mEditTextContent = (EditText) findViewById(R.id.dialog_edittousu);
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEditTextContent.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    To.oo("您还没有任何输入");
                    return;
                }
                if (mCallback != null) {
                    mCallback.onClick(content);
                    dismiss();
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
