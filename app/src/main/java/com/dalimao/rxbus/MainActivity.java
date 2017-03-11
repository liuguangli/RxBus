package com.dalimao.rxbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.dalimao.databus.RegisterBus;
import com.dalimao.databus.RxBus;
import com.dalimao.rxbus.user.UserActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn;
    private TextView mData;
    private View mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        // register to RxBus
        RxBus.getInstance().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregister from RxBus
        RxBus.getInstance().unRegister(this);
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mData = (TextView) findViewById(R.id.data);
        mLoading =  findViewById(R.id.loading);
        findViewById(R.id.btn_to_user).setOnClickListener(this);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:

                mLoading.setVisibility(View.VISIBLE);
                getData();
                break;
            case R.id.btn_to_user:
                startActivity(new Intent(MainActivity.this, UserActivity.class));
                break;
        }
    }

    private void getData() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               // send data in thread
                Data data = new Data();
                data.setContent("hello world");
                RxBus.getInstance().send(data);

            }
        }.start();
    }

    /**
     * @RegisterBus mark to subscribe , will receive in UI thread
     */
    @RegisterBus
    public void onData(Data data) {

        mData.setText(data.getContent());
        mLoading.setVisibility(View.GONE);
    }

    /**
     *  data what you will send
     */
    public static class Data {
        String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

