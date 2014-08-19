package org.easyxms;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class GetIPInfoFromExcel extends GetIPInfoFromFile{

    @Override
    List<Object> getIPInfo(String excel_file) {
        List<Object> objects = new ArrayList<Object>();
        ArrayList<String> host_info = new ArrayList<String>();
        try {
            Workbook ip_info = Workbook.getWorkbook(new File(excel_file));
            Sheet first_sheet = ip_info.getSheet(0);
            int total_rows = first_sheet.getRows() - 1;
            int total_columns = first_sheet.getColumns() - 1;

            for (int i = 1; i <= total_rows ; i++) {
                for (int j = 0; j <= total_columns; j++) {
                    String cell_content = first_sheet.getCell(j, i).getContents();
                    host_info.add(cell_content);
                }
                ServerInfo serverInfo = new ServerInfo(host_info.get(0),host_info.get(1),
                        host_info.get(2),host_info.get(3),Integer.parseInt(host_info.get(4)));
                objects.add(serverInfo);
                host_info.clear();
            }
        } catch (IOException e){
            HelpPrompt.printInfo(e.getMessage());
        } catch (BiffException e){
            HelpPrompt.printInfo(e.getMessage());
        }
        return objects;
    }
}
