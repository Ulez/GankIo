package comulez.github.gankio.ui.base;

import comulez.github.gankio.widget.MultiSwipeRefreshLayout;

/**
 * Created by Ulez on 2016/8/15.
 * Email：lcy1532110757@gmail.com
 */
public interface SwipeRefreshInf {
    void requestDataRefresh();
    void setRefresh(boolean refresh);
    void setProgressViewOffset(boolean scale,int start,int end);
    void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback callback);
}
