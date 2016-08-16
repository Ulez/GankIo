package comulez.github.gankio.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import comulez.github.gankio.R;
import comulez.github.gankio.data.Girl;
import comulez.github.gankio.ui.base.SwipeRefreshInf;
import comulez.github.gankio.ui.base.ToolbarActivity;
import comulez.github.gankio.widget.MultiSwipeRefreshLayout;

/**
 * Created by Ulez on 2016/8/12.
 * Email：lcy1532110757@gmail.com
 */
public class MainActivity extends ToolbarActivity implements SwipeRefreshInf {
    List<Girl> mMeizhiList;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.rl_meizi)
    RecyclerView rlMeizi;
    @Bind(R.id.swipe_refresh_layout)
    MultiSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.main_fb)
    FloatingActionButton mainFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void requestDataRefresh() {

    }

    @Override
    public void setRefresh(boolean refresh) {

    }

    @Override
    public void setProgressViewOffset(boolean scale, int start, int end) {

    }

    @Override
    public void setCanChildScrollUpCallback(MultiSwipeRefreshLayout.CanChildScrollUpCallback callback) {

    }
}
