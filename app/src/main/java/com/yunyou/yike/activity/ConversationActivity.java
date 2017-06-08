package com.yunyou.yike.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;


public class ConversationActivity extends BaseActivity {
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;
    private TextView mTextViewTitle;
    private ImageView mImageViewBack;
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_conversation;
    }

    @Override
    protected int getStateLayoutID() {
        return 0;
    }

    @Override
    protected int getPullRefreshLayoutID() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mTextViewTitle = optionView(R.id.title_tvtitle);
        mImageViewBack = optionView(R.id.title_ivback);

        Intent intent = getIntent();
        mTargetId = intent.getData().getQueryParameter("targetId");//对方id
        mTargetIds = intent.getData().getQueryParameter("targetIds");
        String title = intent.getData().getQueryParameter("title");
        mTextViewTitle.setText(title);
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);

        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();

        fragment.setUri(uri);

    }

    @Override
    protected void setListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

}
