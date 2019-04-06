package pv.com.pvcloudgo.vc.view.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.gxz.PagerSlidingTabStrip;
import com.squareup.okhttp.Response;

import dmax.dialog.*;
import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.http.OkHttpHelper;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.bean.goodsDetailMessageBean;
import pv.com.pvcloudgo.utils.Contants;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.view.ui.activity.other.DetailActivity;
import pv.com.pvcloudgo.vc.adapter.NetworkImageHolderView;
import pv.com.pvcloudgo.model.bean.RecommendGoodsBean;
import pv.com.pvcloudgo.vc.widget.SlideDetailsLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * item页ViewPager里的商品Fragment
 */
public class GoodsInfoFragment extends Fragment implements View.OnClickListener, SlideDetailsLayout.OnSlideDetailsListener {

    public static int selectData[] = {0, 0};

    private PagerSlidingTabStrip psts_tabs;
    private SlideDetailsLayout sv_switch;
    private ScrollView sv_goods_info;
    private FloatingActionButton fab_up_slide;
    public ConvenientBanner vp_item_goods_img;
    private LinearLayout ll_goods_detail, ll_goods_config;
    private TextView tv_goods_detail, tv_goods_config;
    private View v_tab_cursor;
    public FrameLayout fl_content;
    public LinearLayout ll_current_goods, ll_activity, ll_comment, ll_recommend, ll_pull_up;
    public TextView tv_goods_title, tv_new_price, tv_old_price, tv_current_goods, tv_comment_count, tv_good_comment, tv_current_goods_num, numPlus, numMinus;

    /**
     * 当前商品详情数据页的索引分别是图文详情、规格参数
     */
    private int nowIndex;
    private float fromX;
    public GoodsConfigFragment goodsConfigFragment;
    public GoodsInfoWebFragment goodsInfoWebFragment;
    private Fragment nowFragment;
    private List<TextView> tabTextList;
    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    public DetailActivity activity;
    private LayoutInflater inflater;

    private View selectView;

    private ListView selectStyle;
    ArrayAdapter<String> adapter;
    List<String> styleData;
    AlertDialog.Builder builder;
    AlertDialog selectDialog;
    EditText num;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (DetailActivity) context;
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        vp_item_goods_img.startTurning(4000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        vp_item_goods_img.stopTurning();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_goods_info, null);
        initView(rootView);
        initListener();
        initData();
        return rootView;
    }

    private void initListener() {
        fab_up_slide.setOnClickListener(this);
        ll_current_goods.setOnClickListener(this);
        ll_activity.setOnClickListener(this);
        ll_comment.setOnClickListener(this);
        ll_pull_up.setOnClickListener(this);
        ll_goods_detail.setOnClickListener(this);
        ll_goods_config.setOnClickListener(this);
        sv_switch.setOnSlideDetailsListener(this);

    }

    private void initView(View rootView) {
        psts_tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.psts_tabs);
        fab_up_slide = (FloatingActionButton) rootView.findViewById(R.id.fab_up_slide);
        sv_switch = (SlideDetailsLayout) rootView.findViewById(R.id.sv_switch);
        sv_goods_info = (ScrollView) rootView.findViewById(R.id.sv_goods_info);
        v_tab_cursor = rootView.findViewById(R.id.v_tab_cursor);
        vp_item_goods_img = (ConvenientBanner) rootView.findViewById(R.id.vp_item_goods_img);
        fl_content = (FrameLayout) rootView.findViewById(R.id.fl_content);
        ll_current_goods = (LinearLayout) rootView.findViewById(R.id.ll_current_goods);
        ll_activity = (LinearLayout) rootView.findViewById(R.id.ll_activity);
        ll_comment = (LinearLayout) rootView.findViewById(R.id.ll_comment);
        ll_pull_up = (LinearLayout) rootView.findViewById(R.id.ll_pull_up);
        ll_goods_detail = (LinearLayout) rootView.findViewById(R.id.ll_goods_detail);
        ll_goods_config = (LinearLayout) rootView.findViewById(R.id.ll_goods_config);
        tv_goods_detail = (TextView) rootView.findViewById(R.id.tv_goods_detail);
        tv_goods_config = (TextView) rootView.findViewById(R.id.tv_goods_config);
        tv_goods_title = (TextView) rootView.findViewById(R.id.tv_goods_title);
        tv_new_price = (TextView) rootView.findViewById(R.id.tv_new_price);
        tv_old_price = (TextView) rootView.findViewById(R.id.tv_old_price);
        tv_current_goods = (TextView) rootView.findViewById(R.id.tv_current_goods);
        tv_comment_count = (TextView) rootView.findViewById(R.id.tv_comment_count);
        tv_good_comment = (TextView) rootView.findViewById(R.id.tv_good_comment);
        tv_current_goods_num = (TextView) rootView.findViewById(R.id.tv_current_goods_num);

        selectView = inflater.inflate(R.layout.select_dialog, null);
        selectStyle = (ListView) selectView.findViewById(R.id.select_style);
        numPlus = (TextView) selectView.findViewById(R.id.plus);
        numPlus = (TextView) selectView.findViewById(R.id.minus);
        num = (EditText) selectView.findViewById(R.id.num);

        setDetailData();
        setLoopView();

        //设置文字中间一条横线
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        fab_up_slide.hide();

        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        vp_item_goods_img.setPageIndicator(new int[]{R.mipmap.index_white, R.mipmap.index_red});
        vp_item_goods_img.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
