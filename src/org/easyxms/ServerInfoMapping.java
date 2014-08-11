package org.easyxms;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class ServerInfoMapping implements ResultSetToEntityMapping {

    private int cols = 0;

    ServerInfoMapping(int cols) {
        this.cols = cols;
    }

    @Override
    public Object mapping(ResultSet rs) {
        int i = 1;
        ServerInfo serverInfo = new ServerInfo();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        try {
            while (i <= this.cols){
                arrayList.add(rs.getObject(i));
                i++;
            }
            serverInfo = new ServerInfo(rs.getString(i++),rs.getString(i++),rs.getString(i++),
                    rs.getString(i++),rs.getInt(i++));
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return serverInfo;
    }
}
