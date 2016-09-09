package zhuhao.news.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhuhao.news.R;
import zhuhao.news.entity.NewsBody;

public class Main2Activity extends AppCompatActivity {

    @BindView(R.id.webview1)
    WebView mWebView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        mWebView1.getSettings().setJavaScriptEnabled(true);
        mWebView1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset();
            }
        });
        final String docid = "C0A43AQK0514973B";
        String url = "http://c.m.163.com/nc/article/" + docid + "/full.html";
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    String str = new JSONObject(result).getString(docid);
                    NewsBody newsBody = new Gson().fromJson(str, NewsBody.class);
                    String before = "<img src=\"";
                    String after = "\"/> </img>";
                    for (NewsBody.Img img : newsBody.img) {
                        newsBody.body = newsBody.body.replace(img.ref, before + img.src + after);
                    }
                    //                    mWebView1.loadData(newsBody.body, "text/html", "utf-8");
                    mWebView1.loadDataWithBaseURL(null, newsBody.body, "text/html", "utf-8", null);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        //
    }

    private void imgReset() {
        mWebView1.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%';   " +
                "}" +
                "})()");
    }
}