# RxBus
[![](https://jitpack.io/v/liuguangli/RxBus.svg)](https://jitpack.io/#liuguangli/RxBus)

RxBus 是一个发布/订阅模式的事件总线，用法和 EventBus 一样简单。RxBus 基于 RxJava 开发，除了拥有和 EventBus
一样简单的事件总线机制之外，还拥有 RxJava 的丰富特性。


![](https://github.com/liuguangli/RxBus/blob/master/RxBus.png)

# how to use

1. 定义 EventData:

         public static class EventData { /* Additional fields if needed */ }


2. 注解定义订阅者的回调方法，回调方法回在 UI 线程中执行:

         @RegisterBus
         public void onMessageEvent(MessageEvent event) {/* Do something */};

3. 注册／解注册。 观察者需要被注册到 RxBus，其 @RegisterBus 标记的方法才会被扫描到，在不需要的地方记得解注册。

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

3. 发送数据:


        // send data in thread

        Data data = new Data();

        Data.setContent("hello world");

        RxBus.getInstance().send(data);


# 比 EventBus 多的优点

RxBus 提供 chainProcess 方法来包装一个处理过程, 处理结果会自动发送到观察者。


![](https://github.com/liuguangli/RxBus/blob/master/RxBusChain.png)

1. 在 MVP 架构的 M 层你可以这样用

        RxBus.getInstance().chainProcess(new Func1() {
            @Override
            public Object call(Object o) {
                // do something in IO thread, the return data can be subscribe and receive.
                // getUser() is a IO/net process
                User user = getUser();
                user.setId(uid);
                user.setName("大利猫");
                return user;
            }
        });


2. 然后在 P 层接收:

           /**
           * @RegisterBus mark this method to receive data in UI thread
           * @param user
           */

           @RegisterBus

           public void onUser(User user) {

             userView.showUser(user);

           }


# Gradle independence

Step 1. Add the JitPack repository to your build file, Add it in your root build.gradle at the end of repositories:

       	allprojects {
       		repositories {
       			...
       			maven { url 'https://jitpack.io' }
       		}
       	}

Step 2. Add the dependency

       	dependencies {
       		compile 'com.github.liuguangli:RxBus:1.0'
       	}

# License

MIT License

Copyright (c) 2017 刘光利

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE