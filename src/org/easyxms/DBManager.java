package org.easyxms;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DBManager {

    private Connection conn = null;
    private Statement sta = null;
    private PreparedStatement psta = null;
    private ResultSet rs = null;

    /**
     * 用来执行 insert update delete 语句,并返回影响的行数
     * @param sql  要执行的SQL语句
     * @param args 构造SQL语句的字段集合
     * @return 返回影响的行数
     *
     * */
    public int update(String sql,List args){
        int row = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:conf/easyxms.db");
            psta = conn.prepareStatement(sql);
            for (int i = 0; i < args.size(); i++) {
                psta.setObject(i+1,args.get(i));
            }
            row = psta.executeUpdate();
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close();
        }
        return row;
    }


    public List query(String sql,List args,ResultSetToEntityMapping mapping){
        System.out.println(sql);
        List<Object> serverinfo_objs = new ArrayList<Object>();
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:conf/easyxms.db");
            psta = conn.prepareStatement(sql);
            for (int i = 0; i < args.size(); i++) {
                psta.setObject(i+1,args.get(i));
            }
            ResultSet rs = psta.executeQuery();
            while (rs.next()){
                serverinfo_objs.add(mapping.mapping(rs));
            }
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {
            this.close();
        }
        return serverinfo_objs;
    }


    /**
     * 关闭函数
     *
     * */
    public void close(){
        try {
            if (rs !=null){
                rs.close();
                rs = null;
            }
            if (psta !=null){
                psta.close();
                psta = null;
            }
            if (sta != null){
                sta.close();
                sta = null;
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
