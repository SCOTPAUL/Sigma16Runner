package machine;

import machine.instructions.DataStatement;
import machine.instructions.Sigma16Instruction;
import machine.memory.Memory;
import parser.Parser;

/**
 * @author Paul Cowie
 *
 */
public class Machine {
    private Memory<Sigma16Instruction> programMemory;
    private Memory<DataStatement> dataMemory;
    private Parser sigma16InstructionParser;
    private int programCounter;
    
    public Machine(String fileName){
        this.sigma16InstructionParser = new Parser(fileName);
        this.programMemory = sigma16InstructionParser.getProgMem();
        this.dataMemory = sigma16InstructionParser.getDataMem();
        this.programCounter = 0;
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

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

}
