package machine;

import java.io.File;
import java.util.Arrays;

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
    private Memory<DataStatement> dataMemory;
    private Parser sigma16InstructionParser;
    private int programCounter;
    private boolean terminate;
    
    
    public Machine(File fileName){
        this.registers = new Register[16];
        for(byte i=0; i < 16; i++){
            registers[i] = new Register(i);
        }
        
        this.sigma16InstructionParser = new Parser(fileName);
        this.sigma16InstructionParser.parse();
        this.programMemory = sigma16InstructionParser.getProgMem();
        this.dataMemory = sigma16InstructionParser.getDataMem();
        this.programCounter = 0;
        this.terminate = false;
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
        for(DataStatement ds: dataMemory.getMemory()){
            if (ds == null){
                break;
            }
            if(ds.getLabel().equals(label)){
                return (int) ds.getValue();
            }
        }
        return Integer.MIN_VALUE;
    }
    
    public int getLabelAddress(String label){
        for(int i = 0; i < dataMemory.getMemory().size(); i++){
            DataStatement ds = dataMemory.getFromMem(i);
            if (ds == null){
                break;
            }
            if(ds.getLabel().equals(label)){
                return i;
            }
        }
        return Integer.MIN_VALUE;
    }
    

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
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
        return new StringBuilder("PC: ").append(programCounter).append(" Registers: ").append(Arrays.toString(registers)).append(" Terminated: ").append(terminate).append(dataMemory.toString()).toString();
    }

}
