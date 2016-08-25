package comulez.github.gankio.data;

import com.litesuits.orm.db.annotation.Column;

import java.util.Date;

/**
 * Created by Ulez on 2016/8/24.
 * Email：lcy1532110757@gmail.com
 */
public class Gank extends BaseModel{
    @Column("url") public String url;
    @Column("type") public String type;
    @Column("desc") public String desc;
    @Column("who") public String who;
    @Column("used") public boolean used;
    @Column("createdAt") public Date createdAt;
    @Column("updatedAt") public Date updatedAt;
    @Column("publishedAt") public Date publishedAt;
}
