package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import machine.instructions.DataStatement;
import machine.instructions.RRRInstruction;
import machine.instructions.RXInstruction;

/**
 * @author Paul Cowie
 * 
 */
public class Parser {
    private StringBuilder sb;
    private BufferedReader br;
    private File sigma16File;

    public Parser(File textFile) {
        this.sigma16File = textFile;
    }

    public Parser(String textFile) {
        this(new File(textFile));

    }

    private String removeComments(String line) {
        String returnString = null;
        int commentIndex = line.indexOf(';');
        if (commentIndex != -1) {
            returnString = line.substring(0, commentIndex);
        } else {
            returnString = line;
        }

        return returnString;

    }

    public String parse() {
        String returnString = null;
        sb = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(sigma16File));

            String line = null;

            line = br.readLine();
            while (line != null) {
                line = removeComments(line);
                if (line.length() > 0) {
                    sb.append(line).append("\n");
                }
                line = br.readLine();
            }

            returnString = sb.toString();


        } catch (IOException e) {
            returnString = null;
        }
        
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;

    }
    
    /**
     * 
     * @param line
     * @return 0 if data statement, 1 if RRR instruction, 2 if RX, instruction, else -1.
     */
    private int checkStatementType(String[] splitLine){
        if(splitLine[1].equals("data")){
            return 0;
        }
        
        for (String RRROpName: RRRInstruction.RRRInstructions){
            if(splitLine[0].equals(RRROpName) && splitLine.length == 2){
                return 1;
            }
            else if(splitLine[1].equals(RRROpName) && splitLine.length == 3){
                return 1;
            }
        }
        
        for (String RXOpName: RXInstruction.RXInstructions){
            if(splitLine[0].equals(RXOpName) && splitLine.length == 2){
                return 2;
            }
            else if(splitLine[1].equals(RXOpName) && splitLine.length == 3){
                return 2;
            }
        }
        
        return -1;       
        
    }
    
    
    private DataStatement parseDataStatement(String[] splitLine){
        String label = splitLine[0];
        short value = Short.parseShort(splitLine[2]);
        
        return new DataStatement(label, value);
        
    }
    
    
}
