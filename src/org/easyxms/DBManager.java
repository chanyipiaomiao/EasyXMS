package org.easyxms;


import java.io.ObjectStreamException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 用来执行连接数据库、更新、查询操作
 */
class DBManager {

    private Connection conn = null;
    private PreparedStatement psta = null;
    private ResultSet rs = null;


    DBManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:conf/easyxms.db");
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    /**
     * 用来执行 insert update delete 语句,并返回影响的行数
     * @param sql  要执行的SQL语句
     * @param objects 是ServerInfo对象
     * @return 返回影响的行数
     *
     * */
    int[] update(String sql,List<Object> objects){
        int[] rows = new int[objects.size()];
        try {
            if (conn != null){
                psta = conn.prepareStatement(sql);
                for (Object obj : objects){
                    String ip = ((ServerInfo) obj).getIp();
                    if (query(sql,ip)){

                    }
                    psta.setObject(1,ip);
                    psta.setObject(2,((ServerInfo) obj).getServer_group());
                    psta.setObject(3,((ServerInfo) obj).getUsername());
                    psta.setObject(4,((ServerInfo) obj).getPassword());
                    psta.setObject(5,((ServerInfo) obj).getPort());
                    psta.addBatch();
                }
                rows = psta.executeBatch();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close();
        }
        return rows;
    }


    /**
     * 用来执行select语句并返回true false，用来查询ip或者server_group是否存在
     * @param sql 要执行的SQL语句
     * @param ip_group 要查询的是ip或者是server_group
     * @return 如果ip或者是server_group存在则返回true,否则返回false
     */
    boolean query(String sql,String ip_group){
        try {
            if (conn != null){
                psta = conn.prepareStatement(sql);
                psta.setString(1,ip_group);
                ResultSet rs = psta.executeQuery();
                if (rs.next()){
                    return true;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }


    /**
     * 用来执行 select 语句,并返回一个实体对象List
     * @param sql  要执行的SQL语句
     * @param args 构造SQL语句的字段集合
     * @param mapping ResultSet转换为实体类的接口对象
     * @return 返回一个List
     *
     * */
    List<ServerInfo> query(String sql,List args,ResultSetToEntityMapping mapping){
        List<ServerInfo> serverinfo_objs = new ArrayList<ServerInfo>();
        try {
            if (conn != null){
                psta = conn.prepareStatement(sql);
                for (int i = 0; i < args.size(); i++) {
                    psta.setObject(i+1,args.get(i));
                }
                ResultSet rs = psta.executeQuery();
                while (rs.next()){
                    serverinfo_objs.add((ServerInfo)mapping.mapping(rs));
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close();
        }
        return serverinfo_objs;
    }


    /**
     * 用来执行 select 语句,并返回一个String List
     * @param sql   要执行的SQL语句
     * @param args  SQL语句的参数
     * @param identifier 用来标识sql语句想要获得的列名
     * @return  返回一个String List
     */
    List<String> query(String sql,List args,String identifier){
        List<String> result_list = new ArrayList<String>();
        StringBuilder str = new StringBuilder("");
        try {
            if (conn != null){
                psta = conn.prepareStatement(sql);
                for (int i = 0; i < args.size(); i++) {
                    psta.setObject(i+1,args.get(i));
                }
                ResultSet rs = psta.executeQuery();
                if ("ip_group".equals(identifier)){
                    while (rs.next()){
                        str.append(rs.getString("ip"));
                        str.append(" ");
                        str.append(rs.getString("server_group"));
                        result_list.add(str.toString());
                        str.setLength(0);
                    }
                } else if ("ip_group_bygroup".equals(identifier)){
                    while (rs.next()){
                        str.append(rs.getString("ip"));
                        str.append(" ");
                        str.append(rs.getString("server_group"));
                        result_list.add(str.toString());
                        str.setLength(0);
                    }
                } else if ("group".equals(identifier)){
                    while (rs.next()){
                        result_list.add(rs.getString("server_group"));
                    }
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close();
        }
        return result_list;
    }


    /**
     * 关闭函数
     *
     * */
    void close(){
        try {
            if (rs !=null){
                rs.close();
                rs = null;
            }
            if (psta !=null){
                psta.close();
                psta = null;
            }
            if (conn != null){
                conn.close();
                conn = null;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
