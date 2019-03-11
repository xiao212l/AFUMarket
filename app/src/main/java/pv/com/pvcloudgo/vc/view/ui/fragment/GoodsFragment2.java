package pv.com.pvcloudgo.vc.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.okhttp.Response;

import java.util.List;

import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.http.OkHttpHelper;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.bean.goodsFragmentBean;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.adapter.GoodsListRecyclerViewAdapter;

public class GoodsFragment2 extends BaseFragment implements View.OnClickListener {

    private List<goodsFragmentBean.good> goodlist;


    private Context context;
    private ImageView ivGoodsType;

    private RecyclerView recyclerView;

    private GoodsListRecyclerViewAdapter adapter;

    private ImageView ivStick;//置顶

    private int goodsType=0;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        mHttpHelper.get("http://47.95.244.237:9990/chengfeng/product/simple/list?categoryId=100006&pageNum=1&pageSize=2"
                , new SpotsCallBack<goodsFragmentBean>(getActivity()) {
                    @Override
                    public void onSuccess(Response response, goodsFragmentBean goodsBean) {
                        if (goodsBean != null && goodsBean.getMessage().equals("请求成功")) {
                            goodlist = goodsBean.getData();
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


        super.onActivityCreated(savedInstanceState);
        //ivGoodsType= (ImageView)getActivity().findViewById(R.id.iv_goods_type);
//        ivGoodsType.setOnClickListener(this);
        ivStick= (ImageView) getActivity().findViewById(R.id.iv_stick);
        ivStick.setOnClickListener(this);
        recyclerView= (RecyclerView) getActivity().findViewById(R.id.recyclerview);
        adapter = new GoodsListRecyclerViewAdapter(context,goodlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        //设置滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取第一个可见位置
                    int firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                    //当滑动到第十个以上时显示置顶图标
                    if (firstVisibleItemPosition>10) {
                        ivStick.setVisibility(View.VISIBLE);
                    }else {
                        ivStick.setVisibility(View.GONE);
                    }
                }
            }
        });







    }

    public void init() {
        Fresco.initialize(getActivity());
    }

    @Override
    public void onClick(View v) {
          switch (v.getId()){
//            case R.id.iv_goods_type://切换布局
//                if (goodsType==0){
//                    ivGoodsType.setImageResource(R.mipmap.good_type_grid);
//                    //1：设置布局类型
//                    adapter.setType(1);
//                    //2：设置对应的布局管理器
//                    recyclerView.setLayoutManager(new GridLayoutManager(context,2));
//                    //3：刷新adapter
//                    adapter.notifyDataSetChanged();
//                    goodsType=1;
//                }else {
//                    ivGoodsType.setImageResource(R.mipmap.good_type_linear);
//                    adapter.setType(0);
//                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
//                    adapter.notifyDataSetChanged();
//                    goodsType=0;
//                }
//                  break;
            case R.id.iv_stick://置顶
                recyclerView.scrollToPosition(0);
                break;
        }
    }






}
