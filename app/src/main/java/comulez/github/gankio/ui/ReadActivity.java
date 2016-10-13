package comulez.github.gankio.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.NovelRetrofit;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Chapter;
import comulez.github.gankio.data.ChapterItem;
import comulez.github.gankio.widget.MyScrollView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReadActivity extends AppCompatActivity implements MyScrollView.LoadMoreListener {

    ArrayList<ChapterItem> chapterItems;
    @Bind(R.id.chapterName)
    TextView chapterName;
    @Bind(R.id.chapterContent)
    TextView chapterContent;
    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.scrollView)
    MyScrollView scrollView;
    private int mPosition;
    private int firstX;
    private int firstY;
    private int lastX;
    private int lastY;
    private boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);
        parse(getIntent());
        scrollView.setLoadMoreListener(this, mPosition, isLoading,chapterItems.size());
    }

    private void parse(Intent intent) {
        if (intent != null) {
            chapterItems = intent.getParcelableArrayListExtra("chapterItems");
            mPosition = intent.getIntExtra("mPosition", -1);
        } else {
            return;
        }
        if (mPosition >= 0) {
            isLoading = false;
            loadData324(isLoading, mPosition);
        }
    }

    public static Intent newIntent(Context context, ArrayList<ChapterItem> chapterItems, int position) {
        Intent intent = new Intent(context, ReadActivity.class);
        intent.putParcelableArrayListExtra("chapterItems", chapterItems);
        intent.putExtra("mPosition", position);
        return intent;
    }

    @Override
    public void loadData324(boolean isLoading, int position) {
        this.isLoading = true;
        pb.setVisibility(View.VISIBLE);
        Log.e("lcy","position="+position) ;
        NovelRetrofit.getmInstance().getmNovelService()
                .getChapter(chapterItems.get(position).getId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Chapter>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Chapter chapter) {
                chapterName.setVisibility(View.VISIBLE);
                chapterContent.setVisibility(View.VISIBLE);
                chapterName.setText(chapter.getTitle());
                chapterContent.setText(chapter.getText());
                pb.setVisibility(View.GONE);
                ReadActivity.this.isLoading = false;
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
}
