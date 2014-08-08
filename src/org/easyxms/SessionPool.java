package org.easyxms;


import java.util.HashMap;

class SessionPool {

    private static HashMap<String, Object> ssh_connection_pool = null;
    private static HashMap<String, Object> sftp_connection_pool = null;

    public static void setSsh_connection_pool(HashMap<String, Object> ssh_connection_pool) {
        SessionPool.ssh_connection_pool = ssh_connection_pool;
    }

    public static void setSftp_connection_pool(HashMap<String, Object> sftp_connection_pool) {
        SessionPool.sftp_connection_pool = sftp_connection_pool;
    }

    public static HashMap<String, Object> getSsh_connection_pool() {
        return ssh_connection_pool;
    }

    public static HashMap<String, Object> getSftp_connection_pool() {
        return sftp_connection_pool;
    }
}
