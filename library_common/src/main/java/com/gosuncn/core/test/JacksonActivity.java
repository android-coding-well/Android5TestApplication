/*
package com.gosuncn.core.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.gosuncn.core.R;
import com.gosuncn.core.base.BaseActivity;

import java.io.IOException;
import java.io.Serializable;

public class JacksonActivity extends BaseActivity implements View.OnClickListener{

    private TextView textTView;
    private Button json2objBtn;
    private Button obj2jsonBtn;

    private ObjectMapper mapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jackson);

        initView();
        initJackson();
    }
    private void initView(){
        textTView=(TextView)findViewById(R.id.tv_jackson_text);
        json2objBtn=(Button)findViewById(R.id.btn_jackson_json2obj);
        obj2jsonBtn=(Button)findViewById(R.id.btn_jackson_obj2json);
        json2objBtn.setOnClickListener(this);
        obj2jsonBtn.setOnClickListener(this);
    }

    private void initJackson(){
        */
/**
         * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
         * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
         * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
         * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
         * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
         * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
         *//*

        mapper = new ObjectMapper();
		*/
/*
		 * 四种类型：
		 * 1-null:根据原格式；
		 * 2-PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES：小骆驼拼写法,如testName
		 * 3-PropertyNamingStrategy.LOWER_CASE：单词首字母小写，如testname
		 * 4-PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE：大骆驼拼写法，如TestName
		 *//*

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_jackson_json2obj:
                String json = "{\"test_name\":\"bflee\",\"id\":123456,\"test_number\":123.564}";

                TestInfo o;
                try {
                    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
                    o = mapper.readValue(json, TestInfo.class);
                    textTView.setText(o.getId() + "," + o.getTestName() + ","
                            + o.getTestNumber());
                } catch (JsonParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (JsonMappingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
            case R.id.btn_jackson_obj2json:
                TestInfo info=new TestInfo();
                info.setId(125);
                info.setTestName("hello");
                info.setTestNumber(4526.1564);
                try {
                    mapper.setPropertyNamingStrategy(null);
                    String json2=mapper.writeValueAsString(info);
                    textTView.setText(json2);
                } catch (JsonProcessingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            break;
        }

    }

    private class TestInfo implements Serializable {

        */
/**
         *
         *//*

        private static final long serialVersionUID = 427523484706584596L;

        private int id;

        private String testName;

        private double testNumber;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTestName() {
            return testName;
        }

        public void setTestName(String testName) {
            this.testName = testName;
        }

        public double getTestNumber() {
            return testNumber;
        }

        public void setTestNumber(double testNumber) {
            this.testNumber = testNumber;
        }

    }
}
*/
