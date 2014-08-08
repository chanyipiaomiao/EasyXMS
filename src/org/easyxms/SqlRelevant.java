package org.easyxms;


class SqlRelevant {

    private String driver = "org.sqlite.JDBC";
    private static String db_name = "conf/easyxms.db";
    private String sqlite_jdbc_name = String.format("jdbc:sqlite:%s", db_name);
    private String query_all_sql = "select * from easyxms";
    private String query_all_ip_group_sql = "select ip,server_group from easyxms";
    private String query_specify_ip_group_sql = "select ip,server_group from easyxms where server_group = '%s';";
    private String query_single_ip_sql = "select * from easyxms where ip = '%s'";
    private String query_single_group_sql = "select * from easyxms where server_group = '%s'";
    private String list_groups_sql = "select distinct server_group from easyxms";
    private String insert_sql = "insert into easyxms (ip,server_group,username,password,port) values('%s','%s','%s','%s',%d)";
    private String delete_pass_ip = "delete from easyxms where ip = '%s'";
    private String delete_pass_group = "delete from easyxms where server_group = '%s'";
    private String clear_table = "delete from easyxms";
    private String create_table = "create table if not exists easyxms (ip text not null,server_group text not null,username text not null default 'root',password text not null,port integer not null default 22)";

    public static void setDb_name(String db_name) {
        SqlRelevant.db_name = db_name;
    }
    public String getDriver() {
        return driver;
    }
    public String getSqlite_jdbc_name() { return sqlite_jdbc_name; }


    /** 返回创建表语句 */
    public String getCreate_table() {
        return create_table;
    }

    /** 返回插入语句 */
    public String getInsert_sql() {
        return insert_sql;
    }

    /** 返回查询所有IP的SQL语句 */
    public String getQuery_all_sql() {
        return query_all_sql;
    }

    /** 列出所有的IP地址和组 */
    public String getQuery_all_ip_group_sql() { return query_all_ip_group_sql; }

    /** 根据命令行输入的IP查询数据库 */
    public String getQuery_single_ip_sql() { return query_single_ip_sql; }

    /** 根据命令行输入的分组查询数据库该组是否存在和查询分组内的IP */
    public String getQuery_specify_ip_group_sql() { return query_specify_ip_group_sql; }

    /** 返回删除语句 条件是通过IP来删除 */
    public String getDelete_pass_ip() { return delete_pass_ip; }

    /** 返回删除语句 条件是通过分组来删除 */
    public String getDelete_pass_group() { return delete_pass_group; }

    /** 返回清空表语句 */
    public String getClear_table() { return clear_table; }

    /** 通过分组来查询所有服务器信息 */
    public String getQuery_single_group_sql() {
        return query_single_group_sql;
    }

    /** 列出数据库中所有的分组 */
    public String getList_groups_sql() {
        return list_groups_sql;
    }
}
