package com.gosuncn.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gosuncn.core.utils.L;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.iqregister.AccountManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OpenfireActivity extends AppCompatActivity {

    AbstractXMPPConnection connection;
    boolean s=false;

    private EditText getEtUser(){
        return (EditText) findViewById(R.id.et_user);
    }

    private EditText getEtPwd(){
        return (EditText) findViewById(R.id.et_pwd);
    }

    private EditText getEtServer(){
        return (EditText) findViewById(R.id.et_server);
    }

    XMPPTCPConnectionConfiguration.Builder configBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openfire);

        initViews();

         configBuilder = XMPPTCPConnectionConfiguration.builder();
       // configBuilder.setResource("hello");
        configBuilder.setHost("192.168.16.135");
        configBuilder.setPort(5222);
        configBuilder.setServiceName("gosuncn");
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);



    }

    private void initViews() {

    }

    public void sendText(View view) {

        ChatManager chatManager= ChatManager.getInstanceFor(connection);
        chatManager.addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean createdLocally)
                    {
                        if (!createdLocally) {
                            chat.addMessageListener(new ChatMessageListener() {
                                @Override
                                public void processMessage(Chat chat, Message message) {
                                    L.e("123456","Received message=" + message.getBody());
                                    Toast.makeText(OpenfireActivity.this, "Received message=" + message.getBody(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
        Chat chat =chatManager.createChat("a1@gosuncn");
        try {

            chat.sendMessage("Howdy!");
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
            Toast.makeText(OpenfireActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
        }

        InputStream in = null;
        File file=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/tmp.3gp");
        try {
            inputstreamtofile(getResources().getAssets().open("123.3gp"),file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(file.exists()){
            try {
                sendFile("a1@gosuncn/Spark",file,connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(OpenfireActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View view) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                connection = new XMPPTCPConnection(configBuilder.build());//getEtUser().getText().toString(), getEtPwd().getText().toString(), getEtServer().getText().toString());
                try {
                    connection.connect();
                    connection.login(getEtUser().getText().toString(), getEtPwd().getText().toString());
                    s=true;
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                if(!s){
                    Toast.makeText(OpenfireActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OpenfireActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    findRosters();

                    AccountManager accountManager= AccountManager.getInstance(connection);
                    try {
                        accountManager.createAccount("b"+(int)(Math.random()*10), "b");
                        Toast.makeText(OpenfireActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } catch (SmackException.NoResponseException e) {
                        e.printStackTrace();
                    } catch (XMPPException.XMPPErrorException e) {
                        e.printStackTrace();
                    } catch (SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(Object o) {

            }
        });

    }

    // 发送文件
    public static void sendFile(String user, File file,
                                XMPPConnection connection) throws Exception {
        FileTransferManager fileTransferManager = FileTransferManager.getInstanceFor(connection);
        OutgoingFileTransfer fileTransfer = fileTransferManager
                .createOutgoingFileTransfer(user);
        fileTransfer.sendFile(file, "Send");
    }

    public void inputstreamtofile(InputStream ins,File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }

    public void findRosters(){


        Roster roster = Roster.getInstanceFor(connection);
        Collection<RosterEntry> entries = roster.getEntries();
        for (RosterEntry entry : entries) {
            L.e("123456",""+entry);
        }
        roster.addRosterListener(new RosterListener() {
             public void entriesAdded(Collection<String> addresses) {}
            public void entriesDeleted(Collection<String> addresses) {}
            public void entriesUpdated(Collection<String> addresses) {}
            public void presenceChanged(Presence presence) {
                System.out.println("Presence changed: " + presence.getFrom() + " " + presence.getStatus());
            }
        });
    }

    public void setStatus(){


        // Create a new presence. Pass in false to indicate we're unavailable._
        Presence presence = new Presence(Presence.Type.unavailable);
        presence.setStatus("Gone fishing");
// Send the packet (assume we have an XMPPConnection instance called "con").
        try {
            connection.sendStanza(presence);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }


}
