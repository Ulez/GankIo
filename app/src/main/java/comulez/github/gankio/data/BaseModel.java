package comulez.github.gankio.data;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.NotNull;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Unique;

import java.io.Serializable;

/**
 * Created by Ulez on 2016/8/16.
 * Email：lcy1532110757@gmail.com
 */
public class BaseModel implements Serializable{
    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    @Column("_id")
    public long id;
    @NotNull
    @Unique
    @Column("objectId")
    public String objectId;
}
