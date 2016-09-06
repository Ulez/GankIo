package comulez.github.gankio.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.GankApi;
import comulez.github.gankio.GankRetrofit;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Girl;
import comulez.github.gankio.data.GirlData;
import comulez.github.gankio.data.VedioData;
import comulez.github.gankio.ui.adapter.GirlsListAdapter;
import comulez.github.gankio.ui.base.SwipeRefreshInf;
import comulez.github.gankio.ui.base.ToolbarActivity;
import comulez.github.gankio.util.Tutil;
import comulez.github.gankio.widget.MultiSwipeRefreshLayout;
import comulez.github.gankio.widget.MyImageView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Ulez on 2016/8/12.
 * Email：lcy1532110757@gmail.com
 */
public class MainActivity extends ToolbarActivity implements SwipeRefreshInf {
    List<Girl> mMeizhiList = new ArrayList<>();
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.rl_meizi)
    RecyclerView rlMeizi;
    @Bind(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.main_fb)
    FloatingActionButton mainFb;
    private GankApi gankService;
    private int mLastVideoIndex = 1;
    private String TAG = "MainActivity";
    private GirlsListAdapter mMeizhiListAdapter;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.OnScrollListener onscrollListner;
    private boolean beTouched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rlMeizi.setLayoutManager(layoutManager);
        mMeizhiListAdapter = new GirlsListAdapter(mContext, mMeizhiList);
        rlMeizi.setAdapter(mMeizhiListAdapter);
        gankService = GankRetrofit.getmInstance().getmGankService();
        loadData(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mLastVideoIndex = 1;
                loadData(true);
            }
        });
        rlMeizi.addOnScrollListener(getOnScrollListener(layoutManager));
        mMeizhiListAdapter.setOnGirlClickListenr(new GirlsListAdapter.OnGirlClickListenr() {
            @Override
            public void onGirlClick(View v, final MyImageView imageView, TextView gankDec, final Girl girl) {
                Log.e(TAG, "点击----");
                if (girl == null) return;
                if (v == imageView && !beTouched) {
                    beTouched = true;
                    Picasso.with(mContext).load(girl.url).fetch(new Callback() {//加载完了再开启activity，防止activity动画错误；
                        @Override
                        public void onSuccess() {
                            beTouched = false;
                            startPicturAcitivity(imageView, girl);
                        }

                        @Override
                        public void onError() {
                            beTouched = false;
                        }
                    });

                } else if (v == gankDec) {
                    Tutil.l(TAG, "girl.publishedAt=" + girl.publishedAt);
                    java.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String s = girl.publishedAt;
                    s = s.replace("T", " ");
                    s = s.replace("Z", "");
                    Tutil.l(s);
                    Date date = null;
                    try {
                        date = formatter.parse(s);
                        startGankActivity(date);
                    } catch (ParseException e) {
                        Tutil.t("参数错误");
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void startPicturAcitivity(MyImageView imageView, Girl girl) {
        Intent intent = PictureActivity.newIntent(mContext, girl.desc, girl.url);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, imageView, PictureActivity.TRANSIT_IMG);
        try {
            ActivityCompat.startActivity(MainActivity.this, intent, optionsCompat.toBundle());
        } catch (Exception e) {
            mContext.startActivity(intent);
            e.printStackTrace();
        }
    }

    private void startGankActivity(Date publishedAt) {
        Log.e(TAG, "put publishedAt=" + publishedAt);
        startActivity(GankActivity.newIntent(mContext, publishedAt));
    }

    private void loadData(final boolean clean) {
        setRefresh(true);
        Subscription s = Observable
                .zip(gankService.getGirlData(mLastVideoIndex), gankService.getVedioData(mLastVideoIndex), new Func2<GirlData, VedioData, GirlData>() {
                    @Override
                    public GirlData call(GirlData girlData, VedioData vedioData) {
                        for (int i = 0; i < girlData.getResults().size(); i++) {
                            girlData.getResults().get(i).desc = vedioData.getResults().get(i).getDesc();
//                            girlData.getResults().get(i).publishedAt = vedioData.getResults().get(i).getPublishedAt();
                            Log.e(TAG, "position=" + i + ",girl,publishedAt=" + girlData.getResults().get(i).publishedAt + ",vedioData,publishedAt=" + vedioData.getResults().get(i).getPublishedAt());
                        }
                        return girlData;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GirlData>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e(TAG, "onStart");
                        setRefresh(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                        setRefresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                        Snackbar.make(rlMeizi, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
                                .setAction("点击重试", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loadData(false);
                                    }
                                }).show();
                    }

                    @Override
                    public void onNext(GirlData girlData) {
                        if (clean) {
                            mMeizhiList.clear();
                        }
                        int start = 0;
                        if (mMeizhiList != null) {
                            start = mMeizhiList.size();
                        }
                        mMeizhiList.addAll(girlData.getResults());
//                        mMeizhiListAdapter.notifyDataSetChanged();
                        if (clean) {
                            mMeizhiListAdapter.notifyDataSetChanged();
                        } else {
                            mMeizhiListAdapter.notifyItemRangeChanged(start, 10);
                        }
                        setRefresh(false);
                    }
                });

        addSubscription(s);

//        o.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<GirlData>() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        Log.e(TAG, "onStart");
//                        Toast.makeText(mContext, "onStart", Toast.LENGTH_SHORT).show();
//                        setRefresh(true);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        Log.e(TAG, "onCompleted");
//                        Toast.makeText(mContext, "onCompleted", Toast.LENGTH_SHORT).show();
//                        setRefresh(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError");
//                        Snackbar.make(rlMeizi, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
//                                .setAction("点击重试", new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        loadData();
//                                    }
//                                }).show();
//                    }
//
//                    @Override
//                    public void onNext(GirlData girlData) {
//                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//                        rlMeizi.setLayoutManager(layoutManager);
//                        mMeizhiListAdapter = new MeizhiListAdapter(mContext, girlData.getResults());
//                        rlMeizi.setAdapter(mMeizhiListAdapter);
//                    }
//                });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void requestDataRefresh() {
        loadData(true);
    }

    @Override
    public void setRefresh(boolean refresh) {
        if (swipeRefreshLayout == null)
            return;
        swipeRefreshLayout.setRefreshing(refresh);
    }

    @Override
    public void setProgressViewOffset(boolean scale, int start, int end) {

    }

    @Override
    public void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback callback) {

    }

    public RecyclerView.OnScrollListener getOnScrollListener(final StaggeredGridLayoutManager layoutManager) {
        onscrollListner = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] position = layoutManager.findLastCompletelyVisibleItemPositions(new int[2]);
                boolean isBottom = position[1] >= mMeizhiListAdapter.getItemCount() - 5;
                if (isBottom && !swipeRefreshLayout.isRefreshing()) {
                    mLastVideoIndex++;
                    loadData(false);
                }
            }
        };
        return onscrollListner;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_notifiable);
        initNotifiableItemState(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_trending:
//                openGitHubTrending();
                return true;
            case R.id.action_notifiable:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initNotifiableItemState(MenuItem item) {

    }
}
