package comulez.github.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Gank;
import comulez.github.gankio.ui.WebViewActivity;

/**
 * Created by Ulez on 2016/8/24.
 * Email：lcy1532110757@gmail.com
 */
public class GankAdapter extends AniAdapter<GankAdapter.Holder> {
    Context context;
    List<Gank> gankList;
    private String currentType="";
    private String TAG="GankAdapter";

    public GankAdapter(Context context, List<Gank> gankDatas) {
        this.context = context;
        this.gankList = gankDatas;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_gank, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Gank gank = gankList.get(position);
//        if (position==0){
//            holder.category.setVisibility(View.VISIBLE);
//            currentType=gank.type;
//        }
        if (gank.type.equals(currentType)){
            holder.category.setVisibility(View.GONE);
        }else {
            currentType=gank.type;
            holder.category.setVisibility(View.VISIBLE);
            holder.category.setText(gank.type);
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(gank.desc).append(format(context, " (via." +gank.who + ")", R.style.ViaTextAppearance));
        CharSequence gankText = builder.subSequence(0, builder.length());

        holder.tvDesc.setText(gankText);
        showItemAnim(holder.tvDesc,position);
    }
    public static SpannableString format(Context context, String text, int style) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(),0);
        return spannableString;
    }

    @Override
    public int getItemCount() {
        return gankList.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.category)
        TextView category;
        @Bind(R.id.tv_desc)
        TextView tvDesc;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvDesc.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position=getLayoutPosition();
            gankList.get(position);
            context.startActivity(WebViewActivity.newIntent(context,gankList.get(position).url, gankList.get(position).desc));
            Log.e(TAG,"url="+gankList.get(position).url+",描述="+gankList.get(position).desc);
        }
    }
}
