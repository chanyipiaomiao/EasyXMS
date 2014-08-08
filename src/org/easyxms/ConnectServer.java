package org.easyxms;


import com.jcraft.jsch.*;


class ConnectServer{

    private ServerInfo serverInfo = null;
    private String ip = null;
    private static WriteLog writeLog = null;

    ConnectServer(){

    }

    ConnectServer(String ip,ServerInfo serverInfo){
        this.ip = ip;
        this.serverInfo = serverInfo;
    }

    public static void setWriteLog(WriteLog writeLog) {
        ConnectServer.writeLog = writeLog;
    }

    /** 连接服务器打开会话 */
    public Session connectServerOpenSession(){
        JSch jSch = new JSch();
        Session session = null;
        try {
            session = jSch.getSession(serverInfo.getUsername(),ip,serverInfo.getPort());
            session.setPassword(serverInfo.getPassword());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setTimeout(SettingInfo.getConnect_timeout());
            if (SettingInfo.getEnable_http_proxy() == 1){
                session.setProxy(new ProxyHTTP(SettingInfo.getHttp_proxy_server(),SettingInfo.getHttp_proxy_port()));
            }
            session.connect();
        } catch (JSchException e){
            writeLog.writeCommandResult("\n" + HelpPrompt.printConnectError(ip,e.getMessage()));
        }
        return session;
    }
}
