package com.dalimao.rxbus.user.model;

import com.dalimao.databus.RxBus;
import com.dalimao.rxbus.user.bean.User;

import rx.functions.Func1;

/**
 * Created by liuguangli on 17/3/11.
 */

public class UserManagerImpl implements IUserManager {
    @Override
    public void fetchUser(final String uid) {
        RxBus.getInstance().chainProcess(new Func1() {
            @Override
            public Object call(Object o) {
                // do something in IO thread, the return data can be subscribe and receive.
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                User user = new User();
                user.setId(uid);
                user.setName("大利猫");
                return user;
            }
        });
    }
}
