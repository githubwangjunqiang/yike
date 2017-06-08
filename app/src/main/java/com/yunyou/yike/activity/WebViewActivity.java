package com.yunyou.yike.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunyou.yike.BaseActivity;
import com.yunyou.yike.R;
import com.yunyou.yike.entity.EventBusMessage;


/**
 * Created by {王俊强} on 2016/8/2 0002.
 */
public class WebViewActivity extends BaseActivity {
    private WebView mView;
    private ImageView ibBack;
    private TextView tvtitle;

    @Override
    protected void onDestroy() {
        mView.clearCache(false);
        super.onDestroy();
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_webview;
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

        mView = optionView(R.id.webviewactivity_webview);
        ibBack = optionView(R.id.webView_ivback);
        tvtitle = optionView(R.id.webview_tvtitle);
        String url = getIntent().getStringExtra("url");
        if (getIntent().getStringExtra("title") != null) {
            tvtitle.setText(getIntent().getStringExtra("title"));
        }
        WebSettings webSettings = mView.getSettings();
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//关闭webview中缓存
        mView.loadUrl(url);
    }

    @Override
    protected void setListener() {
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.this.finish();
            }
        });
        mView.setDownloadListener(new MyWebViewDownLoadListener());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (!request.getUrl().toString().startsWith("http")) {
                        Log.e("12345", "处理自定义scheme");
                        try {
                            // 以下固定写法
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(request.getUrl().toString()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            WebViewActivity.this.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, request);
                }
            });

        } else {
            mView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Log.e("12345", "分享: " + url);
                    if (!url.startsWith("http")) {
                        Log.e("12345", "处理自定义scheme");
                        try {
                            // 以下固定写法
                            final Intent intent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            WebViewActivity.this.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                        return true;
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

            });
        }
    }

    @Override
    protected void rogerMessage(EventBusMessage message) {

    }

    public static void startWebViewActivity(Context context, String allUrl, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", allUrl);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public void startRefresh(boolean isShowLoadingView) {

    }


    /**
     * 点击超链接 进行下载任务
     */
    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
//            Log.i("tag", "url="+url);
//            Log.i("tag", "userAgent="+userAgent);
//            Log.i("tag", "contentDisposition="+contentDisposition);
//            Log.i("tag", "mimetype="+mimetype);
//            Log.i("tag", "contentLength="+contentLength);
            //调用 系统浏览器 进行下载任务
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }

    /**
     * 按返回键时， 不退出程序而是返回上一浏览页面：
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mView.canGoBack()) {
            mView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
