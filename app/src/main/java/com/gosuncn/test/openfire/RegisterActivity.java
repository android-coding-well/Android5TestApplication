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
import com.gosuncn.test.R;

public class RegisterActivity extends BaseActivity {
    LinearLayout rootLL;

    TextInputLayout userTIL;
    TextInputLayout pwdTIL;

    EditText userET;
    EditText pwdET;

    Button registerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        initDatas();

    }

    private void initDatas() {
    }

    private void initViews() {
        rootLL=(LinearLayout)findViewById(R.id.ll_root);
        userTIL=(TextInputLayout)findViewById(R.id.til_user);
        pwdTIL=(TextInputLayout)findViewById(R.id.til_pwd);
        userET=(EditText)findViewById(R.id.et_user);
        pwdET=(EditText)findViewById(R.id.et_pwd);
        registerBtn=(Button)findViewById(R.id.btn_register);


    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {

    }

    public void register(View view) {
        if(!judgeEmpty()){
            boolean isSuccess=SmackManager.getInstance().register(getUser(),getPwd());
            if(isSuccess){
                Snackbar.make(rootLL,"注册成功",Snackbar.LENGTH_SHORT).show();
                finish();
            }else{
                Snackbar.make(rootLL,"注册失败",Snackbar.LENGTH_SHORT).show();
            }
        }
    }


    private String getUser(){
        return userET.getText().toString();
    }

    private String getPwd(){
        return pwdET.getText().toString();
    }

    private boolean judgeEmpty(){
      if(TextUtils.isEmpty(getUser())){
          userTIL.setError("用户名不能为空");
          return true;
      }
        if(TextUtils.isEmpty(getPwd())){
            pwdTIL.setError("密码不能为空");
            return true;
        }
        return false;
    }


}
