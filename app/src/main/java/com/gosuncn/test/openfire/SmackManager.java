package com.gosuncn.test.openfire;

import com.gosuncn.core.utils.L;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.offline.OfflineMessageManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hwj on 2016/6/1.
 */
public class SmackManager {

    private SmackManager() {
    }

    private AbstractXMPPConnection connection;
    private boolean isConnectionSuccess = false;
    private static SmackManager instance;

    private List<Message> offlineMessages;//离线消息

    public static SmackManager getInstance() {
        if (instance == null) {
            instance = new SmackManager();
        }
        return instance;
    }

    public boolean connect(final String host, final int port, final String serverName) {
        XMPPTCPConnectionConfiguration.Builder configBuilder = XMPPTCPConnectionConfiguration.builder();
        configBuilder.setHost(host);
        configBuilder.setPort(port);
        configBuilder.setServiceName(serverName);
        configBuilder.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        configBuilder.setSendPresence(false);//为了取得离线消息，这里需要设置为false，取完离线消息后记得再设置为在线（通过setStatus(Presence.Type.available)）
        connection = new XMPPTCPConnection(configBuilder.build());
        try {
            connection.connect();
            isConnectionSuccess = true;
        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            isConnectionSuccess = false;
        }
        return isConnectionSuccess;
    }

    public void disconnect() {
        if (isConnectionSuccess) {
            connection.disconnect();
        }
    }

    public boolean isConnectSuccess() {
        return isConnectionSuccess;
    }

    public AbstractXMPPConnection getXMPPConnection() {
        return connection;
    }

    public boolean login(String user, String pwd) {
        if (isConnectionSuccess) {
            try {
                connection.login(user, pwd);
                OfflineMessageManager offlineMessageManager = new OfflineMessageManager(connection);
                offlineMessages = offlineMessageManager.getMessages();
                L.e("123456","离线消息："+offlineMessages.size());
                offlineMessageManager.deleteMessages();
                setStatus(Presence.Type.available);
                return true;
            } catch (XMPPException | SmackException | IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 注册
     * @param user
     * @param pwd
     * @return
     */
    public boolean register(String user, String pwd) {
        AccountManager accountManager = AccountManager.getInstance(connection);
        try {
            accountManager.createAccount(user, pwd);
            return true;
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 获得联系人列表
     * @return
     */
    public List<RosterEntry> getRosterList() {
        if (isConnectionSuccess) {
            Roster roster = Roster.getInstanceFor(connection);
            Collection<RosterEntry> entries = roster.getEntries();
            if (entries != null) {
                List<RosterEntry> list = new ArrayList<RosterEntry>(entries);
                return list;
            }

        }
        return null;
    }

    public Roster getRoster() {
        if (isConnectionSuccess) {
            return Roster.getInstanceFor(connection);
        }
        return null;
    }


    public ChatManager getChatManager() {
        if (isConnectionSuccess) {
            return ChatManager.getInstanceFor(connection);
        }
        return null;
    }

    public FileTransferManager getFileTransferManager() {
        if (isConnectionSuccess) {
            return FileTransferManager.getInstanceFor(connection);
        }
        return null;
    }

    public OfflineMessageManager getOfflineMessageManager() {
        if (isConnectionSuccess) {
            return new OfflineMessageManager(connection);
        }
        return null;
    }

    /**
     * 设置状态（在线 离线等等）
     *
     * @param type
     */
    public boolean setStatus(Presence.Type type) {
        if (isConnectionSuccess) {
            Presence presence = new Presence(type);
            try {
                connection.sendStanza(presence);
                return true;
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 取得离线消息
     * @return
     */
    public List<Message> getOfflineMessages() {
        return offlineMessages;
    }

}
