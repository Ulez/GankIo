package comulez.github.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Gank;

/**
 * Created by Ulez on 2016/8/24.
 * Email：lcy1532110757@gmail.com
 */
public class GankAdapter extends AniAdapter<GankAdapter.Holder> {
    Context context;
    List<Gank> gankList;
    private String currentType="";

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
        if (position==0){
            holder.category.setVisibility(View.VISIBLE);
            currentType=gank.type;
        }
        if (gank.type.equals(currentType)){
            holder.category.setVisibility(View.GONE);
        }else {
            currentType=gank.type;
            holder.category.setVisibility(View.VISIBLE);
            holder.category.setText(gank.type);
        }
        holder.tvDesc.setText(gank.desc);
    }


    @Override
    public int getItemCount() {
        return gankList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @Bind(R.id.category)
        TextView category;
        @Bind(R.id.tv_desc)
        TextView tvDesc;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
