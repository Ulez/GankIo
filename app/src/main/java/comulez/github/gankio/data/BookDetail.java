package comulez.github.gankio.data;

import java.util.List;

/**
 * Created by Ulez on 2016/10/12.
 * Email：lcy1532110757@gmail.com
 */
public class BookDetail {
    private String article_num;
    private String author;
    private int category_id;
    private int crawl_times;
    private String created_at;
    private String description;
    private int id;
    private boolean is_category_hot;
    private boolean is_category_this_week_hot;
    private boolean is_serializing;
    private boolean is_show;
    private String last_update;
    private String link;
    private String name;
    private int num;
    private String pic;
    private String updated_at;

    public String getArticle_num() {
        return article_num;
    }

    public void setArticle_num(String article_num) {
        this.article_num = article_num;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getCrawl_times() {
        return crawl_times;
    }

    public void setCrawl_times(int crawl_times) {
        this.crawl_times = crawl_times;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_category_hot() {
        return is_category_hot;
    }

    public void setIs_category_hot(boolean is_category_hot) {
        this.is_category_hot = is_category_hot;
    }

    public boolean isIs_category_this_week_hot() {
        return is_category_this_week_hot;
    }

    public void setIs_category_this_week_hot(boolean is_category_this_week_hot) {
        this.is_category_this_week_hot = is_category_this_week_hot;
    }

    public boolean isIs_serializing() {
        return is_serializing;
    }

    public void setIs_serializing(boolean is_serializing) {
        this.is_serializing = is_serializing;
    }

    public boolean isIs_show() {
        return is_show;
    }

    public void setIs_show(boolean is_show) {
        this.is_show = is_show;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public List<ChapterItem> chapterItems;

}
