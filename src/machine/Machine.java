package machine;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import machine.instructions.DataStatement;
import machine.instructions.Sigma16Instruction;
import machine.memory.Memory;
import machine.registers.Register;
import parser.Parser;

/**
 * @author Paul Cowie
 *
 */
public class Machine {
    private Register[] registers;
    private Memory<Sigma16Instruction> programMemory;
    private HashMap<String, Short> programLabelLookupTable;
    private Memory<DataStatement> dataMemory;
    private HashMap<String, Short> dataLabelLookupTable;
    private Parser sigma16InstructionParser;
    private int programCounter;
    private boolean terminate;
    
    
    public Machine(File fileName){
        this.registers = new Register[16];
        for(byte i=0; i < 16; i++){
            registers[i] = new Register(this, i);
        }
        
        this.sigma16InstructionParser = new Parser(this, fileName);
        this.sigma16InstructionParser.parse();
        this.programMemory = sigma16InstructionParser.getProgMem();
        this.dataMemory = sigma16InstructionParser.getDataMem();
        this.programCounter = 0;
        this.terminate = false;
        this.dataLabelLookupTable = sigma16InstructionParser.getDataMemLabels();
        this.programLabelLookupTable = sigma16InstructionParser.getProgMemLabels();
    }
    
    
    public Machine(String fileName){
        this(new File(fileName));
    }
    
    
    public void executeFile(){
        while(!terminate){
            Sigma16Instruction instruction = programMemory.getFromMem(programCounter);
            if (instruction != null){
                instruction.execute(this);
            }
        }
    }

    public Memory<Sigma16Instruction> getProgramMemory() {
        return programMemory;
    }

    public void setProgramMemory(Memory<Sigma16Instruction> programMemory) {
        this.programMemory = programMemory;
    }

    public Memory<DataStatement> getDataMemory() {
        return dataMemory;
    }

    public void setDataMemory(Memory<DataStatement> dataMemory) {
        this.dataMemory = dataMemory;
    }
    
    public int getValueFromMemory(String label){
        if(dataLabelLookupTable.containsKey(label)){
            short index = dataLabelLookupTable.get(label);
            return dataMemory.getFromMem(index).getValue();
        }
        else{
            return Integer.MIN_VALUE;
        }
    }
    
    public int getLabelAddress(String label){
        if(dataLabelLookupTable.containsKey(label)){
            return dataLabelLookupTable.get(label);
        }
        else{
            return Integer.MIN_VALUE;
        }
    }

    public int getJumpLabelAddress(String label){
        if(programLabelLookupTable.containsKey(label)){
            return programLabelLookupTable.get(label);
        }
        else{
            return Integer.MIN_VALUE;
        }
    }
    

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public void incrementProgramCounter(int increment){
        programCounter += increment;
    }

    public void incrementProgramCounter(){
        incrementProgramCounter(1);
    }
    
    public Register getRegister(byte regNo){
        return registers[regNo];
    }
    
    public void setRegister(byte regNo, short value) {
        this.registers[regNo].setValue(value);
    }
    
    public void terminate() {
        this.terminate = true;
    }
    
    @Override
    public String toString(){
        return "PC: " + programCounter + " Registers: " + Arrays.toString(registers) + " Terminated: " + terminate + dataMemory.toString();
    }

}
