package org.easyxms;


import java.sql.*;
import java.util.HashMap;


class OperateDataBase {

    private SqlRelevant sqlRelevant = null;
    private Connection conn = null;
    private Statement stat = null;
    private boolean conn_is_ok = true;
    private String get_single_ip_sql = null;
    private String get_single_group_sql = null;
    private String get_specify_ip_group_sql = null;
    private String get_all_ip_group_sql = null;
    private String get_insert_sql = null;
    private String get_delete_pass_ip = null;
    private String get_delete_pass_group = null;
    private String get_clear_table = null;
    private String get_all_sql = null;


    OperateDataBase(){
        sqlRelevant = new SqlRelevant();
        get_single_ip_sql = sqlRelevant.getQuery_single_ip_sql();
        get_single_group_sql = sqlRelevant.getQuery_single_group_sql();
        get_specify_ip_group_sql = sqlRelevant.getQuery_specify_ip_group_sql();
        get_all_ip_group_sql = sqlRelevant.getQuery_all_ip_group_sql();
        get_insert_sql = sqlRelevant.getInsert_sql();
        get_delete_pass_ip = sqlRelevant.getDelete_pass_ip();
        get_delete_pass_group = sqlRelevant.getDelete_pass_group();
        get_clear_table = sqlRelevant.getClear_table();
        get_all_sql = sqlRelevant.getQuery_all_sql();
    }

    public Connection getConn() {
        return conn;
    }
    public Statement getStat() {
        return stat;
    }


    /** 连接数据库 */
    void connectDatabase(){
        try {
            Class.forName(sqlRelevant.getDriver());
            conn = DriverManager.getConnection(sqlRelevant.getSqlite_jdbc_name());
            stat = conn.createStatement();
            stat.executeUpdate(sqlRelevant.getCreate_table());
        } catch (Exception e){
            HelpPrompt.printConnectDatabaseFailed();
            HelpPrompt.printInfo(e.getMessage());
            conn_is_ok = false;
        }
    }

    /** 格式化SQL语句 */
    String formatSQL(String format,String content){
        return String.format(format,content);
    }


    /** 执行SQL语句查询数据 */
    ResultSet selectData(String selectSQL){
        ResultSet rs = null;
        if (conn_is_ok){
            try {
                rs = stat.executeQuery(selectSQL);
            } catch (Exception e){
                HelpPrompt.printInfo(e.getMessage());
            }
        } else {
            HelpPrompt.printConnectDatabaseFailed();
        }
        return rs;
    }


    /** 判断输入的内容是否存在于数据库中 */
    boolean isEnterExists(ResultSet rs){
        boolean is_exists = false;
        try {
            if (rs.next()){
                is_exists = true;
            }
        } catch (Exception e){
            HelpPrompt.printInfo(e.getMessage());
        }
        return is_exists;
    }


    /** 判断输入的分组是否存在于数据库中 */
    boolean isGroupExists(String group){
        ResultSet rs_group_is_exists = selectData(formatSQL(get_specify_ip_group_sql,group));
        return isEnterExists(rs_group_is_exists);
    }


    /** 判断输入的IP是否存在于数据库中 */
    boolean isIPExists(String ip){
        ResultSet rs_ip_is_exists = selectData(formatSQL(get_single_ip_sql,ip));
        return isEnterExists(rs_ip_is_exists);
    }


    /** 查询整个表是否为空 */
    boolean isTableNull(){
        boolean is_table_null = true;
        ResultSet rs = selectData(get_all_ip_group_sql);
        if (isEnterExists(rs)){
            is_table_null = false;
        }
        return is_table_null;
    }


    /** 当条件是all时，列出所有的ip,server_group字段 */
    void listAllIPGroupOnConditionIsAll(){
        if (isTableNull()){
            HelpPrompt.printNoDataInDataBase();
        } else {
            loopPrintIPGroup("all");
        }
    }


    /** 根据分组名循环输出ip,group查询结果 */
    void loopPrintIPGroup(String group_name){
        ResultSet rs = null;
        if (group_name.equals("all")){
            rs = selectData(get_all_ip_group_sql);
        } else {
            rs = selectData(formatSQL(get_specify_ip_group_sql,group_name));
        }
        try {
            while (rs.next()){
                HelpPrompt.printIPGroup(rs.getString("ip"),rs.getString("server_group"));
            }
        } catch (Exception e){
            HelpPrompt.printInfo(e.getMessage());
        }
    }


