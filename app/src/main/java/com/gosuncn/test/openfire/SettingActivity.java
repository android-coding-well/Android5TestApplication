package com.gosuncn.test.openfire;

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

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingActivity extends BaseActivity {

    LinearLayout rootLL;

    TextInputLayout ipTIL;
    TextInputLayout portTIL;
    TextInputLayout nameTIL;

    EditText ipET;
    EditText portET;
    EditText nameET;

    Button connectBtn;
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        initDatas();
    }

    private void initDatas() {
        String ip=ACacheUtil.get(this).getAsString("server_ip");
        String port=ACacheUtil.get(this).getAsString("server_port");
        String name=ACacheUtil.get(this).getAsString("server_name");
        if(!TextUtils.isEmpty(ip)){
            ipET.setText(ip);
        }
        if(!TextUtils.isEmpty(port)){
            portET.setText(port);
        }
        if(!TextUtils.isEmpty(name)){
            nameET.setText(name);
        }
    }

    private void initViews() {
        rootLL=(LinearLayout)findViewById(R.id.ll_root);
        ipTIL=(TextInputLayout)findViewById(R.id.til_ip);
        portTIL=(TextInputLayout)findViewById(R.id.til_port);
        nameTIL=(TextInputLayout)findViewById(R.id.til_name);
        ipET=(EditText)findViewById(R.id.et_ip);
        portET=(EditText)findViewById(R.id.et_port);
        nameET=(EditText)findViewById(R.id.et_name);
        connectBtn=(Button)findViewById(R.id.btn_connect);
        saveBtn=(Button)findViewById(R.id.btn_save);


    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {

    }

    public void connect(View view) {
        if(!judgeEmpty()){
            Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                        if(SmackManager.getInstance().connect(getIp(),Integer.parseInt(getPort()),getName())){
                            subscriber.onCompleted();
                        }else{
                            subscriber.onError(new Throwable("连接失败"));
                        }
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>() {
                @Override
                public void onCompleted() {
                    connectBtn.setText("连接成功");
                }
                @Override
                public void onError(Throwable e) {
                    connectBtn.setText("连接失败");
                }
                @Override
                public void onNext(Object o) {

                }
            });
        }
    }


    private void toConnect() throws IOException, XMPPException, SmackException {
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setHost(getIp());
        configBuilder.setPort(Integer.parseInt(getPort()));
        configBuilder.setServiceName(getName());
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        AbstractXMPPConnection connection = new XMPPTCPConnection(configBuilder.build());
        connection.connect();
    }


    public void save(View view) {
        if(!judgeEmpty()){
            ACacheUtil.get(this).put("server_ip",getIp());
            ACacheUtil.get(this).put("server_port",getPort());
            ACacheUtil.get(this).put("server_name",getName());

            Snackbar.make(rootLL,"已保存",Snackbar.LENGTH_SHORT).show();
        }


    }

    private String getIp(){
        return ipET.getText().toString();
    }
    private String getPort(){
        return portET.getText().toString();
    }
    private String getName(){
        return nameET.getText().toString();
    }

    private boolean judgeEmpty(){
        if(TextUtils.isEmpty(getIp())){
            ipTIL.setError("ip不能为空");

            return true;
        }
        if(TextUtils.isEmpty(getPort())){
            portTIL.setError("port不能为空");
            return true;
        }
        if(TextUtils.isEmpty(getName())){
            nameTIL.setError("服务名不能为空");
            return true;
        }

        return false;
    }


}
