package comulez.github.gankio.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import comulez.github.gankio.R;
import comulez.github.gankio.ui.WebViewActivity;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {
    protected CompositeSubscription mCompositeSubscription;
    protected Context mContext;
    abstract protected int provideContentViewId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
    }

    public void addSubscription(Subscription s) {
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_login:
                loginGitHub();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    protected void loginGitHub() {
        String url = getString(R.string.url_login_github);
        Intent intent = WebViewActivity.newIntent(this, url,
                getString(R.string.action_github_login));
        startActivity(intent);
    }
}