    /** 查询所有分组 */
    void QueryAllGroups(){
        if (isTableNull()){
            HelpPrompt.printNoDataInDataBase();
        } else {
            ResultSet rs = selectData(sqlRelevant.getList_groups_sql());
            try {
                while (rs.next()){
                    System.out.println(rs.getString("server_group"));
                }
            } catch (Exception e){
                HelpPrompt.printInfo(e.getMessage());
            }
        }
    }


    /** 插入数据 */
    void insertData(String ip,String group,String username,String password,int port){
        if (conn_is_ok){
            try {
                if (isIPExists(ip)){
                    HelpPrompt.printIPAlreadyExists(ip);
                } else {
                    stat.executeUpdate(String.format(get_insert_sql,ip,group,username,password,port));
                    HelpPrompt.printAddServerSuccess(ip);
                }
            } catch (Exception e) {
                HelpPrompt.printInfo(e.getMessage());
            }
        } else {
            HelpPrompt.printConnectDatabaseFailed();
        }
    }


    /** 通过IP删除服务器信息 */
    void deleteDataPassIP(String ip){
        if (conn_is_ok){
            String delete_sql = formatSQL(get_delete_pass_ip,ip);
            try {
                if (isIPExists(ip)){
                    stat.executeUpdate(delete_sql);
                    HelpPrompt.printDeleteServerInfoSucessful(ip);
                } else {
                    HelpPrompt.printIpOrGroupNotExists(ip);
                }
            } catch (Exception e){
                HelpPrompt.printInfo(e.getMessage());
            }
        } else {
            HelpPrompt.printConnectDatabaseFailed();
        }
    }


    /** 通过分组删除服务器信息 */
    void deleteDataPassGroup(String group){
        if (conn_is_ok){
            String delete_sql = formatSQL(get_delete_pass_group,group);
            try {
                if (isGroupExists(group)){
                    stat.executeUpdate(delete_sql);
                    HelpPrompt.printDeleteServerInfoSucessful(group);
                } else {
                    HelpPrompt.printIpOrGroupNotExists(group);
                }
            } catch (Exception e){
                HelpPrompt.printInfo(e.getMessage());
            }
        } else {
            HelpPrompt.printConnectDatabaseFailed();
        }
    }


    /** 清空整个表 */
    void clearAllServerInfoFromTable(){
        if (conn_is_ok){
            String clear_all_server_sql = get_clear_table;
            try {
                stat.executeUpdate(clear_all_server_sql);
            } catch (Exception e){
                HelpPrompt.printInfo(e.getMessage());
            }
        } else {
            HelpPrompt.printConnectDatabaseFailed();
        }
    }


    /** 把查询出来的字段放入到ServerInfo对象中 */
    HashMap<String,Object> putAllInfoIntoObject(ResultSet rs){
        HashMap<String,Object> servers_map = new HashMap<String, Object>();
        try {
            while (rs.next()){
                String ip = rs.getString("ip");
                String server_group = rs.getString("server_group");
                String username = EncryptDecryptPassword.Decrypt(rs.getString("username"));
                String password = EncryptDecryptPassword.Decrypt(rs.getString("password"));
                String port = rs.getString("port");
                ServerInfo serverInfo = new ServerInfo();
                serverInfo.setServer_group(server_group);
                serverInfo.setUsername(username);
                serverInfo.setPassword(password);
                serverInfo.setPort(Integer.parseInt(port));
                servers_map.put(ip,serverInfo);
            }
        } catch (SQLException e){
            HelpPrompt.printInfo(e.getMessage());
        }
        return servers_map;
    }


    /** 查询单个IP得到所有的字段 */
    HashMap<String,Object> selectIP(String ip){
        String select_ip = formatSQL(get_single_ip_sql,ip);
        ResultSet rs = selectData(select_ip);
        return putAllInfoIntoObject(rs);
    }


    /** 查询分组得到分组内所有服务器的所有字段 */
    HashMap<String,Object> selectGroup(String group){
        String select_group = formatSQL(get_single_group_sql,group);
        ResultSet rs = selectData(select_group);
        return putAllInfoIntoObject(rs);
    }


    /** 查询所有服务器的所有字段用于连接服务器 */
    HashMap<String,Object> selectAllServer(){
        String select_all = get_all_sql;
        ResultSet rs = selectData(select_all);
        return putAllInfoIntoObject(rs);
    }
}
