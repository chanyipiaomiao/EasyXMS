package org.easyxms;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


class SettingInfo {

    private static int enable_http_proxy = 0;
    private static String http_proxy_server = null;
    private static int http_proxy_port = 80;
    private static int connect_timeout = 3000;
    private static int sleep_time = 300;

    /**
     * 从properties文件中读取信息
     * */
    public static Properties getConfigureFromPropertiesFile(){
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("conf/configure.properties");
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (FileNotFoundException e){
            HelpPrompt.printInfo(e.getMessage());
            System.exit(1);
        } catch (IOException ex){
            HelpPrompt.printInfo(ex.getMessage());
            System.exit(1);
        }
        return properties;
    }

    public static void settingInfo(){

        Properties properties = getConfigureFromPropertiesFile();

        //设置HTTP代理相关
        enable_http_proxy = Integer.parseInt(properties.getProperty("enable_http_proxy"));
        if (enable_http_proxy == 1 ){
            http_proxy_server = properties.getProperty("http_proxy_server");
            http_proxy_port = Integer.parseInt(properties.getProperty("http_proxy_port"));
        }

        //设置连接服务器时超时时间
        connect_timeout = Integer.parseInt(properties.getProperty("connect_time_out"));

        //设置子线程的睡眠时间
        sleep_time = Integer.parseInt(properties.getProperty("sleep_time"));

    }

    public static int getEnable_http_proxy() {
        return enable_http_proxy;
    }

    public static String getHttp_proxy_server() {
        return http_proxy_server;
    }

    public static int getHttp_proxy_port() {
        return http_proxy_port;
    }

    public static int getConnect_timeout() {
        return connect_timeout;
    }

    public static int getSleep_time() {
        return sleep_time;
    }
}
