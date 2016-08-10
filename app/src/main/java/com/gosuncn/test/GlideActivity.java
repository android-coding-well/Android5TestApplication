package com.gosuncn.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.senab.photoview.PhotoView;

/**
 * http://www.codeceo.com/article/android-glide-usage.html
 */
public class GlideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        PhotoView photoView = (PhotoView) findViewById(R.id.pv_photo);
        //with参数最好传递Activity/Fragment,这样可以与生命周期同步，而不是context
        Glide.with(GlideActivity.this)
                .load("http://img3.imgtn.bdimg.com/it/u=4245198817,693717552&fm=21&gp=0.jpg")
                // .diskCacheStrategy(DiskCacheStrategy.ALL)//既缓存全尺寸又缓存其他尺寸
                .placeholder(R.drawable.ic_launcher)//默认图
                .error(R.drawable.ic_error)//加载失败
               // .override(200, 200)//设置尺寸
                .crossFade()//淡入淡出
                //.thumbnail(0.5f)//缩略比例
                .into(photoView);
       // Glide.clear();//清除掉所有图片加载请求

        CircleImageView circleView = (CircleImageView) findViewById(R.id.civ_circle);
        Glide.with(GlideActivity.this)
                .load("http://img55.it168.com/ArticleImages/fnw/2016/0105/5ba4bf5a-1ca2-4b52-98c9-eb17544ba94d.jpg")
                .into(circleView);

    }
}
