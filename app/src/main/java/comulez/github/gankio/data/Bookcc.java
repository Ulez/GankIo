package comulez.github.gankio.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ulez on 2016/10/11.
 * Email：lcy1532110757@gmail.com
 */
public class Bookcc implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.article_num);
        dest.writeString(this.author);
        dest.writeInt(this.id);
        dest.writeByte(is_serializing ? (byte) 1 : (byte) 0);
        dest.writeString(this.last_update);
        dest.writeString(this.name);
        dest.writeString(this.pic);
    }

    public Bookcc() {
    }

    protected Bookcc(Parcel in) {
        this.article_num = in.readString();
        this.author = in.readString();
        this.id = in.readInt();
        this.is_serializing = in.readByte() != 0;
        this.last_update = in.readString();
        this.name = in.readString();
        this.pic = in.readString();
    }

    public static final Parcelable.Creator<Bookcc> CREATOR = new Parcelable.Creator<Bookcc>() {
        @Override
        public Bookcc createFromParcel(Parcel source) {
            return new Bookcc(source);
        }

        @Override
        public Bookcc[] newArray(int size) {
            return new Bookcc[size];
        }
    };
}
