package org.easyxms;


import java.sql.ResultSet;
import java.sql.SQLException;


class ServerInfoMapping implements ResultSetToEntityMapping {


    /**
     * 把ResultSet结果集转换为ServerInfo对象
     * @param rs 结果集对象
     * @return 一个ServerInfo对象
     */
    @Override
    public ServerInfo mapping(ResultSet rs) {

        ServerInfo serverInfo = new ServerInfo();
        int i = 1;
        try {
            serverInfo = new ServerInfo(rs.getString(i++),rs.getString(i++),rs.getString(i++),
                    rs.getString(i++),rs.getInt(i++));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return serverInfo;
    }
}
