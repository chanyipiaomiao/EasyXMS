package org.easyxms;


import org.apache.commons.cli.*;


/**
 * 解析命令行参数
 */
class CommandLineOptionsParser {

    public static void optionArgsParser(String[] args){

        CommandLineParser parser = new GnuParser();
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        Option help = new Option("h","help",false,"Print Help Info");

        Option ip = new Option("i","ip",true,"IP Address");
        ip.setArgName("IP Address");

        Option command = new Option("e","exec",true,"Execute Command");
        command.setArgName("Execute Command");

        Option group_name = new Option("g","group",true,"Group Name");
        group_name.setArgName("Group Name");

        Option src_file = new Option("s","src",true,"Source File");
        src_file.setArgName("Source File");

        Option dst_path = new Option("d","dst",true,"Destination Path");
        dst_path.setArgName("Destination Path");

        Option excel_file = new Option("f","excel",true,"IP Information Excel File");
        excel_file.setArgName("IP Information Excel File");

        options.addOption(help);
        options.addOption(ip);
        options.addOption(command);
        options.addOption(group_name);
        options.addOption(src_file);
        options.addOption(dst_path);
        options.addOption(excel_file);

        String usage = "-i IP -e COMMAND\n" +
                       "-i IP -s /local/path/to/file -d /remote/path/to/file\n" +
                       "-g GROUP_NAME -e COMMAND\n" +
                       "-g GROUP_NAME -s /local/path/to/file -d /remote/path/to/file\n" +
                       "-f EXCEL_FILE -e COMMAND\n" +
                       "-f EXCEL_FILE -s /local/path/to/file -d /remote/path/to/file\n";
        String footer = "\nEasyXMS 2015-02-03  www.linux178.com  58@linux178.com";

        try {
            CommandLine cmd_line = parser.parse(options,args);
            CommandLineAction commandLineAction = new CommandLineAction();
            if (cmd_line.hasOption("i") && cmd_line.hasOption("e")){
                String i_value = cmd_line.getOptionValue("i");
                String e_value = cmd_line.getOptionValue("e");
                commandLineAction.ipExecCommand(i_value,e_value);
            } else if (cmd_line.hasOption("i") && cmd_line.hasOption("s") && cmd_line.hasOption("d")){
                String i_value = cmd_line.getOptionValue("i");
                String s_value = cmd_line.getOptionValue("s");
                String d_value = cmd_line.getOptionValue("d");
                commandLineAction.ipUpload(i_value,s_value,d_value);
            } else if (cmd_line.hasOption("g") && cmd_line.hasOption("e")){
                String g_value = cmd_line.getOptionValue("g");
                String e_value = cmd_line.getOptionValue("e");
                commandLineAction.groupExecCommand(g_value,e_value);
            } else if (cmd_line.hasOption("g") && cmd_line.hasOption("s") && cmd_line.hasOption("d")){
                String g_value = cmd_line.getOptionValue("g");
                String s_value = cmd_line.getOptionValue("s");
                String d_value = cmd_line.getOptionValue("d");
                commandLineAction.groupUpload(g_value,s_value,d_value);
            } else if (cmd_line.hasOption("f") && cmd_line.hasOption("e")){
                String f_value = cmd_line.getOptionValue("f");
                String e_value = cmd_line.getOptionValue("e");
                commandLineAction.fileExecCommand(f_value,e_value);
            } else if (cmd_line.hasOption("f") && cmd_line.hasOption("s") && cmd_line.hasOption("d")){
                String f_value = cmd_line.getOptionValue("f");
                String s_value = cmd_line.getOptionValue("s");
                String d_value = cmd_line.getOptionValue("d");
                commandLineAction.fileUpload(f_value,s_value,d_value);
            } else {
                formatter.printHelp("start.(sh|bat) [OPTIONS]",usage+"Options:",options,footer);
            }
        } catch (ParseException e) {
            HelpPrompt.printInfo("Parsing failed.Reason: " + e.getMessage());
        }
    }
}
