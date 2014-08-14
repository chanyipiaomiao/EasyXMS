package org.easyxms;


import java.util.ArrayList;
import java.util.List;



class ServerInfoDAO {


    static String insert = "insert into ServerInfo (ip,server_group,username,password,port) values(?,?,?,?,?)";
    static String queryAllFieldByIP = "select * from ServerInfo where ip = ?";
    static String queryAllFieldByServerGroup = "select * from ServerInfo where server_group = ?";
    static String queryDistinctServerGroup = "select distinct server_group from ServerInfo";
    static String queryIPServerGroup = "select ip,server_group from ServerInfo";
    static String queryIPServerGroupByServerGroup = "select ip,server_group from ServerInfo where server_group = ?";
    static String queryAll = "select * from ServerInfo";
    static String deleteByIP = "delete from ServerInfo where ip = ?";
    static String deleteByServerGroup = "delete from ServerInfo where server_group = ?";
    static String deleteAll = "delete from ServerInfo";

    public void create(){
        String sql = "create table if not exists ServerInfo " +
                "(ip text not null,server_group text not null," +
                "username text not null default 'root',password text not null," +
                "port integer not null default 22)";
    }

    public int[] insert(List<Object> objects){
        DBManager dbManager = new DBManager();
        return dbManager.update(insert,objects);
    }

    public List<ServerInfo> queryAllFieldByIP(String ip){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(ip);
        return dbManager.query(queryAllFieldByIP,args,new ServerInfoMapping());
    }

    public List<ServerInfo> queryAllFieldByServerGroup(String server_group){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.query(queryAllFieldByServerGroup,args,new ServerInfoMapping());
    }

    public List<String> queryDistinctServerGroup(){
        DBManager dbManager = new DBManager();
        return dbManager.query(queryDistinctServerGroup,new ArrayList(),"group");
    }

    public List<String> queryIPServerGroup(){
        DBManager dbManager = new DBManager();
        return dbManager.query(queryIPServerGroup,new ArrayList(),"ip_group");
    }

    public List<String> queryIPServerGroupByServerGroup(String server_group){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.query(queryIPServerGroupByServerGroup,args,"ip_group_bygroup");
    }

    public List<ServerInfo> queryAll(){
        DBManager dbManager = new DBManager();
        return dbManager.query(queryAll,new ArrayList(),new ServerInfoMapping());
    }

//    public int deleteByIP(String ip){
//        DBManager dbManager = new DBManager();
//        List<String> args = new ArrayList<String>();
//        args.add(ip);
//        return dbManager.update(deleteByIP,args);
//    }
//
//    public int deleteByServerGroup(String server_group){
//        DBManager dbManager = new DBManager();
//        List<String> args = new ArrayList<String>();
//        args.add(server_group);
//        return dbManager.update(deleteByServerGroup,args);
//    }
//
//    public int deleteAll(){
//        DBManager dbManager = new DBManager();
//        List<String> args = new ArrayList<String>();
//        return dbManager.update(deleteAll,args);
//    }
}
