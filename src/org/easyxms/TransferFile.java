package org.easyxms;


import com.jcraft.jsch.Session;

public interface TransferFile {
    public void transfer(Session session);
}
