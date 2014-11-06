/**
 * 
 */
package machine.instructions;

import machine.memory.MemoryValue;
import machine.registers.Register;

/**
 * @author paul
 *
 */
public class RXInstruction extends Sigma16Instruction {
    
    protected MemoryValue mValue;
    protected Register indexFromLabel;
    
    public RXInstruction(String opName, Register destReg, MemoryValue value, Register indexFromLabel){
        this.opName = opName.toLowerCase();
        this.destReg = destReg;
        this.mValue = value;
        this.indexFromLabel = indexFromLabel;
    }

}
