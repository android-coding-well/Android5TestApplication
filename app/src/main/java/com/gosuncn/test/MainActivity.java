package com.gosuncn.test;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.gosuncn.test.GLSurfaceView.Test2Activity;
import com.gosuncn.test.openfire.LoginActivity;
import com.gosuncn.test.recyclerview.RecycleViewActivity;
import com.gosuncn.test.rxjava.CommonEvent;
import com.gosuncn.test.rxjava.Request1;
import com.gosuncn.test.rxjava.RxBus;
import com.gosuncn.test.rxjava.UrlRootInfo;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    CoordinatorLayout rootCLay;
    Subscription rxSubscription;
    View view;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSearchView();
        initTextInputLayout();

        rootCLay = (CoordinatorLayout) findViewById(R.id.cl_root);
        view = (View) findViewById(R.id.v_white);
        //Toolbar示例
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("标题");


        setSupportActionBar(toolbar);

        //当使用CollapsingToolbarLayout实现折叠效果时，这两个属性变为无效，标题要在CollapsingToolbarLayout设置
        //toolbar.setTitle("标题");
        //toolbar.setSubtitle("子标题");


        toolbar.setLogo(R.mipmap.ic_launcher);//设置logo
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Toolbar Navigation clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //FloatingActionButton 示例
        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                                startActivity(intent);

                            }
                        }).show();
            }
        });


        final View oval = this.findViewById(R.id.iv_img2);
        oval.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                /*  第一个参数view：是你要进行圆形缩放的 view；
                    第二和第三个参数：分别是开始缩放点的 x 和 y 坐标；
                    第四和第五：分别是开始的半径和结束的半径*/
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        oval,
                        oval.getWidth() / 2,
                        oval.getHeight() / 2,
                        oval.getWidth(),
                        0);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });
        final View rect = this.findViewById(R.id.iv_img4);

        rect.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Animator animator = ViewAnimationUtils.createCircularReveal(
                        rect,
                        0,
                        0,
                        0,
                        (float) Math.hypot(rect.getWidth(), rect.getHeight()));
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(2000);
                animator.start();
            }
        });


        final ImageView img = (ImageView) findViewById(R.id.iv_img11);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionDrawable transition = (TransitionDrawable) (getResources().getDrawable(R.drawable.expand_collapse));
                img.setImageDrawable(transition);
                transition.startTransition(1000);
            }
        });


         rxSubscription = RxBus.getDefault().toObserverable(CommonEvent.class)
        .subscribe(new Action1<CommonEvent>() {
            @Override
            public void call(CommonEvent s) {
                Toast.makeText(MainActivity.this, s.text, Toast.LENGTH_SHORT).show();
            }
        });

        new Request1().getServer(new Callback<UrlRootInfo>() {

            @Override
            public void onResponse(Call<UrlRootInfo> call, Response<UrlRootInfo> response) {
                Log.e("123456", "onResponse:code=" + response.code()+" " + response.body().help_url);
            }

            @Override
            public void onFailure(Call<UrlRootInfo> call, Throwable t) {

                Log.e("123456", "  onFailure:" + t.getMessage());
            }
        });

        new Request1().upload(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.e("123456", "onResponse:code=" + response.code());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("123456", "  onFailure:" + t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    private void initTextInputLayout() {

        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.til_edit);
        textInputLayout.setHint("hint2");
        //textInputLayout.setError("error");
    }

    private void initSearchView() {
        SearchView searchView = (SearchView) findViewById(R.id.sv_search);
        searchView.onActionViewExpanded();//表示在内容为空时不显示取消的x按钮，内容不为空时显示.
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(getApplicationContext(), "onClose", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "onQueryTextSubmit:" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getApplicationContext(), "onQueryTextChange:" + newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_scrolling) {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                Animator animator=ViewAnimationUtils.createCircularReveal(view,view.getWidth()/2,view.getHeight()/2,5,5000).setDuration(300);
                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animator.start();
            }else{
                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                startActivity(intent);
            }

            return true;
        }
        if (id == R.id.action_vector) {
            Intent intent = new Intent(getApplicationContext(), VectorActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_explosion) {
            Intent intent = new Intent(getApplicationContext(), ExplosionActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_recycler) {
            Intent intent = new Intent(getApplicationContext(), RecycleViewActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_glsurfaceview) {
            Intent intent = new Intent(getApplicationContext(), Test2Activity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_tab) {
            Intent intent = new Intent(getApplicationContext(), TabActivity.class);
            startActivity(intent);

            return true;
        }

        if (id == R.id.action_rxjava) {
            Intent intent = new Intent(getApplicationContext(), RxJavaActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_loadingview) {
            Intent intent = new Intent(getApplicationContext(), LoadingViewActivity.class);
            startActivity(intent);

            return true;
        }
        if (id == R.id.action_bottomsheet) {
            Intent intent = new Intent(getApplicationContext(), BottomSheet2Activity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_pulltorefresh) {
            Intent intent = new Intent(getApplicationContext(), PullToRefreshActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_glide) {
            Intent intent = new Intent(getApplicationContext(), GlideActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_openfire) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.mi1) {
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }
        }
        if (id == R.id.mi2) {
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
            }
        }
        if (id == R.id.mi3) {
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
               getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                recreate();
            }
        }

        if (id == R.id.mi4) {
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                recreate();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
