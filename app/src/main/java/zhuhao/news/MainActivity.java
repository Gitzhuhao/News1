package zhuhao.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhuhao.news.entity.NetEaseType;
import zhuhao.news.utils.CommonUrls;
import zhuhao.news.utils.IgnoreTypes;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        intent = new Intent(MainActivity.this, NewsActivity.class);
        Animation animation = AnimationUtils.loadAnimation(this,
                R.anim.set_1);
        ivLogo.startAnimation(animation);
        getList();
    }

    private void getList() {
        final long time = System.currentTimeMillis();
        String url = CommonUrls.getCommonUrls().getNewsType();

        RequestParams entity = new RequestParams(url);

        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NetEaseType netEaseType = gson.fromJson(result, NetEaseType.class);
                //集合的元素必须可序列化
                //用ArrayList<>保存，不能用List
                intent.putExtra("list", netEaseType.gettList());

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                long endTime = System.currentTimeMillis();
                if ((endTime - time) < 1500) {
                    try {
                        Thread.sleep(1500 - (endTime - time));
                    } catch (Exception e) {
                    }
                }
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

    }

    //忽略没有数据的空数据
    private void ignore(NetEaseType netEaseType) {
        List<NetEaseType.TList> tobeDeleted = new ArrayList<>();
        for (int i = 0; i < IgnoreTypes.TYPES.length; i++) {
            for (int j = 0; j < netEaseType.gettList().size(); j++) {
                if (IgnoreTypes.TYPES[i].equals(netEaseType.gettList().get(j).getTname())) {
                    tobeDeleted.add(netEaseType.gettList().get(j));
                }
            }

        }
        netEaseType.gettList().removeAll(tobeDeleted);
    }
}
