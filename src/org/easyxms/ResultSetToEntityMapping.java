package org.easyxms;

import java.sql.ResultSet;


public interface ResultSetToEntityMapping {
    public Object mapping(ResultSet rs);
}
