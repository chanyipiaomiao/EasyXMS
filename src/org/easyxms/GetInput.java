package org.easyxms;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


class GetInput {

    public String getInputFromStandardInput(){
        String input = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            input = br.readLine();
        } catch (IOException e){
            HelpPrompt.printInfo(e.getMessage());
        }
        return input;
    }
}
