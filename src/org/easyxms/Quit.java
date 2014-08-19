package org.easyxms;


class Quit {

    /** 退出方法 */
    public static void quit(){
        HelpPrompt.printAskQuit();
        try {
            String quit = new GetInput().getInputFromStandardInput();
            if ("y".equals(quit) || "Y".equals(quit) || quit.length() == 0){
                System.exit(0);
            }
        } catch (Exception e){
            HelpPrompt.printInfo(e.getMessage());
        }
    }
}
