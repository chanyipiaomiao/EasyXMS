package org.easyxms;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

class ReadConfigureFile {

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
}
