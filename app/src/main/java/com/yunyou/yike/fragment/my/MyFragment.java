package com.yunyou.yike.fragment.my;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunyou.yike.App;
import com.yunyou.yike.BaseMVPFragment;
import com.yunyou.yike.Interface_view.IView;
import com.yunyou.yike.R;
import com.yunyou.yike.RongIM.RongIMLoginManager;
import com.yunyou.yike.activity.LoginActivity;
import com.yunyou.yike.activity.MyAppInfoActivity;
import com.yunyou.yike.activity.MyYueActivity;
import com.yunyou.yike.activity.XinxiGuanliActivity;
import com.yunyou.yike.dagger2.DaggerMyFragmentCompcoent;
import com.yunyou.yike.dagger2.PresenterMobule;
import com.yunyou.yike.entity.EventBusMessage;
import com.yunyou.yike.entity.KeFu;
import com.yunyou.yike.entity.MyUserInfo;
import com.yunyou.yike.presenter.MyFragmentPresenter;
import com.yunyou.yike.ui_view.dialog.DiaLogCustomerservice;
import com.yunyou.yike.ui_view.dialog.DiaLogSuggestions;
import com.yunyou.yike.utils.ActivityCollector;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import java.lang.ref.WeakReference;
import java.util.Map;

import javax.inject.Inject;


/**
 * Created by ${王俊强} on 2017/4/19.
 */
