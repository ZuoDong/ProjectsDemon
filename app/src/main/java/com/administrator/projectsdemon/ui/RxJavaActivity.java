package com.administrator.projectsdemon.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.administrator.projectsdemon.R;
import com.blankj.utilcode.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        initView();

        rxTest();
    }

    private void rxTest() {
        /**
         * 普通样式
         */
        //被观察者
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("HelloWord");
                subscriber.onCompleted();
            }
        });
        //观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                text.setText("onNext-->"+s);
            }
        };
        //注册观察者
        observable.subscribe(subscriber);
        /**
         * 数字样式
         */
        Observable.just(6).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                text.append("\n"+integer);
            }
        });
        /**
         * 字符串样式
         */
        Observable.just("我是字符串样式").subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                text.append("\n"+s);
            }
        });
        /**
         * map预处理
         */
        Observable.just("I am -->").map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                return s+"map";
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                text.append("\nmap处理的结果"+s);
            }
        });
        /**
         * timer操作符
         */
        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(new Func1<Long, Object>() {
            @Override
            public Object call(Long aLong) {
                ToastUtils.showShortToast("timer操作符，延迟2秒显示");
                return null;
            }
        }).subscribe();
//        Observable.mergeDelayError(loadBitmapFromLocal().subscribeOn(Schedulers.io()),
//                loadBitmapFromNet().subscribeOn(Schedulers.newThread()),
//                Observable.timer(3,TimeUnit.SECONDS)).map(new Func1() {
//            @Override
//            public Object call(Object o) {
//                return null;
//            }
//        }).sample(2,TimeUnit.SECONDS,AndroidSchedulers.mainThread()).flatMap(new Func1() {
//            @Override
//            public Object call(Object o) {
//                return null;
//            }
//        }).subscribe(new Action1() {
//            @Override
//            public void call(Object o) {
//
//            }
//        });
        /**
         * create创建操作符
         */
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                text.append("\n\n---create");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    private Observable loadBitmapFromLocal() {
        return null;
    }

    private void initView() {
        text = (TextView) findViewById(R.id.text);
    }
}
