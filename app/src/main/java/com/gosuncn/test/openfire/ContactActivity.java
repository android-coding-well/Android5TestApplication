package com.gosuncn.test.openfire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.L;
import com.gosuncn.test.R;
import com.gosuncn.test.openfire.adapter.ContactAdapter;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactActivity extends BaseActivity {

    RelativeLayout rootRL;
    SwipeRefreshLayout freshSRL;
    ListView listLV;

    ContactAdapter adapter;
    List<RosterEntry> list=new ArrayList<RosterEntry>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        initViews();
        initDatas();
    }

    @Override
    protected void onDestroy() {
        SmackManager.getInstance().disconnect();
        super.onDestroy();
    }

    private void initDatas() {
        getContactList();

        L.e("123456","组个数："+SmackManager.getInstance().getRoster().getGroupCount());
        Collection<RosterGroup> dd=SmackManager.getInstance().getRoster().getGroups();
        List<RosterGroup> list=new ArrayList<RosterGroup>(dd);
        for(RosterGroup rosterGroup : list){
            L.e("123456",rosterGroup.getName()+" "+rosterGroup.getEntryCount());
        }
        //rosterGroup.addEntry(new RosterEntry());
    }
    private void getContactList() {
            List<RosterEntry> l=SmackManager.getInstance().getRosterList();
        if(l==null){
            Snackbar.make(rootRL,"联系人获取失败",Snackbar.LENGTH_SHORT).show();
        }else{
            RosterGroup rosterGroup=SmackManager.getInstance().getRoster().createGroup("创建群组");
            try {
                rosterGroup.addEntry(l.get(0));
                L.e("123456","组创建成功");
            } catch (SmackException.NoResponseException e) {
                e.printStackTrace();
            } catch (XMPPException.XMPPErrorException e) {
                e.printStackTrace();
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            list.clear();
            list.addAll(l);
            adapter.notifyDataSetChanged();
        }
        freshSRL.setRefreshing(false);
    }

    private void initViews() {
        rootRL=(RelativeLayout)findViewById(R.id.rl_root);
        freshSRL=(SwipeRefreshLayout)findViewById(R.id.srl_refresh);
        listLV=(ListView)findViewById(R.id.lv_list);
        adapter=new ContactAdapter(this,list,R.layout.item_contact);
        listLV.setAdapter(adapter);
        listLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ContactActivity.this,ChatActivity.class);

                intent.putExtra("UserJID",list.get(position).getUser());
               // intent.putExtra("UserName",list.get(position).getName());
                startActivity(intent);
            }
        });

        freshSRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                freshSRL.setRefreshing(true);
                getContactList();
            }
        });
    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {

    }
}
