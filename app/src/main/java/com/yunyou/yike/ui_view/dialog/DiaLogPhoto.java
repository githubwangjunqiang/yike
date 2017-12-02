package com.yunyou.yike.ui_view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.yunyou.yike.R;


/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class DiaLogPhoto extends Dialog {
    private CallBack mCallBack;

    public DiaLogPhoto(Context context, CallBack callBack) {
        super(context, R.style.dialogWindowAnimPhoto);
        mCallBack = callBack;
    }

    private Button mButtonPictures, mButtonGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_un_photo);
        mButtonGallery = (Button) findViewById(R.id.dailog_photo_btnGallery);
        mButtonPictures = (Button) findViewById(R.id.dailog_photo_btnpictures);
        setListener();
        setAdapter();
    }

    /**
     * 监听器
     */
    private void setListener() {
        mButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//图库
                if (mCallBack != null) {
                    mCallBack.gallery();
                }
                dismiss();
            }
        });
        mButtonPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//拍照
                if (mCallBack != null) {
                    mCallBack.takePictures();
                }
                dismiss();
            }
        });
    }


    public interface CallBack {
        void takePictures();

        void gallery();
    }


    /**
     * 设置适配器
     */
    private void setAdapter() {
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
        window.setWindowAnimations(R.style.dialogWindowAnimPhoto);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
