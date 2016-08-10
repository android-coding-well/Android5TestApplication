package com.gosuncn.test.GLSurfaceView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gosuncn.test.R;


public class Test2Activity extends AppCompatActivity {


    private TestGLSurfaceView mGLView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        mGLView =(TestGLSurfaceView)findViewById(R.id.gl);//这里使用的是自定义的GLSurfaceView的子类
    }
    public void onPause(){
        super.onPause();
        mGLView.onPause();
    }

    public void onResume(){
        super.onResume();
        mGLView.onResume();
    }
}
