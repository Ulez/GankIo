package comulez.github.gankio.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ulez on 2016/10/12.
 * Email：lcy1532110757@gmail.com
 */
public class ChapterItem implements Parcelable {

    /**
     * id : 7122374
     * num : 1000
     * subject : 意念成魔
     * title : 第一章：少爺回來了？
     */

    private int id;
    private int num;
    private String subject;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.num);
        dest.writeString(this.subject);
        dest.writeString(this.title);
    }

    public ChapterItem() {
    }

    protected ChapterItem(Parcel in) {
        this.id = in.readInt();
        this.num = in.readInt();
        this.subject = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<ChapterItem> CREATOR = new Parcelable.Creator<ChapterItem>() {
        @Override
        public ChapterItem createFromParcel(Parcel source) {
            return new ChapterItem(source);
        }

        @Override
        public ChapterItem[] newArray(int size) {
            return new ChapterItem[size];
        }
    };
}
