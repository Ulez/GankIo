package comulez.github.gankio.ui;

import android.os.Bundle;

import comulez.github.gankio.R;
import comulez.github.gankio.ui.base.ToolbarActivity;

public class GankActivity extends ToolbarActivity {

    public static String EXTRA_GANK_DATE="date";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gank;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
