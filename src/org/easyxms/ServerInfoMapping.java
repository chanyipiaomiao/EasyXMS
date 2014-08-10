package org.easyxms;


import java.sql.ResultSet;
import java.sql.SQLException;

class ServerInfoMapping implements ResultSetToEntityMapping {

    @Override
    public Object mapping(ResultSet rs) {
        int i = 1;
        ServerInfo serverInfo = null;
        try {
            serverInfo = new ServerInfo(rs.getString(i++),rs.getString(i++),rs.getString(i++),
                    rs.getString(i++),rs.getInt(i++));

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return serverInfo;
    }
}
