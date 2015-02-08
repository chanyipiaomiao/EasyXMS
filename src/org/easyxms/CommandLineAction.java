package org.easyxms;


import com.jcraft.jsch.Session;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 直接从命令行操作时的动作
 */
public class CommandLineAction {

    HashMap<String, Session> sessionHashMap = new HashMap<String, Session>();
    ServerInfoDAO serverInfoDAO = new ServerInfoDAO();


    /**
     * 直接从命令行执行命令
     * @param objects ServerInfo对象列表
     * @param command 要执行的命令
     */
    public void commandlineExec(List<ServerInfo> objects,String command){
        long start_time = System.currentTimeMillis();
        WriteLog writeLog = new WriteLog();
        SessionPool.setSsh_connection_pool(sessionHashMap);
        writeLog.writeCommand(command);
        ExecCommand.setCommand(command);
        ExecCommand.setWriteLog(writeLog);
        MultiThread multiThread = new MultiThread();
        multiThread.startMultiThread(objects,"ssh");
        HashMap<String, Session> sessions = SessionPool.getSsh_connection_pool();
        for (Session session : sessions.values()){
            session.disconnect();
        }
        sessionHashMap.clear();
        long end_time = System.currentTimeMillis();
        HelpPrompt.printExecuteTime(objects.size(),(end_time - start_time)/1000);
    }


    /**
     * 直接从命令行上传文件
     * @param src_file 上传的原文件
     * @param remote_path 远程服务器的路径
     * @param objects ServerInfo对象列表
     */
    public void commandlineUploadFile(String src_file,String remote_path,List<ServerInfo> objects){
        WriteLog writeLog = new WriteLog();
        SessionPool.setSftp_connection_pool(sessionHashMap);
        UploadFile.setWriteLog(writeLog);
        UploadFile.setSrc(src_file);
        UploadFile.setDst(remote_path);
        if (! remote_path.startsWith("/")){
            remote_path = "$HOME/" + remote_path;
        }
        HelpPrompt.printFileSizeAndRemotePath(src_file,remote_path);
        long start_time = System.currentTimeMillis();
        MultiThread multiThread = new MultiThread();
        multiThread.startMultiThread(objects,"sftp");
        HashMap<String, Session> sessions = SessionPool.getSftp_connection_pool();
        for (Session session : sessions.values()){
            session.disconnect();
        }
        sessionHashMap.clear();
        long end_time = System.currentTimeMillis();
        System.out.println();
        HelpPrompt.printExecuteTime(objects.size(),(end_time - start_time)/1000);
    }


    /**
     * 命令行单IP执行命令
     * @param ip IP地址
     * @param command 要执行的命令
     */
    public void ipExecCommand(String ip,String command){
        if (FunctionKit.checkStringIsIP(ip)){
            List<ServerInfo> objects = serverInfoDAO.queryAllFieldByIP(ip);
            if (objects.size() != 0){
                commandlineExec(objects,command);
            } else {
                HelpPrompt.printIpOrGroupNotExists(ip);
            }
        } else {
            HelpPrompt.printIsNotIP(ip);
        }
    }


    /**
     * 命令行单IP执行上传文件
     * @param ip IP地址
     * @param src 上传的源文件
     * @param dst 目标地址
     */
    public void ipUpload(String ip,String src,String dst){
        if (FunctionKit.checkStringIsIP(ip)){
            if (FunctionKit.checkFileIsExists(src)){
                List<ServerInfo> objects = serverInfoDAO.queryAllFieldByIP(ip);
                if (objects.size() != 0){
                    commandlineUploadFile(src, dst, objects);
                } else {
                    HelpPrompt.printIpOrGroupNotExists(ip);
                }
            } else {
                HelpPrompt.printSrcFileNotExists(src);
            }
        } else {
            HelpPrompt.printIsNotIP(ip);
        }
    }


    /**
     * 命令行 分组执行命令
     * @param group 分组
     * @param command 执行的命令
     */
    public void groupExecCommand(String group,String command){
        List<ServerInfo> objects = null;
        if ("all".equals(group)){
            objects = serverInfoDAO.queryAll();
        } else {
            objects = serverInfoDAO.queryAllFieldByServerGroup(group);
        }

        if (objects.size() !=0){
            commandlineExec(objects,command);
        } else {
            HelpPrompt.printIpOrGroupNotExists(group);
        }
    }


    /**
     * 命令行 分组上传文件
     * @param group 分组
     * @param src 上传的源文件
     * @param dst 目标路径
     */
    public void groupUpload(String group,String src,String dst){
        if (FunctionKit.checkFileIsExists(src)){
            List<ServerInfo> objects = null;
            if ("all".equals(group)){
                objects = serverInfoDAO.queryAll();
            } else {
                objects = serverInfoDAO.queryAllFieldByServerGroup(group);
            }

            if (objects.size() != 0){
                commandlineUploadFile(src, dst, objects);
            } else {
                HelpPrompt.printIpOrGroupNotExists(group);
            }
        } else {
            HelpPrompt.printSrcFileNotExists(src);
        }
    }


    /**
     * 命令行 读取文件中的IP信息来执行命令
     * @param file 要读取的文件
     * @param command 要执行的命令
     */
    public void fileExecCommand(String file,String command){
        if (FunctionKit.checkFileIsExists(file)){
            GetIPInfoFromFile getIPInfoFromFile = new GetIPInfoFromExcel();
            List<ServerInfo> objects = new ArrayList<ServerInfo>();
            for (Object obj : getIPInfoFromFile.getIPInfo(file)){
                objects.add((ServerInfo)obj);
            }
            commandlineExec(objects, command);
        } else {
            HelpPrompt.printSrcFileNotExists(file);
        }
    }


    /**
     * 命令行 读取文件中的IP信息来上传文件
     * @param file 要读取的文件
     * @param src 上传的源文件
     * @param dst 目标路径
     */
    public void fileUpload(String file,String src,String dst){
        if (FunctionKit.checkFileIsExists(file)){
            GetIPInfoFromFile getIPInfoFromFile = new GetIPInfoFromExcel();
            List<ServerInfo> objects = new ArrayList<ServerInfo>();
            for (Object obj : getIPInfoFromFile.getIPInfo(file)){
                objects.add((ServerInfo)obj);
            }
            commandlineUploadFile(src,dst,objects);
        } else {
            HelpPrompt.printSrcFileNotExists(file);
        }
    }
}
