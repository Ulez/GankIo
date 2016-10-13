package comulez.github.gankio.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import comulez.github.gankio.util.Tutil;
import comulez.github.gankio.widget.MyImageView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GirlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GirlFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.rl_meizi)
    RecyclerView rlMeizi;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout  swipeRefreshLayout;

    private GankApi gankService;
    private int mLastVideoIndex = 1;
    private String TAG = "GirlFragment";
    private GirlsListAdapter mMeizhiListAdapter;
    private RecyclerView.OnScrollListener onscrollListner;
    private boolean beTouched = false;
    List<Girl> mMeizhiList = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Activity mContext;
    private CompositeSubscription mCompositeSubscription;


    public GirlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GirlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GirlFragment newInstance(String param1, String param2) {
        GirlFragment fragment = new GirlFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext=getActivity();
        View view = inflater.inflate(R.layout.fragment_girl, container, false);
        ButterKnife.bind(this, view);
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
        return view;
    }

    private void startPicturAcitivity(MyImageView imageView, Girl girl) {
        Intent intent = PictureActivity.newIntent(mContext, girl.desc, girl.url);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imageView, PictureActivity.TRANSIT_IMG);
        try {
            ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
        } catch (Exception e) {
            mContext.startActivity(intent);
            e.printStackTrace();
        }
    }

    private void startGankActivity(Date publishedAt) {
        startActivity(GankActivity.newIntent(mContext, publishedAt));
    }

    private void loadData(final boolean clean) {
        swipeRefreshLayout.setRefreshing(true);
        Subscription s = Observable
                .zip(gankService.getGirlData(mLastVideoIndex), gankService.getVedioData(mLastVideoIndex), new Func2<GirlData, VedioData, GirlData>() {
                    @Override
                    public GirlData call(GirlData girlData, VedioData vedioData) {
                        for (int i = 0; i < girlData.getResults().size(); i++) {
                            girlData.getResults().get(i).desc +="*"+ vedioData.getResults().get(i).getDesc();
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
                    }

                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
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
                        swipeRefreshLayout.setRefreshing(false);
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
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(s);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_trending:
                openGitHubTrending();
                return true;
            case R.id.action_search:
                openGitHubSearch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openGitHubSearch() {
        String url = getString(R.string.url_login_github_search);
        String title = getString(R.string.action_github_search);
        Intent intent = WebViewActivity.newIntent(mContext, url, title);
        startActivity(intent);
    }

    private void openGitHubTrending() {
        String url = getString(R.string.url_github_trending);
        String title = getString(R.string.action_github_trending);
        Intent intent = WebViewActivity.newIntent(mContext, url, title);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
