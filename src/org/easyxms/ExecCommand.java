package org.easyxms;


import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;



/** 执行命令类 继承ConnectServer类 实现Runnable接口 */
class ExecCommand extends ConnectServer implements Runnable{

    private static String command = null;
    private String ip = null;
    private static CountDownLatch countDownLatch = null;
    private Session session = null;
    private static int is_use_session_pool = 0;  //是否使用session会话池
    private static WriteLog writeLog = null;

    ExecCommand(String ip,Session session) {
        this.ip = ip;
        this.session = session;
    }

    ExecCommand(ServerInfo serverInfo) {
        super(serverInfo);
        this.ip = serverInfo.getIp();
    }

    @Override
    public void run() {
        if (is_use_session_pool == 0){
            openExecCommandChannel();
        } else {
            execCommandGetResult(session);
        }
        countDownLatch.countDown();
    }

    public static void setWriteLog(WriteLog writeLog) {
        ExecCommand.writeLog = writeLog;
    }

    public static void setIs_use_session_pool(int is_use_session_pool) {
        ExecCommand.is_use_session_pool = is_use_session_pool;
    }


    //设置子线程同步计数器
    public static void setCountDownLatch(CountDownLatch count_down) {
        countDownLatch = count_down;
    }


    //设置要执行的命令
    public static void setCommand(String command) {
        ExecCommand.command = command;
    }


    /**
     * 打开执行命令通道
     */
    public void openExecCommandChannel(){
        Session session = super.connectServerOpenSession();
        if (session.isConnected()){
            //把session会话放到 session连接池中，以备下一次使用
            SessionPool.getSsh_connection_pool().put(ip,new ConnectionObject(session));
            execCommandGetResult(session);
        }
    }


    /**
     * 执行命令得到结果
     * @param session 连接之后形成的会话
     */
    public void execCommandGetResult(Session session){
        StringBuilder result = new StringBuilder();
        result.append(String.format("======== [ %s ] Execute Command: < %s >, The result is :\n",ip,command));
        try {
            ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
            channelExec.setEnv("LC_MESSAGES","en_US.UTF-8");
            channelExec.setCommand(command);
            BufferedReader exec_result = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
            BufferedReader error_result = new BufferedReader(new InputStreamReader(channelExec.getErrStream()));
            channelExec.connect();
            Thread.sleep(SettingInfo.getSleep_time());

            //得到标准输出
            while (exec_result.ready()){
                result.append(exec_result.readLine());
                result.append("\n");
            }

            //得到标准错误输出
            while (error_result.ready()){
                result.append(error_result.readLine());
                result.append("\n");
            }

        } catch (IOException e){
            HelpPrompt.printInfo(e.getMessage());
        } catch (InterruptedException e){
            HelpPrompt.printInfo(e.getMessage());
        } catch (JSchException e){
            HelpPrompt.printInfo(e.getMessage());
        }
        String result_string = result.toString();
        writeLog.writeCommandResult(result_string);
        HelpPrompt.printInfo(result_string);
    }
}
