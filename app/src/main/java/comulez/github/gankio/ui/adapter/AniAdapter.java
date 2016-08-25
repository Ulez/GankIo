package comulez.github.gankio.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import comulez.github.gankio.R;

/**
 * Created by Ulez on 2016/8/24.
 * Email：lcy1532110757@gmail.com
 */
public class AniAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{
    private int mLastPosition=-1;

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void showItemAnim(final View view, final int position) {
        if (position>mLastPosition){
            view.setAlpha(0.0f);
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation animation= AnimationUtils.loadAnimation(view.getContext(), R.anim.item_anim);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            view.setAlpha(1.0f);//这里为啥要有个监听？
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {}
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    view.startAnimation(animation);
                }
            },50*position);
            mLastPosition=position;
        }
    }
}
