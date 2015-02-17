package machine.instructions;

import machine.Machine;
import machine.registers.Register;

/**
 * @author Paul Cowie
 *
 */
public abstract class Sigma16Instruction {
    
    protected String opName;
    protected Register destReg;
    protected String label;
    
    public String getOpName(){
        return this.opName;
    }
    
    public Register getDestReg(){
        return this.destReg;
    }
    
    public void setOpName(String opName){
        this.opName = opName.toLowerCase();
    }
    
    public void setDestReg(Register destReg){
        this.destReg = destReg;
    }

    public boolean hasLabel(){
        return label != null;
    }

    public String getLabel(){
        return label;
    }
    
    public abstract void execute(Machine m);
    
    

}
