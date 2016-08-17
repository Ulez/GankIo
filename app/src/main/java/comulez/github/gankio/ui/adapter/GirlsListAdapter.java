package comulez.github.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Girl;

/**
 * Created by Ulez on 2016/8/17.
 * Email：lcy1532110757@gmail.com
 */
public class GirlsListAdapter extends RecyclerView.Adapter<GirlsListAdapter.GirlHolder> {

    private Context mContext;
    private List<Girl> mGirls;

    public GirlsListAdapter(Context context, List<Girl> list) {
        mContext = context;
        mGirls = list;
    }

    @Override
    public GirlHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_girl, parent, false);
        return new GirlHolder(v);
    }

    @Override
    public void onBindViewHolder(GirlHolder holder, int position) {
        Girl girl = mGirls.get(position);
        String text = girl.desc.length() > 20 ? girl.desc.substring(0, 10) + "..." : girl.desc;
//        Glide.with(mContext)
//                .load(girl.url)
//                .centerCrop()
//                .into(holder.imageView);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mGirls.size();
    }

    @Override
    public void onViewRecycled(GirlHolder holder) {
        super.onViewRecycled(holder);
    }

    public class GirlHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.iv_meizhi)
        ImageView imageView;
        @Bind(R.id.tv_title)
        TextView textView;
        Girl girl;

        public GirlHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
            imageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onGirlClickListenr != null)
                onGirlClickListenr.onGirlClick(v, imageView, girl);
        }
    }

    public void setOnGirlClickListenr(OnGirlClickListenr onGirlClickListenr) {
        this.onGirlClickListenr = onGirlClickListenr;
    }

    private OnGirlClickListenr onGirlClickListenr;

    public interface OnGirlClickListenr {
        void onGirlClick(View v, ImageView imageView, Girl girl);
    }
}
