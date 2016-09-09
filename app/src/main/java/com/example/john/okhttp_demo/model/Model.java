package com.example.john.okhttp_demo.model;

import com.example.john.okhttp_demo.manager.OkManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZheWei on 2016/9/8.
 * 数据请求模块---M
 */
public class Model {

    private OkHttpClient mClient;
    private String url = "https://static.nowcoder.com/images/res/essenceTopic/al.png";
    //    private Listener mListener;
    OkManager mOkManager;

    private String JsonUrl = "http://web.juhe.cn:8080/constellation/getAll";

    private String postUrl = "https://www.obisoft.com.cn/api/Register";

    public Model() {
        mClient = new OkHttpClient();
        mOkManager = OkManager.getInstance();
    }

    /**
     * 进行网络数据获取
     */
    public void getNet(final Listener listener) {
        Request request = new Request.Builder().get().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                listener.onSuccess(bytes);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                listener.onFailed();
            }
        });
    }

    /**
     * 进行json获取字符串
     */
    public void getJson(final JsonListener listener) {
        mOkManager.asyncJsonStringByUrl(JsonUrl, new OkManager.Func1() {
            @Override
            public void onResponse(String result) {
                listener.onSuccess(result);
            }
        });
    }

    /**
     * post请求
     */
    public void postJson(String email, String pass, final JsonListener listener) {
        Map<String, String> map = new HashMap<>();
        map.put("Email", email);
        map.put("Password", pass);
        if (mOkManager != null) {
            mOkManager.sendComplexForm(postUrl, map, new OkManager.Func4() {
                @Override
                public void onResponse(JSONObject result) {
                    listener.onSuccess(result.toString());
                }
            });
        }
    }


    public interface JsonListener {
        void onSuccess(String string);

        void onFailed();
    }

    //    public void setOnListener(Listener listener) {
    //
    //        mListener = listener;
    //    }

    public interface Listener {
        void onSuccess(byte[] bytes);

        void onFailed();
    }
}
