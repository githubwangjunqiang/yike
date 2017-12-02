package com.yunyou.yike.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.yunyou.yike.App;
import com.yunyou.yike.AppManager;
import com.yunyou.yike.BaseMVPActivity;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.adapter.FlowAdapter;
import com.yunyou.yike.dagger2.DaggerXinxiCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.Bean;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.HappyList;
import com.yunyou.yike.entity.Image;
import com.yunyou.yike.entity.MyUserInfo;
import com.yunyou.yike.entity.WorkerType;
import com.yunyou.yike.listener.PermissionListener;
import com.yunyou.yike.presenter.XinxiGuanliPresenter;
import com.yunyou.yike.ui_view.dialog.DiaLogPhoto;
import com.yunyou.yike.ui_view.dialog.DiaLogWorkerType;
import com.yunyou.yike.utils.BitmapUtils_My;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.PhotoTask;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

public class XinxiGuanliActivity extends BaseMVPActivity<IView.IxinxiGuanliActivityView, XinxiGuanliPresenter>
        implements IView.IxinxiGuanliActivityView {

    private TextView mTextViewTitle, mTextViewOk;//标题/确定按钮
    private ImageView mImageViewBack;//返回按钮
    private TagFlowLayout mTagFlowLayout;//爱好选择
    private FlowAdapter mFlowAdapter;//爱好适配器
    private EditText mEditTextNickName, mEditTextName, mEditTextPhone, mEditTextNumnber;
    private TextView mButtonWorkerType;
    private TextView mButtonPic;
    private SimpleDraweeView mDraweeViewPic, mDraweeViewNumberImage;
    private List<HappyList.DataBean> mDataBeanList;
    private static final int PHOTOPIC = 0x1;
    private static final int PHOTONUM = 0x2;
    private static final int GALLERYPIC = 0x3;
    private static final int GALLERYNUM = 0x4;
    private static final int PHOTO_REQUEST_CUTPIC = 0x5;

    @Inject
    XinxiGuanliPresenter mXinxiGuanliPresenter;
    private File filePic, fileNumber;//拍照用文件
    private WorkerType mWorkerType;
    private Uri mUriPic, mUriNumber;
    private PhotoTask photoTask;
    private String type_of_work;
    private String hobby;
    private String picPath;
    private String numberPath;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_xinxi_guanli;
    }

    @Override
    protected int getStateLayoutID() {
        return R.id.xinxi_statelayout;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.xinxi_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mImageViewBack = optionView(R.id.title_ivback);
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mTextViewOk = optionView(R.id.title_btn);
        mTagFlowLayout = optionView(R.id.xinxi_gvaihao);
        mButtonPic = optionView(R.id.edit_image_tv);
        mEditTextNickName = optionView(R.id.edit_nickname);
        mDraweeViewPic = optionView(R.id.edit_image);
        mEditTextName = optionView(R.id.edit_name);
        mEditTextPhone = optionView(R.id.edit_phone);
        mEditTextNumnber = optionView(R.id.edit_shenfennumber);
        mButtonWorkerType = optionView(R.id.delayedClickButton_gongzhong);
        mDraweeViewNumberImage = optionView(R.id.simdview_shenfenzheng);
        mTextViewTitle.setText(R.string.xinxiguanli);
        mTagFlowLayout.setMaxSelectCount(-1);
        mTextViewOk.setVisibility(View.VISIBLE);
        mTextViewOk.setText(R.string.ok);
        startRefresh(true);
    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mButtonPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击修改头像
                showPermissionDialogPhoto(true);
            }
        });
        mDraweeViewNumberImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击上传身份照片
                showPermissionDialogPhoto(false);
            }
        });
        mButtonWorkerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWorkerType != null) {//点击选择工种对话框|
                    showDialogWorker(mWorkerType.getData());
                } else {
                    mXinxiGuanliPresenter.getWorkerType();
                }
            }
        });
        mTextViewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击确定按钮
                toSubMit(v);
            }
        });
    }

    /**
     * 点击确定
     */
    private void toSubMit(View view) {
        if (TextUtils.isEmpty(numberPath) && mUriNumber == null) {
            To.ss(view, "请选择身份证件照片...");
            return;
        }
        if (TextUtils.isEmpty(picPath) && mUriPic == null) {
            To.ss(view, "请选择头像照片...");
            return;
        }
        String nickName = mEditTextNickName.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            To.ss(view, "请输入昵称...");
            return;
        }
        String userName = mEditTextName.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            To.ss(view, "请输入姓名...");
            return;
        }
        String userPhone = mEditTextPhone.getText().toString().trim();
        if (TextUtils.isEmpty(userPhone)) {
            To.ss(view, "请输入联系电话...");
            return;
        }
        String userNumber = mEditTextNumnber.getText().toString().trim();
        if (TextUtils.isEmpty(userNumber)) {
            To.ss(view, "请输入身份证件号码...");
            return;
        }
        if (TextUtils.isEmpty(type_of_work)) {
            To.ss(view, "请选择您自己的工种，可多选...");
            return;
        }
        Set<Integer> selectedList = mTagFlowLayout.getSelectedList();
        if (selectedList == null || selectedList.isEmpty()) {
            To.ss(view, "请选择您的一两个爱好吧亲...");
            return;
        }
        Iterator<Integer> iterator = selectedList.iterator();
        StringBuffer buffer = new StringBuffer();
        while (iterator.hasNext()) {
            int next = iterator.next();
            if (mDataBeanList.size() > next) {
                buffer.append(mDataBeanList.get(next).getId()).append(",");
            }
        }
        if (buffer.length() > 0) {
            buffer = buffer.deleteCharAt(buffer.length() - 1);
        }

        Map<String, String> map = new ArrayMap<>();
        map.put("nickname", nickName);
        map.put("user_name", userName);
        map.put("tel", userPhone);
        map.put("card_no", userNumber);
        map.put("type_of_work", type_of_work);
        map.put("hobby", buffer.toString());
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));

        List<Uri> listUri = new ArrayList<>();
        boolean isPic = false;
        boolean isnumUri = false;
        if (mUriPic != null) {
            isPic = true;
            listUri.add(mUriPic);
        }
        if (mUriNumber != null) {
            listUri.add(mUriNumber);
            isnumUri = true;
        }
        if (listUri.isEmpty()) {
            toUnData(map, picPath, numberPath);
        } else {
            toOutImage(listUri, map, isPic, isnumUri);
        }
    }

    /**
     * 上传照片
     *
     * @param map
     */
    private void toOutImage(List<Uri> listUri, final Map<String, String> map,
                            final boolean isPic, final boolean isnumUri) {
        photoTask = new PhotoTask(this, Bean.fileUpload, listUri, new PhotoTask.TaskCallback() {
            @Override
            public void onPreExecute() {
                showLoodingDialog("上传图片中...");

            }

            @Override
            public void onProgressUpdate(Object bitmap) {

            }

            @Override
            public void onError() {
                showContentView(null);
                To.ss(mEditTextName, "上传图片失败");
            }

            @Override
            public void onSuccess(List<String> string) {
                if (isPic && isnumUri) {
                    picPath = new Gson().fromJson(string.get(0), Image.class).getData();
                    numberPath = new Gson().fromJson(string.get(1), Image.class).getData();
                } else if (!isPic) {
                    numberPath = new Gson().fromJson(string.get(0), Image.class).getData();
                } else if (!isnumUri) {
                    picPath = new Gson().fromJson(string.get(0), Image.class).getData();
                }

                if (TextUtils.isEmpty(picPath) || TextUtils.isEmpty(numberPath)) {
                    To.ss(mEditTextName, "服务器返回路径错误，请您重试");
                    showContentView(numberPath);
                    return;
                }
                mUriPic = null;
                mUriNumber = null;
                toUnData(map, picPath, numberPath);
            }
        });
        photoTask.execute();
    }

    /**
     * 修改资料
     *
     * @param map
     */
    private void toUnData(Map<String, String> map, String picPath1, String numberPath1) {

        map.put("head_pic", picPath1);
        map.put("card_pic", numberPath);
        mXinxiGuanliPresenter.updata_info(map);
    }

    /**
     * 显示工种对话框
     */
    private void showDialogWorker(List<WorkerType.DataBean> list) {

        new DiaLogWorkerType(this, list, type_of_work, new DiaLogWorkerType.CallBack() {

            @Override
            public void submit(String stringId, String names) {
                type_of_work = stringId;
                mButtonWorkerType.setText(names.toString() + "");
            }
        }).show();
    }

    /**
     * 显示请求权限 对话框 拍照权限
     */
    private void showPermissionDialogPhoto(final boolean isPic) {
        String[] permission = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        PermissionActivity.startPermissionActivity(permission, new PermissionListener() {
            @Override
            public void success() {
                showPhotoDialog(isPic);
            }

            @Override
            public void error(String[] permission) {
                To.ee("亲，您关闭了本应用打开相机的权限，请您在设置中开启吧");
            }
        });

    }

    /**
     * @param isPic
     */
    private void showPhotoDialog(final boolean isPic) {
        new DiaLogPhoto(XinxiGuanliActivity.this, new DiaLogPhoto.CallBack() {
            @Override
            public void takePictures() {
                try {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    if (isPic) {//是头像
                        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                            filePic = new File(AppManager.getInstance().getContext()
                                    .getExternalCacheDir(), "yike_photo" + "头像" + ".jpg");
                            if (filePic != null) {
                                if (filePic.exists()) {
                                    filePic.delete();
                                    filePic.createNewFile();
                                }
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(filePic));
                                startActivityForResult(intent, PHOTOPIC);
                                return;
                            }
                        }
                    } else {
                        String externalStorageState = Environment.getExternalStorageState();
                        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
                            if (fileNumber != null && fileNumber.exists()) {
                                fileNumber.delete();
                            }
                            fileNumber = new File(AppManager.getInstance().getContext()
                                    .getExternalCacheDir(), "yike_photo" + "身份证明" + ".jpg");
                            if (fileNumber != null) {
                                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(fileNumber));
                                startActivityForResult(intent, PHOTONUM);
                                return;
                            }
                        }
                    }
                    To.ee("您的存储卡不可用，请检查存储卡是否安装");
                } catch (Exception e) {
                    e.printStackTrace();
                    To.ee("您的存储卡不可用，请检查存储卡是否安装");
                }


            }

            @Override
            public void gallery() {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                if (isPic) {
                    startActivityForResult(intent, GALLERYPIC);
                } else {
                    startActivityForResult(intent, GALLERYNUM);
                }
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTOPIC://拍照返回(头像)
                if (resultCode == RESULT_OK && filePic != null && filePic.length() > 0) {
                    crop(Uri.fromFile(filePic),
                            mDraweeViewPic.getWidth(),
                            mDraweeViewPic.getHeight(),
                            PHOTO_REQUEST_CUTPIC);
                }
                break;
            case PHOTONUM://拍照返回(身份证)
                if (resultCode == RESULT_OK && fileNumber != null && fileNumber.length() > 0) {
                    try {
                        Bitmap bitmapFormUri = BitmapUtils_My.getBitmapFormUri(this, Uri.fromFile(fileNumber));
                        mUriNumber = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),
                                bitmapFormUri, null, null));
                        mDraweeViewNumberImage.setImageURI(mUriNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogUtils.d("转换失败");

                    }

                } else {
                    LogUtils.d("返回失败");
                }
                break;
            case GALLERYPIC://图库返回
                if (resultCode == RESULT_OK && data != null) {
                    crop(data.getData(),
                            mDraweeViewPic.getWidth(),
                            mDraweeViewPic.getHeight(),
                            PHOTO_REQUEST_CUTPIC);
                }
                break;
            case GALLERYNUM://图库返回(身份证)
                if (resultCode == RESULT_OK && data != null) {
                    LogUtils.d("图库返回的uri=" + data.getData());
                    mUriNumber = data.getData();
                    try {
                        Bitmap bitmapFormUri = BitmapUtils_My.getBitmapFormUri(this, mUriNumber);
                        mUriNumber = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmapFormUri, null, null));
                        mDraweeViewNumberImage.setImageURI(mUriNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case PHOTO_REQUEST_CUTPIC://裁剪返回
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    mUriPic = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null, null));
                    mDraweeViewPic.setImageURI(mUriPic);
                    bitmap.recycle();
                }
                break;
        }
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        Map<String, String> map = new ArrayMap<>();
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        mXinxiGuanliPresenter.getUserInfo(isShowLoadingView, map);
    }


    @Override
    protected XinxiGuanliPresenter mPresenterCreate() {
        DaggerXinxiCompcoent.builder().appCompcoent(((App) getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule())
                .build().inject(this);
        return mXinxiGuanliPresenter;
    }

    @Override
    public void showUserinfo(MyUserInfo myUserInfo) {
        MyUserInfo.DataBean data = myUserInfo.getData();
        type_of_work = data.getType_of_work();
        hobby = data.getHobby();
        picPath = data.getHead_pic();
        numberPath = data.getCard_pic();
        mEditTextNickName.setText(TextUtils.isEmpty(data.getNickname()) ? "" : data.getNickname());
        mEditTextName.setText(TextUtils.isEmpty(data.getUser_name()) ? "" : data.getUser_name());
        mEditTextPhone.setText(TextUtils.isEmpty(data.getMobile()) ? "" : data.getMobile());
        mEditTextNumnber.setText(TextUtils.isEmpty(data.getCard_no()) ? "" : data.getCard_no());
        mButtonWorkerType.setText("点击选择工种");
        mDraweeViewPic.setImageURI(Uri.parse(data.getHead_pic() + ""));
        mDraweeViewNumberImage.setImageURI(Uri.parse(data.getCard_pic() + ""));
        //请求爱好数据
        mXinxiGuanliPresenter.getHobbyList(new ArrayMap<String, String>());
    }


    @Override
    public void showHappyList(HappyList happyList) {
        if (mFlowAdapter != null) {
            mDataBeanList.clear();
            mDataBeanList.addAll(happyList.getData());
            mFlowAdapter.notifyDataChanged();
        } else {
            mDataBeanList = new ArrayList<>();
            mDataBeanList.addAll(happyList.getData());
            mFlowAdapter = new FlowAdapter(mDataBeanList, this);
            mTagFlowLayout.setAdapter(mFlowAdapter);
        }

        try {
            if (!TextUtils.isEmpty(hobby)) {
                if (hobby.contains(",")) {
                    String[] split = hobby.split(",");
                    for (int i = 0; i < happyList.getData().size(); i++) {
                        HappyList.DataBean dataBean = happyList.getData().get(i);
                        for (int j = 0; j < split.length; j++) {
                            if (dataBean.getId().equals(split[j])) {
                                mFlowAdapter.setSelectedList(i);
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < happyList.getData().size(); i++) {
                        HappyList.DataBean dataBean = happyList.getData().get(i);
                        if (dataBean.getId().equals(hobby)) {
                            mFlowAdapter.setSelectedList(i);
                        }
                    }
                    mFlowAdapter.setSelectedList(Integer.parseInt(hobby));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUnDataSuccess(String success) {
        To.dd(success);
    }

    @Override
    public void showWorkerType(WorkerType workerType) {
        mWorkerType = workerType;
        showDialogWorker(workerType.getData());
    }

    /**
     * 去裁剪界面
     *
     * @param uri
     */
    private void crop(Uri uri, int width, int height, int code) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, code);
    }

    @Override
    protected void onDestroy() {
        if (photoTask != null && !photoTask.isCancelled()) {
            photoTask.cancel(true);
        }
        photoTask = null;
        super.onDestroy();
    }
}
