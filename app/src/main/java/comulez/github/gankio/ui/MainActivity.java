package comulez.github.gankio.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.GankApi;
import comulez.github.gankio.GankRetrofit;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Girl;
import comulez.github.gankio.data.GirlData;
import comulez.github.gankio.ui.adapter.MeizhiListAdapter;
import comulez.github.gankio.ui.base.SwipeRefreshInf;
import comulez.github.gankio.ui.base.ToolbarActivity;
import comulez.github.gankio.widget.MultiSwipeRefreshLayout;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Ulez on 2016/8/12.
 * Email：lcy1532110757@gmail.com
 */
public class MainActivity extends ToolbarActivity implements SwipeRefreshInf {
    List<Girl> mMeizhiList=new ArrayList<>();
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
    private MeizhiListAdapter mMeizhiListAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        loadData();
    }

    private void loadData() {
        gankService = GankRetrofit.getmInstance().getmGankService();
        Observable<GirlData> o = gankService.getGirlData(mLastVideoIndex);
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GirlData>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        Log.e(TAG, "onStart");
                        Toast.makeText(mContext,"onStart",Toast.LENGTH_SHORT).show();
                        setRefresh(true);
                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                        Toast.makeText(mContext,"onCompleted",Toast.LENGTH_SHORT).show();
                        setRefresh(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                        Toast.makeText(mContext,"onError",Toast.LENGTH_SHORT).show();
                        Snackbar.make(rlMeizi, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
                                .setAction("点击重试", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loadData();
                                    }
                                }).show();
                    }

                    @Override
                    public void onNext(GirlData girlData) {
                        Log.e(TAG, "onNext,"+girlData.getResults().get(1).url);
                        Toast.makeText(mContext,girlData.getResults().get(1).url,Toast.LENGTH_SHORT).show();

                        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                        rlMeizi.setLayoutManager(layoutManager);

                        mMeizhiListAdapter = new MeizhiListAdapter(mContext, girlData.getResults());
                        rlMeizi.setAdapter(mMeizhiListAdapter);

//                        mMeizhiListAdapter.setOnGirlClickListenr(new GirlsListAdapter.OnGirlClickListenr() {
//                            @Override
//                            public void onGirlClick(View v, ImageView imageView, Girl girl) {
//
//                            }
//                        });

                    }
                });
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void requestDataRefresh() {

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
}
