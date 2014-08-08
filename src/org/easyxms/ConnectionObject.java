package org.easyxms;


import com.jcraft.jsch.Session;


/** Session 对象 */
class ConnectionObject {

    private Session session = null;

    ConnectionObject(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
