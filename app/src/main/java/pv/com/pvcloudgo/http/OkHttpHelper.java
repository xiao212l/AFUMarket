package pv.com.pvcloudgo.http;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Route;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okio.BufferedSink;
import pv.com.pvcloudgo.utils.Contants;
import pv.com.pvcloudgo.vc.view.ui.activity.start.LoginActivity;
import pv.com.pvcloudgo.app.App;
import pv.com.pvcloudgo.model.base.BaseRespMsg;
import pv.com.pvcloudgo.utils.ActivityManager;
import pv.com.pvcloudgo.utils.ToastUtils;



public class OkHttpHelper {


    public static final int TOKEN_MISSING = 401;// token 丢失
    public static final int TOKEN_ERROR = 402; // token 错误
    public static final int TOKEN_EXPIRE = 403; // token 过期

    public static final String TAG = "OkHttpHelper";

    private static OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;

    private Handler mHandler;


//        static {
//            mInstance = new OkHttpHelper();
//        }

    public OkHttpHelper() {

        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());

    }

    public static OkHttpHelper getInstance() {
        return mInstance;
    }


    public void get(String url, Map<String, Object> param, BaseCallback callback) {


        Request request = buildGetRequest(url, param);

        request(request, callback);

    }

    public void get(String url, BaseCallback callback) {

        get(url, null, callback);
    }


    public void Get(String url,String Token, BaseCallback callback) {


        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+Token)
                .build();

        request(request, callback);

    }

    public void Post(String url, String json,String Token, BaseCallback callback) {

        Request.Builder builder = new Request.Builder()
                .url(url);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);

        builder.post(body);
        Request request = builder.addHeader("Authorization","Bearer "+Token).build();
        request(request, callback);
    }

    public void Post(String url, String Token, BaseCallback callback) {

        Request.Builder builder = new Request.Builder()
                .url(url);
        builder.post(new RequestBody() {
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {

            }
        });
        Request request = builder.addHeader("Authorization", "Bearer " + Token).build();
        request(request, callback);
    }


    public void post(String url, Map<String, Object> param, BaseCallback callback) {

        Request request = buildPostRequest(url, param);


        request(request, callback);
    }

    public void post(String url, String json, BaseCallback callback) {

        Request.Builder builder = new Request.Builder()
                .url(url);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        builder.post(body);
        Request request = builder.build();
        request(request, callback);
    }





    public void put(String url, String json, BaseCallback callback) {

        Request.Builder builder = new Request.Builder()
                .url(url);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),json);
        builder.put(body);
        Request request = builder.build();
        request(request, callback);

    }



    public void request(final Request request, final BaseCallback callback) {

        callback.onBeforeRequest(request);

        mHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                callbackFailure(callback, request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
//                    callback.onResponse(response);
                callbackResponse(callback, response);

                if (response.isSuccessful()) {

                    String resultStr = response.body().string();
                    Log.e(TAG, "URL:" + response.request().httpUrl().url().toString() + " result=" + resultStr);

                    if (callback.mType == String.class) {
                        callbackSuccess(callback, response, resultStr);
                    } else {
                        if (response.request().httpUrl().url().toString().contains(Contants.API.BASE_URL)) {
                            try {//尝试解析为基础数据
                                BaseRespMsg respMsg = mGson.fromJson(resultStr, BaseRespMsg.class);
                                if (respMsg != null && respMsg.getStatus().equals(BaseRespMsg.STATUS_ERROR) && !TextUtils.isEmpty(respMsg.getMessage())) {
                                    ToastUtils.show(respMsg.getMessage());
                                    if (respMsg.getMessage().equals("抱歉，请先登录")) {
                                        App application = App.getInstance();
                                        application.clearUser();
                                        ActivityManager.getInstance().finishAll();
                                        callback.mContext.startActivity(new Intent(callback.mContext, LoginActivity.class));
                                    }
                                    callback.onServerError(response, response.code(), respMsg.getMessage());
                                    return;
                                }
                            } catch (IllegalStateException e) {
                            }
                        }
                        try {
                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback, response, obj);
                            Log.e(TAG, "suc=" + resultStr);
                        } catch (com.google.gson.JsonParseException e) { // Json解析的错误

                            callback.onError(response, response.code(), e);
                            Log.e(TAG, "err=" + e.getMessage());
                        } catch (Exception e) {
                            callback.onError(response, response.code(), e);
                        }
                    }
                } else if (response.code() == TOKEN_ERROR || response.code() == TOKEN_EXPIRE || response.code() == TOKEN_MISSING) {
                    Log.e(TAG, "TOKEN err=" + response.body().string());
                    callbackTokenError(callback, response);
                } else {
                    Log.e(TAG, "err=" + response.body().string());
                    callbackError(callback, response, null);
                }

            }
        });


    }


    private void callbackTokenError(final BaseCallback callback, final Response response) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onTokenError(response, response.code());
            }
        });
    }

    private void callbackSuccess(final BaseCallback callback, final Response response, final Object obj) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }


    private void callbackError(final BaseCallback callback, final Response response, final Exception e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }


    private void callbackFailure(final BaseCallback callback, final Request request, final IOException e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
            }
        });
    }


    private void callbackResponse(final BaseCallback callback, final Response response) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(response);
            }
        });
    }


    private Request buildPostRequest(String url, Map<String, Object> params) {

        return buildRequest(url, HttpMethodType.POST, params);
    }

    private Request buildGetRequest(String url, Map<String, Object> param) {

        return buildRequest(url, HttpMethodType.GET, param);
    }

    private Request buildRequest(String url, HttpMethodType methodType, Map<String, Object> params) {


        Request.Builder builder = new Request.Builder()
                .url(url);

        if (methodType == HttpMethodType.POST) {
            RequestBody body = builderFormData(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {

            url = buildUrlParams(url, params);
            builder.url(url);

            builder.get();
        }


        return builder.build();
    }


    private String buildUrlParams(String url, Map<String, Object> params) {

        if (params == null)
            params = new HashMap<>(1);

        String token = App.getInstance().getToken();
        if (!TextUtils.isEmpty(token))
            params.put("token", token);


        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey() + "=" + (entry.getValue() == null ? "" : entry.getValue().toString()));
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }

        if (url.indexOf("?") > 0) {
            url = url + "&" + s;
        } else {
            url = url + "?" + s;
        }

        return url;
    }

    private RequestBody builderFormData(Map<String, Object> params) {


        FormEncodingBuilder builder = new FormEncodingBuilder();

        if (params != null) {


            for (Map.Entry<String, Object> entry : params.entrySet()) {

                builder.add(entry.getKey(), entry.getValue() == null ? "" : entry.getValue().toString());
            }

//            String token = App.getInstance().getToken();
//            if (!TextUtils.isEmpty(token))
//                builder.add("token", token);
         }

        return builder.build();

    }


    enum HttpMethodType {

        GET,
        POST,

    }


}