public class MyFragment extends BaseMVPFragment<IView.IMyFragmentView, MyFragmentPresenter>
        implements IView.IMyFragmentView {

    private Button mButtonLoginOut;
    private MyUserInfo mMyUserInfo;

    /**
     * 传参构造方法
     *
     * @param param1
     * @return
     */
    public static MyFragment newInstance(String param1) {
        MyFragment fragment = new MyFragment();
        return fragment;
    }


    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return R.id.my_layout;
    }

    @Override
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    private SimpleDraweeView mDraweeView;
    private TextView mTextViewDataInfo,//信息管理
            mTextViewQianbao,//我的钱包
            mTextViewName,//昵称
            mTextViewComment,//好评率
            mTextViewYue,//余额
            mTextViewNotice, //1为最新公告
            mTextViewProtocol,//2服务协议
            mTextViewPrize,//3平台奖惩
            mTextViewLearn,//  4学习园地
            mTextViewScore,//5评分说明
            mTextViewCustomerService,//客服
            mTextViewTousu;//投诉建议

    private RatingBar mRatingBar;
    @Inject
    MyFragmentPresenter mMyFragmentPresenter;

    private WeakReference<KeFu> mFuWeakReference;

    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
        mDraweeView = optainView(R.id.my_image);
        mButtonLoginOut = optainView(R.id.my_btn_loginout);
        mTextViewName = optainView(R.id.my_tvname);
        mTextViewYue = optainView(R.id.my_text_yue);
        mTextViewComment = optainView(R.id.my_text_good_comment);
        mTextViewDataInfo = optainView(R.id.my_tvxinxiguanli);
        mTextViewQianbao = optainView(R.id.my_qianbao);
        mRatingBar = optainView(R.id.my_ratingbar);

        mTextViewScore = optainView(R.id.my_tvpingfen);
        mTextViewProtocol = optainView(R.id.my_tvxieyi);
        mTextViewNotice = optainView(R.id.my_tvgonggao);
        mTextViewPrize = optainView(R.id.my_tvjiang);
        mTextViewLearn = optainView(R.id.my_tvyuandi);

        mTextViewTousu = optainView(R.id.my_tousu);
        mTextViewCustomerService = optainView(R.id.textView4);
    }

    @Override
    protected void setlistener() {
        mTextViewDataInfo.setOnClickListener(new View.OnClickListener() {//信息管理
            @Override
            public void onClick(View v) {//信息管理
                startActivity(new Intent(getContext(), XinxiGuanliActivity.class));
            }
        });
        mTextViewQianbao.setOnClickListener(new View.OnClickListener() {//我的钱包
            @Override
            public void onClick(View v) {//我的钱包界面（我的余额我的银行卡）
                startActivity(new Intent(getContext(), MyYueActivity.class));
            }
        });
        mButtonLoginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击退出登录
                RongIMLoginManager.getInstance().disConnectRongIM(true);
                ActivityCollector.finishAll();
                LoginActivity.startLoginActivity(getActivity(), true);
            }
        });
        mTextViewNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//1为最新公告
                MyAppInfoActivity.startMyAppInfoActivity(getContext(), MyAppInfoActivity.MYTYPE1);
            }
        });
        mTextViewProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//2服务协议
                MyAppInfoActivity.startMyAppInfoActivity(getContext(), MyAppInfoActivity.MYTYPE2);
            }
        });
        mTextViewPrize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//3平台奖惩
                MyAppInfoActivity.startMyAppInfoActivity(getContext(), MyAppInfoActivity.MYTYPE3);
            }
        });
        mTextViewLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 4学习园地
                MyAppInfoActivity.startMyAppInfoActivity(getContext(), MyAppInfoActivity.MYTYPE4);
            }
        });
        mTextViewScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// 5评分说明
                MyAppInfoActivity.startMyAppInfoActivity(getContext(), MyAppInfoActivity.MYTYPE5);
            }
        });
        mTextViewCustomerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//联系客服
                getCustomerServiceData();
            }
        });
        mTextViewTousu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//投诉建议
                showDialogTousu();
            }
        });
    }

    /**
     * 显示投诉建议对话框
     */
    private void showDialogTousu() {
        new DiaLogSuggestions(getContext(), new DiaLogSuggestions.Callback() {
            @Override
            public void onClick(String content) {
                Map<String, String> map = new ArrayMap<String, String>();
                map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
                mMyFragmentPresenter.setfeedback(map);
            }
        }).show();
    }

    /**
     * 尝试获取客服数据
     */
    private void getCustomerServiceData() {
        if (mFuWeakReference == null || mFuWeakReference.get() == null) {
            mMyFragmentPresenter.getCustomerService();
        } else {
            showDialogCustomerService(mFuWeakReference.get());
        }
    }

    /**
     * 显示客服对话框
     *
     * @param keFu
     */
    private void showDialogCustomerService(KeFu keFu) {
        new DiaLogCustomerservice(getContext(), keFu).show();
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {
        Map<String, String> map = new ArrayMap<>();
        map.put("token", SpService.getSP().getUserToken(SpService.getSP().getPhone()));
        mMyFragmentPresenter.getUserInfo(map, isShowLoadingView);
    }


    @Override
    protected void RogerMessage(EventBusMessage message) {

    }


    @Override
    public void showUserInfo(Object myUserInfo) {
        if (myUserInfo != null && myUserInfo instanceof MyUserInfo) {
            mMyUserInfo = (MyUserInfo) myUserInfo;
            setViewInfo(mMyUserInfo);
        }
    }

    @Override
    public void showCustomerService(KeFu keFu) {
        mFuWeakReference = new WeakReference<KeFu>(keFu);
        showDialogCustomerService(mFuWeakReference.get());
    }

    @Override
    public void showSuggertions(String string) {
        To.ss(mDraweeView, string);
    }

    /**
     * 写入信息
     *
     * @param myUserInfo
     */
    private void setViewInfo(MyUserInfo myUserInfo) {
        try {
            mDraweeView.setImageURI(Uri.parse(myUserInfo.getData().getHead_pic()));
            mTextViewName.setText(myUserInfo.getData().getNickname());
            mTextViewComment.setText(myUserInfo.getData().getGood_comment() + "%");
            mTextViewYue.setText(myUserInfo.getData().getTotal_money());
            mRatingBar.setRating(Float.parseFloat(myUserInfo.getData().getRank()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected MyFragmentPresenter mPresenterCreate() {
        DaggerMyFragmentCompcoent.builder()
                .appCompcoent(((App) getActivity().getApplication()).getAppCompcoent())
                .presenterMobule(new PresenterMobule())
                .build().inject(this);
        return mMyFragmentPresenter;
    }
}
