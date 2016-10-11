package comulez.github.gankio.data;

import java.util.List;

/**
 * Created by Ulez on 2016/10/8.
 * Email：lcy1532110757@gmail.com
 */
public class NovelBean {
    private int type;
    private int total;
    private boolean success;
    private String errorlog;
    private int sortType;
    private String keyWord;
    private String check_word;
    private int allTotal;
    private List<ItemsEntity> items;
    private List<Book> charge_book_items;
    private List<Book> all_book_items;
    private List<Book> guess_like_items;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorlog() {
        return errorlog;
    }

    public void setErrorlog(String errorlog) {
        this.errorlog = errorlog;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getCheck_word() {
        return check_word;
    }

    public void setCheck_word(String check_word) {
        this.check_word = check_word;
    }

    public int getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(int allTotal) {
        this.allTotal = allTotal;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public List<Book> getCharge_book_items() {
        return charge_book_items;
    }

    public void setCharge_book_items(List<Book> charge_book_items) {
        this.charge_book_items = charge_book_items;
    }

    public List<Book> getAll_book_items() {
        return all_book_items;
    }

    public void setAll_book_items(List<Book> all_book_items) {
        this.all_book_items = all_book_items;
    }

    public List<Book> getGuess_like_items() {
        return guess_like_items;
    }

    public void setGuess_like_items(List<Book> guess_like_items) {
        this.guess_like_items = guess_like_items;
    }

    public static class ItemsEntity {
        private String name;
        private String classes;
        private String desc;
        private String status;
        private int gid;
        private String category;
        private int nid;
        private String author;
        private String site;
        private int chargeGid;
        private boolean tempFree;
        private int activityType;
        private String cpId;
        private String sourceId;
        private String imgUrl;
        private String lastChapterName;
        private int chapterCount;
        private long lastTime;
        private int subscribeCount;
        private int siteCount;
        private int charge;
        private int topicGroupId;
        private int topicNum;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getChargeGid() {
            return chargeGid;
        }

        public void setChargeGid(int chargeGid) {
            this.chargeGid = chargeGid;
        }

        public boolean isTempFree() {
            return tempFree;
        }

        public void setTempFree(boolean tempFree) {
            this.tempFree = tempFree;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getCpId() {
            return cpId;
        }

        public void setCpId(String cpId) {
            this.cpId = cpId;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLastChapterName() {
            return lastChapterName;
        }

        public void setLastChapterName(String lastChapterName) {
            this.lastChapterName = lastChapterName;
        }

        public int getChapterCount() {
            return chapterCount;
        }

        public void setChapterCount(int chapterCount) {
            this.chapterCount = chapterCount;
        }

        public long getLastTime() {
            return lastTime;
        }

        public void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }

        public int getSubscribeCount() {
            return subscribeCount;
        }

        public void setSubscribeCount(int subscribeCount) {
            this.subscribeCount = subscribeCount;
        }

        public int getSiteCount() {
            return siteCount;
        }

        public void setSiteCount(int siteCount) {
            this.siteCount = siteCount;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getTopicGroupId() {
            return topicGroupId;
        }

        public void setTopicGroupId(int topicGroupId) {
            this.topicGroupId = topicGroupId;
        }

        public int getTopicNum() {
            return topicNum;
        }

        public void setTopicNum(int topicNum) {
            this.topicNum = topicNum;
        }
    }

    public static class Book {
        private String name;
        private String classes;
        private String desc;
        private String status;
        private int gid;
        private String category;
        private int nid;
        private String author;
        private String site;
        private int chargeGid;
        private boolean tempFree;
        private int activityType;
        private String cpId;
        private String sourceId;
        private String imgUrl;
        private String lastChapterName;
        private int chapterCount;
        private long lastTime;
        private int subscribeCount;
        private int siteCount;
        private int charge;
        private int topicGroupId;
        private int topicNum;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getGid() {
            return gid;
        }

        public void setGid(int gid) {
            this.gid = gid;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getChargeGid() {
            return chargeGid;
        }

        public void setChargeGid(int chargeGid) {
            this.chargeGid = chargeGid;
        }

        public boolean isTempFree() {
            return tempFree;
        }

        public void setTempFree(boolean tempFree) {
            this.tempFree = tempFree;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getCpId() {
            return cpId;
        }

        public void setCpId(String cpId) {
            this.cpId = cpId;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLastChapterName() {
            return lastChapterName;
        }

        public void setLastChapterName(String lastChapterName) {
            this.lastChapterName = lastChapterName;
        }

        public int getChapterCount() {
            return chapterCount;
        }

        public void setChapterCount(int chapterCount) {
            this.chapterCount = chapterCount;
        }

        public long getLastTime() {
            return lastTime;
        }

        public void setLastTime(long lastTime) {
            this.lastTime = lastTime;
        }

        public int getSubscribeCount() {
            return subscribeCount;
        }

        public void setSubscribeCount(int subscribeCount) {
            this.subscribeCount = subscribeCount;
        }

        public int getSiteCount() {
            return siteCount;
        }

        public void setSiteCount(int siteCount) {
            this.siteCount = siteCount;
        }

        public int getCharge() {
            return charge;
        }

        public void setCharge(int charge) {
            this.charge = charge;
        }

        public int getTopicGroupId() {
            return topicGroupId;
        }

        public void setTopicGroupId(int topicGroupId) {
            this.topicGroupId = topicGroupId;
        }

        public int getTopicNum() {
            return topicNum;
        }

        public void setTopicNum(int topicNum) {
            this.topicNum = topicNum;
        }
    }
}
