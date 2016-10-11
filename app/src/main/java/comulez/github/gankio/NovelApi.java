package comulez.github.gankio;

import java.util.List;

import comulez.github.gankio.data.NovelBean;
import comulez.github.gankio.data.RecmendBean;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

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
    rx.Observable<List<RecmendBean>> getWeekRank(@Path("page") int page);

    //http://api.novelking.cc/api/v1/novels/this_month_hot.json?page={page}//本月排行
    @GET("/api/v1/novels/this_month_hot.json?page={page}")
    Observable<List<RecmendBean>> getMonthRank(@Path("page") int page);

    //http://api.novelking.cc/api/v1/novels/hot.json?page={page}//总人气排行
    @GET("/api/v1/novels/hot.json?page={page}")
    rx.Observable<List<RecmendBean>> getAllRank(@Path("page") int page);
   // http://api.easou.com/api/bookapp/search.m?word=%E7%8E%84%E5%B9%BB&type=2&page_id=1&count=20&sort_type=0&cid=eef_easou_book&version=002&os=android&udid=1D6306147D6D13B28807856B31ED272E&appverion=1033&ch=blp1298_10269_001&statId=j2fSuyqnF1Zh4GY7Q4M2HxNASbH4c
   //    /api/bookapp/search.m?word=%E7%8E%84%E5%B9%BB&type=2&page_id=1&count=20&sort_type=0&cid=eef_easou_book&version=002&os=android&udid=1D6306147D6D13B28807856B31ED272E&appverion=1033&ch=blp1298_10269_001&statId=j2fSuyqnF1Zh4GY7Q4M2HxNASbH4c
   @GET("/api/bookapp/search.m")
   rx.Observable<NovelBean> getYishou(@Query("word") String word,
                                      @Query("type") int type,
                                      @Query("page_id") int page_id,
                                      @Query("count") int count,
                                      @Query("sort_type") int sort_type,
                                      @Query("cid") String cid,
                                      @Query("version") String version,
                                      @Query("os") String os,
                                      @Query("udid") String udid,
                                      @Query("appverion") int appverion,
                                      @Query("ch") String ch,
                                      @Query("statId") String statId);
    //word=%E7%8E%84%E5%B9%BB
    // type=2
    // page_id=1
    // count=20
    // sort_type=0
    // cid=eef_easou_book
    // version=002
    // os=android
    // udid=1D6306147D6D13B28807856B31ED272E
    // appverion=1033
    // ch=blp1298_10269_001
    // statId=j2fSuyqnF1Zh4GY7Q4M2HxNASbH4c"
}
