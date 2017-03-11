# RxBus
RxBus is a publish/subscribe event bus optimized for Android, like EventBus.
RxBus Based on Rx Java,it has a simple event bus as EventBus, but also the rich features of Rx Java.

![]()

# how to use

1.Define EventData:

    public static class EventData { /* Additional fields if needed */ }


2.Prepare subscribers: Declare and annotate your subscribing method, it will called in UI thread:

     @RegisterBus
    public void onMessageEvent(MessageEvent event) {/* Do something */};

3.Register and unregister your subscriber. For example on Android, activities and fragments should usually register according to their life cycle:

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
3.Post events:


      // send data in thread
       Data data = new Data();
       Data.setContent("hello world");
       RxBus.getInstance().send(data);


＃ Advantages other than EventBus
RxBus provides chainProcess method to package a process, and the results will be sent to the subscriber.

![]()


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


 then in UI thread:

       /**
        * @RegisterBus mark this method to receive data in UI thread
        * @param user
        */
       @RegisterBus
       public void onUser(User user) {
           userView.showUser(user);
       }

