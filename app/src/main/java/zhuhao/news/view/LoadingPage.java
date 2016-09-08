package zhuhao.news.view;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import zhuhao.news.R;
import zhuhao.news.app.GlobalApplication;

/**
 * Created by admin1 on 2016/9/5.
 */
public abstract class LoadingPage extends FrameLayout {

    public static final int STAT_LOADING = 0;
    public static final int STAT_ERROR = 1;
    public static final int STAT_EMPTY = 2;
    public static final int STAT_SUCCESS = 3;

    private int currentState;//当前状态

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;

    public LoadingPage(Context context) {
        super(context);
        initViews();
    }

    private void initViews() {
        if (loadingView == null) {
            loadingView = View.inflate(getContext(), R.layout.layout_loading, null);
            addView(loadingView);

        }

        if (errorView == null) {
            errorView = View.inflate(getContext(), R.layout.layout_error, null);
            addView(errorView);

        }

        if (emptyView == null) {
            emptyView = View.inflate(getContext(), R.layout.layout_empty, null);
            addView(emptyView);

        }
        showPage();

    }

    public void startNetWork() {

        String url = getUrl();
        if (url == null) {
            currentState = STAT_SUCCESS;
            showPage();
        } else {
            x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if (TextUtils.isEmpty(result)) {
                        currentState = STAT_EMPTY;
                        Toast.makeText(getContext(), "空", Toast.LENGTH_SHORT).show();
                    } else {
                        currentState = STAT_SUCCESS;
                        parseData(result);
                        //                        Toast.makeText(getContext(), "数据解析完毕", Toast.LENGTH_SHORT).show();

                    }
                    showPage();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    currentState = STAT_ERROR;
                    showPage();
                }

                @Override
                public void onCancelled(CancelledException cex) {
                }

                @Override
                public void onFinished() {
                }
            });
        }
    }

    private void showPage() {

        GlobalApplication.handler.post(new Runnable() {
            @Override
            public void run() {
                loadingView.setVisibility(currentState == STAT_LOADING ? View.VISIBLE : View.INVISIBLE);
                errorView.setVisibility(currentState == STAT_ERROR ? View.VISIBLE : View.INVISIBLE);
                emptyView.setVisibility(currentState == STAT_EMPTY ? View.VISIBLE : View.INVISIBLE);
                if (successView == null) {
                    successView = View.inflate(getContext(), getSuccessLayout(), null);
                    addView(successView);
                    //碎片中绑定id
                    bindView(successView);
                    currentState = STAT_SUCCESS;
                }
                successView.setVisibility(currentState == STAT_SUCCESS ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    protected abstract void parseData(String result);

    protected abstract String getUrl();

    protected abstract void bindView(View successView);

    protected abstract int getSuccessLayout();
}

