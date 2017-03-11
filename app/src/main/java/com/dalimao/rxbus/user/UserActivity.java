package com.dalimao.rxbus.user;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dalimao.rxbus.R;
import com.dalimao.rxbus.user.bean.User;
import com.dalimao.rxbus.user.model.UserManagerImpl;
import com.dalimao.rxbus.user.presenter.UserPresenter;

public class UserActivity extends AppCompatActivity implements IUserView, View.OnClickListener {

    private Button mBtn;
    private TextView mUser;
    private RelativeLayout mActivityUser;
    private View mLoading;
    private UserPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
        presenter = new UserPresenter(new UserManagerImpl(), this);
        presenter.register();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unRegister();
    }

    @Override
    public void showUser(User user) {

        mUser.setText("userName:" + user.getName());
        mLoading.setVisibility(View.GONE);
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mUser = (TextView) findViewById(R.id.user);
        mLoading =  findViewById(R.id.loading);

        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:

                // start get user
                presenter.fetchUser("007");
                mLoading.setVisibility(View.VISIBLE);
                break;
        }
    }
}
