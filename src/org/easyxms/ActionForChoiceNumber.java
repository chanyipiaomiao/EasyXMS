package org.easyxms;


import com.jcraft.jsch.Session;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * 按对应的数字执行相对应的动作
 */
class ActionForChoiceNumber {


    /**
     * 从命令行读取输入
     * @return 一个字符串
     */
    String getInputContent(){
        GetInput getInput = new GetInput();
        return getInput.getInputFromStandardInput();
    }


    /**
     * 把字符串转换为一个对象
     * @param host 主机信息字符串
     * @return ServerInfo对象
     */
    ServerInfo stringToObject(String host){
        ServerInfo serverInfo = null;
        String[] host_infos = host.split("\\s+");
        if (host_infos.length == 5){
            String ip = host_infos[0];
            if (FunctionKit.checkStringIsIP(ip)){
                String group = host_infos[1];
                String username = EncryptDecryptPassword.Encrypt(host_infos[2]);
                String password = EncryptDecryptPassword.Encrypt(host_infos[3]);
                int port = Integer.parseInt(host_infos[4]);
                serverInfo = new ServerInfo(ip,group,username,password,port);
            } else {
                HelpPrompt.printInputError();
            }
        } else {
            HelpPrompt.printInputError();
        }
        return serverInfo;
    }


    /**
     * 从命令行增加一台服务器信息
     * @param serverInfoDAO  ServerInfo数据库访问对象
     */
    void addServerFromCommandLine(ServerInfoDAO serverInfoDAO){

        HelpPrompt.printAddServer();
        String input = getInputContent();
        List<Object> objects = new ArrayList<Object>();

        if (input.contains(";")){
            String hosts[] = input.trim().split(";");
            for (String host : hosts){
                ServerInfo serverInfo = stringToObject(host);
                if (serverInfo != null){
                    objects.add(serverInfo);
                }
            }
        } else {
            ServerInfo serverInfo = stringToObject(input);
            if (serverInfo != null){
                objects.add(serverInfo);
            }
        }

        if (objects.size() != 0){
            serverInfoDAO.insert(objects);
        }
    }


    /**
     * 从Excel文件中添加服务器信息
     * @param serverInfoDAO ServerInfo数据库访问对象
     */
    void addServerFromExcelFile(ServerInfoDAO serverInfoDAO){
        HelpPrompt.printExcelFilePath();
        String ask_excel_file = getInputContent();
        if (! FunctionKit.checkStringLengthIsZero(ask_excel_file)){
            HelpPrompt.printInputError();
        } else {
            GetIPInfoFromFile getIPInfoFromFile = new GetIPInfoFromExcel();
            List<Object> objects = getIPInfoFromFile.getIPInfo(ask_excel_file);
            if (objects.size() != 0){
                serverInfoDAO.insert(objects);
            }
        }
    }


    /**
     * 列出数据库中的服务器信息（IP Group）
     * @param serverInfoDAO  ServerInfo数据库访问对象
     */
    void listIPGroupFromDatabase(ServerInfoDAO serverInfoDAO){
        if (serverInfoDAO.queryAll().size() == 0){
            HelpPrompt.printNoDataInDataBase();
        } else {
            HelpPrompt.printListServer();
            String ask_list_group = getInputContent();
            if (! FunctionKit.checkStringLengthIsZero(ask_list_group)){
                HelpPrompt.printInputError();
            } else {
                String list_group[] = ask_list_group.split("\\s+");
                if (list_group.length == 1 && "all".equals(list_group[0])){
                    List<String> result = serverInfoDAO.queryIPServerGroup();
                    for (String ip_group : result){
                        System.out.println(ip_group);
                    }
                } else {
                    for (String group_name : list_group){
                        if (serverInfoDAO.queryAllFieldByServerGroup(group_name).size() != 0){
                            for (String ip_group : serverInfoDAO.queryIPServerGroupByServerGroup(group_name)){
                                System.out.println(ip_group);
                            }
                        } else {
                            HelpPrompt.printIpOrGroupNotExists(group_name);
                        }
                    }
                }
            }
        }
    }


    /**
     * 列出数据库中的分组
     * @param serverInfoDAO ServerInfo数据库访问对象
     */
    void listGroupFromDatabase(ServerInfoDAO serverInfoDAO){
        if (serverInfoDAO.queryAll().size() == 0){
            HelpPrompt.printNoDataInDataBase();
        } else {
            List<String> result = serverInfoDAO.queryDistinctServerGroup();
            for (String group : result){
                System.out.println(group);
            }
        }
    }


    /**
     * 删除指定的服务器信息
     * @param serverInfoDAO  ServerInfo数据库访问对象
     */
    void deleteServerInfoFromDatabase(ServerInfoDAO serverInfoDAO){
        if (serverInfoDAO.queryAll().size() == 0){
            HelpPrompt.printNoDataInDataBase();
        } else {
            HelpPrompt.printSpecifyIPOrGroupOrAllForDelete();
            String ask_delete_ip_or_group = getInputContent();
            if (! FunctionKit.checkStringLengthIsZero(ask_delete_ip_or_group)){
                HelpPrompt.printInputError();
            } else {
                String delete_ip_group[] = ask_delete_ip_or_group.split("\\s+");
                if (delete_ip_group.length == 1 && "all".equals(delete_ip_group[0])){
                    HelpPrompt.printAskClearTable();
                    String ask_clear_all_server = getInputContent();
                    if ("y".equals(ask_clear_all_server) || "Y".equals(ask_clear_all_server)){
                        serverInfoDAO.deleteAll();
                    } else {
                        HelpPrompt.printNothingHappen();
                    }
                } else {
                    for (String ip_or_group : delete_ip_group){
                        if (FunctionKit.checkStringIsIP(ip_or_group)){
                            serverInfoDAO.deleteByIP(ip_or_group);
                        } else {
                            serverInfoDAO.deleteByServerGroup(ip_or_group);
                        }
                    }
                }
            }
        }
    }


