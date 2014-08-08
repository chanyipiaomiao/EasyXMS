package org.easyxms;


import java.util.HashMap;


class ActionForChoiceNumber {

    /** 从命令行读取输入 */
    String getInputContent(){
        GetInput getInput = new GetInput();
        return getInput.getInputFromStandardInput();
    }

    /** 从命令行增加一台服务器信息 */
    void addServerFromCommandLine(OperateDataBase operateDataBase){
        HelpPrompt.printAddServer();
        String cmd = getInputContent();
        String command[] = cmd.split("\\s+");
        if (command.length == 5 && FunctionKit.checkStringIsIP(command[0])){
            String username = EncryptDecryptPassword.Encrypt(command[2]);
            String password = EncryptDecryptPassword.Encrypt(command[3]);
            int port = Integer.parseInt(command[4]);
            operateDataBase.insertData(command[0],command[1],username,password,port);
        } else {
            HelpPrompt.printInputError();
        }
    }


    /** 从Excel文件中添加服务器信息 */
    void addServerFromExcelFile(OperateDataBase operateDataBase){
        HelpPrompt.printExcelFilePath();
        String ask_excel_file = getInputContent();
        if (! FunctionKit.checkStringLengthIsZero(ask_excel_file)){
            HelpPrompt.printInputError();
        } else {
            GetIPInfoFromFile getIPInfoFromFile = new GetIPInfoFromExcel();
            HashMap<String,Object> servers_map = getIPInfoFromFile.getIPInfo(ask_excel_file);
            if (servers_map.size() != 0){
                for (String ip : servers_map.keySet()){
                    ServerInfo serverInfo = (ServerInfo)servers_map.get(ip);
                    String group = serverInfo.getServer_group();
                    String username = EncryptDecryptPassword.Encrypt(serverInfo.getUsername());
                    String password = EncryptDecryptPassword.Encrypt(serverInfo.getPassword());
                    int port = serverInfo.getPort();
                    operateDataBase.insertData(ip,group,username,password,port);
                }
            }
        }
    }


    /** 列出数据库中的服务器信息（IP Group） */
    void listIPGroupFromDatabase(OperateDataBase operateDataBase){
        if (operateDataBase.isTableNull()){
            HelpPrompt.printNoDataInDataBase();
        } else {
            HelpPrompt.printListServer();
            String ask_list_group = getInputContent();
            if (! FunctionKit.checkStringLengthIsZero(ask_list_group)){
                HelpPrompt.printInputError();
            } else {
                String list_group[] = ask_list_group.split("\\s+");
                if (list_group.length == 1 && "all".equals(list_group[0])){
                    operateDataBase.listAllIPGroupOnConditionIsAll();
                } else {
                    for (String group_name : list_group){
                        if (operateDataBase.isGroupExists(group_name)){
                            operateDataBase.loopPrintIPGroup(group_name);
                        } else {
                            HelpPrompt.printIpOrGroupNotExists(group_name);
                        }
                    }
                }
            }
        }
    }


    /** 列出数据库中的分组 */
    void listGroupFromDatabase(OperateDataBase operateDataBase){
        operateDataBase.QueryAllGroups();
    }


