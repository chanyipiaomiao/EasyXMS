package org.easyxms;


import com.jcraft.jsch.*;
import java.io.File;
import java.util.concurrent.CountDownLatch;


class UploadFile extends ConnectServer implements TransferFile,Runnable {

    private String ip = null;
    private Session session = null;
    private static String src = null;
    private static String dst = null;
    private static int is_use_session_pool = 0;  //是否使用session会话池
    private static WriteLog writeLog = null;
    private static CountDownLatch countDownLatch = null;


    UploadFile(ServerInfo serverInfo) {
        super(serverInfo);
        this.ip = serverInfo.getIp();
    }

    public static void setSrc(String src) {
        UploadFile.src = src;
    }

    public static void setDst(String dst) {
        UploadFile.dst = dst;
    }

    public static void setCountDownLatch(CountDownLatch countDownLatch) {
        UploadFile.countDownLatch = countDownLatch;
    }

    public static void setIs_use_session_pool(int is_use_session_pool) {
        UploadFile.is_use_session_pool = is_use_session_pool;
    }

    public static void setWriteLog(WriteLog writeLog) {
        UploadFile.writeLog = writeLog;
    }


    @Override
    public void run() {
        openExecCommandChannel();
        countDownLatch.countDown();
    }

    /**
     * 打开SFTP通道
     */
    public void openExecCommandChannel(){
        session = super.connectServerOpenSession();
        if (session.isConnected()){
            //把session会话放到 session连接池中，以备下一次使用
            SessionPool.getSftp_connection_pool().put(ip, session);
            transfer();
        }
    }


    /**
     * 上传文件
     */
    @Override
    public void transfer() {
        try {
            ChannelSftp channelSftp = (ChannelSftp)session.openChannel("sftp");
            channelSftp.setEnv("LC_MESSAGES", "en_US.UTF-8");
            channelSftp.connect();
            channelSftp.put(src, dst, new FileTransferProgressMonitor());
            writeLog.writeFileUpload(String.format("Upload %s To %s@%s",(new File(src).getAbsoluteFile()),dst,ip));
            channelSftp.disconnect();
        } catch (JSchException e){
            HelpPrompt.printInfo(e.getMessage());
        } catch (SftpException e){
            HelpPrompt.printInfo(e.getMessage());
        }
    }
}
