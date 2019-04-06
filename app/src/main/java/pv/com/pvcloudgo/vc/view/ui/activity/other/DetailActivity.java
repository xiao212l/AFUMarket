package pv.com.pvcloudgo.vc.view.ui.activity.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import com.squareup.okhttp.Response;

import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.http.OkHttpHelper;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.bean.addCartMessageBean;
import pv.com.pvcloudgo.model.bean.goodsDetailMessageBean;
import pv.com.pvcloudgo.utils.Contants;
import pv.com.pvcloudgo.utils.ToastUtil;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.adapter.ItemTitlePagerAdapter;
import pv.com.pvcloudgo.vc.base.BaseActivity;
import pv.com.pvcloudgo.vc.view.ui.fragment.GoodsCommentFragment;
import pv.com.pvcloudgo.vc.view.ui.fragment.GoodsDetailFragment;
import pv.com.pvcloudgo.vc.view.ui.fragment.GoodsInfoFragment;
import pv.com.pvcloudgo.vc.widget.NoScrollViewPager;

import pv.com.pvcloudgo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends BaseActivity {
    public PagerSlidingTabStrip psts_tabs;
    public NoScrollViewPager vp_content;
    public TextView tv_title;
    public Button buy_only, share_bill;
    public TextView add_cart;
    public int id;

    private List<Fragment> fragmentList = new ArrayList<>();
    private GoodsInfoFragment goodsInfoFragment;
    private GoodsDetailFragment goodsDetailFragment;
    private GoodsCommentFragment goodsCommentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        psts_tabs = (PagerSlidingTabStrip) findViewById(R.id.psts_tabs);
        vp_content = (NoScrollViewPager) findViewById(R.id.vp_content);
        tv_title = (TextView) findViewById(R.id.tv_title);
        buy_only = (Button) findViewById(R.id.buy_only);
        share_bill = (Button) findViewById(R.id.share_bill);
        add_cart = (TextView) findViewById(R.id.add_cart);

        fragmentList.add(goodsInfoFragment = new GoodsInfoFragment());
        fragmentList.add(goodsDetailFragment = new GoodsDetailFragment());
        fragmentList.add(goodsCommentFragment = new GoodsCommentFragment());
        vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"商品", "详情", "评价"}));
        vp_content.setOffscreenPageLimit(3);
        psts_tabs.setViewPager(vp_content);


        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GoodsInfoFragment.selectData[0] != 0) {

                    int skuId = GoodsInfoFragment.selectData[0];
                    int count = GoodsInfoFragment.selectData[1];

                    OkHttpHelper HttpHelper = new OkHttpHelper();
                    HttpHelper.Post(Contants.API.BASE_URL + "cart/add/" + skuId + "/" + count
                            , App.getInstance().getToken(), new SpotsCallBack<addCartMessageBean>(v.getContext()) {
                                @Override
                                public void onSuccess(Response response, addCartMessageBean addCartBean) {
                                    if (addCartBean != null && addCartBean.getMessage().equals("商品添加购物车成功")) {
                                        ToastUtils.show("添加购物车成功！");
                                        finish();

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
                    HttpHelper = null;


                } else {
                    ToastUtils.show("请选择型号数量！");
                }
            }
        });


    }


    public void next(Class cls, int data) {


        Intent intent = new Intent(DetailActivity.this, cls);
        intent.putExtra("position", data);

        startActivityForResult(intent, true, Contants.REQUEST_CODE);

    }

}