//        vp_recommend.setPageIndicator(new int[]{R.drawable.shape_item_index_white, R.drawable.shape_item_index_red});
//        vp_recommend.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);

        getData();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        tabTextList = new ArrayList<>();
        tabTextList.add(tv_goods_detail);
        tabTextList.add(tv_goods_config);
    }

    public void init() {

    }

    /**
     * 加载完商品详情执行
     */
    public void setDetailData() {
        goodsConfigFragment = new GoodsConfigFragment();
        goodsInfoWebFragment = new GoodsInfoWebFragment();
        fragmentList.add(goodsConfigFragment);
        fragmentList.add(goodsInfoWebFragment);

        nowFragment = goodsInfoWebFragment;
        fragmentManager = getChildFragmentManager();
        //默认显示商品详情tab
        fragmentManager.beginTransaction().replace(R.id.fl_content, nowFragment).commitAllowingStateLoss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pull_up:
                //上拉查看图文详情
                sv_switch.smoothOpen(true);
                break;

            case R.id.fab_up_slide:
                //点击滑动到顶部
                sv_goods_info.smoothScrollTo(0, 0);
                sv_switch.smoothClose(true);
                break;

            case R.id.ll_goods_detail:
                //商品详情tab
                nowIndex = 0;
                scrollCursor();
                switchFragment(nowFragment, goodsInfoWebFragment);
                nowFragment = goodsInfoWebFragment;
                break;

            case R.id.ll_goods_config:
                //规格参数tab
                nowIndex = 1;
                scrollCursor();
                switchFragment(nowFragment, goodsConfigFragment);
                nowFragment = goodsConfigFragment;
                break;


            case R.id.ll_current_goods:
                showWindow();
            default:
                break;
        }
    }

    /**
     * 给商品轮播图设置图片路径
     */
    public void setLoopView() {
        List<String> imgUrls = new ArrayList<>();
//        imgUrls.add("http://img4.hqbcdn.com/product/79/f3/79f3ef1b0b2283def1f01e12f21606d4.jpg");
//        imgUrls.add("http://img14.hqbcdn.com/product/77/6c/776c63e6098f05fdc5639adc96d8d6ea.jpg");
//        imgUrls.add("http://img13.hqbcdn.com/product/41/ca/41cad5139371e4eb1ce095e5f6224f4d.jpg");
//        imgUrls.add("http://img10.hqbcdn.com/product/fa/ab/faab98caca326949b87b770c8080e6cf.jpg");
//        imgUrls.add("http://img2.hqbcdn.com/product/6b/b8/6bb86086397a8cd0525c449f29abfaff.jpg");
        //初始化商品图片轮播
        vp_item_goods_img.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new NetworkImageHolderView();
            }
        }, imgUrls);
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {
        if (status == SlideDetailsLayout.Status.OPEN) {
            //当前为图文详情页
            fab_up_slide.show();
            activity.vp_content.setNoScroll(true);
            activity.tv_title.setVisibility(View.VISIBLE);
            activity.psts_tabs.setVisibility(View.GONE);
        } else {
            //当前为商品详情页
            fab_up_slide.hide();
            activity.vp_content.setNoScroll(false);
            activity.tv_title.setVisibility(View.GONE);
            activity.psts_tabs.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 滑动游标
     */
    private void scrollCursor() {
        TranslateAnimation anim = new TranslateAnimation(fromX, nowIndex * v_tab_cursor.getWidth(), 0, 0);
        anim.setFillAfter(true);//设置动画结束时停在动画结束的位置
        anim.setDuration(50);
        //保存动画结束时游标的位置,作为下次滑动的起点
        fromX = nowIndex * v_tab_cursor.getWidth();
        v_tab_cursor.startAnimation(anim);

        //设置Tab切换颜色
        for (int i = 0; i < tabTextList.size(); i++) {
            tabTextList.get(i).setTextColor(i == nowIndex ? getResources().getColor(R.color.material_blue) : getResources().getColor(R.color.backColor));
        }
    }

    /**
     * 切换Fragment
     * <p>(hide、show、add)
     *
     * @param fromFragment
     * @param toFragment
     */
    private void switchFragment(Fragment fromFragment, Fragment toFragment) {
        if (nowFragment != toFragment) {
            fragmentTransaction = fragmentManager.beginTransaction();
            if (!toFragment.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(fromFragment).add(R.id.fl_content, toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到activity中
            } else {
                fragmentTransaction.hide(fromFragment).show(toFragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
            }
        }
    }

    /**
     * 处理推荐商品数据(每两个分为一组)
     *
     * @param data
     * @return
     */
    public static List<List<RecommendGoodsBean>> handleRecommendGoods(List<RecommendGoodsBean> data) {
        List<List<RecommendGoodsBean>> handleData = new ArrayList<>();
        int length = data.size() / 2;
        if (data.size() % 2 != 0) {
            length = data.size() / 2 + 1;
        }
        for (int i = 0; i < length; i++) {
            List<RecommendGoodsBean> recommendGoods = new ArrayList<>();
            for (int j = 0; j < (i * 2 + j == data.size() ? 1 : 2); j++) {
                recommendGoods.add(data.get(i * 2 + j));
            }
            handleData.add(recommendGoods);
        }
        return handleData;
    }

    public void getData() {
        OkHttpHelper HttpHelper = new OkHttpHelper();
        HttpHelper.Get(Contants.API.BASE_URL + "product/1"
                , App.getInstance().getToken(), new SpotsCallBack<goodsDetailMessageBean>(getActivity()) {
                    @Override
                    public void onSuccess(Response response, goodsDetailMessageBean DetailBean) {
                        if (DetailBean != null && DetailBean.getMessage().equals("请求成功")) {
                            tv_goods_title.setText(DetailBean.getData().getName() + "\n" + DetailBean.getData().getSubtitle() + "\n" + DetailBean.getData().getDetail());
                            int price[] = new int[DetailBean.getData().getPurchaseProductSkus().size()];
                            int spellPrice[] = new int[DetailBean.getData().getPurchaseProductSkus().size()];
                            int i = 0;
                            for (goodsDetailMessageBean.goodsDetailBean.PurchaseProductSkusBean style : DetailBean.getData().getPurchaseProductSkus()) {
                                price[i] = style.getPrice();
                                spellPrice[i++] = style.getSpellPrice();
                            }
                            Arrays.sort(price);
                            Arrays.sort(spellPrice);
                            tv_old_price.setText("￥" + price[0] + "-" + price[DetailBean.getData().getPurchaseProductSkus().size() - 1]);
                            tv_new_price.setText(spellPrice[0] + "-" + spellPrice[DetailBean.getData().getPurchaseProductSkus().size() - 1]);


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

    }

    public void showWindow() {


        OkHttpHelper HttpHelper = new OkHttpHelper();
        HttpHelper.Get(Contants.API.BASE_URL + "product/1"
                , App.getInstance().getToken(), new SpotsCallBack<goodsDetailMessageBean>(getActivity()) {
                    @Override
                    public void onSuccess(Response response, goodsDetailMessageBean DetailBean) {
                        if (DetailBean != null && DetailBean.getMessage().equals("请求成功")) {
                            int i = 0;

                            styleData = new ArrayList<>();
                            for (goodsDetailMessageBean.goodsDetailBean.PurchaseProductSkusBean style : DetailBean.getData().getPurchaseProductSkus()) {
                                styleData.add(style.getAttributeName());
                            }

                            selectView = inflater.inflate(R.layout.select_dialog, null);
                            selectStyle = (ListView) selectView.findViewById(R.id.select_style);
                            numPlus = (TextView) selectView.findViewById(R.id.plus);
                            numMinus = (TextView) selectView.findViewById(R.id.minus);
                            num = (EditText) selectView.findViewById(R.id.num);

                            if (adapter == null) {
                                adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, styleData) {
                                    @Override
                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View view = super.getView(position, convertView, parent);
                                        TextView text = (TextView) view.findViewById(android.R.id.text1);
                                        text.setTextColor(Color.BLACK);
                                        return view;
                                    }
                                };

                            }
                            selectStyle.setAdapter(adapter);

                            numPlus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int Num = Integer.parseInt(num.getText().toString());
                                    Num += 1;
                                    num.setText("" + Num);
                                }
                            });

                            numMinus.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int Num = Integer.parseInt(num.getText().toString());
                                    if (Num != 0) {
                                        Num -= 1;
                                    }


                                    num.setText("" + Num);
                                }
                            });


                            selectStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    tv_current_goods.setText(DetailBean.getData().getPurchaseProductSkus().get(position).getAttributeName());
                                    tv_current_goods_num.setText("x" + num.getText());
                                    selectDialog.dismiss();

                                    selectData[0] = DetailBean.getData().getPurchaseProductSkus().get(position).getId();
                                    selectData[1] = Integer.parseInt(num.getText().toString());

                                }
                            });
                            builder = new AlertDialog.Builder(getContext());

                            //通过布局填充器获login_layout
                            //获取两个文本编辑框（密码这里不做登陆实现，仅演示）

                            builder.setView(selectView);//设置login_layout为对话提示框

//                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            });

                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                            selectDialog = builder.show();

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

    }

}
