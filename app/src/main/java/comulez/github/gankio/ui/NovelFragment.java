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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import comulez.github.gankio.NovelApi;
import comulez.github.gankio.NovelRetrofit;
import comulez.github.gankio.R;
import comulez.github.gankio.data.NovelBean;
import comulez.github.gankio.ui.adapter.DividerItemDecoration;
import comulez.github.gankio.ui.adapter.NovelsAdpter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NovelFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Context context;
    private String TAG = "NovelFragment";
    private RecyclerView recyclerView;
    private List<NovelBean.Book> books = new ArrayList<>();
    private NovelsAdpter novelsAdpter;
    private RecyclerView.OnScrollListener onscrollListner;
    private int page_id =1;

    public NovelFragment() {
    }

    public static NovelFragment newInstance(String param1, String param2) {
        NovelFragment fragment = new NovelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_novel, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_books);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        novelsAdpter = new NovelsAdpter(context, books);
        recyclerView.setAdapter(novelsAdpter);
        recyclerView.addOnScrollListener(getOnScrollListener(linearLayoutManager));
        novelsAdpter.setOnItemClickListener(new NovelsAdpter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, NovelBean.Book book) {
                Toast.makeText(context,position+book.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        loadData(false);
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
                    loadData(false);
                }
            }
        };
        return onscrollListner;
    }

    private void loadData(final boolean clear) {
        NovelApi novelService = NovelRetrofit.getmInstance().getmNovelService();
        novelService
                .getYishou("玄幻",2,page_id,20,0,"eef_easou_book","002","android","1D6306147D6D13B28807856B31ED272E",1033,"blp1298_10269_001","j2fSuyqnF1Zh4GY7Q4M2HxNASbH4c")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NovelBean>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError" + e.toString());
                    }

                    @Override
                    public void onNext(NovelBean novelBean) {
                        if (clear)
                            books.clear();
                        books.addAll(novelBean.getAll_book_items());
                        novelsAdpter.notifyDataSetChanged();
                    }
                });
    }
}
