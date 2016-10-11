package comulez.github.gankio.data;

/**
 * Created by Ulez on 2016/10/11.
 * Email：lcy1532110757@gmail.com
 */
public class RecmendBean {

    /**
     * article_num : 1606篇
     * author : 凸透神瑛
     * id : 19350
     * is_serializing : true
     * last_update : 16-10-11
     * name : 意念成魔
     * pic : htt6465s.jpg
     */

    private String article_num;
    private String author;
    private int id;
    private boolean is_serializing;
    private String last_update;
    private String name;
    private String pic;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_serializing() {
        return is_serializing;
    }

    public void setIs_serializing(boolean is_serializing) {
        this.is_serializing = is_serializing;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
