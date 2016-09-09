package com.example.john.okhttp_demo.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.john.okhttp_demo.R;
import com.example.john.okhttp_demo.presenter.NetPresenter;
import com.example.john.okhttp_demo.widgt.CropSquareTrans;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.image)
    ImageView mImage;

    NetPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mPresenter = new NetPresenter(this);
    }


    @Override
    public void showTU(final byte[] bytes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                bitmap = new CropSquareTrans().transform(bitmap);
                mImage.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void showFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "失败了", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void showJson(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "" + string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showPost(final String string) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "" + string, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick({R.id.btn, R.id.btn_getString, R.id.btn_post})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                mPresenter.doGet();
                break;
            case R.id.btn_getString:
                mPresenter.doGetJsonString();
                break;
            case R.id.btn_post:
                mPresenter.Register("111@qq.com", "12345");
                break;
        }
    }
}


