package comulez.github.gankio.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Ulez on 2016/10/8.
 * Email：lcy1532110757@gmail.com
 */
public class NovelBean {
    private int id;
    private String name;
    private List<Novel> novels;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Novel> getNovels() {
        return novels;
    }

    public void setNovels(List<Novel> novels) {
        this.novels = novels;
    }

    public static class Novel implements Parcelable {
        private int id;
        private String name;
        private String author;
        private String pic;
        private String article_num;
        private String last_update;
        private boolean is_serializing;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getArticle_num() {
            return article_num;
        }

        public void setArticle_num(String article_num) {
            this.article_num = article_num;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }

        public boolean isIs_serializing() {
            return is_serializing;
        }

        public void setIs_serializing(boolean is_serializing) {
            this.is_serializing = is_serializing;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.author);
            dest.writeString(this.pic);
            dest.writeString(this.article_num);
            dest.writeString(this.last_update);
            dest.writeByte(is_serializing ? (byte) 1 : (byte) 0);
        }

        public Novel() {
        }

        protected Novel(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.author = in.readString();
            this.pic = in.readString();
            this.article_num = in.readString();
            this.last_update = in.readString();
            this.is_serializing = in.readByte() != 0;
        }

        public static final Parcelable.Creator<Novel> CREATOR = new Parcelable.Creator<Novel>() {
            @Override
            public Novel createFromParcel(Parcel source) {
                return new Novel(source);
            }

            @Override
            public Novel[] newArray(int size) {
                return new Novel[size];
            }
        };
    }
}
