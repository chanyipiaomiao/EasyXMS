package org.easyxms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 写日志类
 */
class WriteLog {

    private static Log log_command = LogFactory.getLog("command_history");
    private static Log log_commmand_result = LogFactory.getLog("command_result");


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
}
