package com.yunyou.yike.RongIM;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.yunyou.yike.App;
import com.yunyou.yike.utils.LogUtils;
import com.yunyou.yike.utils.SpService;
import com.yunyou.yike.utils.To;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Discussion;
import io.rong.imlib.model.UserInfo;

import static io.rong.imlib.model.PublicServiceMenu.PublicServiceMenuItemType.Group;

/**
 * Created by ${王俊强} on 2017/6/6.
 */

public class RongIMLoginManager {
    private static final RongIMLoginManager ourInstance = new RongIMLoginManager();
    private Context mContext;

    public static RongIMLoginManager getInstance() {
        return ourInstance;
    }

    public void init(Context context) {
        mContext = context;
        RongIM.init(mContext);//初始化融云
        setUserInfoData();
    }


    private RongIMLoginManager() {
    }

    /**
     * 关闭融云
     */
    public void disConnectRongIM(boolean isPushOut) {
        if (isPushOut) {
            RongIM.getInstance().logout();
        } else {
            RongIM.getInstance().disconnect();
        }
    }


    /**
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token 从服务端获取的用户身份令牌（Token）。
     * @return RongIM  客户端核心类的实例。
     */
    public void connect(String token, final RongIMConnentListener listener) {
        LogUtils.d("融云token=" + token);
        if (TextUtils.isEmpty(token)) {
            To.ee("对不起亲 我们攻城狮正在努力查找错误");
            if (listener != null) {
                listener.onError(null);
            }
            return;
        }
        if (mContext.getApplicationInfo().packageName.equals(
                App.getCurProcessName(mContext.getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    if (listener != null) {
                        listener.onTokenIncorrect();
                    }
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    LogUtils.d("RongIM-onSuccess" + "" + userid);
                    SpService.getSP().saveR_userId(SpService.getSP().getPhone(), userid);
                    if (listener != null) {
                        listener.onSuccess(userid);
                    }

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.e("连接融云失败=" + errorCode.getMessage() + "--" + errorCode.getValue());
                    if (listener != null) {
                        listener.onError(errorCode);
                    }
                }
            });
        }
    }

    /**
     * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
     *
     * @param userInfoProvider 用户信息提供者。
     * @param isCacheUserInfo  设置是否由 IMKit 来缓存用户信息。<br>
     *                         如果 App 提供的 UserInfoProvider
     *                         每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
     *                         此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
     * @see UserInfoProvider
     */
    private void setUserInfoData() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {

                return findUserById(userId);//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }

        }, false);
    }

    /**
     * 去自己服务器请求用户数据
     *
     * @param userId
     * @return
     */
    private UserInfo findUserById(String userId) {

        /**
         * 刷新用户缓存数据。
         *
         * @param userInfo 需要更新的用户缓存数据。
         */
        RongIM.getInstance().refreshUserInfoCache(new UserInfo("userId", "啊明",
                Uri.parse("http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png")));


        /**
         * 刷新群组缓存数据。
         *
         * @param group 需要更新的群组缓存数据。
         */

//    public void refreshGroupInfoCache(Group group);

        /**
         * 刷新讨论组缓存数据，可用于讨论组修改名称后刷新讨论组内其他人员的缓存数据。
         *
         * @param discussion 需要更新的讨论组缓存数据。
         */
//    public void refreshDiscussionCache(Discussion discussion)
        return null;
    }


}
