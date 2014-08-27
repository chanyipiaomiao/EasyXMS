package org.easyxms;


import com.jcraft.jsch.*;


class ConnectServer{

    private ServerInfo serverInfo = null;
    private static WriteLog writeLog = null;

    ConnectServer(){

    }

    ConnectServer(ServerInfo serverInfo){
        this.serverInfo = serverInfo;
    }

    public static void setWriteLog(WriteLog writeLog) {
        ConnectServer.writeLog = writeLog;
    }


    /**
     * 连接服务器打开会话
     * @return 连接会话对象
     */
    public Session connectServerOpenSession(){
        JSch jSch = new JSch();
        Session session = null;
        String ip = serverInfo.getIp();
        try {
            session = jSch.getSession(EncryptDecryptPassword.Decrypt(serverInfo.getUsername()),ip,serverInfo.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setConfig("userauth.gssapi-with-mic", "no");
            session.setTimeout(SettingInfo.getConnect_timeout());
            session.setPassword(EncryptDecryptPassword.Decrypt(serverInfo.getPassword()));
            if (SettingInfo.getEnable_http_proxy() == 1){
                session.setProxy(new ProxyHTTP(SettingInfo.getHttp_proxy_server(),SettingInfo.getHttp_proxy_port()));
            }
            session.connect();
        } catch (JSchException e){
            writeLog.writeCommandResult("\n" + HelpPrompt.printConnectError(ip,e.getMessage()) + "\n");
        }
        return session;
    }
}
