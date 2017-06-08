package com.yunyou.yike.fragment.msg.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunyou.yike.BaseMainFragment;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;

import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class MsgFragment extends BaseMainFragment {
    private static MsgFragment mMsgFragment;

    public static MsgFragment newInstance() {
        if (mMsgFragment == null) {
            mMsgFragment = new MsgFragment();
        }
        return mMsgFragment;
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
    protected View getViewLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);

        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);

        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();

        fragment.setUri(uri);
        return view;
    }


    @Override
    protected void initView(View viewLayout, Bundle savedInstanceState) {
    }

    @Override
    protected void setlistener() {

    }

    @Override
    protected void RogerMessage(EventBusMessage message) {

    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }
}
