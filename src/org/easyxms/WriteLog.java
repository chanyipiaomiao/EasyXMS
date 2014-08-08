package org.easyxms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class WriteLog {

    private static Log log_command = LogFactory.getLog("command_history");
    private static Log log_commmand_result = LogFactory.getLog("command_result");

    public void writeCommand(String command){
        log_command.info(command);
    }

    public void writeCommandResult(String result){
        log_commmand_result.info(result);
    }
}
