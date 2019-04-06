package pv.com.pvcloudgo.vc.view.ui.activity.order;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.model.bean.payMessageBean;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.view.ui.activity.addr.AddressListActivity;
import pv.com.pvcloudgo.vc.view.ui.activity.other.PayResultActivity;
import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.vc.adapter.WareOrderAdapter;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.base.BaseRespMsg;
import pv.com.pvcloudgo.utils.CartProvider;
import pv.com.pvcloudgo.utils.Contants;
import pv.com.pvcloudgo.vc.base.BaseActivity;

import static pv.com.pvcloudgo.vc.adapter.CartRecyclerViewAdapter.CartItem;


public class CreateOrderActivity extends BaseActivity implements View.OnClickListener {


    int type;
    List<String> cartData;
    /**
     * 银联支付渠道
     */
    private static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    private static final String CHANNEL_BFB = "bfb";
    /**
     * 京东支付渠道
     */
    private static final String CHANNEL_JDPAY_WAP = "jdpay_wap";


    @Bind(R.id.txt_order)
    TextView txtOrder;

    @Bind(R.id.cart_list)
    ListView mListView;


    @Bind(R.id.rl_alipay)
    RelativeLayout mLayoutAlipay;

    @Bind(R.id.rl_wechat)
    RelativeLayout mLayoutWechat;

    @Bind(R.id.rl_bd)
    RelativeLayout mLayoutBd;


    @Bind(R.id.rb_alipay)
    RadioButton mRbAlipay;

    @Bind(R.id.rb_webchat)
    RadioButton mRbWechat;

    @Bind(R.id.rb_bd)
    RadioButton mRbBd;

    @Bind(R.id.btn_createOrder)
    Button mBtnCreateOrder;

    @Bind(R.id.txt_total)
    TextView mTxtTotal;

    @Bind(R.id.order_name)
    TextView mName;

    @Bind(R.id.order_addr)
    TextView mAddr;

    @Bind(R.id.select_addr)
    LinearLayout selectAddr;


    private CartProvider cartProvider;

    private WareOrderAdapter mAdapter;

    private String orderNum;
    private String payChannel = CHANNEL_ALIPAY;
    private float amount = 0;


    private HashMap<String, RadioButton> channels = new HashMap<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        cartData = new ArrayList<>();
        Intent intent = getIntent();
        type = intent.getIntExtra("position", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        ButterKnife.bind(this);

        init();

        showData();


    }


    private void init() {

        channels.put(CHANNEL_ALIPAY, mRbAlipay);
        channels.put(CHANNEL_WECHAT, mRbWechat);
        channels.put(CHANNEL_BFB, mRbBd);

        mLayoutAlipay.setOnClickListener(this);
        mLayoutWechat.setOnClickListener(this);
        mLayoutBd.setOnClickListener(this);


        for (int i = 0; i < CartItem.checkgroup.length; i++) {
            if (CartItem.checkgroup[i].isChecked()) {
                amount += CartItem.cartItem[i].getPrice() * CartItem.cartItem[i].getCount();
                cartData.add(CartItem.cartItem[i].getName() + "     x" + CartItem.cartItem[i].getCount());
            }

        }
        mTxtTotal.setText("应付款： ￥" + amount);

    }


