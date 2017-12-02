package com.yunyou.yike.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.App;
import com.yunyou.yike.AppManager;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.dagger2.DaggerSendFeelCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.listener.PermissionListener;
import com.yunyou.yike.presenter.IPresenter;
import com.yunyou.yike.presenter.SendFeelPresenter;
import com.yunyou.yike.utils.BitmapUtils_My;
import com.yunyou.yike.utils.To;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import javax.inject.Inject;

public class SendFeelActivity extends BaseMVPActivity<IView.ISendFeelActivityView, SendFeelPresenter> implements
        IPresenter.ISendFeelPrenester {
    @Inject
    SendFeelPresenter mSendFeelPresenter;
    private File filePic;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_fabu_ganxiang;
    }

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    private EditText mEditTextContent;
    private ImageView mImageViewOne, mImageViewTow, mImageViewThree, mImageViewBack;
    private TextView mTextViewTitle;
    private BottomSheetBehavior behavior;
    private int index;
    private static final String INDEX = "index";
    private static final int PHOTOPIC = 0x1;
    private static final int GALLERYNUM = 0x4;
    private Button mButtonTackPhone, mButtonAlbum;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(INDEX, index);
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        mEditTextContent = optionView(R.id.send_edit);
        mImageViewOne = optionView(R.id.send_ivimage0);
        mImageViewTow = optionView(R.id.send_ivimage1);
        mImageViewThree = optionView(R.id.send_ivimage2);
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mButtonTackPhone = optionView(R.id.sendfeel_photo);
        mButtonAlbum = optionView(R.id.sendfeel_album);


        mTextViewTitle.setText(R.string.fabuganxiang);
        View viewById = findViewById(R.id.bottom_layout);
        behavior = BottomSheetBehavior.from(viewById);
        behavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(INDEX, 0);
        }


    }

    @Override
    protected void setListener() {
        mImageViewOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//第一张图片
                showPictureView(0);
            }
        });
        mImageViewTow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//第2张图片
                showPictureView(1);
            }
        });
        mImageViewThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//第3张图片
                showPictureView(2);
            }
        });
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//返回
                finish();
            }
        });
        mButtonTackPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//拍照
                tackPhoto();
            }
        });
        mButtonAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//相册
                openAlume();
            }
        });
    }

    /**
     * 打开相册
     */
    private void openAlume() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERYNUM);
        }
    }

    /**
     * 启动拍照界面
     */
    private void tackPhoto() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                filePic = new File(AppManager.getInstance().getContext()
                        .getExternalCacheDir(), getApplication().getPackageName()
                        + "_" + getClass().getName() + ".jpg");
                if (filePic != null) {
                    if (filePic.exists()) {
                        filePic.delete();
                        filePic.createNewFile();
                    }
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(filePic));
                    startActivityForResult(intent, index);
                }
            } catch (IOException e) {
                e.printStackTrace();
                To.ss(mImageViewBack, "请重试");
            }
        } else {
            To.oo("请检查您的sd卡是否可用");
        }
    }

    /**
     * 显示选择照片方式对话框
     *
     * @param inde
     */
    private void showPictureView(final int inde) {
        String[] permissios = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        PermissionActivity.startPermissionActivity(permissios, new PermissionListener() {
            @Override
            public void success() {
                if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                index = inde;
            }

            @Override
            public void error(String[] permission) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
                To.ee("您必须开启权限 才能拍照哦亲.....");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == index && resultCode == RESULT_OK) {//拍照返回
            if (filePic.length() > 0) {
                WeakReference<Bitmap> bitmapWeakReference = new WeakReference<Bitmap>(
                        BitmapUtils_My.getBitmapFormUri(this, Uri.fromFile(filePic)));
                switch (index) {
                    case 0://
                        mImageViewOne.setImageBitmap(bitmapWeakReference.get());
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            } else {
                To.ee("拍照存储文件失败，请重试");
            }
        }
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    @Override
    protected SendFeelPresenter mPresenterCreate() {
        DaggerSendFeelCompcoent.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule()).build().inject(this);
        return mSendFeelPresenter;
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }

    @Override
    public void subMitFeelData() {

    }

    /**
     * 点击确定按钮
     *
     * @param view
     */
    public void subMitImage(View view) {

    }
}
