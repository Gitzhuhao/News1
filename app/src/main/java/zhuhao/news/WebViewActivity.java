package zhuhao.news;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview1)
    WebView mWebview1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        String url = getIntent().getStringExtra("url");
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
        });
    }

    //如果希望浏览的网页后退而不是退出浏览器，需要WebView覆盖URL加载，让它自动生成历史访问记录，
    // 那样就可以通过前进或后退访问已访问过的站点。
    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebview1.canGoBack()) {
                mWebview1.goBack();//返回上一页面
                return true;
            } else {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    //判断页面加载过程
    //    mWebview1.setWebChromeClient(new WebChromeClient() {
    //        @Override
    //        public void onProgressChanged(WebView view, int newProgress) {
    //            // TODO Auto-generated method stub
    //            if (newProgress == 100) {
    //                // 网页加载完成
    //
    //            } else {
    //                // 加载中
    //
    //            }
    //
    //        }
    //    });
    //   // 优先使用缓存
    //
    //    mWebview1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    //  //  不使用缓存：
    //
    //    mWebview1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

}