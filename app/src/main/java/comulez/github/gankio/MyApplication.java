package comulez.github.gankio;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Ulez on 2016/8/16.
 * Email：lcy1532110757@gmail.com
 */
public class MyApplication extends Application{
    private static final String DB_NAME = "gank.db";
    public static Context sContext;
//    public static LiteOrm sDb;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext=this;
        CrashReport.initCrashReport(getApplicationContext(), "900056001", false);
//        sDb = LiteOrm.newSingleInstance(this,DB_NAME);
//        if (BuildConfig.DEBUG){
//            sDb.setDebugged(true);
//        }
    }

    public static Context getsContext() {
        return sContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
