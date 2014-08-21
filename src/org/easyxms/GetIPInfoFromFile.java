package org.easyxms;



import java.util.List;


/**
 * 抽象类 从文件中获取IP信息
 */
abstract class GetIPInfoFromFile {
    abstract List<Object> getIPInfo(String file);
}
