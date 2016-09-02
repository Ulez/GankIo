package comulez.github.gankio.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.GankApi;
import comulez.github.gankio.GankRetrofit;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Content;
import comulez.github.gankio.ui.base.ToolbarActivity;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 加载的网页版gankio；
 */
public class GankActivity2 extends ToolbarActivity {
    public static String EXTRA_GANK_DATE = "date";
    @Bind(R.id.webview)
    WebView webview;
    private int year;
    private int month;
    private int day;
    private String TAG = "GankActivity2";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank2;
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
        webview.setWebViewClient(new WebViewClient());
        webview.setWebChromeClient(new WebChromeClient());

        GankApi gankService = GankRetrofit.getmInstance().getmGankService();
        Log.e(TAG, "year=" + year + "month=" + month + "day=" + day);
        gankService
                .getGankContent(year, month, day)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Content>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }

                    @Override
                    public void onNext(Content content) {
                        String data = content.getResults().get(0).getContent();
                        Log.e(TAG, "onNext,data==" + data.substring(0, 100));

//                        webview.loadData(data, "text/html; charset=UTF-8", null);//这种写法可以正确解码
                        webview.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
//                        webview.loadUrl("http://gank.io/"+year+"/"+month+"/"+day);//加载的网页版gankio；
                    }
                });

    }

    public static Intent newIntent(Context mContext, Date publishedAt) {
        Intent intent = new Intent(mContext, GankActivity2.class);
        intent.putExtra(EXTRA_GANK_DATE, publishedAt);
        return intent;
    }

    private void parseIntent() {
        Intent intent = getIntent();
        Serializable mDate = intent.getSerializableExtra(EXTRA_GANK_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((Date) mDate);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }
}
