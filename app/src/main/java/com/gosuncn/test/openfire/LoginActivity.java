package com.gosuncn.test.openfire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.ACacheUtil;
import com.gosuncn.test.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    String serverIp;
    int serverPort;
    String serverName;

    LinearLayout rootLL;

    TextInputLayout userTIL;
    TextInputLayout pwdTIL;

    EditText userET;
    EditText pwdET;

    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initDatas();
    }

    private void initDatas() {
        String ip = ACacheUtil.get(this).getAsString("server_ip");
        String port = ACacheUtil.get(this).getAsString("server_port");
        String name = ACacheUtil.get(this).getAsString("server_name");
        if (TextUtils.isEmpty(ip)) {
            serverIp = "192.168.16.135";
            serverPort = 5222;
            serverName = "gosuncn";
        } else {
            serverIp = ip;
            serverPort = Integer.parseInt(port);
            serverName = name;
        }
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                if (!SmackManager.getInstance().isConnectSuccess()) {
                    SmackManager.getInstance().connect(serverIp, serverPort, serverName);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();

    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {

    }

    private void initViews() {
        rootLL = (LinearLayout) findViewById(R.id.ll_root);
        userTIL = (TextInputLayout) findViewById(R.id.til_user);
        pwdTIL = (TextInputLayout) findViewById(R.id.til_pwd);
        userET = (EditText) findViewById(R.id.et_user);
        pwdET = (EditText) findViewById(R.id.et_pwd);
        loginBtn = (Button) findViewById(R.id.btn_login);


    }

    public void setting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        if (!judgeEmpty()) {
            if (SmackManager.getInstance().isConnectSuccess()) {
                showLoadingDialog();
                boolean isSuccess = SmackManager.getInstance().login(getUser(), getPwd());
                cancelLoadingDialog();
                if (isSuccess) {
                    Intent intent = new Intent(LoginActivity.this, ContactActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(rootLL, "登录失败", Snackbar.LENGTH_SHORT).show();
                }

            } else {
                Snackbar.make(rootLL, "未连接", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private String getUser() {
        return userET.getText().toString();
    }

    private String getPwd() {
        return pwdET.getText().toString();
    }

    private boolean judgeEmpty() {
        if (TextUtils.isEmpty(getUser())) {
            userTIL.setError("用户名不能为空");
            return true;
        }
        if (TextUtils.isEmpty(getPwd())) {
            pwdTIL.setError("密码不能为空");
            return true;
        }
        return false;
    }
}
