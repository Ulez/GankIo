package comulez.github.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.data.NovelBean;

/**
 * Created by Ulez on 2016/10/11.
 * Email：lcy1532110757@gmail.com
 */
public class NovelsAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NovelBean.Book> bookList;
    private Context mContext;
    private static final int MORE = 2;
    private static final int NORMAL = 1;

    public NovelsAdpter(Context context, List<NovelBean.Book> list) {
        mContext = context;
        bookList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NORMAL:
                return new NovelHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_novel, parent, false));
            default:
                return new MoreHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case NORMAL:
                NovelBean.Book book = bookList.get(position);
                Glide.with(mContext)
                        .load(book.getImgUrl())
                        .centerCrop()
                        .into(((NovelHolder) holder).iv_book);
                ((NovelHolder) holder).author.setText(book.getAuthor() + " 著");
                ((NovelHolder) holder).tv_bookname.setText(book.getName());
                ((NovelHolder) holder).tv_booktype.setText(book.getClasses());
                ((NovelHolder) holder).last_chapter.setText("最新:" + book.getLastChapterName());
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
            return bookList.size()+1 ;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("LCY","position="+position);
        if (position <bookList.size())
            return NORMAL;
        else
        return MORE;
    }

    public class MoreHolder extends RecyclerView.ViewHolder {

        public MoreHolder(View itemView) {
            super(itemView);
        }
    }

    public class NovelHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_book)
        ImageView iv_book;
        @Bind(R.id.tv_bookname)
        TextView tv_bookname;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.tv_booktype)
        TextView tv_booktype;
        @Bind(R.id.tv_last_chapter)
        TextView last_chapter;
        @Bind(R.id.ll)
        LinearLayout ll;

        public NovelHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
