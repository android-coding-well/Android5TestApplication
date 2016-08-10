/*
package com.gosuncn.core.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gosuncn.core.R;
import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.db.DatabaseHelper;
import com.gosuncn.core.domain.User;
import com.gosuncn.core.utils.L;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class OrmLiteActivity extends BaseActivity implements View.OnClickListener{

    private TextView textTView;
    private Button addBtn;
    private Button delBtn;
    private Button updateBtn;
    private Button getBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orm_lite);
        initView();
    }

    private void initView(){
        textTView=(TextView)findViewById(R.id.tv_ormlite_text);
        addBtn=(Button)findViewById(R.id.btn_ormlite_add);
        delBtn=(Button)findViewById(R.id.btn_ormlite_delete);
        updateBtn=(Button)findViewById(R.id.btn_ormlite_update);
        getBtn=(Button)findViewById(R.id.btn_ormlite_get);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        getBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_ormlite_add:
                testAddUser();
            break;
            case R.id.btn_ormlite_delete:
                testDeleteUser();
            break;
            case R.id.btn_ormlite_update:
                testUpdateUser();
            break;
            case R.id.btn_ormlite_get:
                testList();
            break;
        }
    }

    public void testAddUser()
    {

        User u1 = new User("hwj", "2B青年");
        DatabaseHelper helper = DatabaseHelper.getHelper(this);
        try
        {
            helper.getUserDao().create(u1);
            u1 = new User("hwj2", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new User("hwj3", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new User("hwj4", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new User("hwj5", "2B青年");
            helper.getUserDao().create(u1);
            u1 = new User("hwj6", "2B青年");
            helper.getUserDao().create(u1);

            testList();


        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void testDeleteUser()
    {
        DatabaseHelper helper = DatabaseHelper.getHelper(this);
        try
        {
            helper.getUserDao().deleteById(2);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void testUpdateUser()
    {
        DatabaseHelper helper = DatabaseHelper.getHelper(this);
        try
        {
            User u1 = new User("hwj-android", "2B青年");
            u1.setId(3);
            helper.getUserDao().update(u1);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void testList()
    {
        DatabaseHelper helper = DatabaseHelper.getHelper(this);
        try
        {
            User u1 = new User("hwj-android", "2B青年");
            u1.setId(2);
            List<User> users = helper.getUserDao().queryForAll();
            L.i("123456", users.toString());
            User user= helper.getUserDao().queryForId(1);
            L.i("123456", "id1="+user.toString());
            textTView.setText(users.toString() + "\n\nid:1=" + user.toString());
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
*/
