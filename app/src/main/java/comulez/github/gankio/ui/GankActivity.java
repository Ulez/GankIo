package comulez.github.gankio.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import comulez.github.gankio.GankApi;
import comulez.github.gankio.GankRetrofit;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Gank;
import comulez.github.gankio.data.GankData;
import comulez.github.gankio.ui.adapter.GankAdapter;
import comulez.github.gankio.ui.base.ToolbarActivity;
import comulez.github.gankio.util.Tutil;
import comulez.github.gankio.widget.LoveVideoView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GankActivity extends ToolbarActivity {

    private static String EXTRA_GANK_DATE = "date";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.iv_pre)
    ImageView iv_preview;
    @Bind(R.id.im_play)
    ImageView imPlay;
    @Bind(R.id.rv_ganks)
    RecyclerView rvGanks;
    @Bind(R.id.stub_empty_view)
    ViewStub stubEmptyView;
    LoveVideoView mVideoView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private ViewStub mVideoViewStub;
    private String TAG = "GankActivity";
    private OkHttpClient client;
    private List<Gank> gankList;
    private GankAdapter adapter;
    private int year = 2016;
    private int month = 7;
    private int day = 1;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.VISIBLE);
        mVideoViewStub = (ViewStub) findViewById(R.id.stub_video_web);
        parseIntent();
        initRecy();
        GankApi gankService = GankRetrofit.getmInstance().getmGankService();
        gankService
                .getGankData(year, month, day)
                .map(new Func1<GankData, GankData>() {
                    @Override
                    public GankData call(GankData gankData) {
                        try {
                            String oldUrl = gankData.results.休息视频List.get(0).url;
                            gankData.results.休息视频List.get(0).url += getPreImageUrl(oldUrl);
                            Log.e(TAG, "Func1===" + gankData.results.休息视频List.get(0).url);
                            return gankData;
                        } catch (Exception e) {
//                            loadWeb();
                            e.printStackTrace();
                            return gankData;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GankData>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadWeb();
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(GankData gankData) {
                        addDatas(gankData.results);
                        adapter.notifyDataSetChanged();
                        Glide.with(mContext).load(getPreImgUrl(gankData.results.休息视频List.get(0).url)).into(iv_preview);
                    }
                });
        setVideoViewPosition(getResources().getConfiguration());
    }

    boolean mIsVideoViewInflated = false;

    private void setVideoViewPosition(Configuration newConfig) {
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE: {
                if (mIsVideoViewInflated) {
                    mVideoViewStub.setVisibility(View.VISIBLE);
                } else {
                    mVideoView = (LoveVideoView) mVideoViewStub.inflate();
                    mIsVideoViewInflated = true;
//                    String tip = getString(R.string.tip_video_play);
//                    // @formatter:off
//                    new Once(mVideoView.getContext()).show(tip, () ->
//                            Snackbar.make(mVideoView, tip, Snackbar.LENGTH_INDEFINITE)
//                                    .setAction(R.string.i_know, v -> {})
//                                    .show());
//                    // @formatter:on
                }
                if (gankList.size() > 0 && gankList.get(0).type.equals("休息视频")) {
                    String url = new String(gankList.get(0).url);
                    mVideoView.loadUrl(handle(url));
                    appBarLayout.setVisibility(View.GONE);
                } else
                    appBarLayout.setVisibility(View.VISIBLE);
                break;
            }
            case Configuration.ORIENTATION_PORTRAIT:
            case Configuration.ORIENTATION_UNDEFINED:
            default: {
                appBarLayout.setVisibility(View.VISIBLE);
                mVideoViewStub.setVisibility(View.GONE);
                break;
            }
        }
    }

    private String handle(String url) {
        Tutil.l(TAG, "完整_url=" + url);
        int xxx = url.indexOf("*");
        if (xxx > 0) {
            return url.substring(0, xxx);
        } else return url;
    }

    private void loadWeb() {
        WebView webView = new WebView(mContext);
        Log.e(TAG, "error,," + "http://gank.io/" + year + "/" + month + "/" + day);
        webView.loadUrl("http://gank.io/" + year + "/" + month + "/" + day);//加载的网页版gankio；
        setContentView(webView);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            clearVideoView();
            return true;
        }
//        if (event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {  //表示按返回键
//                webview.goBack();   //后退
//                //webview.goForward();//前进
//                return true;    //已处理
//            }
//        }
        return super.onKeyDown(keyCode, event);
    }

    private void clearVideoView() {
        if (mVideoView != null) {
            mVideoView.clearHistory();
            mVideoView.clearCache(true);
            mVideoView.loadUrl("about:blank");
            mVideoView.pauseTimers();
        }
    }

    /**
     * 包含网页url和预览图url；
     *
     * @param allUrl
     * @return
     */
    private String getPreImgUrl(String allUrl) {
        int yy = allUrl.indexOf("*");
        if (yy == -1)
            return allUrl;
        else
            return allUrl.substring(yy + 1, allUrl.length());
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

    private void initRecy() {
        gankList = new ArrayList<>();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
        adapter = new GankAdapter(mContext, gankList);
        rvGanks.setLayoutManager(manager);
        rvGanks.setAdapter(adapter);
    }

    private void addDatas(GankData.Result result) {
        if (result == null) return;
        if (result.休息视频List != null)
            gankList.addAll(0, result.休息视频List);
        if (result.androidList != null)
            gankList.addAll(result.androidList);
        if (result.iOSList != null)
            gankList.addAll(result.iOSList);
        if (result.appList != null)
            gankList.addAll(result.appList);
        if (result.拓展资源List != null)
            gankList.addAll(result.拓展资源List);
        if (result.瞎推荐List != null)
            gankList.addAll(result.瞎推荐List);
    }

    String getHtml(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private String getPreImageUrl(String oldUrl) {
        client = new OkHttpClient();
        String result = null;
        int s0 = -1;
        int s1 = -1;
        int s2 = -1;
        try {
            result = getHtml(oldUrl);
            s0 = result.indexOf("\"og:image\" content=");
            if (s0 == -1)//  <img src="http://i0.hdslb.com/bfs/archive/b308552417ab6dcfe98f873bd4882f0a511ad838.jpg" style="display:none;" class="cover_image
                s0 = result.indexOf("<img src=");
            s1 = result.indexOf("http", s0);
            s2 = result.indexOf(".jpg", s1) + 4;
            return "*" + result.substring(s1, s2);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @OnClick(R.id.header_appbar)
    void onPlayVideo() {
//        resumeVideoView();
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        if (gankList.size() > 0 && gankList.get(0).type.equals("休息视频")) {
//            Tutil.t(getString(R.string.loading));
//        } else {
//            closePlayer();
//        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setVideoViewPosition(newConfig);
        super.onConfigurationChanged(newConfig);
    }


    private void resumeVideoView() {
        if (mVideoView != null) {
            mVideoView.resumeTimers();
            mVideoView.onResume();
        }
    }

    void closePlayer() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Tutil.t(getString(R.string.tip_for_no_gank));
    }

    public static Intent newIntent(Context mContext, Date publishedAt) {
        Intent intent = new Intent(mContext, GankActivity.class);
        intent.putExtra(EXTRA_GANK_DATE, publishedAt);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) mVideoView.destroy();
        ButterKnife.unbind(this);
    }


    @Override
    protected void onPause() {
        if (mVideoView != null) mVideoView.onPause();
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) mVideoView.onResume();
    }
}
