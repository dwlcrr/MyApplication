package com.example.test.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.test.R;
import com.example.test.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by guxiuzhong on 2015/7/6.
 */
public class TestRxFragment extends Fragment {

    public static Fragment getInstance(Bundle bundle) {
        TestRxFragment fragment = new TestRxFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmnet_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        TextView tv = (TextView) view.findViewById(R.id.tv_id);
        tv.setText(getArguments().getString("title"));
        testRx();
    }

    private void testRx() {
        //1 .使用create( ),最基本的创建方式：先创建个数据发射源,很好理解，就是发射数据用的
        Observable<String> sender = Observable.create(new OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("Hi，Weavey！");  //发送数据"Hi，Weavey！"
                subscriber.onCompleted();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        });
        //2.使用just( )，将为你创建一个Observable并自动为你调用 onNext( ) 发射数据：
        Observable observableJust = Observable.just("22", "112");

        //3.使用from( )，遍历集合，发送每个item：
        //遍历list 每次发送一个注意，just()方法也可以传list，但是发送的是整个list对象，而from（）发送的是list的一个item
        List<String> list = new   ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");
        Observable fromObservable = Observable.from(list);

        //4.使用interval( ),创建一个按固定时间间隔发射整数序列的Observable，可用作定时器：
        Observable intervalObservable = Observable.interval(1, TimeUnit.SECONDS);//每隔一秒发送一次

        //5.使用timer( ),创建一个Observable，它在一个给定的延迟后发射一个特殊的值，等同于Android中Handler的postDelay( )方法：
        Observable timeObservable = Observable.timer(3, TimeUnit.SECONDS);  //3秒后发射一个值

        //再创建个数据接收源，同理，接收数据用的
        Observer<String> receiver = new Observer<String>() {
            @Override
            public void onCompleted() {
                //数据接收完成时调用
            }

            @Override
            public void onError(Throwable e) {
                //发生错误调用
            }

            @Override
            public void onNext(String s) {
                //正常接收数据调用
                System.out.print(s);  //将接收到来自sender的问候"Hi，Weavey！"
            }
        };
        sender.subscribe(receiver);

        //如果你不在意数据是否接收完或者是否出现错误，即不需要Observer的 onCompleted() 和 onError() 方法，可使用 Action1 ，
        // subscribe() 支持将 Action1 作为参数传入,RxJava将会调用它的 call 方法来接收数据，代码如下：

        Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                List<User> userList = null;
                //从数据库获取用户表数据并赋给userList
                subscriber.onNext(userList);
            }
        }).flatMap(new Func1<List<User>, Observable<User>>() {
            @Override
            public Observable<User> call(List<User> users) {
                return Observable.from(users);
            }
        }).filter(new Func1<User, Boolean>() {
            @Override
            public Boolean call(User user) {
                return user.getName().equals("小明");
            }
        }).map(new Func1<User, User>() {
            @Override
            public User call(User user) {
                //根据小明的数据user从数据库查找出小明的父亲user2
                User user2 = new User();
                return user2;
            }
        }).subscribe(new Action1<User>() {
            @Override
            public void call(User user) {
                //拿到谜之小明的爸爸的数据
            }
        });
    }
}
