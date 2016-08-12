package comulez.github.gankio.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import comulez.github.gankio.R;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
