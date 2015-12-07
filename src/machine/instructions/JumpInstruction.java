package machine.instructions;

import machine.Machine;
import machine.registers.Register;

/**
 * Represents a parameterless jump instruction, since this instruction does not
 * match the other Sigma16 syntax.
 * 
 * @author Paul Cowie
 * 
 */
public class JumpInstruction extends Sigma16Instruction{
    
    public static final String JumpInstruction = "jump";
    
    private String opName;
    private String memValue;
    private String label;
    private Register indexFromLabel;
    
    public JumpInstruction(String opName, String value, Register indexFromLabel, String label){
        this.opName = opName.toLowerCase();
        this.memValue = value;
        this.indexFromLabel = indexFromLabel;
        this.label = label;
    }
    
    public JumpInstruction(String opName, String value, Register indexFromLabel){
        this(opName, value, indexFromLabel, null);
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (label != null){
            sb.append("[").append(label).append("]");
        }
        return sb.append(opName).append(" ").append(memValue).append(",").append(indexFromLabel).toString();
    }

    @Override
    public void execute(Machine m) {
        int regValue = m.getRegister(indexFromLabel.getRegNum()).getValue();
        m.setProgramCounter(m.getJumpLabelAddress(memValue) + regValue);
    }
    

}
