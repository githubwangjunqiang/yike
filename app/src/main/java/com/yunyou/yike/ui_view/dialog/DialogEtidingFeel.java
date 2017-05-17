package com.yunyou.yike.ui_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yunyou.yike.R;
import com.yunyou.yike.utils.To;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DialogEtidingFeel extends Dialog {
    private CallBack mCallBack;
    private EditText mEditText;
    private Button mButton;
    private int grPosition, chidPosition;

    public DialogEtidingFeel(Context context, int grPosition, int chidPosition, CallBack callBack) {
        super(context, R.style.WinDialog);
        this.mCallBack = callBack;
        this.grPosition = grPosition;
        this.chidPosition = chidPosition;
        setContentView(R.layout.dialog_feel_editing);
        setListener();
    }

    /**
     * 监听器
     */
    private void setListener() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    submit();
                    return true;
                }
                return false;
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0 && !mButton.isSelected()) {
                    mButton.setSelected(true);
                }
                if (s.length() < 1 && mButton.isSelected()) {
                    mButton.setSelected(false);
                }

            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButton.isSelected()){
                    submit();
                }
            }
        });
    }

    /**
     * 提交
     */
    private void submit() {
        String trim = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            To.oo(this.getContext().getString(R.string.ninmeiyourenheshuru));
            return;
        }
        if (mCallBack != null) {
            mCallBack.addFeel(trim, grPosition, chidPosition);
        }
        dismiss();
    }

    public interface CallBack {
        void addFeel(String strings, int grounpPosition, int childPosition);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mEditText = (EditText) findViewById(R.id.dialog_feel_editing);
        mButton = (Button) findViewById(R.id.dialog_feel_btnok);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        window.setWindowAnimations(R.style.WinDialog);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
