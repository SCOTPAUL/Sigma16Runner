package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import machine.instructions.DataStatement;
import machine.instructions.RRRInstruction;
import machine.instructions.RXInstruction;
import machine.registers.Register;
import machine.registers.RegisterNumberInvalidException;

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

    public String parse() throws RegisterNumberInvalidException {
        String returnString = null;
        sb = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(sigma16File));

            String line = null;

            line = br.readLine();
            while (line != null) {
                line = removeComments(line);
                if (line.length() > 0) {
                    //TODO: Replace all of this with a way to store these in some sort of instruction memory
                    
                    if(checkStatementType(line.trim().split("\\s+"))==1){
                        System.out.println(parseRRRInstruction(line.trim().split("\\s+")));
                    }
                    if((checkStatementType(line.trim().split("\\s+"))==2)){
                        System.out.println(parseRXInstruction(line.trim().split("\\s+")));
                    }
                    if((checkStatementType(line.trim().split("\\s+"))==0)){
                        System.out.println(parseDataStatement(line.trim().split("\\s+")));
                    }
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
    
    private Register[] splitRRRCode(String RRRCodes) throws NumberFormatException, RegisterNumberInvalidException{
        String[] rCodes = RRRCodes.split(",");
        Register[] registers = new Register[3];
        for (int i = 0; i < rCodes.length; i++){
            registers[i] = new Register(Byte.parseByte(rCodes[i].replaceAll("R", "")));
            
        }
        
        return registers;
        
    }
    
    private String[] splitRXCode(String RXCodes) throws NumberFormatException, RegisterNumberInvalidException{
        String[] rCodes = RXCodes.split(",");
        String[] codeParts = new String[3];
        for (int i = 0; i < rCodes.length; i++){
            codeParts[i] = rCodes[i].replaceAll("[R\\]]", "");
            
        }
        
        return codeParts;
        
    }
    
    
    private RRRInstruction parseRRRInstruction(String[] splitLine) throws RegisterNumberInvalidException{
        if(splitLine.length == 2){
            String opName = splitLine[0];
            Register[] registers = splitRRRCode(splitLine[1]) ;
            return new RRRInstruction(opName, registers[0], registers[1], registers[2]);
        }
        
        else if(splitLine.length == 3){
            String label = splitLine[0];
            String opName = splitLine[1];
            Register[] registers = splitRRRCode(splitLine[2]);
            return new RRRInstruction(opName, registers[0], registers[1], registers[2], label);
        }
        
        else{
            return null;
        }
    }
    
    
    private RXInstruction parseRXInstruction(String splitLine[]) throws NumberFormatException, RegisterNumberInvalidException{
        if(splitLine.length == 2){
            String opName = splitLine[0];
            String[] codeParts = splitRXCode(splitLine[1]);
            Register destReg = new Register(Byte.parseByte(codeParts[0]));
            String[] memoryPart = codeParts[1].split("\\[");
            String value = memoryPart[0];
            Register indexFromLabel = new Register(Byte.parseByte(memoryPart[1].replaceAll("[\\]R]", "")));
            return new RXInstruction(opName, destReg, value, indexFromLabel);
        }
        
        else if(splitLine.length == 3){
            System.out.println(splitLine[0]);
            String label = splitLine[0];
            String opName = splitLine[1];
            String[] codeParts = splitRXCode(splitLine[2]);
            Register destReg = new Register(Byte.parseByte(codeParts[0]));
            String[] memoryPart = codeParts[1].split("\\[");
            String value = memoryPart[0];
            Register indexFromLabel = new Register(Byte.parseByte(memoryPart[1]));
            return new RXInstruction(opName, destReg, value, indexFromLabel, label);
        }
        
        else{
            return null;
        }
    }
    
    
}
