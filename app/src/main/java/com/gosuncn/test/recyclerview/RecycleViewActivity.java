package com.gosuncn.test.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gosuncn.test.R;
import com.gosuncn.test.recyclerview.decoration.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * RecycleView 示例
 */
public class RecycleViewActivity extends AppCompatActivity {

    SwipeRefreshLayout refreshSRLay;
    RecyclerView listRView;
    MyAdapter adapter;
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);

        initViews();
        initDatas();
    }

    private void initDatas() {
        for (int i = 'A'; i < 'z'; i++) {
            list.add("" + (char) i);
        }
    }

    private void initViews() {
        refreshSRLay = (SwipeRefreshLayout) findViewById(R.id.srl_refresh);
        listRView = (RecyclerView) findViewById(R.id.rv_list);
        refreshSRLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshSRLay.setRefreshing(true);
                Snackbar.make(refreshSRLay, "刷新", Snackbar.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshSRLay.setRefreshing(false);
                    }
                }, 2000);
            }
        });

       /*1------设置布局管理器
        LinearLayoutManager 线性管理器，支持横向、纵向。
        GridLayoutManager 网格布局管理器
        StaggeredGridLayoutManager 瀑布就式布局管理器*/
        listRView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //listRView.setLayoutManager(new GridLayoutManager(this,3));
        //HORIZONTAL表示多少行，VERTICAL表示有多少列
        // listRView.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL));


        //2------设置适配器
        //listRView.setAdapter(adapter = new HomeAdapter());
        listRView.setAdapter(adapter = new MyAdapter(this, list));


        //3------设置监听器
        adapter.setOnItemClickLitener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                list.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                list.add("add" + position);
                adapter.notifyDataSetChanged();
            }
        });

        //4------设置动画
        //设置Item增加、移除动画
        listRView.setItemAnimator(new DefaultItemAnimator());


        //5-----设置分割

        //1)默认
        listRView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        //2)自定义
      /*  listRView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(Color.RED)
                .sizeResId(R.dimen.divider)
                .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                .build());*/
        //3)自定义画笔
       /* Paint paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        listRView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this).paint(paint).build());*/
      /*  //4)设置9-patch图
        listRView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.mipmap.ic_launcher)
                .size(15)
                .build());*/

    }

    class MyAdapter extends BaseRecyclerAdapter<String> {

        public MyAdapter(Context context, @NonNull List<String> list) {
            super(context, list);
        }

        /**
         * 在此处返回item布局id
         *
         * @return
         */
        @Override
        public int getItemLayoutResId() {
            return R.layout.item_home;
        }

        /**
         * 设置控件的显示内容
         *
         * @param holder
         * @param position
         * @param s
         */
        @Override
        public void convert(RecyclerViewHolder holder, int position, String s) {
            holder.setText(R.id.tv_item_home, s);
        }
    }

}
