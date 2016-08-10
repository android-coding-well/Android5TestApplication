package com.gosuncn.test;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ScrollingActivity extends AppCompatActivity {
    CollapsingToolbarLayout toolBarLayout;
    CoordinatorLayout rootCLay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        rootCLay=(CoordinatorLayout)findViewById(R.id.cl_root);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        //设置收缩时的背景色
        toolBarLayout.setContentScrimColor(getResources().getColor(R.color.colorAccent));
        //设置收缩时的标题色
        toolBarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.holo_green_dark));
        //设置展开时的标题色
        toolBarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));


       /* Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(final Palette palette) {
//                Palette.Swatch a = palette.getVibrantSwatch();//有活力
//                Palette.Swatch b = palette.getDarkVibrantSwatch();//有活力 暗色
//                Palette.Swatch c = palette.getLightVibrantSwatch();//有活力 亮色
//                Palette.Swatch d = palette.getMutedSwatch();//柔和
//                Palette.Swatch e = palette.getDarkMutedSwatch();//柔和 暗色
//                Palette.Swatch f = palette.getLightMutedSwatch();//柔和 亮色
//                int color1 = a.getBodyTextColor();//内容颜色
//                int color2 = a.getTitleTextColor();//标题颜色
//                int color3 = a.getRgb();//rgb颜色
                int defaultColor = getResources().getColor(android.R.color.holo_red_dark);
                int defaultTitleColor = getResources().getColor(android.R.color.white);
                int bgColor = palette.getDarkVibrantColor(defaultColor);
                int titleColor = palette.getLightVibrantColor(defaultTitleColor);
                toolBarLayout.setContentScrimColor(bgColor);
                toolBarLayout.setCollapsedTitleTextColor(titleColor);
                toolBarLayout.setExpandedTitleColor(titleColor);
            }
        });*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
