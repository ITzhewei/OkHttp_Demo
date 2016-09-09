package com.example.john.okhttp_demo.presenter;

/**
 * Created by ZheWei on 2016/9/8.
 */
public interface IPresenter {
    void doGet();

    /**
     * 得到json字符串数据
     */
    void doGetJsonString();

    /**
     * 注册操作
     */
    void Register(String email, String pass);
}
