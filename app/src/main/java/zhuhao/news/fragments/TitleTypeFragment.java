package zhuhao.news.fragments;


import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import zhuhao.news.R;
import zhuhao.news.base.BaseFragment;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class TitleTypeFragment extends BaseFragment {


    @BindView(R.id.textView2)
    TextView mTextView;
    @BindView(R.id.gridView1)
    GridView mGridView1;
    @BindView(R.id.ll_visible)
    LinearLayout mLlVisible;
    @BindView(R.id.iv_type2)
    ImageView mIvType2;


    @Override
    protected void initData() {
        showSuccessPage(null);
        mLlVisible.setVisibility(View.VISIBLE);

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
        return R.layout.layout_title_type;
    }

    @OnClick(R.id.iv_type2)
    public void onClick() {
        Toast.makeText(getContext(), "返回NewsFragment", Toast.LENGTH_SHORT).show();

    }
}
