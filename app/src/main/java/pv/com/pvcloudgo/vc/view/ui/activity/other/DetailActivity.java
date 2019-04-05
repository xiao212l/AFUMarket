package pv.com.pvcloudgo.vc.view.ui.activity.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.gxz.PagerSlidingTabStrip;
import pv.com.pvcloudgo.vc.adapter.ItemTitlePagerAdapter;
import pv.com.pvcloudgo.vc.view.ui.fragment.GoodsCommentFragment;
import pv.com.pvcloudgo.vc.view.ui.fragment.GoodsDetailFragment;
import pv.com.pvcloudgo.vc.view.ui.fragment.GoodsInfoFragment;
import pv.com.pvcloudgo.vc.widget.NoScrollViewPager;

import pv.com.pvcloudgo.R;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public PagerSlidingTabStrip psts_tabs;
    public NoScrollViewPager vp_content;
    public TextView tv_title;

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

        fragmentList.add(goodsInfoFragment = new GoodsInfoFragment());
        fragmentList.add(goodsDetailFragment = new GoodsDetailFragment());
        fragmentList.add(goodsCommentFragment = new GoodsCommentFragment());
        vp_content.setAdapter(new ItemTitlePagerAdapter(getSupportFragmentManager(),
                fragmentList, new String[]{"商品", "详情", "评价"}));
        vp_content.setOffscreenPageLimit(3);
        psts_tabs.setViewPager(vp_content);
    }
}
