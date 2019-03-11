package pv.com.pvcloudgo.vc.view.ui.activity.start;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pv.com.pvcloudgo.R;
import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.model.bean.LoginBean;
import pv.com.pvcloudgo.model.bean.Param;
import pv.com.pvcloudgo.model.bean.User;
import pv.com.pvcloudgo.http.SpotsCallBack;
import pv.com.pvcloudgo.utils.ToastUtils;
import pv.com.pvcloudgo.vc.base.BaseActivity;
import pv.com.pvcloudgo.vc.widget.ClearEditText;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
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
    @Bind(R.id.etxt_phone)
    ClearEditText mEtxtPhone;
    @Bind(R.id.etxt_pwd)
    ClearEditText mEtxtPwd;
    @Bind(R.id.txt_toReg)
    TextView txtToReg;
    @Bind(R.id.find_pass_tv)
    TextView findPassTv;
    @Bind(R.id.btn_login)
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        initToolBar();

        txtToReg.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegActivity.class)));
        findPassTv.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, FindPassActivity.class)));
    }


    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        setupToolbar(toolbar, true);

        toolbarTitle.setText("登录");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    public void testlogin() {
        App application = App.getInstance();
        String token = "testtest";
        User user = new User();
        application.putUser(user, token);
        setResult(RESULT_OK);
        finish();
    }

    /**
     * TODO
     * 测试登录，目前服务器不可用了，可以更改为自己的服务器地址进行测试
     *
     * @param view
     */
    @OnClick(R.id.btn_login)
    public void login(View view) {


        String phone = mEtxtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(this, "请输入手机号码");
            return;
        }

        String pwd = mEtxtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.show(this, "请输入密码");
            return;
        }


        Map<String, Object> params = new Param(2);

//        params.put("password", pwd);
//        params.put("username", phone);

        params.put("password",pwd);
        params.put("username", phone);


        mHttpHelper.post("http://47.95.244.237:9990/chengfeng/per/login"
                , params, new SpotsCallBack<LoginBean>(this) {
                    @Override
                    public void onSuccess(Response response, LoginBean LoginBean) {
                        if (LoginBean != null && LoginBean.getMessage().equals("用户登录成功")) {

                            ToastUtils.show(LoginBean.getMessage());
                            App application = App.getInstance();
                            LoginBean.getData().setPassword(pwd);
                            application.putUser(LoginBean.getData(), LoginBean.getData().getToken());
                            setResult(RESULT_OK);
                            finish();

                        } else {
                            ToastUtils.show(LoginBean.getMessage());
                        }


                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {
                        ToastUtils.show("用户名或密码错误");
                    }

                    @Override
                    public void onServerError(Response response, int code, String errmsg) {
                        ToastUtils.show(errmsg);
                    }
                });


    }


    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName), "utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


}
