package comulez.github.gankio.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        parseIntent();

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);//自适应屏幕也要用到；
        settings.setLoadWithOverviewMode(true);//自适应屏幕；
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);

        if (!TextUtils.isEmpty(url)) {
            url=handle(url);
            webview.setWebViewClient(new WebViewClient());
            webview.setWebChromeClient(new WebChromeClient());
            Log.e(TAG,"load_url="+url);
            webview.loadUrl(url);
        }
    }
    private String handle(String url) {
        int yy = url.indexOf("*");
        if (yy == -1)
            return url;
        else
            return url.substring(0, yy);
    }
    private void parseIntent() {
        Intent intent = getIntent();
        url = intent.getStringExtra(WEB_UEL);
        desc = intent.getStringExtra(WEB_DESC);
        Log.e(TAG, "url=" + url + ",desc=" + desc);
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
}
