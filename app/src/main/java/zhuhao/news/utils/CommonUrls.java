package zhuhao.news.utils;

/**
 * Created by Administrator on 2016/9/8 0008.
 * 封装获取各种请求链接的工具类：
 */
public class CommonUrls {
    private String BASE_URL = "http://c.m.163.com/";
    private static CommonUrls commonUrls;

    /**
     * 得到本类唯一单例对象 简单的单例模式
     */
    public static CommonUrls getCommonUrls() {
        if (commonUrls == null) {
            commonUrls = new CommonUrls();
        }
        return commonUrls;
    }

    /**
     * 得到新闻列表的url，需要传入当前的分类tid
     */
    public String getListUrl(String tid) {
        return BASE_URL + "nc/article/list/" + tid + "/0-20.html";
    }

    /**
     * 得到新闻分类列表
     */
    public String getNewsType() {
        return BASE_URL + "nc/topicset/android/subscribe/manage/listspecial.html";
    }

    /**
     * 得到网页完整的json结果，需要提供docid，并且手动解析到webview
     */
    public String getFullUrl(String docid) {
        return BASE_URL + "nc/article/" + docid + "/full.html";
    }
    /**
     * 得到热门评论列表，需要新闻的postid/docid
     */
    public String getHotCommentUrl(String docid) {
        return "http://comment.news.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/" + docid + "/comments/hotTopList?";
    }

    /**
     * 得到最新评论列表：同上
     */
    public String getNewCommentUrl(String docid) {
        return "http://comment.news.163.com/api/v1/products/a2869674571f77b5a0867c3d71db5856/threads/" + docid + "/comments/newList?";
    }
}