    /**
     * 为连接服务器做准备
     * @param serverInfoDAO  ServerInfo数据库访问对象
     * @param ssh_sftp 用来标识连接的动作 是 ssh 还是 sftp
     */
    void connectServerForSSHSFTP(ServerInfoDAO serverInfoDAO,String ssh_sftp){
        if (serverInfoDAO.queryAll().size() == 0){
            HelpPrompt.printNoDataInDataBase();
        } else {
            HelpPrompt.printListIPOrGroupOrAllForExec();
            String ask_server = getInputContent();
            if (! FunctionKit.checkStringLengthIsZero(ask_server)){
                HelpPrompt.printInputError();
            } else {
                String ip_or_group_array[] = ask_server.split("\\s+");
                List<ServerInfo> objects = new ArrayList<ServerInfo>();
                boolean check_occur_error = false;
                if (ip_or_group_array.length == 1 && "all".equals(ip_or_group_array[0])){
                    objects = serverInfoDAO.queryAll();
                } else {
                    for (String ip_or_group : ip_or_group_array){
                        if (FunctionKit.checkStringIsIP(ip_or_group)){
                            List<ServerInfo> result = serverInfoDAO.queryAllFieldByIP(ip_or_group);
                            if (result.size() != 0){
                                objects.addAll(result);
                            } else {
                                HelpPrompt.printIpOrGroupNotExists(ip_or_group);
                            }
                        } else if (serverInfoDAO.queryAllFieldByServerGroup(ip_or_group).size() != 0) {
                            objects.addAll(serverInfoDAO.queryAllFieldByServerGroup(ip_or_group));
                        } else {
                            HelpPrompt.printIpOrGroupNotExists(ip_or_group);
                            check_occur_error = true;
                        }
                    }
                }
                if (! check_occur_error){
                    WriteLog writeLog = new WriteLog();
                    if ("ssh".equals(ssh_sftp)){
                        SessionPool.setSsh_connection_pool(new HashMap<String, Session>());
                        ExecCommand.setWriteLog(writeLog);
                        ConnectServer.setWriteLog(writeLog);
                        loopGetCommandForExec(writeLog,objects);
                    } else {
                        SessionPool.setSftp_connection_pool(new HashMap<String, Session>());
                        UploadFile.setWriteLog(writeLog);
                        ConnectServer.setWriteLog(writeLog);
                        uploadFile(writeLog,objects);
                    }
                }
            }
        }
    }


    /**
     * 循环从命令行获取命令用于执行
     * @param writeLog 用来写日志的对象
     * @param objects ServerInfo对象列表
     */
    void loopGetCommandForExec(WriteLog writeLog,List<ServerInfo> objects){
        HelpPrompt.printAskExecCommand();
        String command = getInputContent();
        if (! FunctionKit.checkStringLengthIsZero(command)) {
            HelpPrompt.printInputError();
        } else if ("q".equals(command) || "Q".equals(command)){
            HelpPrompt.printExitExecCommand();
            HashMap<String, Session> ssh_connection_pool = SessionPool.getSsh_connection_pool();
            if (ssh_connection_pool.size() != 0){
                for (String ip : ssh_connection_pool.keySet()){
                    Session session = ssh_connection_pool.get(ip);
                    if (session.isConnected()){
                        session.disconnect();
                    }
                }
            }
            ssh_connection_pool.clear();
        } else {
            writeLog.writeCommand(command);

            //设置要执行的命令
            if (FunctionKit.checkCommandIsForceDeleteRootDirectory(command)){
                HelpPrompt.printYouCanntExecThisCommand();
            } else {
                ExecCommand.setCommand(command);
                MultiThread multiThread = new MultiThread();
                HashMap<String, Session> ssh_connection_pool = SessionPool.getSsh_connection_pool();
                int ssh_connection_pool_size = ssh_connection_pool.size();
                long start_time = System.currentTimeMillis();
                if (ssh_connection_pool_size == 0){
                    multiThread.startMultiThread(objects,"ssh");  //新建会话多线程执行
                } else {
                    multiThread.startMultiThread(ssh_connection_pool); //使用连接池 多线程执行
                }
                long end_time = System.currentTimeMillis();
                System.out.printf("Servers Number: %d ,Execute Time: %ds\n",objects.size(),(end_time - start_time)/1000);
            }

            //递归调用自身获取命令执行
            loopGetCommandForExec(writeLog,objects);
        }
    }


    /**
     * 上传文件
     * @param writeLog 用来写日志的对象
     * @param objects ServerInfo对象列表
     */
    void uploadFile(WriteLog writeLog,List<ServerInfo> objects){
        HelpPrompt.printFilePath();
        String file_info = getInputContent();
        if (! FunctionKit.checkStringLengthIsZero(file_info)){
            HelpPrompt.printInputError();
        } else {
            String[] src_dst = file_info.split("\\s+");
            String file = src_dst[0];
            File src_file = new File(file);
            if (src_file.exists() && src_file.isFile() ){
                if (src_dst.length == 1){
                    UploadFile.setSrc(file);
                    UploadFile.setDst("/tmp");
                    MultiThread multiThread = new MultiThread();
                    multiThread.startMultiThread(objects,"sftp");
                }
            } else if (src_file.isDirectory()){
                System.out.println("Oops.不支持上传目录!");
            } else {
                System.out.printf("啊哦. [ %s ] 不存在哦.\n",src_file);
            }
        }
    }
}

