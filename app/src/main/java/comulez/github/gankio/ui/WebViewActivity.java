package comulez.github.gankio.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.ui.base.ToolbarActivity;

public class WebViewActivity extends ToolbarActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.webview)
    WebView webview;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private String url;
    private String desc;
    private String TAG = "WebViewActivity";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        parseIntent();

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);//自适应屏幕也要用到；
        settings.setLoadWithOverviewMode(true);//自适应屏幕；
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        if (!TextUtils.isEmpty(url)) {
            webview.setWebViewClient(new MyWebViewClient());
            webview.setWebChromeClient(new MyChromeClient());
            Log.e(TAG, "web_video_url=" + url);
            webview.loadUrl(url);
        }
    }

    private class MyChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress<100){
                progressBar.setProgress(newProgress);
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
    private class MyWebViewClient extends WebViewClient {

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }


    private void parseIntent() {
        Intent intent = getIntent();
        url = intent.getStringExtra(WEB_UEL);
        if (url.indexOf("*")>0){
            url=url.substring(0,url.indexOf("*"));
        }
        desc = intent.getStringExtra(WEB_DESC);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键
                webview.goBack();   //后退
                //webview.goForward();//前进
                return true;    //已处理
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private static String WEB_UEL = "url";
    private static String WEB_DESC = "desc";

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_UEL, url);
        intent.putExtra(WEB_DESC, desc);
        return intent;
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (webview != null) webview.destroy();
        ButterKnife.unbind(this);
    }


    @Override protected void onPause() {
        if (webview != null) webview.onPause();
        super.onPause();
    }


    @Override protected void onResume() {
        super.onResume();
        if (webview != null) webview.onResume();
    }
}
