package comulez.github.gankio.util;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import comulez.github.gankio.MyApplication;

/**
 * Created by Ulez on 2016/9/2.
 * Email：lcy1532110757@gmail.com
 */
public class Tutil {
    public static void t(String message) {
        Toast.makeText(MyApplication.getsContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void l(String tag, String s) {
        if (TextUtils.isEmpty(tag))
            tag = "default";
        Log.e(tag, s);
    }

    public static void l(String s) {
        l(null,s);
    }
}
