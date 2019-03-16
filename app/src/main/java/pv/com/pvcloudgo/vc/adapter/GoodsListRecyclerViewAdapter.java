package pv.com.pvcloudgo.vc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.okhttp.Response;

import java.util.List;
import java.util.Map;

import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.http.OkHttpHelper;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.bean.LoginBean;
import pv.com.pvcloudgo.model.bean.Param;
import pv.com.pvcloudgo.model.bean.goodsFragmentBean;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.base.BaseActivity;

/**
 * Created by ZhengJiao on 2017/4/27.
 */
public class GoodsListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<goodsFragmentBean.good> goodlist;
    private final Context context;
    private int type = 0;//0:LinearViewHolder  1:GridViewHolder

    public GoodsListRecyclerViewAdapter(Context context, List<goodsFragmentBean.good> goodlist) {
        this.context = context;
        this.goodlist = goodlist;

    }


    public void setGoodlist(List<goodsFragmentBean.good> goodlist){
        this.goodlist.addAll(goodlist);
    }

    //点击切换布局的时候通过这个方法设置type
    public void setType(int type) {
        this.type = type;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseView;
        if (viewType == 0) {
            baseView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_goods_list, parent, false);
            LinearViewHolder linearViewHolder = new LinearViewHolder(baseView);
            return linearViewHolder;
        } else {
            View footer = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_goods_foot, parent, false);
//            GridViewHolder gridViewHolder = new GridViewHolder(baseView);
//            return gridViewHolder;
            RecyclerView.ViewHolder viewHolder = new FooterHolder(footer);
            return viewHolder;

        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof LinearViewHolder) {
//            ToastUtils.show("" + position);

            LinearViewHolder linearViewHolder = (LinearViewHolder) holder;
            linearViewHolder.tvName.setText(goodlist.get(0).getName());
            linearViewHolder.tvPriceYZ.setText("拼购价：¥ " + goodlist.get(0).getSpellPrice());
            linearViewHolder.tvPrice.setText("单买价：¥ 4199");
            Glide.with(context).load(goodlist.get(0).getMainImage()).into(linearViewHolder.iv);

        }


    }


//        if (goodlist!=null){
//
//
//            linearViewHolder.iv.setImageResource(R.drawable.pp_shoes);
//        }else {
//
//            LinearViewHolder linearViewHolder= (LinearViewHolder) holder;
//            linearViewHolder.tvName.setText("小米8顶配版 双曲面屏 小米商城黑色现货购买 赠送保护套");
//            linearViewHolder.tvPriceYZ.setText("拼购价：¥2298 ");
//            linearViewHolder.tvPrice.setText("单买价：¥ 4199");
//            linearViewHolder.iv.setImageResource(R.drawable.pp_shoes);

//            GridViewHolder gridViewHolder= (GridViewHolder) holder;
//            gridViewHolder.tvName.setText("小米note2顶配版 双曲面屏 小米商城黑色现货购买 赠送保护套");
//            gridViewHolder.tvPriceYZ.setText("颐众价：¥ 2298");
//            gridViewHolder.tvPrice.setText("市场价：¥ 2399");
//           gridViewHolder.iv.setImageResource(R.drawable.pp_shoes);
//            if (position%2==0)
//                gridViewHolder.rightView.setVisibility(View.VISIBLE);
//            else
//                gridViewHolder.rightView.setVisibility(View.GONE);
//        }
//


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()&&getItemCount()!=0) {
            return 1;
        } else {
            return type;
        }
    }


    @Override
    public int getItemCount() {
        if(goodlist!=null){return (goodlist.size() + 1);

        }else{
            return 0;
        }
    }

public static class LinearViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv;
    private TextView tvName, tvPriceYZ, tvPrice;

    public LinearViewHolder(View itemView) {
        super(itemView);
        iv = (ImageView) itemView.findViewById(R.id.imageview);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvPriceYZ = (TextView) itemView.findViewById(R.id.tv_price_yz);
        tvPrice = (TextView) itemView.findViewById(R.id.tv_price);


    }
}

public static class GridViewHolder extends RecyclerView.ViewHolder {

    private ImageView iv;
    private TextView tvName, tvPriceYZ, tvPrice;
    private View rightView;

    public GridViewHolder(View itemView) {
        super(itemView);
        iv = (ImageView) itemView.findViewById(R.id.imageview);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvPriceYZ = (TextView) itemView.findViewById(R.id.tv_price_yz);
        tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        rightView = itemView.findViewById(R.id.view_right);
    }
}

static class FooterHolder extends RecyclerView.ViewHolder {
    ProgressBar progressBar;
    TextView message;

    FooterHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.item_footer_progress);
        message = (TextView) itemView.findViewById(R.id.item_footer_message);
    }
}

}
