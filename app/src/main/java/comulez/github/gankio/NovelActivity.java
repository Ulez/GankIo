package comulez.github.gankio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.data.BookDetail;
import comulez.github.gankio.data.Bookcc;
import comulez.github.gankio.data.ChapterItem;
import comulez.github.gankio.ui.ReadActivity;
import comulez.github.gankio.ui.adapter.ChapterAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NovelActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.iv_book)
    ImageView ivBook;
    @Bind(R.id.tv_bookname)
    TextView tvBookname;
    @Bind(R.id.author)
    TextView author;
    @Bind(R.id.tv_booktype)
    TextView tvBooktype;
    @Bind(R.id.tv_last_update)
    TextView tvLastUpdate;
    @Bind(R.id.ll)
    LinearLayout ll;
    @Bind(R.id.tv_latest)
    TextView tvLatest;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.tv_dec)
    TextView tvDec;
    @Bind(R.id.tv_chapters)
    TextView tvChapters;
    @Bind(R.id.rv_detail)
    RelativeLayout rvDetail;
    @Bind(R.id.pb_loading)
    ProgressBar pbLoading;
    private NovelApi novelService;
    private Bookcc book;
    private Context context;
    private ChapterAdapter adapter;
    private ListView listView;
    private ProgressBar pb_loading;
    private TextView name;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);
        ButterKnife.bind(this);
        context=this;
        book = getIntent().getParcelableExtra("book");
        novelService = NovelRetrofit.getmInstance().getmNovelService();
        tvChapters.setOnClickListener(this);
        if (book != null) {
            Glide.with(this)
                    .load(book.getPic())
                    .placeholder(R.drawable.bg_default_cover)
                    .centerCrop()
                    .into(ivBook);
            author.setText(book.getAuthor() + " 著");
            tvBookname.setText(book.getName());
            novelService.getBookDetail(book.getId())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<BookDetail>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(BookDetail bookDetail) {
                            tvDec.setText(handle(bookDetail.getDescription()));
                            tvLastUpdate.setText("更新:" + bookDetail.getLast_update());
                            tvLatest.setText("章数:" + bookDetail.getArticle_num());
                            pbLoading.setVisibility(View.GONE);
                            rvDetail.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }

    private String handle(String description) {
        try {
            return description.substring(description.indexOf("】：") + 3, description.length());
        } catch (Exception e) {
            e.printStackTrace();
            return description;
        }
    }

    public static Intent newIntence(Context context, Bookcc book) {
        Intent intent = new Intent(context, NovelActivity.class);
        intent.putExtra("book", book);
        return intent;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_chapters:
                getChapters();
                break;
        }

    }

    private void getChapters() {
        if (alertDialog==null){
            View  view= getLayoutInflater().inflate(R.layout.chapters, null);
            listView = (ListView) view.findViewById(R.id.lv_chapter);
            name = (TextView) view.findViewById(R.id.name);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(book.getName()).append(format(context, "  " +book.getAuthor() + " 著", R.style.ViaTextAppearance2));
            CharSequence gankText = spannableStringBuilder.subSequence(0, spannableStringBuilder.length());
            name.setText(gankText);
            pb_loading = (ProgressBar) view.findViewById(R.id.progressBar3);
            AlertDialog.Builder  builder=new AlertDialog.Builder(context);
            builder.setView(view);
            alertDialog = builder.create();
            loadChapter();
        }
        if (alertDialog.isShowing()){
            alertDialog.hide();
        }else {
            alertDialog.show();
        }

    }

    private void loadChapter() {
        novelService.getBookChapters(book.getId(), true)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<ChapterItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(final ArrayList<ChapterItem> chapterItems) {
                        adapter = new ChapterAdapter(context,chapterItems);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                context.startActivity(ReadActivity.newIntent(context,chapterItems,position));
                            }
                        });
                        listView.setVisibility(View.VISIBLE);
                        pb_loading.setVisibility(View.GONE);
                    }
                });
    }

    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(),0);
        return spannableString;
    }
}
