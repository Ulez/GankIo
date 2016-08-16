package comulez.github.gankio;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lcy on 2016/8/16.
 */
public class GankRetrofit {

    private static GankRetrofit mInstance;
    private final long DEFAULT_TIMEOUT = 10;
    private final String BASE_URL = "http://gank.io/api";
    private GankApi mGankService;
    private Retrofit retrofit;

    private GankRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mGankService = retrofit.create(GankApi.class);
    }

    public static GankRetrofit getmInstance() {
        if (mInstance == null) {
            synchronized (GankRetrofit.class) {
                if (mInstance == null)
                    mInstance = new GankRetrofit();
            }
        }
        return mInstance;
    }
}
