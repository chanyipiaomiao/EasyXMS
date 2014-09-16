package org.easyxms;


import org.apache.commons.cli.*;



class CommandLineOptionsParser {

    public static void optionArgsParser(String[] args){

        CommandLineParser parser = new GnuParser();
        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

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

        options.addOption(ip);
        options.addOption(command);
        options.addOption(group_name);
        options.addOption(src_file);
        options.addOption(dst_path);
        options.addOption(excel_file);

        String usage = "-i IP -e COMMAND\n\t\t\t-i IP -s /local/path/to/file -d /remote/path/to/file\n";
        String footer = "\nEasyXMS 1.0  2014-09-16  www.linux178.com  58@linux178.com";
        formatter.printHelp("start.(sh|bat) [OPTIONS]",usage+"Options:",options,footer);
    }

}
