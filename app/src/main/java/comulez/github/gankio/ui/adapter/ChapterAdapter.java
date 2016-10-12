package comulez.github.gankio.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import comulez.github.gankio.R;
import comulez.github.gankio.data.ChapterItem;

/**
 * Created by Ulez on 2016/10/12.
 * Email：lcy1532110757@gmail.com
 */
public class ChapterAdapter extends BaseAdapter{
    Context context;
    List<ChapterItem> chapterItems;
    public ChapterAdapter(Context context, List<ChapterItem> chapterItems) {
        this.context=context;
        this.chapterItems=chapterItems;
    }

    @Override
    public int getCount() {
        return chapterItems.size();
    }

    @Override
    public ChapterItem getItem(int position) {
        return chapterItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converterView, ViewGroup parent) {
        ViewHolder holder = null;
        if(converterView==null) {
            converterView= LayoutInflater. from( context).inflate(R.layout.item_chapter, parent, false) ;
            holder = new ViewHolder();
            holder.textView = (TextView)converterView.findViewById(R.id.chapterName);
            converterView.setTag(holder);
        } else {
            holder = (ViewHolder )converterView.getTag();
        }
        holder.textView.setText(chapterItems.get(position).getTitle());
        return converterView;
    }

    public static class ViewHolder {
        TextView textView;
    }
}
