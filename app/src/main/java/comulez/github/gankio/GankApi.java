package comulez.github.gankio;

import comulez.github.gankio.data.GankData;
import comulez.github.gankio.data.GirlData;
import comulez.github.gankio.data.VedioData;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lcy on 2016/8/16.
 */
public interface GankApi {
    @GET("/api/data/福利/10" + "/{page}")
    rx.Observable<GirlData> getGirlData(@Path("page") int page);

    @GET("/api/data/休息视频/10" + "/{page}")
    rx.Observable<VedioData> getVedioData(@Path("page") int page);

    //http://gank.io/api/day/2016/8/16
    @GET("/api/day/{year}/{month}/{day}")
    rx.Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

}
