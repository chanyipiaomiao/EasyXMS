package org.easyxms;

import java.sql.ResultSet;


/**
 * 结果集转换为实体类接口
 */
public interface ResultSetToEntityMapping {
    public Object mapping(ResultSet rs);
}
