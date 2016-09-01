package comulez.github.gankio.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GankActivity extends ToolbarActivity {

    public static String EXTRA_GANK_DATE = "date";
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
        parseIntent();
        initRecy();
        GankApi gankService = GankRetrofit.getmInstance().getmGankService();
        Log.e(TAG, "year=" + year + "month=" + month + "day=" + day);
        gankService
                .getGankData(year, month, day)
                .map(new Func1<GankData, GankData>() {
                    @Override
                    public GankData call(GankData gankData) {
                        try {
                            String oldUrl = gankData.results.休息视频List.get(0).url;
                            gankData.results.休息视频List.get(0).url += ("*" + getPreImageUrl(oldUrl));
                            Log.e(TAG, "Func1===" + gankData.results.休息视频List.get(0).url);
                            return gankData;
                        } catch (Exception e) {
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
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "error");
                    }

                    @Override
                    public void onNext(GankData gankData) {
                        addDatas(gankData.results);
                        adapter.notifyDataSetChanged();
                        Glide.with(mContext).load(handle(gankData.results.休息视频List.get(0).url)).into(iv_preview);
                    }
                });
    }

    private String handle(String url) {
        int yy = url.indexOf("*");
        if (yy == -1)
            return url;
        else
            return url.substring(yy+1, url.length());
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
            return result.substring(s1, s2);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @OnClick(R.id.im_play)
    public void onClick() {

    }

    public static Intent newIntent(Context mContext, Date publishedAt) {
        Intent intent = new Intent(mContext, GankActivity.class);
        intent.putExtra(GankActivity.EXTRA_GANK_DATE, publishedAt);
        return intent;
    }
}
