package comulez.github.gankio.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import comulez.github.gankio.util.Tutil;

/**
 * Created by Ulez on 2016/10/12.
 * Email：lcy1532110757@gmail.com
 */
public class MyScrollView extends ScrollView {
    private int firstX;
    private int firstY;
    private int lastX;
    private int lastY;
    private int position;
    private boolean isLoading;
    private int size;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstX = (int) event.getRawX();
                firstY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                if (firstX != 0 && firstY != 0 && lastX != 0 && lastY != 0 && Math.abs(lastX - firstX) > Math.abs(lastY - firstY) && Math.abs(lastX - firstX) > 100) {
                    if (position >= size - 1) {

                    }
                    if (lastX - firstX > 0) {
                        if (position == 0) {
                            Tutil.t("已是第一章");
                            return super.onTouchEvent(event);
                        } else {
                            position--;
                        }
                    } else {
                        if (position == size - 1) {
                            Tutil.t("已是最后一章");
                            return super.onTouchEvent(event);
                        } else
                            position++;
                    }
                    if (loadMoreListener != null)
                        loadMoreListener.loadData324(isLoading, position);
                    firstX = 0;
                    firstY = 0;
                    lastX = 0;
                    lastY = 0;
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private LoadMoreListener loadMoreListener;


    public void setLoadMoreListener(LoadMoreListener loadMoreListener, int position, boolean isLoading, int size) {
        this.position = position;
        this.isLoading = isLoading;
        this.loadMoreListener = loadMoreListener;
        this.size = size;
    }

    public interface LoadMoreListener {
        void loadData324(boolean isLoading, int position);
    }
}
