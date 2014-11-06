package machine.instructions;

import machine.registers.Register;

/**
 * @author Paul Cowie
 *
 */
public class RRRInstruction extends Sigma16Instruction {
    
    protected Register firstReg;
    protected Register secondReg;
    
    public RRRInstruction(String opName, Register destReg, Register firstReg, Register secondReg){
        this.opName = opName.toLowerCase();
        this.destReg = destReg;
        this.firstReg = firstReg;
        this.secondReg = secondReg;
    }

}
