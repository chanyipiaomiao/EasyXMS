package org.easyxms;


/**
 * 打印信息类
 */
class HelpPrompt {

    private static String command_prompt[] = {"1    Execute  Command",
                                              "2    Upload  File",
                                              "3    List  Servers From Database",
                                              "4    List  Groups From Database",
                                              "5    Delete  Server From Database",
                                              "6    Add  Server From < Command Line >",
                                              "7    Add  Server From < Excel File >",
                                              "8    Clear Screen"};


    private static String program_name = "EasyXMS";
    private static String program_prompt = String.format("%s (? Help | q/Q Quit) >>> ",program_name);
    private static String welcome_prompt = String.format("" +
            "Welcome to Use < %s > , Please Type < ? > Get Help or < q/Q > Quit",program_name);


    /**
     * 显示命令帮助
     */
    public static void printPrompt(){
        System.out.println();
        for (String i : command_prompt){
            System.out.printf("\t%s\n",i);
        }
        System.out.println();
    }


    /**
     * 显示程序名称及提示
     */
    public static void printProgramName(){
        System.out.print(program_prompt);
    }


    /**
     * 显示欢迎信息
     */
    public static void printWelcomeInfo(){
        int welcome_len = welcome_prompt.length();
        String henggang_num = FunctionKit.repeatString("-",welcome_len);
        System.out.println(henggang_num);
        System.out.println(welcome_prompt);
        System.out.println(henggang_num);
    }


    /**
     * 询问是否退出
     */
    public static void printAskQuit(){
        System.out.print("Are you sure Quit ([y]/n): ");
    }


    /**
     * 显示输入错误
     */
    public static void printInputError(){
        System.out.println("Input Error!");
    }


    /**
     * 显示增加服务器提示
     */
    public static void printAddServer(){
        System.out.print("e.g. 1.1.1.1 web root 123 22;2.2.2.2 db root 123 22\n>>>>>");
    }


    /**
     * 提示输入Excel文件的路径
     */
    public static void printExcelFilePath(){
        System.out.print("Enter Excel File Path: ");
    }


    /**
     * 显示增加服务器成功
     * @param ip IP地址
     */
    public static void printAddServerSuccess(String ip){
        System.out.printf("[ %s ] Add ... OK\n", ip);
    }


    /**
     * 显示IP信息已经存在
     * @param ip IP地址
     */
    public static void printIPAlreadyExists(String ip){
        System.out.printf("[ %s ] Already Exists\n", ip);
    }


    /**
     * 显示列出服务器信息提示
     */
    public static void printListServer(){
        System.out.print("Choice List Server ( all|group_name ): ");
    }


    /**
     * 显示数据库中没有数据 用于在列出信息时
     */
    public static void printNoDataInDataBase(){
        System.out.println("Oops,No Server in Database!!!");
    }


    /**
     * 显示输入要删除的IP信息
     */
    public static void printSpecifyIPOrGroupOrAllForDelete(){
        System.out.print("Enter ( all|ip|group ) For Delete :");
    }


    /**
     * 显示IP不在数据库中 用于在删除信息
     * @param ip_or_group IP或者分组
     */
    public static void printIpOrGroupNotExists(String ip_or_group){
        System.out.printf("[ %s ] Not Exists\n", ip_or_group);
    }


    /**
     * 显示删除IP或分组成功
     * @param ip_or_group IP或者分组
     */
    public static void printDeleteServerInfoSucessful(String ip_or_group){
        System.out.printf("[ %s ] Delete ... OK\n", ip_or_group);
    }


    /**
     * 询问是否真的要清空整个表
     */
    public static void printAskClearTable(){
        System.out.print("Clear All Server (y/n) :");
    }


    /**
     * 显示清空表成功
     */
    public static void printClearTableSucessful(){
        System.out.println("Clear All Server ... OK");
    }


    /**
     * 显示什么也没发生 清空表的时候，选择n或者其他键（除y|Y之外）
     */
    public static void printNothingHappen(){
        System.out.println("Nothing Happen!!!");
    }


    /**
     * 显示请输入命令
     */
    public static void printAskExecCommand(){
        System.out.print("Enter Command Like 'df -hP' (q/Q Quit) :");
    }


    /**
     * 输出 选择 要执行命令的IP,分组,所有主机
     */
    public static void printListIPOrGroupOrAllForExec(){
        System.out.print("Enter ( all|ip|group ) For ssh or sftp :");
    }


    /**
     * 显示退出执行命令
     */
    public static void printExitExecCommand(){
        System.out.println("Exit Exec Command!!");
    }


    /**
     * 显示不能执行此命令
     */
    public static void printYouCanntExecThisCommand(){
        System.out.println("You Can't Exec This Command");
    }


    /**
     * 显示该IP session 已经断开
     * @param ip IP地址
     */
    public static void printIPSessionAlreadyDisconnect(String ip){
        System.out.println(String.format("Session < %s > is already disconnected!!!",ip));
    }


    /**
     * 显示信息(出错信息、命令执行的结果)
     * @param string 要打印的字符串
     */
    public static void printInfo(String string){
        System.out.println(string);
    }


    /**
     * 显示连接错误信息
     * @param ip  IP地址
     * @param message  出错的信息
     * @return 一个格式化好的字符串
     */
    public static String printConnectError(String ip,String message){
        String info = String.format("[ %s ] ... Connect Failue,Cause --> %s ",ip,message);
        int info_len = info.length();
        String star_num = FunctionKit.repeatString("*",info_len);
        System.out.println(star_num);
        System.out.println(info);
        System.out.println(star_num);
        System.out.println();
        return String.format("%s\n%s\n%s",star_num,info,star_num);
    }


    /**
     * 显示输入文件路径
     */
    public static void printFilePath(){
        System.out.print("Enter <Local> File Path and <Remote> dir :");
    }



    /**
     * 显示命令或者上传文件所花费的时间
     * @param nums 服务器的数量
     * @param time 命令或者上传文件所花费的时间
     */
    public static void printExecuteTime(int nums,long time){
        System.out.printf("Servers Number: %d ,Execute Time: %ds\n",nums,time);
    }
}
