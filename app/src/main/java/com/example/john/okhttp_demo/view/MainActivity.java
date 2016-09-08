package com.example.john.okhttp_demo.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.john.okhttp_demo.R;
import com.example.john.okhttp_demo.presenter.NetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements IView {


    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.btn)
    Button mBtn;

    NetPresenter mPresenter;

    private OkHttpClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mClient = new OkHttpClient();

        mPresenter = new NetPresenter(this);
    }


    @OnClick(R.id.btn)
    public void onClick() {
        mPresenter.doGet();
    }

    @Override
    public void showTU(final byte[] bytes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
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

}
