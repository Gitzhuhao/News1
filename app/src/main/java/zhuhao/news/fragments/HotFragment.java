package zhuhao.news.fragments;

import zhuhao.news.R;
import zhuhao.news.base.BaseFragment;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class HotFragment extends BaseFragment {



    @Override
    protected void initData() {
        showSuccessPage(null);
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
        return R.layout.layout_hot;
    }
}
