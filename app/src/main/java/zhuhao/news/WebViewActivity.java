package zhuhao.news;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhuhao.news.entity.NewsBody;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview1)
    WebView mWebview1;
    @BindView(R.id.iv_left)
    ImageView mIvLeft;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_right)
    ImageView mIvRight;
    //字体颜色设为白色, “p”标签内的字体颜色  “*”定义了字体大小以及行高；
    public final static String CSS_STYLE1 = "<style>* {font-size:16px;line-height:20px;}p {color:#FFFFFF;}</style>";
    //比较全的：
    //上面全局样式：“*”定义了字体大小以及行高；“p”标签内的字体颜色；“a”标签内的字体颜色；“img”标签的图片最大宽度；“pre”为代码样式；
    public final static String CSS_STYLE = "<style>* {font-size:14px;line-height:18px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;}</style>";

    //上面全局样式：“*”定义了字体大小以及行高；“p”标签内的字体颜色；“a”标签内的字体颜色；“img”标签的图片最大宽度；“pre”为代码样式；
    private PopupWindow pop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        final String url = getIntent().getStringExtra("url");
        final String docid = getIntent().getStringExtra("docid");
        mWebview1.getSettings().setJavaScriptEnabled(true);
        mWebview1.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //WebView加载web资源
        mWebview1.loadUrl(url);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebview1.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
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
                    //data是要显示的内容
                    mWebview1.loadDataWithBaseURL(null, CSS_STYLE + newsBody.body, "text/html", "utf-8", null);
                    //                    mWebview1.loadDataWithBaseURL(null, newsBody.body, "text/html", "utf-8", null);
                    setTitleText(newsBody.dkeys);
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

    //设置web标题
    private void setTitleText(String title) {
        if (title.toCharArray().length <= 18) {
            mTvTitle.setText(title);
        }
        String str = title.substring(0, 15);
        mTvTitle.setText(str + "...");
    }


    private void imgReset() {
        mWebview1.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%';   " +
                "}" +
                "})()");
    }


    //如果希望浏览的网页后退而不是退出浏览器，需要WebView覆盖URL加载，让它自动生成历史访问记录，
    // 那样就可以通过前进或后退访问已访问过的站点。
    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview1.canGoBack()) {
            mWebview1.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //设置web页面的点击效果
    @OnClick({R.id.iv_left, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                Toast.makeText(this, "返回activity", Toast.LENGTH_SHORT).show();
                break;

            case R.id.iv_right:
                Toast.makeText(this, "收藏，分享", Toast.LENGTH_SHORT).show();
                View view1 = View.inflate(this, R.layout.layout_popupwindow, null);
                if (pop == null) {
                    pop = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    view1.findViewById(R.id.btn_comment).setOnClickListener(popListener);
                    view1.findViewById(R.id.btn_favor).setOnClickListener(popListener);
                    view1.findViewById(R.id.btn_share).setOnClickListener(popListener);
                    pop.setTouchable(true);
                    pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                View view2 = View.inflate(this, R.layout.layout_popupwindow, null);
                pop.showAsDropDown(view2);
        }
    }

    private View.OnClickListener popListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_comment:
                    Toast.makeText(WebViewActivity.this, "评论", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_favor:
                    Toast.makeText(WebViewActivity.this, "收藏", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_share:
                    shareNews();
                    break;
            }
            pop.dismiss();
        }
    };

    //创建分享功能
    private void shareNews() {
            /*
             * 纯文本  intent.setType("text/plain");
             * 图片分享 it.setType("image/png"); 　
             * 添加图片 File f = new File(Environment.getExternalStorageDirectory()+"/name.png");
             *
             * Uri uri = Uri.fromFile(f); intent.putExtra(Intent.EXTRA_STREAM,uri); 　
             */
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }
}

