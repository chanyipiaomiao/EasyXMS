package org.easyxms;


public class EasyXMS {

    public static void main(String[] args){

        if (args.length != 0){
            CommandLineOptionsParser.optionArgsParser(args);
        } else {
            //读取配置文件并设置
            SettingInfo.settingInfo();

            //开始执行
            ChoiceNumber choiceNumber = new ChoiceNumber();
            choiceNumber.loopGetValue();
        }
    }
}