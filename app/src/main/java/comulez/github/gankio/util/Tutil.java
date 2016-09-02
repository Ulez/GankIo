package comulez.github.gankio.util;

import android.widget.Toast;

import comulez.github.gankio.App;

/**
 * Created by Ulez on 2016/9/2.
 * Email：lcy1532110757@gmail.com
 */
public class Tutil {
    public static void t(String message) {
        Toast.makeText(App.getsContext(),message,Toast.LENGTH_SHORT).show();
    }
}
