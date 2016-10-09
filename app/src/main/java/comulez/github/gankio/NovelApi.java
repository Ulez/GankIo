package comulez.github.gankio;

import java.util.List;

import comulez.github.gankio.data.NovelBean;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ulez on 2016/10/9.
 * Email：lcy1532110757@gmail.com
 */
public interface NovelApi {

    //http://api.novelking.cc/api/v1/recommend_categories.json //推荐主页数据
    @GET("/api/v1/recommend_categories.json")
    rx.Observable<NovelBean> getRecommends();

    //http://api.novelking.cc/api/v1/novels/recommend_category_novels.json?recommend_category_id={value}//推荐更多
    @GET("/api/v1/novels/recommend_category_novels.json?recommend_category_id={value}")
    rx.Observable<NovelBean> getRecommend(@Path("value") int value);

    //http://api.novelking.cc/api/v1/novels/new_uploaded_novels.json?&page={page}//最新上架小说
    @GET("/api/v1/novels/new_uploaded_novels.json?&page={page}")
    rx.Observable<NovelBean> getUploaded(@Path("page") int page);

    //http://api.novelking.cc/api/v1/novels/all_novel_update.json?&page={page}//最近更新
    @GET("/api/v1/novels/all_novel_update.json?&page={page}")
    rx.Observable<NovelBean> getUpdates(@Path("page") int page);

    //http://api.novelking.cc/api/v1/novels/this_week_hot.json?page={page}//本周排行
    @GET("/api/v1/novels/this_week_hot.json?page={page}")
    rx.Observable<NovelBean> getWeekRank(@Path("page") int page);

    //http://api.novelking.cc/api/v1/novels/this_month_hot.json?page={page}//本月排行
    @GET("/api/v1/novels/this_month_hot.json?page={page}")
    rx.Observable<List<NovelBean.Novel>> getMonthRank(@Path("page") int page);

    //http://api.novelking.cc/api/v1/novels/hot.json?page={page}//总人气排行
    @GET("/api/v1/novels/hot.json?page={page}")
    rx.Observable<NovelBean> getAllRank(@Path("page") int page);


}
