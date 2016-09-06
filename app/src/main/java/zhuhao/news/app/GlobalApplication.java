package zhuhao.news.app;

import android.app.Application;
import android.os.Handler;

import org.xutils.x;


/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class GlobalApplication extends Application {

    private static GlobalApplication app;
    public static Handler handler;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        handler = new Handler();
        x.Ext.init(this);
    }


    public static GlobalApplication getApp() {

        return app;
    }

}
