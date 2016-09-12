package zhuhao.news;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhuhao.news.adapter.NewsRecycleAdapter;
import zhuhao.news.base.BaseFragment;
import zhuhao.news.fragments.FavorFragment;
import zhuhao.news.fragments.HotFragment;
import zhuhao.news.fragments.LoginFragment;
import zhuhao.news.fragments.NewsFragment;
import zhuhao.news.fragments.TitleTypeFragment;

public class NewsActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    FavorFragment ff;
    HotFragment hf;
    TitleTypeFragment tf;
    LoginFragment lf;
    NewsFragment nf;
    @BindView(R.id.iv_type)
    ImageView mIvType;
    @BindView(R.id.iv_title)
    ImageView mIvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        ff = new FavorFragment();
        hf = new HotFragment();
        lf = new LoginFragment();
        tf = new TitleTypeFragment();
        nf = NewsFragment.getInstance(getIntent().getExtras());
        addFragment(nf);
        setListeners();
    }

    private void addFragment(Fragment f) {
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, f).commit();
    }

    private void setListeners() {
        radiogroup1.setOnCheckedChangeListener(this);
    }

    //单选组的选中监听
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.radioButton1:
                showFragment(nf);
                break;
            case R.id.radioButton2:
//                mIvTitle.setVisibility(View.GONE);
                mIvType.setVisibility(View.INVISIBLE);
                showFragment(hf);
                break;
            case R.id.radioButton3:
//                mIvTitle.setVisibility(View.GONE);
                mIvType.setVisibility(View.INVISIBLE);
                showFragment(ff);
                break;
            case R.id.radioButton4:
                mIvTitle.setVisibility(View.GONE);
                mIvType.setVisibility(View.INVISIBLE);
                showFragment(lf);
                break;
        }
    }

    private void showFragment(Fragment f) {

        //显示要显示的这个fragment
        //如果是已经加过的，就直接显示，没加过就自动加上
        //把其他那些已经加过的其他fragment都隐藏
        FragmentManager fm = getSupportFragmentManager();
        List<Fragment> list = fm.getFragments();
        Fragment tempFragment = null;
        if (list != null) {

            for (Fragment fragment : list) {
                if (fragment != f) {
                    //
                    fm.beginTransaction().hide(fragment).commit();
                } else {
                    tempFragment = fragment;
                }
            }
            //里面有，直接显示，没有，先加，再显示
            if (tempFragment == null) {
                addFragment(f);
                tempFragment = f;
            }
            fm.beginTransaction().show(tempFragment).commit();

        }

    }

    @Override
    public void onFragmentInteraction(int viewId, Bundle bundle) {
        switch (viewId) {
            case NewsRecycleAdapter.RECYCLER_ITEM:
                //跳转activity，传值过去
                Intent intent = new Intent(NewsActivity.this, WebViewActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    //设置对标题栏目的转换
    @OnClick(R.id.iv_type)
    public void onClick() {
        Toast.makeText(this, "列表显示分类", Toast.LENGTH_SHORT).show();
        showFragment(tf);
        mIvType.setVisibility(View.INVISIBLE);
    }
}
