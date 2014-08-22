package org.easyxms;

import com.jcraft.jsch.SftpProgressMonitor;

 class FileUploadProgressMonitor implements SftpProgressMonitor {

     private long transfered;

     @Override
     public void init(int op, String src, String dst, long file_length) {
         System.out.println("传输开始");
     }

     @Override
     public boolean count(long count) {
         transfered = transfered + count;
         System.out.println("Currently transferred total size: " + transfered + " bytes");
         return true;
     }

     @Override
     public void end() {
         System.out.println("传输完毕");
     }
 }
