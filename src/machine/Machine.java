package machine;

import machine.memory.Memory;
import parser.Parser;

/**
 * @author Paul Cowie
 *
 */
public class Machine {
    private Memory programMemory;
    private Memory dataMemory;
    private Parser Sigma16InstructionParser;
    
    public Machine(String fileName){
        this.programMemory = new Memory();
        this.dataMemory = new Memory();
        this.Sigma16InstructionParser = new Parser(fileName);
        
    }

}
