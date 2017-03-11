package com.dalimao.rxbus.user.presenter;

import com.dalimao.databus.RegisterBus;
import com.dalimao.databus.RxBus;
import com.dalimao.rxbus.user.IUserView;
import com.dalimao.rxbus.user.bean.User;
import com.dalimao.rxbus.user.model.IUserManager;

/**
 * Created by liuguangli on 17/3/11.
 * MVP - P
 */

public class UserPresenter {
    private IUserManager manager;
    private IUserView userView;

    public UserPresenter(IUserManager manager, IUserView userView) {
        this.manager = manager;
        this.userView = userView;
    }
    public void fetchUser(String uid) {
        manager.fetchUser(uid);
    }

    /**
     *  register self to RxBus
     */
    public void register() {
        RxBus.getInstance().register(this);
    }

    /**
     *  unregister from RxBus
     */
    public void unRegister() {
        RxBus.getInstance().unRegister(this);
    }

    /**
     * @RegisterBus mark this method to receive data in UI thread
     * @param user
     */
    @RegisterBus
    public void onUser(User user) {
        userView.showUser(user);
    }
}
