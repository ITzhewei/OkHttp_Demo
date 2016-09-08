package com.example.john.okhttp_demo.model;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZheWei on 2016/9/8.
 */
public class Model {

    private OkHttpClient mClient;
    private String url = "https://static.nowcoder.com/images/res/essenceTopic/al.png";
    private Listener mListener;

    public Model() {
        mClient = new OkHttpClient();
    }

    /**
     * 进行网络数据获取
     */
    public void getNet() {
        Request request = new Request.Builder().get().url(url).build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                mListener.onSuccess(bytes);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                mListener.onFailed();
            }
        });
    }

    public void setOnListener(Listener listener) {

        mListener = listener;
    }

    public interface Listener {
        void onSuccess(byte[] bytes);

        void onFailed();
    }
}
