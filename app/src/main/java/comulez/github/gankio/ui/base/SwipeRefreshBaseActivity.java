package comulez.github.gankio.ui.base;

import butterknife.Bind;
import comulez.github.gankio.R;
import comulez.github.gankio.widget.MultiSwipeRefreshLayout;

/**
 * Created by Ulez on 2016/8/12.
 * Email：lcy1532110757@gmail.com
 */
public abstract class SwipeRefreshBaseActivity extends ToolbarActivity implements SwipeRefreshInf{
    @Bind(R.id.swipe_refresh_layout) public MultiSwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mIsRequestDataRefresh = false;
}
