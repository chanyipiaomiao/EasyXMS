package org.easyxms;


import org.apache.commons.cli.*;



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

        String usage = "-i IP -e COMMAND\n-i IP -s /local/path/to/file -d /remote/path/to/file\n" +
                       "-g GROUP_NAME -e COMMAND\n" +
                       "-g GROUP_NAME -s /local/path/to/file -d /remote/path/to/file\n" +
                       "-f EXCEL_FILE -e COMMAND\n" +
                       "-f EXCEL_FILE -s /local/path/to/file -d /remote/path/to/file\n";
        String footer = "\nEasyXMS 1.0  2014-09-16  www.linux178.com  58@linux178.com";

        try {
            CommandLine cmd_line = parser.parse(options,args);
            if (cmd_line.hasOption("i") && cmd_line.hasOption("e")){
                System.out.println(cmd_line.getOptionValue("i"));
                System.out.println(cmd_line.getOptionValue("e"));
            } else if (cmd_line.hasOption("i") && cmd_line.hasOption("s") && cmd_line.hasOption("d")){
                System.out.println(cmd_line.getOptionValue("i"));
                System.out.println(cmd_line.getOptionValue("s"));
                System.out.println(cmd_line.getOptionValue("d"));
            } else if (cmd_line.hasOption("g") && cmd_line.hasOption("e")){
                System.out.println(cmd_line.getOptionValue("g"));
                System.out.println(cmd_line.getOptionValue("e"));
            } else if (cmd_line.hasOption("g") && cmd_line.hasOption("s") && cmd_line.hasOption("d")){
                System.out.println(cmd_line.getOptionValue("g"));
                System.out.println(cmd_line.getOptionValue("s"));
                System.out.println(cmd_line.getOptionValue("d"));
            } else if (cmd_line.hasOption("f") && cmd_line.hasOption("e")){
                System.out.println(cmd_line.getOptionValue("f"));
                System.out.println(cmd_line.getOptionValue("e"));
            } else if (cmd_line.hasOption("f") && cmd_line.hasOption("s") && cmd_line.hasOption("d")){
                System.out.println(cmd_line.getOptionValue("f"));
                System.out.println(cmd_line.getOptionValue("s"));
                System.out.println(cmd_line.getOptionValue("d"));
            }
        } catch (ParseException e) {
            System.err.println( "Parsing failed.Reason: " + e.getMessage());
        }

//        formatter.printHelp("start.(sh|bat) [OPTIONS]",usage+"Options:",options,footer);
    }

}
