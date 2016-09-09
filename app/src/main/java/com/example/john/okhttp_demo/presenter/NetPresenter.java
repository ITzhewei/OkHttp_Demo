package com.example.john.okhttp_demo.presenter;

import com.example.john.okhttp_demo.model.Model;
import com.example.john.okhttp_demo.view.IView;

/**
 * Created by ZheWei on 2016/9/8.
 */
public class NetPresenter implements IPresenter {

    IView mView;


    public NetPresenter(IView view) {
        mView = view;
    }


    @Override
    public void doGet() {
        Model model = new Model();
        model.getNet(new Model.Listener() {
            @Override
            public void onSuccess(byte[] bytes) {
                mView.showTU(bytes);
            }

            @Override
            public void onFailed() {
                mView.showFailed();
            }
        });
        //        model.setOnListener(new Model.Listener() {
        //            @Override
        //            public void onSuccess(byte[] bytes) {
        //                mView.showTU(bytes);
        //            }
        //
        //            @Override
        //            public void onFailed() {
        //                mView.showFailed();
        //            }
        //        });
    }


    @Override
    public void doGetJsonString() {
        Model model = new Model();
        model.getJson(new Model.JsonListener() {
            @Override
            public void onSuccess(String string) {
                mView.showJson(string);
            }

            @Override
            public void onFailed() {

            }
        });
    }

    @Override
    public void Register(String email, String pass) {
        Model model = new Model();
        model.postJson(email, pass, new Model.JsonListener() {
            @Override
            public void onSuccess(String string) {
                mView.showPost(string);
            }

            @Override
            public void onFailed() {

            }
        });
    }

}
