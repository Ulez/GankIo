package comulez.github.gankio.ui.base;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import comulez.github.gankio.R;

/**
 * 带有toolbar的activity；toolbar统一命名id；
 * Created by Ulez on 2016/8/12.
 * Email：lcy1532110757@gmail.com
 */
public abstract class ToolbarActivity extends BaseActivity {
    private boolean mIsHiden=false;


    public void toobarClick(){};
    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar==null||mAppBar==null)
            throw new  IllegalStateException("ToolbarActivity must contain a toolbar.");
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toobarClick();
            }
        });

        setSupportActionBar(mToolbar);
        if (canBack()){
           ActionBar actionbar= getSupportActionBar();
            if (actionbar!=null)
                actionbar.setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }
    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }
    public boolean canBack() {
        return false;
    }
    protected void hideOrShowToolbar(){
        mAppBar.animate()
                .translationY(mIsHiden?0:-mAppBar.getHeight())
                .setDuration(1000)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsHiden=!mIsHiden;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId()==android.R.id.home){
          onBackPressed();
          return true;
      }else
        return super.onOptionsItemSelected(item);
    }
}
