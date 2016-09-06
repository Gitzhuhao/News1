package zhuhao.news.entity;

import java.util.List;

/**
 * Created by admin1 on 2016/9/3.
 */
public class NewsEase {
    public String title;
    public String source;
    public String tname;
    public int hasHead;
    public String imgsrc;
    public String ptime;
    public String ltitle;
    public String postid;
    public int replyCount;
    public String url;
    public List<Image> imgextra;
    public List<AD> ads;

    public NewsEase(String title, String source, String tname, int hasHead, String imgsrc, String ptime, String ltitle, String postid, int replyCount, String url, List<Image> imgextra, List<AD> ads) {
        this.title = title;
        this.source = source;
        this.tname = tname;
        this.hasHead = hasHead;
        this.imgsrc = imgsrc;
        this.ptime = ptime;
        this.ltitle = ltitle;
        this.postid = postid;
        this.replyCount = replyCount;
        this.url = url;
        this.imgextra = imgextra;
        this.ads = ads;
    }

    public static class AD{
        public AD(String title, String tag, String imgsrc, String subtitle) {
            this.title = title;
            this.tag = tag;
            this.imgsrc = imgsrc;
            this.subtitle = subtitle;
        }

        public String title;
        public String tag;
        public String imgsrc;
        public String subtitle;
    }

    public static class Image {
        public Image(String imgsrc) {
            this.imgsrc = imgsrc;
        }

        public String imgsrc;
    }
}
