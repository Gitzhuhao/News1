package zhuhao.news.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import zhuhao.news.R;
import zhuhao.news.entity.NewsEase;
import zhuhao.news.utils.XImageUtil;

/**
 * Created by admin1 on 2016/9/3.
 */


public class NewsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int RECYCLER_ITEM = 0;

    private List<NewsEase> list;

    public NewsRecycleAdapter(List<NewsEase> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public NewsRecycleAdapter(Context context) {
        this.context = context;
    }


    private Context context;
    public static final int VIEW_TYPE_VP = 0;
    public static final int VIEW_TYPE_ONE_HEAD = 1;
    public static final int VIEW_TYPE_ONE_BIG = 2;
    public static final int VIEW_TYPE_THREE_SMALL = 3;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_ONE_BIG:
                View v = View.inflate(context, R.layout.layout_item_one_img, null);
                return new OneImageHolder(v);
            case VIEW_TYPE_VP:
                v = View.inflate(context, R.layout.layout_item_vp, null);
                return new ViewPagerHolder(v);
            case VIEW_TYPE_THREE_SMALL:
                v = View.inflate(context, R.layout.layout_item_three_img, null);
                return new ThreeImageHolder(v);
            case VIEW_TYPE_ONE_HEAD:
                v = View.inflate(context, R.layout.layout_item_one_head, null);
                return new HeadPicHolder(v);
        }
        return null;
    }

    public void addData(List<NewsEase> l) {
        if (l == null)
            return;
        if (list == null) {
            setList(l);
            return;
        }
        list.addAll(l);
    }

    public void setList(List<NewsEase> list) {
        this.list = list;
    }
    public List<NewsEase> getList() {
        return list;
    }

    @Override
    public int getItemViewType(int position) {
        NewsEase newsEase = list.get(position);
        if (position == 0) {
            if (newsEase.ads == null && newsEase.title != null) {
                return VIEW_TYPE_ONE_HEAD;
            } else {
                return VIEW_TYPE_VP;
            }
        } else {
            if (newsEase.imgextra == null) {
                return VIEW_TYPE_ONE_BIG;
            } else {
                return VIEW_TYPE_THREE_SMALL;
            }
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        NewsEase east = list.get(position);
        String title = null;
        String t1 = east.title;
        String t2 = east.ltitle;
        t1 = t1 == null ? "" : t1;
        t2 = t2 == null ? "" : t2;
        title = t1.length() > t2.length() ? t1 : t2;

        if (holder instanceof OneImageHolder) {
            OneImageHolder h = (OneImageHolder) holder;
            XImageUtil.display(h.imgLeft, east.imgsrc);
            h.tvTitle.setText(title);
            h.tvFollow.setText(east.replyCount + "跟帖");

        } else if (holder instanceof HeadPicHolder) {
            HeadPicHolder h = (HeadPicHolder) holder;
            XImageUtil.display(h.imgHead, east.imgsrc);

            h.tvTitle.setText(title);


        } else if (holder instanceof ThreeImageHolder) {
            ThreeImageHolder h = (ThreeImageHolder) holder;
            XImageUtil.display(h.img1, east.imgsrc);
            XImageUtil.display(h.img2, east.imgextra.get(0).imgsrc);
            XImageUtil.display(h.img3, east.imgextra.get(1).imgsrc);
            h.tvTitle.setText(title);
            h.tvFollow.setText(east.replyCount + "跟帖");

        } else if (holder instanceof ViewPagerHolder) {
            //            ViewPagerHolder h = (ViewPagerHolder) holder;
            //            AdAdapter adapter = new AdAdapter();
            //            h.vpager.setAdapter(adapter);
            //            h.vpager.setCurrentItem(Integer.MAX_VALUE / 2);

        }

        //点击事件：网址：
        String url = list.get(position).url;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickListener.itemClick(RECYCLER_ITEM, position);
            }
        });


    }

    //使用接口把当前位置position传入NewslistFragment

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void itemClick(int viewId, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    //一张图，右边是文字的布局
    public static class OneImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_left)
        ImageView imgLeft;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_follow)
        TextView tvFollow;

        public OneImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //三张图并排，上边是标题下边时间的布局
    public static class ThreeImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.tv_follow)
        TextView tvFollow;

        public ThreeImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    //第一行viewpager的布局
    public static class ViewPagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vpager)
        ViewPager vpager;

        public ViewPagerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    //第一行有一张大图的布局
    public static class HeadPicHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_head)
        ImageView imgHead;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public HeadPicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}


