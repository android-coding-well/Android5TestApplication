package com.gosuncn.test;

import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.ImageView;

/**
 * 在build.gradle中加入
 * android {
 * defaultConfig {
 * vectorDrawables.useSupportLibrary = true
 * }
 * }
 */
public class VectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
        Transition explode = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            explode = TransitionInflater.from(this).inflateTransition(android.R.transition.explode);
                getWindow().setEnterTransition(explode);

        }


        //演示矢量动画，只支持android5.0
        //1--创建vector xml，
        //2--创建animated_vector xml
        //3--在animated_vector中指定动画部分
        //4--在xml中设置src为animated_vector，如 android:src="@drawable/animatedvector_android"
        //5--启动动画，参考以下代码
        ImageView androidIView = (ImageView) findViewById(R.id.iv_android);
        AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(this, R.drawable.animatedvector_android);
        androidIView.setImageDrawable(animatedVectorDrawableCompat);
        ((Animatable) androidIView.getDrawable()).start();


        final ImageView demo1IView = (ImageView) findViewById(R.id.iv_demo1);
        demo1IView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(VectorActivity.this, R.drawable.animatedvector_demo1);
                demo1IView.setImageDrawable(animatedVectorDrawableCompat);
                ((Animatable) demo1IView.getDrawable()).start();


            }
        });


        //这个在api19的时候奔溃，原因未知
        final ImageView demo2IView = (ImageView) findViewById(R.id.iv_demo2);
        demo2IView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatedVectorDrawableCompat animatedVectorDrawableCompat = AnimatedVectorDrawableCompat.create(VectorActivity.this, R.drawable.animatedvector_square);
                demo2IView.setImageDrawable(animatedVectorDrawableCompat);
                ((Animatable) demo2IView.getDrawable()).start();

            }
        });


    }
}
