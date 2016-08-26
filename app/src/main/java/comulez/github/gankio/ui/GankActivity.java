package comulez.github.gankio.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
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
    private String url = "http://www.miaopai.com/show/bJWgofJk3Fq0mXsdWQcPpg__.htm";
    private OkHttpClient client;
    private List<Gank> gankList;
    private GankAdapter adapter;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initRecy();
        GankApi gankService = GankRetrofit.getmInstance().getmGankService();
        gankService
                .getGankData(2016, 8, 24)
                .map(new Func1<GankData, GankData>() {
                    @Override
                    public GankData call(GankData gankData) {
                        String oldUrl = gankData.results.休息视频List.get(0).url;
                        gankData.results.休息视频List.get(0).url = getPreImageUrl(oldUrl);
                        Log.e(TAG, "Func1===" + gankData.results.休息视频List.get(0).url);
                        return gankData;
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
                        Glide.with(mContext).load(gankData.results.休息视频List.get(0).url).into(iv_preview);
                    }
                });
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
        try {
            result = getHtml(oldUrl);
            int s0 = result.indexOf("\"og:image\" content=");
            int s1 = result.indexOf("http", s0);
            int s2 = result.indexOf("\"/>", s1);
            return result.substring(s1, s2);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @OnClick(R.id.im_play)
    public void onClick() {

    }
}
