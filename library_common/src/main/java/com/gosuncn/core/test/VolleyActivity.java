/*
package com.gosuncn.core.test;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.gosuncn.core.R;
import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.volley.BitmapCache;
import com.gosuncn.core.volley.VolleyUtil;

public class VolleyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView netimgIView;
    private Button loadwebBtn;
    private Button loadimg1Btn;
    private Button loadimg2Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);
        initView();
    }


    private void initView() {
        netimgIView = (ImageView) findViewById(R.id.iv_volley_netimg);
        loadwebBtn = (Button) findViewById(R.id.btn_volley_loadweb);
        loadimg1Btn = (Button) findViewById(R.id.btn_volley_loadimg1);
        loadimg2Btn = (Button) findViewById(R.id.btn_volley_loadimg2);
        loadwebBtn.setOnClickListener(this);
        loadimg1Btn.setOnClickListener(this);
        loadimg2Btn.setOnClickListener(this);
    }


    private void loadWeb() {
        String url = "http://www.baidu.com";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(VolleyActivity.this, "success", Toast.LENGTH_LONG).show();
                Toast.makeText(VolleyActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolleyActivity.this, "error", Toast.LENGTH_LONG).show();
                Toast.makeText(VolleyActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setTag("123");
        VolleyUtil.getHttpQueue(this).add(stringRequest);
    }

    private void loadImg2() {
        String url = "https://www.baidu.com/img/bdlogo.png";
        ImageLoader loader = new ImageLoader(VolleyUtil.getHttpQueue(this), new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(netimgIView, R.drawable.ic_launcher, R.drawable.ic_launcher);
        loader.get(url, listener);
    }


    private void loadImg1() {
        String url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/logo_white_81d09391.png";
        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                netimgIView.setImageBitmap(response);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(VolleyActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        VolleyUtil.getHttpQueue(this).add(request);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_volley_loadweb:
                loadWeb();
                break;
            case R.id.btn_volley_loadimg1:
                loadImg1();
                break;
            case R.id.btn_volley_loadimg2:
                loadImg2();
                break;
        }
    }
}
*/
