package org.easyxms;

import com.jcraft.jsch.SftpProgressMonitor;
import java.text.DecimalFormat;

class FileUploadProgressMonitor implements SftpProgressMonitor {

    private long transfered;
    private static long filesize;


    @Override
    public void init(int op, String src, String dst, long file_length) {
        System.out.println("The Transferring Start ...");
    }

    @Override
    public boolean count(long count) {
        transfered = transfered + count;
        DecimalFormat percent_format = new DecimalFormat("#.##");
        double percent = ((double)transfered * 100)/(double)filesize;
        System.out.printf("\rProgress: %s%% Transfered: %d Total: %d",percent_format.format(percent),transfered,filesize);
        return true;
    }

    @Override
    public void end() {
        System.out.print("\nTransfered Complete");
    }

    public static void setFilesize(long filesize) {
        FileUploadProgressMonitor.filesize = filesize;
    }
}
