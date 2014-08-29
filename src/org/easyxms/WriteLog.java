package org.easyxms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 写日志类
 */
class WriteLog {

    private static Log log_command = LogFactory.getLog("command_history");
    private static Log log_commmand_result = LogFactory.getLog("command_result");
    private static Log log_upload_file = LogFactory.getLog("upload_file");


    /**
     * 写执行过的命令到日志
     * @param command 执行的命令
     */
    public void writeCommand(String command){
        log_command.info(command);
    }


    /**
     * 写命令执行的结果到日志
     * @param result 命令执行的结果
     */
    public void writeCommandResult(String result){
        log_commmand_result.info(result);
    }


    /**
     * 写 文件上传记录到文件
     * @param file_upload 文件上传结果的字符串
     */
    public void writeFileUpload(String file_upload){
        log_upload_file.info(file_upload);
    }
}
