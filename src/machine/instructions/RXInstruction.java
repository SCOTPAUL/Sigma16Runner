/**
 * 
 */
package machine.instructions;

import machine.registers.Register;

/**
 * @author paul
 *
 */
public class RXInstruction extends Sigma16Instruction {
    
    public static final String[] RXInstructions = { "load", "lea", "store",
            "jump", "jumpT", "jumpF" };
    
    protected String memValue;
    protected Register indexFromLabel;
    
    public RXInstruction(String opName, Register destReg, String value, Register indexFromLabel, String label){
        this.opName = opName.toLowerCase();
        this.destReg = destReg;
        this.memValue = value;
        this.indexFromLabel = indexFromLabel;
        super.label = label;
    }
    
    public RXInstruction(String opName, Register destReg, String value, Register indexFromLabel){
        this(opName, destReg, value, indexFromLabel, null);
    }
    
    

}
