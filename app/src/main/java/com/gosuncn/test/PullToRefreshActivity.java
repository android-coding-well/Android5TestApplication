package com.gosuncn.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gosuncn.core.utils.DensityUtil;

import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class PullToRefreshActivity extends AppCompatActivity {

    PtrFrameLayout ptrFrame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        ptrFrame=(PtrFrameLayout)findViewById(R.id.store_house_ptr_frame);

        // 1 StoreHouseHeader
        final StoreHouseHeader storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, DensityUtil.dp2px(this, 15), 0, 0);
        storeHouseHeader.initWithStringArray(R.array.storehouse);
        storeHouseHeader.initWithString("I love you");//using a string, support: A-Z 0-9 -,此句只能放置在initWithStringArray之后才生效

        //2 PtrClassicDefaultHeader
        PtrClassicDefaultHeader ptrClassicDefaultHeader= new PtrClassicDefaultHeader(this);

        //3 MaterialHeader
        MaterialHeader materialHeader= new MaterialHeader(this);
        materialHeader.setColorSchemeColors(new int[]{Color.BLUE});

        //4 自定义header
        //View header1 = LayoutInflater.from(this).inflate(R.layout.pull_to_refresh_header,ptrFrame,false);
        MyHeader header2=new MyHeader(this);


        ptrFrame.setHeaderView(storeHouseHeader);
        ptrFrame.addPtrUIHandler(storeHouseHeader);
        //ptrFrame.setPinContent(true);//content是否固定
        ptrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrame.refreshComplete();
                    }
                }, 1800);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private class MyHeader extends FrameLayout implements PtrUIHandler{

        public MyHeader(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView();
        }


        public MyHeader(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView();
        }

        public MyHeader(Context context) {
            super(context);
            initView();
        }

        ProgressBar progressBar;
        TextView textView;

        private void initView(){
            View header = LayoutInflater.from(getContext()).inflate(R.layout.pull_to_refresh_header, this);
            progressBar=(ProgressBar) header.findViewById(R.id.progressBar3);
            textView=(TextView) header.findViewById(R.id.tv_text);
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }



        /**
         * When the content view has reached top and refresh has been completed, view will be reset.
         *
         * @param frame
         */
        @Override
        public void onUIReset(PtrFrameLayout frame) {
            progressBar.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }

        /**
         * prepare for loading
         *
         * @param frame
         */
        @Override
        public void onUIRefreshPrepare(PtrFrameLayout frame) {
            progressBar.setVisibility(View.GONE);
            textView.setText("即将刷新");
            textView.setVisibility(View.VISIBLE);
        }

        /**
         * perform refreshing UI
         *
         * @param frame
         */
        @Override
        public void onUIRefreshBegin(PtrFrameLayout frame) {
            textView.setText("刷新中...");
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * perform UI after refresh
         *
         * @param frame
         */
        @Override
        public void onUIRefreshComplete(PtrFrameLayout frame) {
            progressBar.setVisibility(View.GONE);
            textView.setText("刷新完成");
            textView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

        }
    }
}
