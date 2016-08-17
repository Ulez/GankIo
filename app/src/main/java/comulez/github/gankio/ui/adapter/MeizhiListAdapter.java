/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package comulez.github.gankio.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Girl;

/**
 * Created by drakeet on 6/20/15.
 */
public class MeizhiListAdapter extends RecyclerView.Adapter<MeizhiListAdapter.GirlHolder> {

    public static final String TAG = "MeizhiListAdapter";

    private List<Girl> mList;
    private Context mContext;
    private OnGirlClickListenr onGirlClickListenr;


    public MeizhiListAdapter(Context context, List<Girl> meizhiList) {
        mList = meizhiList;
        mContext = context;
    }


    @Override public GirlHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.item_meizhi, parent, false);
        return new GirlHolder(v);
    }


    @Override
    public void onBindViewHolder(final GirlHolder viewHolder, final int position) {
        Girl meizhi = mList.get(position);
        int limit = 48;
        String text = meizhi.desc.length() > limit ? meizhi.desc.substring(0, limit) +"..." : meizhi.desc;
        viewHolder.meizhi = meizhi;
        viewHolder.titleView.setText(text);
        viewHolder.card.setTag(meizhi.desc);

        Glide.with(mContext)
             .load(meizhi.url)
             .centerCrop()
             .into(viewHolder.meizhiView);
    }


    @Override public void onViewRecycled(GirlHolder holder) {
        super.onViewRecycled(holder);
    }


    @Override public int getItemCount() {
        return mList.size();
    }


    public void setOnMeizhiTouchListener(OnGirlClickListenr onMeizhiTouchListener) {
        this.onGirlClickListenr = onMeizhiTouchListener;
    }


    class GirlHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.iv_meizhi)
        ImageView meizhiView;
        @Bind(R.id.tv_title) TextView titleView;
        View card;
        Girl meizhi;


        public GirlHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
            meizhiView.setOnClickListener(this);
            card.setOnClickListener(this);
        }


        @Override public void onClick(View v) {
            onGirlClickListenr.onTouch(v, meizhiView, card, meizhi);
        }
    }

    public interface OnGirlClickListenr {
        void onTouch(View v, View meizhiView, View card, Girl meizhi);
    }
}
