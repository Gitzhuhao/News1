package zhuhao.news.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import zhuhao.news.view.LoadingPage;


public abstract class BaseFragment extends Fragment {
    protected View rootView;
    private LoadingPage loadingPage;

    public BaseFragment() {
    }

    public BaseFragment(View rootView) {
        this.rootView = rootView;
    }

    protected OnFragmentInteractionListener mListener;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int viewId, Bundle bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {

            loadingPage = new LoadingPage(getContext()) {
                @Override
                protected void parseData(String result) {
                    parseRealData(result);
                }

                @Override
                protected String getUrl() {
                    return getRealURL();
                }

                @Override
                protected void bindView(View successView) {
                    ButterKnife.bind(BaseFragment.this, successView);

                    initData();
                }

                @Override
                protected int getSuccessLayout() {
                    return getRealLayout();
                }
            };
            rootView = loadingPage;
        }

        return rootView;
    }

    public void showSuccessPage(String url) {
        loadingPage.setUrl(url);
        loadingPage.startNetWork();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            //            Toast.makeText(BaseFragment.this.getContext(), "移除rootView", Toast.LENGTH_SHORT).show();
            parent.removeView(rootView);
        }
    }

    public void onButtonPressed(int viewId, Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(viewId, bundle);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    //初始化界面
    protected abstract void initData();

    protected abstract String getRealURL();

    protected abstract void parseRealData(String result);

    protected abstract int getRealLayout();
}