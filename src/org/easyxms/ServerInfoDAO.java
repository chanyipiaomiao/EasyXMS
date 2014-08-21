package org.easyxms;


import java.util.ArrayList;
import java.util.List;


/**
 * ServerInfo数据库访问对象类
 */
class ServerInfoDAO {


    private String insert = "insert into ServerInfo (ip,server_group,username,password,port) values(?,?,?,?,?)";
    private String queryAllFieldByIP = "select * from ServerInfo where ip = ?";
    private String queryAllFieldByServerGroup = "select * from ServerInfo where server_group = ?";
    private String queryDistinctServerGroup = "select distinct server_group from ServerInfo";
    private String queryIPServerGroup = "select ip,server_group from ServerInfo";
    private String queryIPServerGroupByServerGroup = "select ip,server_group from ServerInfo where server_group = ?";
    private String queryAll = "select * from ServerInfo";
    private String deleteByIP = "delete from ServerInfo where ip = ?";
    private String deleteByServerGroup = "delete from ServerInfo where server_group = ?";
    private String deleteAll = "delete from ServerInfo";
    private String createTable = "create table if not exists ServerInfo " +
                                "(ip text not null,server_group text not null," +
                                "username text not null default 'root',password text not null," +
                                "port integer not null default 22)";


    /**
     * 在初始化的时候创建表
     */
    ServerInfoDAO() {
        this.createTable();
    }


    /**
     * 创建表
     */
    public void createTable(){
        DBManager dbManager = new DBManager();
        dbManager.create(createTable);
    }


    /**
     * 插入信息
     * @param objects ServerInfo对象列表
     * @return 整数数组,每一个元素代表每一条语句影响的行数
     */
    public int[] insert(List<Object> objects){
        DBManager dbManager = new DBManager();
        return dbManager.insert(insert, objects, queryAllFieldByIP);
    }


    /**
     * 根据IP地址进行查询所有字段
     * @param ip 要查询的IP地址
     * @return 一个ServerInfo对象列表
     */
    public List<ServerInfo> queryAllFieldByIP(String ip){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(ip);
        return dbManager.query(queryAllFieldByIP,args,new ServerInfoMapping());
    }

    /**
     * 根据分组来查询所有字段
     * @param server_group 分组
     * @return 一个ServerInfo对象列表
     */
    public List<ServerInfo> queryAllFieldByServerGroup(String server_group){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.query(queryAllFieldByServerGroup,args,new ServerInfoMapping());
    }


    /**
     * 查询所有的分组
     * @return 一个分组字符串列表
     */
    public List<String> queryDistinctServerGroup(){
        DBManager dbManager = new DBManager();
        return dbManager.query(queryDistinctServerGroup,new ArrayList(),"group");
    }


    /**
     * 查询出IP和分组
     * @return 一个IP分组字符串列表
     */
    public List<String> queryIPServerGroup(){
        DBManager dbManager = new DBManager();
        return dbManager.query(queryIPServerGroup,new ArrayList(),"ip_group");
    }


    /**
     * 根据分组来查询IP和分组
     * @param server_group 分组
     * @return 一个IP分组字符串列表
     */
    public List<String> queryIPServerGroupByServerGroup(String server_group){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.query(queryIPServerGroupByServerGroup,args,"ip_group_bygroup");
    }


    /**
     * 查询所有的字段
     * @return 一个ServerInfo对象列表
     */
    public List<ServerInfo> queryAll(){
        DBManager dbManager = new DBManager();
        return dbManager.query(queryAll,new ArrayList(),new ServerInfoMapping());
    }


    /**
     * 根据IP进行删除
     * @param ip IP地址
     * @return 一个整数数组,每一个元素代表每一条语句影响的行数
     */
    public int[] deleteByIP(String ip){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(ip);
        return dbManager.delete(deleteByIP, args, queryAllFieldByIP);
    }


    /**
     * 根据分组进行删除
     * @param server_group 分组
     * @return 一个整数数组,每一个元素代表每一条语句影响的行数
     */
    public int[] deleteByServerGroup(String server_group){
        DBManager dbManager = new DBManager();
        List<String> args = new ArrayList<String>();
        args.add(server_group);
        return dbManager.delete(deleteByServerGroup,args,queryAllFieldByServerGroup);
    }


    /**
     * 清空表
     * @return 影响的的行数
     */
    public int deleteAll(){
        DBManager dbManager = new DBManager();
        return dbManager.delete(deleteAll);
    }
}
