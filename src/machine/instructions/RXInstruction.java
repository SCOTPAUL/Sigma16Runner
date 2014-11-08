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
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (label != null){
            sb.append("[").append(label).append("]");
        }
        return sb.append(opName).append(" ").append(destReg).append(",").append(memValue).append(",").append(indexFromLabel).toString();
    }

}
