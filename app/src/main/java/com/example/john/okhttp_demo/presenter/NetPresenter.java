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
        model.getNet();
        model.setOnListener(new Model.Listener() {
            @Override
            public void onSuccess(byte[] bytes) {
                mView.showTU(bytes);
            }

            @Override
            public void onFailed() {
                mView.showFailed();
            }
        });
    }
}