    public void showData() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, cartData) {
            @Override
            public View getView(int position, View convertView, ViewGroup
                    parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };

        mListView.setAdapter(adapter);

//调整listview高度避免与scollview发生冲突
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, mListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = totalHeight + (mListView.getDividerHeight() * (adapter.getCount() - 1));
        params.height += 5;
        mListView.setLayoutParams(params);
//

    }


    @Override
    public void onClick(View v) {

        selectPayChannle(v.getTag().toString());
    }


    public void selectPayChannle(String paychannel) {


        for (Map.Entry<String, RadioButton> entry : channels.entrySet()) {

            payChannel = paychannel;
            RadioButton rb = entry.getValue();
            if (entry.getKey().equals(paychannel)) {

                boolean isCheck = rb.isChecked();
                rb.setChecked(!isCheck);

            } else
                rb.setChecked(false);
        }


    }


    @OnClick(R.id.btn_createOrder)
    public void createNewOrder(View view) {

        postNewOrder();
    }

    @OnClick(R.id.select_addr)
    public void selectOrder(View view) {

        next(AddressListActivity.class, 1);

    }


    private void postNewOrder() {
        int skuId = 0;
        int count = 0;
        int shoppingId = 0;

        for (int i = 0; i < CartItem.checkgroup.length; i++) {
            if (CartItem.checkgroup[i].isChecked()) {
                skuId = CartItem.cartItem[i].getSkuId();
                count = CartItem.cartItem[i].getCount();
                shoppingId = AddressListActivity.addr.getId();
            }

        }

        mHttpHelper.Post(Contants.API.BASE_URL + "order/place/separate/" + skuId + "/" + count + "/" + shoppingId, App.getInstance().getToken(), new SpotsCallBack<payMessageBean>(this) {
            @Override
            public void onSuccess(Response response, payMessageBean payMessageBean) {
                if (payMessageBean != null && payMessageBean.getMessage().equals("下单成功")) {


                    ToastUtils.show(payMessageBean.getMessage());
                    finish();

                } else {
                    ToastUtils.show("请求失败");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                mBtnCreateOrder.setEnabled(true);
            }

            @Override
            public void onServerError(Response response, int code, String errmsg) {

            }
        });


    }


    private void openPaymentActivity(String charge) {

        Intent intent = new Intent();
        String packageName = getPackageName();
        ComponentName componentName = new ComponentName(packageName, packageName + ".wxapi.WXPayEntryActivity");
        intent.setComponent(componentName);
//        intent.putExtra(PaymentActivity.EXTRA_CHARGE, charge);
        startActivityForResult(intent, Contants.REQUEST_CODE_PAYMENT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Contants.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");

                if (result.equals("success"))
                    changeOrderStatus(1);
                else if (result.equals("fail"))
                    changeOrderStatus(-1);
                else if (result.equals("cancel"))
                    changeOrderStatus(-2);
                else
                    changeOrderStatus(0);

            }
        } else if (requestCode == Contants.REQUEST_CODE) {
            mName.setText(AddressListActivity.addr.getReceiverName() + "(" + AddressListActivity.addr.getReceiverPhone() + ")");
            mAddr.setText(AddressListActivity.addr.getReceiverAddress());
        }


    }


    private void changeOrderStatus(final int status) {

        Map<String, Object> params = new HashMap<>(5);
        params.put("order_num", orderNum);
        params.put("status", status + "");


        mHttpHelper.post(Contants.API.ORDER_COMPLEPE, params, new SpotsCallBack<BaseRespMsg>(this) {
            @Override
            public void onSuccess(Response response, BaseRespMsg o) {

                toPayResultActivity(status);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                toPayResultActivity(-1);
            }

            @Override
            public void onServerError(Response response, int code, String errmsg) {

            }
        });

    }


    private void toPayResultActivity(int status) {

        Intent intent = new Intent(this, PayResultActivity.class);
        intent.putExtra("status", status);

        startActivity(intent);
        this.finish();

    }


    class WareItem {
        private Long ware_id;
        private int amount;

        public WareItem(Long ware_id, int amount) {
            this.ware_id = ware_id;
            this.amount = amount;
        }

        public Long getWare_id() {
            return ware_id;
        }

        public void setWare_id(Long ware_id) {
            this.ware_id = ware_id;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

    }

    public void next(Class cls, int data) {


        Intent intent = new Intent(CreateOrderActivity.this, cls);
        intent.putExtra("position", data);

        startActivityForResult(intent, true, Contants.REQUEST_CODE);

    }
}
