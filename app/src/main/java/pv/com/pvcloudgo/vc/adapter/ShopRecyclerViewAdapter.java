package pv.com.pvcloudgo.vc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.DraweeView;
import com.squareup.okhttp.Response;

import java.util.List;
import java.util.Map;

import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.http.OkHttpHelper;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.bean.Param;
import pv.com.pvcloudgo.model.bean.goodsFragmentBean;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.view.ui.fragment.VipFragment;
import pv.com.pvcloudgo.vc.view.ui.fragment.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link VipFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ShopRecyclerViewAdapter extends RecyclerView.Adapter<ShopRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final VipFragment.OnListFragmentInteractionListener mListener;

    public ShopRecyclerViewAdapter(List<DummyItem> items, VipFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_vip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.

                    Map<String, Object> params = new Param(3);
                    params.put("categoryId", "100006");
                    params.put("pageNum", "1");
                    params.put("pageSize", "2");

                    OkHttpHelper mHttpHelper = new OkHttpHelper();
                    mHttpHelper.get("http://47.95.244.237:9990/chengfeng/product/simple/list"
                            , params, new SpotsCallBack<goodsFragmentBean>(v.getContext()) {
                                @Override
                                public void onSuccess(Response response, goodsFragmentBean goodsBean) {

                                    if (goodsBean != null && goodsBean.getMessage().equals("请求成功")) {
                                        goodsBean.getData();
                                        ShopRecyclerViewAdapter.ViewHolder ViewHolder = (ShopRecyclerViewAdapter.ViewHolder) holder;
                                        ViewHolder.GoodName.setText( goodsBean.getData().get(0).getName());
                                        ViewHolder.GoodText.setText("拼购价：¥ " +  goodsBean.getData().get(0).getSpellPrice());
                                        Glide.with(v.getContext()).load(goodsBean.getData().get(0).getMainImage()).into(ViewHolder.GoodImage);
                                        ToastUtils.show("加载成功");
                                    } else {
                                        ToastUtils.show("请求失败");
                                    }
                                }

                                @Override
                                public void onError(Response response, int code, Exception e) {
                                    ToastUtils.show("请求失败");
                                }

                                @Override
                                public void onServerError(Response response, int code, String errmsg) {
                                    ToastUtils.show("请求失败,服务器无响应");
                                }
                            });





                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public DummyItem mItem;
        public TextView ItemAtten,GoodText,GoodName;
        public ImageView GoodImage;
        public ViewHolder(View view) {
            super(view);
            mView = view;
            ItemAtten = (TextView)view.findViewById(R.id.item_atten);
            GoodText = (TextView)view.findViewById(R.id.good_text);
            GoodName = (TextView)view.findViewById(R.id.good_name);
            GoodImage  = (ImageView)view.findViewById(R.id.item_ff);
        }

    }
}
