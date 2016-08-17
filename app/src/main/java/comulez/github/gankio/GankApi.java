package comulez.github.gankio;

import comulez.github.gankio.data.GirlData;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lcy on 2016/8/16.
 */
public interface GankApi {
    @GET("/api/data/福利/10" + "/{page}")
    rx.Observable<GirlData> getGirlData(@Path("page") int page);

}
