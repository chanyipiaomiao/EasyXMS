package org.easyxms;


import java.util.ArrayList;
import java.util.List;


class ServerInfoDAO {

    public void create(){
        String sql = "create table if not exists ServerInfo " +
                "(ip text not null,server_group text not null," +
                "username text not null default 'root',password text not null," +
                "port integer not null default 22)";
    }

    public void insert(String ip,String server_group,String username,String password,int port){
        String sql = "insert into ServerInfo (ip,server_group,username,password,port) values('?','?','?','?',?)";
    }

    public void queryAllFieldByIP(String ip){
        String sql = String.format("select * from ServerInfo where ip = '%s'",ip);
    }

    public void queryAllFieldByServerGroup(String server_group){
        String sql = String.format("select * from ServerInfo where server_group = '%s'",server_group);
    }

    public void queryDistinctServerGroup(){
        String sql = "select distinct server_group from ServerInfo";
    }

    public List<Object> queryIPServerGroup(){
        String sql = "select ip,server_group from ServerInfo";
        int cols = 2;
        DBManager dbManager = new DBManager();
        List<Object> result_list =  dbManager.query(sql,new ArrayList(),new ServerInfoMapping(cols));
        return result_list;
    }

    public void queryIPServerGroupByServerGroup(String server_group){
        String sql = String.format("select ip,server_group from server_info where server_group = '%s'",server_group);
    }

    public List<Object> queryAll(){
        String sql = "select * from ServerInfo";
        int cols = 5;
        DBManager dbManager = new DBManager();
        List<Object> result_list =  dbManager.query(sql,new ArrayList(),new ServerInfoMapping(cols));
        return result_list;
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
