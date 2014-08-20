package org.easyxms;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 用来执行连接数据库、插入、查询、删除操作
 */
class DBManager {


    /**
     * 获得数据库连接
     * @return 连接对象
     */
    Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:conf/easyxms.db");
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }


    /**
     * 创建表
     * @param create_table_sql 创建表语句
     */
    void create(String create_table_sql){
        Connection conn = this.getConnection();
        PreparedStatement psta = null;
        try {
            if (conn != null){
                psta = conn.prepareStatement(create_table_sql);
                psta.executeUpdate();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close(psta,conn);
        }
    }


    /**
     * 用来执行 insert 语句,并返回影响的行数
     * @param update_sql  要执行的插入,删除SQL语句
     * @param objects 是ServerInfo对象
     * @param query_sql  查询的某个IP或者组的SQL语句
     * @return 返回影响的行数
     *
     * */
    int[] insert(String update_sql,List<Object> objects,String query_sql){
        Connection conn = this.getConnection();
        PreparedStatement psta = null;
        int[] rows = new int[0];
        try {
            if (conn != null){
                psta = conn.prepareStatement(update_sql);
                for (Object obj : objects){
                    String ip = ((ServerInfo) obj).getIp();
                    if (query(query_sql,ip)){
                        HelpPrompt.printIPAlreadyExists(ip);
                    } else {
                        psta.setObject(1,ip);
                        psta.setObject(2,((ServerInfo) obj).getServer_group());
                        psta.setObject(3,((ServerInfo) obj).getUsername());
                        psta.setObject(4,((ServerInfo) obj).getPassword());
                        psta.setObject(5,((ServerInfo) obj).getPort());
                        psta.addBatch();
                        HelpPrompt.printAddServerSuccess(ip);
                    }
                }
                rows = psta.executeBatch();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close(psta,conn);
        }
        return rows;
    }


    /**
     * 删除操作
     * @param delete_sql 删除的SQL语句
     * @param args       删除语句的参数
     * @param query_sql  查询语句，删除之前需要查询是否存在
     * @return 返回一个影响的行数的数组
     */
    int[] delete(String delete_sql,List<String> args,String query_sql){
        int[] rows = new int[0];
        Connection conn = this.getConnection();
        PreparedStatement psta = null;
        try {
            if (conn != null){
                psta = conn.prepareStatement(delete_sql);
                String isIPorGroup = args.get(0);
                if (query(query_sql,isIPorGroup)){
                    psta.setObject(1,isIPorGroup);
                    psta.executeUpdate();
                    HelpPrompt.printDeleteServerInfoSucessful(isIPorGroup);
                } else {
                    HelpPrompt.printIpOrGroupNotExists(isIPorGroup);
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close(psta,conn);
        }
        return rows;
    }


    /**
     * 清空表
     * @param delete_all_sql 清空表的SQL语句
     * @return 影响的行数
     */
    int delete(String delete_all_sql){
        int rows = 0;
        Connection conn = this.getConnection();
        PreparedStatement psta = null;
        try {
            psta = conn.prepareStatement(delete_all_sql);
            rows = psta.executeUpdate();
            HelpPrompt.printClearTableSucessful();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close(psta,conn);
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
        Connection conn = this.getConnection();
        PreparedStatement psta = null;
        ResultSet rs = null;
        try {
            if (conn != null){
                psta = conn.prepareStatement(sql);
                psta.setString(1,ip_group);
                rs = psta.executeQuery();
                if (rs.next()){
                    return true;
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close(rs,psta,conn);
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
        Connection conn = this.getConnection();
        List<ServerInfo> serverinfo_objs = new ArrayList<ServerInfo>();
        PreparedStatement psta = null;
        ResultSet rs = null;
        try {
            if (conn != null){
                psta = conn.prepareStatement(sql);
                for (int i = 0; i < args.size(); i++) {
                    psta.setObject(i+1,args.get(i));
                }
                rs = psta.executeQuery();
                while (rs.next()){
                    serverinfo_objs.add((ServerInfo)mapping.mapping(rs));
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close(rs,psta,conn);
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
        Connection conn = this.getConnection();
        PreparedStatement psta = null;
        ResultSet rs = null;
        List<String> result_list = new ArrayList<String>();
        StringBuilder str = new StringBuilder("");
        try {
            if (conn != null){
                psta = conn.prepareStatement(sql);
                for (int i = 0; i < args.size(); i++) {
                    psta.setObject(i+1,args.get(i));
                }
                rs = psta.executeQuery();
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
            this.close(rs,psta,conn);
        }
        return result_list;
    }


    /**
     * 关闭函数
     *
     * */
    void close(ResultSet rs,PreparedStatement psta,Connection conn){
        try {
            if (rs !=null){
                rs.close();
            }
            if (psta !=null){
                psta.close();
            }
            if (conn != null){
                conn.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    /**
     * 关闭函数
     *
     * */
    void close(PreparedStatement psta,Connection conn){
        try {
            if (psta !=null){
                psta.close();
            }
            if (conn != null){
                conn.close();
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
