package org.easyxms;


import com.jcraft.jsch.SftpProgressMonitor;
import java.text.DecimalFormat;


class FileTransferProgressMonitor implements SftpProgressMonitor{

    private long transfered;
    private static long filesize;

    @Override
    public void init(int op, String src, String dst, long file_length) {}

    @Override
    public boolean count(long count) {
        transfered = transfered + count;
        DecimalFormat percent_format = new DecimalFormat("#.##");
        double percent = ((double)transfered * 100)/(double)filesize;
        System.out.printf("\r%s%% Transfered: %d Total: %d",percent_format.format(percent),transfered,filesize);
        return true;
    }

    @Override
    public void end() {
        System.out.print("\nTransfer Completed.\n");
    }

    public static void setFilesize(long filesize) {
        FileTransferProgressMonitor.filesize = filesize;
    }
}
