package zhuhao.news.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import zhuhao.news.entity.NetEaseType;
import zhuhao.news.fragments.NewsListFragment;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class NewsTypeAdapter extends FragmentPagerAdapter {

    private List<NetEaseType.TList> titleList;


    //标题集合
    public NewsTypeAdapter(FragmentManager fm, List<NetEaseType.TList> titleList) {
        super(fm);
        this.titleList = titleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titleList == null ? "未命名" : titleList.get(position).getTname();
    }

    @Override
    public Fragment getItem(int position) {
        //tid:传进framgment
        Bundle bundle = new Bundle();
        bundle.putString("tid", titleList.get(position).getTid());
        bundle.putString("tname", titleList.get(position).getTname());
        return NewsListFragment.getInstance(bundle);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }
}
