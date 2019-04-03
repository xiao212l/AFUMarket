package pv.com.pvcloudgo.vc.view.ui.activity.addr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Response;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.model.bean.addressBean;
import pv.com.pvcloudgo.model.bean.addressMessageBean;
import pv.com.pvcloudgo.vc.adapter.AddressAdapter;
import pv.com.pvcloudgo.vc.adapter.decoration.DividerItemDecoration;
import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.model.bean.Address;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.base.BaseRespMsg;
import pv.com.pvcloudgo.utils.Contants;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.base.BaseActivity;
import pv.com.pvcloudgo.vc.widget.PVToolBar;


public class AddressListActivity extends BaseActivity {


    @Bind(R.id.toolbar)
     PVToolBar mToolBar;

    @Bind(R.id.recycler_view)
     RecyclerView mRecyclerview;

    @Bind(R.id.address_add)
    FloatingActionButton addButton;

//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//    @Bind(R.id.toolbar_title)
//    TextView toolbarTitle;
//    @Bind(R.id.toolbar_left_logo)
//    ImageView toolbarLeftLogo;
//    @Bind(R.id.toolbar_logo)
//    ImageView toolbarLogo;
//    @Bind(R.id.toolbar_left_title)
//    TextView toolbarLeftTitle;
//    @Bind(R.id.toolbar_right_title)
//    TextView toolbarRightTitle;

     AddressAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        ButterKnife.bind(this);

        initToolbar();

        initAddress();


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toAddActivity();
            }
        });


    }


    private void initToolbar(){

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mToolBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
//        setupToolbar(toolbar, true);
//
//        toolbarTitle.setText("收货地址");
    }


    private void toAddActivity() {

        Intent intent = new Intent(this,AddressAddActivity.class);
        startActivityForResult(intent, Contants.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        initAddress();

    }

    private void initAddress(){



        mHttpHelper.Get(Contants.API.BASE_URL +"shipping/listall", App.getInstance().getToken(), new SpotsCallBack<addressMessageBean>(this) {


            @Override
            public void onSuccess(Response response, addressMessageBean addressMessage) {
                List<addressBean> addresses = addressMessage.getData();
                showAddress(addresses);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onServerError(Response response, int code, String errmsg) {

            }
        });
    }


    private void showAddress(List<addressBean> addresses) {

        if(mAdapter ==null) {
            mAdapter = new AddressAdapter(this, addresses, new AddressAdapter.AddressLisneter() {
                @Override
                public void setDefault(addressBean address) {

                    updateAddress(address);

                }

                @Override
                public void setDefault(Address address) {

                }

            });
            mRecyclerview.setAdapter(mAdapter);
            mRecyclerview.setLayoutManager(new LinearLayoutManager(AddressListActivity.this));
            mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        }
        else{
           // mAdapter.refreshData(addresses);
            mRecyclerview.setAdapter(mAdapter);
        }

    }


    public void updateAddress(addressBean address){


        mHttpHelper.get(Contants.API.ADDRESS_UPDATE,  new SpotsCallBack<BaseRespMsg>(this) {

            @Override
            public void onSuccess(Response response, BaseRespMsg baseRespMsg) {
                if(baseRespMsg.getStatus() == BaseRespMsg.STATUS_SUCCESS){

                    initAddress();
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }

            @Override
            public void onServerError(Response response, int code, String errmsg) {
                ToastUtils.show(errmsg);
            }
        });

    }






}