    /** 删除指定的服务器信息 */
    void deleteServerInfoFromDatabase(OperateDataBase operateDataBase){
        if (operateDataBase.isTableNull()){
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
                        operateDataBase.clearAllServerInfoFromTable();
                        HelpPrompt.printClearTableSucessful();
                    } else {
                        HelpPrompt.printNothingHappen();
                    }
                } else {
                    for (String ip_or_group : delete_ip_group){
                        if (FunctionKit.checkStringIsIP(ip_or_group)){
                            operateDataBase.deleteDataPassIP(ip_or_group);
                        } else {
                            operateDataBase.deleteDataPassGroup(ip_or_group);
                        }
                    }
                }
            }
        }
    }


    /** 为连接服务器做准备 */
    void connectServerForSSHSFTP(OperateDataBase operateDataBase,String ssh_sftp){
        if (operateDataBase.isTableNull()){
            HelpPrompt.printNoDataInDataBase();
        } else {
            HelpPrompt.printListIPOrGroupOrAllForExec();
            String ask_server = getInputContent();
            if (! FunctionKit.checkStringLengthIsZero(ask_server)){
                HelpPrompt.printInputError();
            } else {
                if (operateDataBase.isTableNull()){
                    HelpPrompt.printNoDataInDataBase();
                } else {
                    String ip_or_group_array[] = ask_server.split("\\s+");
                    HashMap<String,Object> servers_map = new HashMap<String, Object>();
                    boolean check_occur_error = false;
                    if (ip_or_group_array.length == 1 && "all".equals(ip_or_group_array[0])){
                        servers_map = operateDataBase.selectAllServer();
                    } else {
                        for (String ip_or_group : ip_or_group_array){
                            if (FunctionKit.checkStringIsIP(ip_or_group)){
                                if (operateDataBase.isIPExists(ip_or_group)){
                                    servers_map.putAll(operateDataBase.selectIP(ip_or_group));
                                } else {
                                    HelpPrompt.printIpOrGroupNotExists(ip_or_group);
                                }
                            } else if (operateDataBase.isGroupExists(ip_or_group)) {
                                servers_map.putAll(operateDataBase.selectGroup(ip_or_group));
                            } else {
                                HelpPrompt.printIpOrGroupNotExists(ip_or_group);
                                check_occur_error = true;
                            }
                        }
                    }
                    if (! check_occur_error){
                        WriteLog writeLog = new WriteLog();
                        if ("ssh".equals(ssh_sftp)){
                            SessionPool.setSsh_connection_pool(new HashMap<String, Object>());
                            ExecCommand.setWriteLog(writeLog);
                            ConnectServer.setWriteLog(writeLog);
                            loopGetCommandForExec(writeLog,servers_map);
                        } else {
                            SessionPool.setSftp_connection_pool(new HashMap<String, Object>());
                            System.out.println("SFTP");
                        }
                    }
                }
            }
        }
    }


    /** 循环从命令行获取命令用于执行 */
    void loopGetCommandForExec(WriteLog writeLog,HashMap<String,Object> servers_map){
        HelpPrompt.printAskExecCommand();
        String command = getInputContent();
        if (! FunctionKit.checkStringLengthIsZero(command)) {
            HelpPrompt.printInputError();
        } else if ("q".equals(command) || "Q".equals(command)){
            HelpPrompt.printExitExecCommand();
            HashMap<String, Object> ssh_connection_pool = SessionPool.getSsh_connection_pool();
            if (ssh_connection_pool.size() != 0){
                for (String ip : ssh_connection_pool.keySet()){
                    ConnectionObject connectionObject = (ConnectionObject)ssh_connection_pool.get(ip);
                    if (connectionObject.getSession().isConnected()){
                        connectionObject.getSession().disconnect();
                    }
                }
            }
        } else {
            writeLog.writeCommand(command);
            //设置要执行的命令
            if (FunctionKit.checkCommandIsForceDeleteRootDirectory(command)){
                HelpPrompt.printYouCanntExecThisCommand();
            } else {
                ExecCommand.setCommand(command);
                MultiThread multiThread = new MultiThread();
                HashMap<String, Object> ssh_connection_pool = SessionPool.getSsh_connection_pool();
                int ssh_connection_pool_size = ssh_connection_pool.size();
                if (ssh_connection_pool_size == 0){
                    multiThread.startMultiThread("new",servers_map);  //多线程执行
                } else {
                    multiThread.startMultiThread("session",ssh_connection_pool); //使用连接池 多线程执行
                }
            }

            //递归调用自身获取命令执行
            loopGetCommandForExec(writeLog,servers_map);
        }
    }
}

