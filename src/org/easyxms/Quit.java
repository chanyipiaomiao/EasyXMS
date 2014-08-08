package org.easyxms;


class Quit {

    /** 退出方法 */
    public static void quit(OperateDataBase operateDataBase){
        HelpPrompt.printAskQuit();
        try {
            String quit = new GetInput().getInputFromStandardInput();
            if ("y".equals(quit) || "Y".equals(quit) || quit.length() == 0){
                if (operateDataBase.getStat() != null){
                    operateDataBase.getStat().close();
                }
                if (operateDataBase.getConn() != null){
                    operateDataBase.getConn().close();
                }
                System.exit(0);
            }
        } catch (Exception e){
            HelpPrompt.printInfo(e.getMessage());
        }
    }
}
