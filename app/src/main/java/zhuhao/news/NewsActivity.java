package zhuhao.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhuhao.news.base.BaseFragment;
import zhuhao.news.fragments.FavorFragment;
import zhuhao.news.fragments.HotFragment;
import zhuhao.news.fragments.LoginFragment;
import zhuhao.news.fragments.NewsFragment;

public class NewsActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.main_content)
    FrameLayout mainContent;
    @BindView(R.id.radiogroup1)
    RadioGroup radiogroup1;
    FavorFragment ff;
    HotFragment hf;
    LoginFragment lf;
    NewsFragment nf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        ff = new FavorFragment();
        hf = new HotFragment();
        lf = new LoginFragment();
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
                showFragment(hf);
                break;
            case R.id.radioButton3:
                showFragment(ff);
                break;
            case R.id.radioButton4:
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

    }
}
