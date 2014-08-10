package org.easyxms;


import java.util.ArrayList;

class ServerInfoDAO {

    public static void create(){
        String sql = "create table if not exists ServerInfo " +
                "(ip text not null,server_group text not null," +
                "username text not null default 'root',password text not null," +
                "port integer not null default 22)";
    }

    public static void insert(String ip,String server_group,String username,String password,int port){
        String sql = "insert into ServerInfo (ip,server_group,username,password,port) values('?','?','?','?',?)";
    }

    public static void selectAllFieldByIP(String ip){
        String sql = String.format("select * from ServerInfo where ip = '%s'",ip);
    }

    public static void selectAllFieldByServerGroup(String server_group){
        String sql = String.format("select * from ServerInfo where server_group = '%s'",server_group);
    }

    public static void selectDistinctServerGroup(){
        String sql = "select distinct server_group from ServerInfo";
    }

    public static void selectIPServerGroup(){
        String sql = "select ip,server_group from ServerInfo";
    }

    public static void selectIPServerGroupByServerGroup(String server_group){
        String sql = String.format("select ip,ServerInfo from server_info where server_group = '%s'",server_group);
    }

    public static void selectAll(){
        String sql = "select * from ServerInfo";
        DBManager dbManager = new DBManager();
        dbManager.query(sql,new ArrayList(),new ServerInfoMapping());
    }

    public static void deleteByIP(String ip){
        String sql = String.format("delete from ServerInfo where ip = '%s'",ip);
    }

    public static void deleteByServerGroup(String server_group){
        String sql = String.format("delete from ServerInfo where server_group = '%s'",server_group);
    }

    public static void deleteAll(){
        String sql = "delete from ServerInfo";
    }
}
