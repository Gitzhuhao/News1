package zhuhao.news.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import zhuhao.news.app.GlobalApplication;

/**
 * Created by admin1 on 2016/9/5.
 */
public class UIUtils {

    //--------------------本类对象获取方法---------------------
    public static Context getContext() {

        return GlobalApplication.getApp();
    }

    //-------------------获取资源方法---------------------------
    public static Resources getResource() {

        return getContext().getResources();
    }

    //-------------------获取颜色ID方法---------------------------
    public static int getColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getResource().getColor(colorId, null);


        } else {
            return getResource().getColor(colorId);
        }

    }

    //-------------------获取资源ID下字符串的方法---------------------------
    public static String getString(int StringId) {

        return getResource().getString(StringId);
    }
}
