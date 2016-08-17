package comulez.github.gankio;

import comulez.github.gankio.data.GirlData;
import comulez.github.gankio.data.VedioData;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by lcy on 2016/8/16.
 */
public interface GankApi {
    @GET("/api/data/福利/10" + "/{page}")
    rx.Observable<GirlData> getGirlData(@Path("page") int page);

    @GET("/api/data/休息视频/10" + "/{page}")
    Observable<VedioData> getVedioData(@Path("page") int page);

}
