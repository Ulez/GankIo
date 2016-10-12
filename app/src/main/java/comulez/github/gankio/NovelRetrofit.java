package comulez.github.gankio;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ulez on 2016/10/9.
 * Email：lcy1532110757@gmail.com
 */
public class NovelRetrofit {
    private static NovelRetrofit mInstance;
    private final long DEFAULT_TIMEOUT = 10;
    private final String BASE_URL = "http://api.novelking.cc";
    private NovelApi mNovelService;
    private Retrofit retrofit;

    private NovelRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        mNovelService = retrofit.create(NovelApi.class);
    }

    public static NovelRetrofit getmInstance() {
        if (mInstance == null) {
            synchronized (NovelRetrofit.class) {
                if (mInstance == null)
                    mInstance = new NovelRetrofit();
            }
        }
        return mInstance;
    }

    public NovelApi getmNovelService() {
        return mNovelService;
    }
}
