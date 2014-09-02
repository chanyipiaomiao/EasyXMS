package org.easyxms;


import com.jcraft.jsch.SftpProgressMonitor;
import java.text.DecimalFormat;


class FileTransferProgressMonitor implements SftpProgressMonitor{

    private long transfered;
    private static long filesize;
    private String ip;

    FileTransferProgressMonitor(String ip) {
        this.ip = ip;
    }

    @Override
    public void init(int op, String src, String dst, long file_length) {
    }

    @Override
    public boolean count(long count) {
        transfered = transfered + count;
        DecimalFormat percent_format = new DecimalFormat("#.##");
        double percent = ((double)transfered * 100)/(double)filesize;
        System.out.printf("\r%s %s %s%% Transfered: %d Total: %d", ip, "正在传输...",
                percent_format.format(percent), transfered, filesize);
        return true;
    }

    @Override
    public void end() {
        System.out.println();
    }

    public static void setFilesize(long filesize) {
        FileTransferProgressMonitor.filesize = filesize;
    }
}
