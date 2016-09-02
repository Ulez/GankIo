package comulez.github.gankio;

import comulez.github.gankio.data.Content;
import comulez.github.gankio.data.GankData;
import comulez.github.gankio.data.GirlData;
import comulez.github.gankio.data.VedioData;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lcy on 2016/8/16.
 */
public interface GankApi {
    //http://gank.io/api/data/福利/10/1
    @GET("/api/data/福利/10" + "/{page}")
    rx.Observable<GirlData> getGirlData(@Path("page") int page);

    //http://gank.io/api/data/休息视频/10/1
    @GET("/api/data/休息视频/10" + "/{page}")
    rx.Observable<VedioData> getVedioData(@Path("page") int page);

    //http://gank.io/api/day/2016/8/16
    @GET("/api/day/{year}/{month}/{day}")
    rx.Observable<GankData> getGankData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    //http://gank.io/api/history/content/day/2016/05/11    获取特定日期网站数据:
    @GET("/api/history/content/day/{year}/{month}/{day}")
    rx.Observable<Content> getGankContent(@Path("year") int year, @Path("month") int month, @Path("day") int day);

}
