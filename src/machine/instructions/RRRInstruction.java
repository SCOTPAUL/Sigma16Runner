package machine.instructions;

import machine.registers.Register;

/**
 * @author Paul Cowie
 *
 */
public class RRRInstruction extends Sigma16Instruction {
    
    public static final String[] RRRInstructions = {"add", "sub", "mul", "div", "cmplt", "cmpgt", "cmpeq"};
    
    protected Register firstReg;
    protected Register secondReg;
    
    public RRRInstruction(String opName, Register destReg, Register firstReg, Register secondReg, String label){
        this.opName = opName.toLowerCase();
        this.destReg = destReg;
        this.firstReg = firstReg;
        this.secondReg = secondReg;
        super.label = label;
    }
    
    public RRRInstruction(String opName, Register destReg, Register firstReg, Register secondReg){
        this(opName, destReg, firstReg, secondReg, null);
    }

}
