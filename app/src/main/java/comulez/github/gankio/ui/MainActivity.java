package comulez.github.gankio.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import comulez.github.gankio.R;
import comulez.github.gankio.ui.base.SwipeRefreshBaseActivity;

/**
 * Created by Ulez on 2016/8/12.
 * Email：lcy1532110757@gmail.com
 */
public class MainActivity extends SwipeRefreshBaseActivity {
   RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
