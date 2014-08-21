package org.easyxms;


import com.jcraft.jsch.Session;
import java.util.HashMap;


/**
 * Session连接池
 */
class SessionPool {

    private static HashMap<String, Session> ssh_connection_pool = null;
    private static HashMap<String, Session> sftp_connection_pool = null;

    public static void setSsh_connection_pool(HashMap<String, Session> ssh_connection_pool) {
        SessionPool.ssh_connection_pool = ssh_connection_pool;
    }

    public static void setSftp_connection_pool(HashMap<String, Session> sftp_connection_pool) {
        SessionPool.sftp_connection_pool = sftp_connection_pool;
    }

    public static HashMap<String, Session> getSsh_connection_pool() {
        return ssh_connection_pool;
    }

    public static HashMap<String, Session> getSftp_connection_pool() {
        return sftp_connection_pool;
    }
}
