package comulez.github.gankio.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import comulez.github.gankio.NovelActivity;
import comulez.github.gankio.NovelApi;
import comulez.github.gankio.NovelRetrofit;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Bookcc;
import comulez.github.gankio.ui.adapter.DividerItemDecoration;
import comulez.github.gankio.ui.adapter.NovelsAdpter;
import comulez.github.gankio.util.Constant;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NovelFragment extends Fragment {
    private static final String novel_type = "novel_type";
    private int mType;
    private Context context;
    private String TAG = "NovelFragment";
    private RecyclerView recyclerView;
    private List<Bookcc> books = new ArrayList<>();
    private NovelsAdpter novelsAdpter;
    private RecyclerView.OnScrollListener onscrollListner;
    private int page_id =1;
    private ProgressBar pb_loading;

    public NovelFragment() {
    }

    public static NovelFragment newInstance(int type) {
        NovelFragment fragment = new NovelFragment();
        Bundle args = new Bundle();
        args.putInt(novel_type, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            mType = getArguments().getInt(novel_type);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_books);
        pb_loading = (ProgressBar) view.findViewById(R.id.pg_loading);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        novelsAdpter = new NovelsAdpter(context, books);
        recyclerView.setAdapter(novelsAdpter);
        recyclerView.addOnScrollListener(getOnScrollListener(linearLayoutManager));
        novelsAdpter.setOnItemClickListener(new NovelsAdpter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Bookcc book) {
                Log.e(TAG,"id="+book.getId());
                context.startActivity(NovelActivity.newIntence(context,book));
            }
        });
        loadDataByType(false, mType);
        return view;
    }

    public RecyclerView.OnScrollListener getOnScrollListener(final LinearLayoutManager layoutManager) {
        onscrollListner = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = layoutManager.findLastVisibleItemPosition();
                boolean isBottom = pos >= books.size() - 1;
                if (isBottom) {
                    page_id++;
                    loadDataByType(false,mType);
                }
            }
        };
        return onscrollListner;
    }

    private void loadDataByType(final boolean clear, int novel_type) {
        Observable<List<Bookcc>> observable=getObservabel(novel_type);
        if (observable==null)
            return;
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bookcc>>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError" + e.toString());
                    }

                    @Override
                    public void onNext(List<Bookcc> novelBean) {
                        if (clear)
                            books.clear();
                        books.addAll(novelBean);
                        novelsAdpter.notifyDataSetChanged();
                        pb_loading.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
    }

    private Observable<List<Bookcc>> getObservabel(int novel_type) {
        Log.e("lcyy","type="+novel_type+"----page="+page_id);
        NovelApi novelService = NovelRetrofit.getmInstance().getmNovelService();
        switch (novel_type){
            case Constant.NOVEL_TYPE_XUANHUAN:
                return  novelService.getRecommend(novel_type,page_id);
            case Constant.NOVEL_TYPE_WUXIA:
                return  novelService.getRecommend(novel_type,page_id);
            case Constant.NOVEL_TYPE_YANQING:
                return  novelService.getRecommend(novel_type,page_id);
            case Constant.NOVEL_TYPE_DUSHI:
                return  novelService.getRecommend(novel_type,page_id);
            case Constant.NOVEL_TYPE_XUANYI:
                return  novelService.getRecommend(novel_type,page_id);
            default:
                return null;
        }
    }
}
