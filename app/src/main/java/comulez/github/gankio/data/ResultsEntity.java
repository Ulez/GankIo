package comulez.github.gankio.data;

/**
 * Created by Ulez on 2016/9/2.
 * Email：lcy1532110757@gmail.com
 */
public class ResultsEntity extends BaseModel {
    private String content;
    private String publishedAt;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
