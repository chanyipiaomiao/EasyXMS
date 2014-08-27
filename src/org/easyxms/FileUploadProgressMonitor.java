package org.easyxms;

import com.jcraft.jsch.SftpProgressMonitor;
import java.text.DecimalFormat;

class FileUploadProgressMonitor implements SftpProgressMonitor {

    private long transfered;
    private static long filesize;


    @Override
    public void init(int op, String src, String dst, long file_length) {
        System.out.println("传输开始");
    }

    @Override
    public boolean count(long count) {
        transfered = transfered + count;
        DecimalFormat percent_format = new DecimalFormat("#.##");
        double percent = ((double)transfered * 100)/(double)filesize;
        System.out.printf("\r进度: %s%% 已经传输大小: %d 总大小: %d",percent_format.format(percent),transfered,filesize);
        return true;
    }

    @Override
    public void end() {
        System.out.println("\n传输完毕");
    }

    public static void setFilesize(long filesize) {
        FileUploadProgressMonitor.filesize = filesize;
    }
}
