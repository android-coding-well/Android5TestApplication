package com.gosuncn.test;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gosuncn.test.rxjava.CommonEvent;
import com.gosuncn.test.rxjava.RxBus;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * http://blog.chinaunix.net/uid-20771867-id-5187376.html
 */
public class RxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        test3();

        //延时操作
        Observable.just("延时5s").delay(5000, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        RxBus.getDefault().post(new CommonEvent<Bitmap>(1, "CommonEvent is comming"));
        Observable.just(new int[]{1,2});
    }

    private void test() {

        Observable<String> stringObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("hi");
                subscriber.onNext("world");
                subscriber.onCompleted();
            }
        });
        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        };

        stringObservable
                .subscribe(stringSubscriber);


    }


    private void test2() {
        String[] s = {"hello", "world"};
        Observable<String[]> stringObservable = Observable.just(s);
        stringObservable.subscribe(new Action1<String[]>() {
            @Override
            public void call(String[] s) {
                Toast.makeText(RxJavaActivity.this, s[0], Toast.LENGTH_SHORT).show();
                Toast.makeText(RxJavaActivity.this, s[1], Toast.LENGTH_SHORT).show();
            }
        });

        Observable.from(s).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {

            }
        });
    }

    private void test3() {
        Observable.just("hello world!")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " --hwj";
                    }
                })
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return "no.1  " + s;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void test4() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("ddd");
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(RxJavaActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     *
     * http://www.tuicool.com/articles/3YF3eqf
     * https://github.com/GoogleChrome/custom-tabs-client
     * @param view
     */
    public void turnToWeb(View view) {
        String url = "https://www.baidu.com";
        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                .setShowTitle(true)
                .setToolbarColor(ContextCompat.getColor(this,  R.color.blue))
                .setStartAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .build();
        // 启动 chrome
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }
}
