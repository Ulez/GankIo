package comulez.github.gankio.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lcy on 2016/8/14.
 */
public class Once {
    private SharedPreferences sp;
    Context context;

    public Once(Context context) {
        this.context = context;
        sp=context.getSharedPreferences("once",Context.MODE_PRIVATE);
    }
    public void show(String tagKey,OnceCallBack callBack){
        boolean isSecondTime=sp.getBoolean(tagKey,false);
        if (!isSecondTime){
            callBack.onOnce();
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean(tagKey,true);
            editor.apply();
        }
    }
    public void show(int tagKeyResId,OnceCallBack callBack){
        show(context.getString(tagKeyResId),callBack);
    }
    public interface OnceCallBack{
        void onOnce();
    }
}
