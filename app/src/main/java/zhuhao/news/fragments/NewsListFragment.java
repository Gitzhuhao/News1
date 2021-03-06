package zhuhao.news.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import zhuhao.news.R;
import zhuhao.news.adapter.NewsRecycleAdapter;
import zhuhao.news.base.BaseFragment;
import zhuhao.news.entity.NewsEase;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class NewsListFragment extends BaseFragment {
    private String tid;
    @BindView(R.id.recyclerView1)
    RecyclerView recyclerView1;
    NewsRecycleAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean isPrepared;//加载是否准备好
    private boolean isVisible;//是否可见
    private boolean isCompleted;//是否已经加载完成。

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {//frahment从不可见到完全可见的时候，会调用该方法
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {

    }

    //懒加载的方法,在这个方法里面我们为Fragment的各个组件去添加数据

    protected void onVisible() {
        lazyLoad();
    }

    private void lazyLoad() {
        if (!isPrepared || !isVisible || isCompleted)
            return;
        showSuccessPage();
    }

    //懒加载的方法,在这个方法里面我们为Fragment的各个组件去添加数据


    @Override
    protected void initData() {
        isPrepared = true;
        layoutManager = new LinearLayoutManager(getContext());
        Bundle bundle = getArguments();
        if (bundle != null) {
            tid = bundle.getString("tid");
            //组合成url显示
            lazyLoad();
        }
    }

    @Override
    protected String getRealURL() {
        String url = "http://c.m.163.com/nc/article/list/" + tid + "/0-20.html";
        Toast.makeText(NewsListFragment.this.getContext(), "tid:" + tid, Toast.LENGTH_SHORT).show();
        return url;
    }

    @Override
    protected void parseRealData(String result) {
        String tname = getArguments().getString("tname");
        //加载数据完成
        List<NewsEase> newslist = new ArrayList<NewsEase>();
        try {
            JSONArray jsonArray = new JSONObject(result).getJSONArray(tid);
            for (int i = 0; i < jsonArray.length(); i++) {
                NewsEase newsEase = new Gson().fromJson(jsonArray.getString(i), NewsEase.class);
                newslist.add(newsEase);
            }

        } catch (JSONException e) {
            Toast.makeText(NewsListFragment.this.getContext(), "吐司失败", Toast.LENGTH_SHORT).show();
        }
        adapter = new NewsRecycleAdapter(newslist, getContext());
        recyclerView1.setAdapter(adapter);
        //                recyclerView1.addItemDecoration(new MyDecoration());
        recyclerView1.setLayoutManager(layoutManager);
        isCompleted = true;
    }

    public static NewsListFragment getInstance(Bundle bundle) {
        NewsListFragment fragment = new NewsListFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected int getRealLayout() {
        return R.layout.layout_newslist;
    }
}