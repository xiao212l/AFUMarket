package pv.com.pvcloudgo.vc.view.ui.activity.start;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Response;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.model.bean.Param;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.model.base.BaseRespMsg;
import pv.com.pvcloudgo.model.bean.SignInBean;
import pv.com.pvcloudgo.utils.Contants;
import pv.com.pvcloudgo.utils.CountTimerView;
import pv.com.pvcloudgo.utils.ToastUtil;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.base.BaseActivity;
import pv.com.pvcloudgo.vc.widget.ClearEditText;

public class RegActivity extends BaseActivity {

    private static final String TAG = "RegActivity";

    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";
    @Bind(R.id.edittxt_phone)
    ClearEditText mEtxtPhone;
    @Bind(R.id.edittxt_username)
    ClearEditText mUsername;
    @Bind(R.id.edittxt_pwd)
    ClearEditText mEtxtPwd;
    @Bind(R.id.edittxt_email)
    ClearEditText mEmail;
    @Bind(R.id.edittxt_id)
    ClearEditText mId;
    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_logo)
    ImageView toolbarLogo;
    @Bind(R.id.toolbar_left_title)
    TextView toolbarLeftTitle;
    @Bind(R.id.toolbar_right_title)
    TextView toolbarRightTitle;
    @Bind(R.id.image_right)
    ImageView imageRight;
    @Bind(R.id.image_exit)
    ImageView imageExit;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    private SpotsDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);

        initToolBar();

    }

    @OnClick(R.id.ac_reg_btn)
    public void goReg() {
        toolbarTitle.setText("注册");
        String username = mUsername.getText().toString().trim();
        String phone = mEtxtPhone.getText().toString().trim().replaceAll("\\s*", "");
        String password = mEtxtPwd.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String id = mId.getText().toString().trim();
        SignIn(phone, username, password, email, id);
    }


    private void initToolBar() {
        setupToolbar(toolbar, true);

    }

    private void SignIn(String phone, String username, String password, String email, String id) {

        String json;
        if (phone.isEmpty()) {
            ToastUtils.show("请输入手机号码");
            return;
        } else if (username.isEmpty()) {
            ToastUtils.show("请输入用户名");
            return;
        } else if(password.isEmpty()){
            ToastUtils.show("请输入密码");
            return;
        }

        json = "{\n" +
                (id.isEmpty()?"":("\"idcard\":"+"\""+ id+"\""+",\n" )) +
                (email.isEmpty()?"":("\"email\":"+"\"" +email +"\""+",\n" ))+
                "  \"password\": \""+password+"\",\n" +
                "  \"phone\": \""+phone+"\",\n" +
                "  \"username\": \""+username+"\"\n" +
                "}";

        mHttpHelper.post("http://47.95.244.237:9990/chengfeng/per/registry",
                json, new SpotsCallBack<SignInBean>(this) {


                    @Override
                    public void onSuccess(Response response, SignInBean respMsg) {


                        if (respMsg != null && respMsg.getMessage().equals("用户注册成功")) {
//                    startActivity(new Intent(mContext, BandPhoneActivity.class));
                            ToastUtil.showToast(mContext, R.drawable.ic_opt_suc, "注册成功");
                            finish();
                        } else {
                            ToastUtils.show("注册失败");
                        }


                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {
//                        showFail();
                        ToastUtils.show(json);
                    }

                    @Override
                    public void onServerError(Response response, int code, String errmsg) {

                    }
                });


    }

}
