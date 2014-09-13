package org.easyxms;


import com.jcraft.jsch.SftpProgressMonitor;


class FileTransferProgressMonitor implements SftpProgressMonitor{

    private static String str = "|/-\\";


    /**
     * 传输开始的时候执行
     * @param op 标识是GET还是PUT
     * @param src 源
     * @param dst 目标
     * @param file_length 文件的大小
     */
    @Override
    public void init(int op, String src, String dst, long file_length) {}


    /**
     * 到目前为止已经传输了多少字节
     * @param count 目前已经传输了多少字节
     * @return true
     */
    @Override
    public boolean count(long count) {
        System.out.printf("\rAll Servers are Transferring,Please Wait moment ... %s",
                str.charAt((int)(Math.random()*str.length())));
        return true;
    }


    /**
     * 传输结束的时候执行
     */
    @Override
    public void end() {}

}
