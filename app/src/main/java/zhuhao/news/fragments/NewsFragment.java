package zhuhao.news.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import zhuhao.news.R;
import zhuhao.news.adapter.NewsTypeAdapter;
import zhuhao.news.base.BaseFragment;
import zhuhao.news.entity.NetEaseType;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class NewsFragment extends BaseFragment {


    @BindView(R.id.indicator)
    TabPageIndicator indicator;
    @BindView(R.id.pager)
    ViewPager pager;
    NewsTypeAdapter adapter;
    @BindView(R.id.iv_type)
    ImageView mIvType;

    //新闻首页
    //viewpager
    //分了很多分类，要从网络获取，
    //viewpager indicator
    //新闻列表，recyclerview,多布局，上拉下拉
    @Override
    protected void initData() {
        //先给viewpager设置适配器
        //适配器里要有标题
        //        tvTitle.setText("首页");
        //  getList();
        showSuccessPage(null);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<NetEaseType.TList> list = (ArrayList<NetEaseType.TList>) bundle.getSerializable("list");
            adapter = new NewsTypeAdapter(getFragmentManager(), list);
            pager.setAdapter(adapter);
            indicator.setViewPager(pager);
            indicator.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected String getRealURL() {
        return null;
    }

    @Override
    protected void parseRealData(String result) {

    }

    @Override
    protected int getRealLayout() {
        return R.layout.layout_news;
    }

    //添加了一个静态的工厂方法，保存传递过来的参数
    public static NewsFragment getInstance(Bundle bundle) {
        NewsFragment nf = new NewsFragment();
        if (bundle != null) {
            nf.setArguments(bundle);
        }
        return nf;
    }

    //设置对标题栏目的转换
    @OnClick(R.id.iv_type)
    public void onClick() {
        Toast.makeText(getContext(), "列表显示分类", Toast.LENGTH_SHORT).show();




    }
}
