package com.gosuncn.test.openfire;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.L;
import com.gosuncn.test.R;
import com.gosuncn.test.openfire.adapter.ChatAdapter;
import com.gosuncn.test.openfire.domain.ChatInfo;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatActivity extends BaseActivity {


    LinearLayout rootLL;
    ListView listLV;
    EditText contentET;
    ImageView sendIV;
    ImageView fileIV;
    ChatAdapter adapter;
    List<ChatInfo> list=new ArrayList<ChatInfo>();

    String userJID;//聊天对象
    ChatManager chatManager;
    FileTransferManager fileTransferManager;
    Chat chat;

    String myUserJID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();
        initDatas();
    }

    private void initDatas() {
        myUserJID=SmackManager.getInstance().getXMPPConnection().getUser();
        initChatManager();
        initFileTransferManager();


    }


    private void initFileTransferManager() {
        fileTransferManager=SmackManager.getInstance().getFileTransferManager();
        fileTransferManager.addFileTransferListener(new FileTransferListener() {
            @Override
            public void fileTransferRequest(FileTransferRequest request) {
                //调用request的accetp表示接收文件，也可以调用reject方法拒绝接收
                final IncomingFileTransfer inTransfer = request.accept();
                try {
                   L.e("123456","接收到文件发送请求，文件名称："+request.getFileName());
                    //接收到的文件要放在哪里
                    String filePath = Environment.getExternalStorageDirectory().toString()+"/"+request.getFileName();
                    inTransfer.recieveFile(new File(filePath));
                    //如果要时时获取文件接收的状态必须在线程中监听，如果在当前线程监听文件状态会导致一下接收为0
                    new Thread(){
                        @Override
                        public void run(){
                            long startTime = System.currentTimeMillis();
                            while(!inTransfer.isDone()){
                                if (inTransfer.getStatus().equals(FileTransfer.Status.error)){
                                    L.e("123456","error!!!"+inTransfer.getError());
                                }else{
                                    double progress = inTransfer.getProgress();
                                    progress*=100;
                                    L.e("123456","status="+inTransfer.getStatus());
                                    L.e("123456","progress="+progress);
                                }
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            L.e("123456","used "+((System.currentTimeMillis()-startTime)/1000)+" seconds  ");
                        }
                    }.start();
                }  catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void initChatManager() {
        chatManager=  SmackManager.getInstance().getChatManager();
        chat=chatManager.createChat(userJID);
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
                                    String msg=message.getBody();
                                    if(!TextUtils.isEmpty(msg)){
                                        Observable.just(msg).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {

                                            }

                                            @Override
                                            public void onNext(String o) {
                                                ChatInfo info=new ChatInfo();
                                                info.isTo=false;
                                                info.userJID=userJID.split("@")[0];
                                                info.content=o;
                                                list.add(info);
                                                listLV.setSelection(listLV.getBottom());
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
    }

    private void initViews() {
        rootLL = (LinearLayout) findViewById(R.id.ll_root);
        listLV = (ListView) findViewById(R.id.lv_list);
        contentET = (EditText) findViewById(R.id.et_text);
        sendIV = (ImageView) findViewById(R.id.iv_send);
        fileIV = (ImageView) findViewById(R.id.iv_file);
        listLV.setAdapter(adapter=new ChatAdapter(this,list,R.layout.item_chat));

    }

    /**
     * 在此处处理intent传值
     */
    @Override
    protected void processExtraData() {
        userJID= getIntent().getStringExtra("UserJID");
    }

    public void sendClick(View view) {
        if(!judgeEmpty()){

            try {
                chat.sendMessage(getContent());

                ChatInfo info=new ChatInfo();
                info.isTo=true;
                info.userJID=myUserJID.split("@")[0];
                info.content=getContent();
                list.add(info);
                listLV.setSelection(listLV.getBottom());
                adapter.notifyDataSetChanged();

                contentET.setText(null);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
                Snackbar.make(rootLL,"发送失败，未连接",Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    private String getContent(){
        return contentET.getText().toString();
    }


    private boolean judgeEmpty(){
        if(TextUtils.isEmpty(getContent())){
            Snackbar.make(rootLL,"内容不能为空",Snackbar.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    public void fileClick(View view) {
        selectFileFromLocal();

    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
                switch(requestCode){
                    case 111:
                        Uri uri = data.getData();
                        if(uri!=null){
                            sendFileByUri(uri);
                        }
                        break;
                }



            }

    }

    // 发送文件
    private  void sendFile(String toUser, File file){

        Presence p = SmackManager.getInstance().getRoster().getPresence(toUser);
        if(p==null){
            Snackbar.make(rootLL,"用户不存在",Snackbar.LENGTH_SHORT).show();
            return;
        }
        toUser = p.getFrom();//提取完整的用户名称
        OutgoingFileTransfer fileTransfer = fileTransferManager
                .createOutgoingFileTransfer(toUser);
        try {
            fileTransfer.sendFile(file, "file");

           L.e("123456","sending file status="+fileTransfer.getStatus());
            long startTime = -1;
            while (!fileTransfer.isDone()){
                if (fileTransfer.getStatus().equals(FileTransfer.Status.error)){
                    L.e("123456","error!!!"+fileTransfer.getError());
                }else{
                    double progress = fileTransfer.getProgress();
                    if(progress>0.0 && startTime==-1){
                        startTime = System.currentTimeMillis();
                    }
                    progress*=100;
                    L.e("123456","status="+fileTransfer.getStatus());
                    L.e("123456","progress="+progress);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("used "+((System.currentTimeMillis()-startTime)/1000)+" seconds  ");
        } catch (SmackException e) {
            Snackbar.make(rootLL,"发送失败",Snackbar.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally{
          cancelLoadingDialog();
        }
    }
    /**
     * 选择文件
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //19以后这个api不可用，demo这里简单处理成图库选择图片
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, 111);
    }
    /**
     * 根据uri发送文件
     * @param uri
     */
    protected void sendFileByUri(Uri uri){
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            Snackbar.make(rootLL,"文件不存在",Snackbar.LENGTH_SHORT).show();
            return;
        }
        //大于10M不让发送
        if (file.length() > 10 * 1024 * 1024) {
            Snackbar.make(rootLL,"文件过大",Snackbar.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        sendFile(userJID,file);
    }
}
