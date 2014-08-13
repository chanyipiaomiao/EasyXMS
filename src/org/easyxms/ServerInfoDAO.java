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

    public void insert(){
        String sql = "insert into ServerInfo (ip,server_group,username,password,port) values(?,?,?,?,?)";
    }

    public List<ServerInfo> queryAllFieldByIP(String ip){
        String sql = "select * from ServerInfo where ip = ?";
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(ip);
        return dbManager.query(sql,args,new ServerInfoMapping());
    }

    public List<ServerInfo> queryAllFieldByServerGroup(String server_group){
        String sql = "select * from ServerInfo where server_group = ?";
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.query(sql,args,new ServerInfoMapping());
    }

    public List<String> queryDistinctServerGroup(){
        String sql = "select distinct server_group from ServerInfo";
        DBManager dbManager = new DBManager();
        return dbManager.query(sql,new ArrayList(),"group");
    }

    public List<String> queryIPServerGroup(){
        String sql = "select ip,server_group from ServerInfo";
        DBManager dbManager = new DBManager();
        return dbManager.query(sql,new ArrayList(),"ip_group");
    }

    public List<String> queryIPServerGroupByServerGroup(String server_group){
        String sql = "select ip,server_group from ServerInfo where server_group = ?";
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.query(sql,args,"ip_group_bygroup");
    }

    public List<ServerInfo> queryAll(){
        String sql = "select * from ServerInfo";
        DBManager dbManager = new DBManager();
        return dbManager.query(sql,new ArrayList(),new ServerInfoMapping());
    }

    public int deleteByIP(String ip){
        String sql = "delete from ServerInfo where ip = ?";
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(ip);
        return dbManager.update(sql,args);
    }

    public int deleteByServerGroup(String server_group){
        String sql = "delete from ServerInfo where server_group = ?";
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.update(sql,args);
    }

    public int deleteAll(){
        String sql = "delete from ServerInfo";
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        return dbManager.update(sql,args);
    }
}
