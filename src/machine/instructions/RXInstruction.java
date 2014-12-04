/**
 * 
 */
package machine.instructions;

import machine.Machine;
import machine.registers.Register;

/**
 * @author Paul Cowie
 *
 */
public class RXInstruction extends Sigma16Instruction {
    
    public static final String[] RXInstructions = { "load", "lea", "store",
            "jumpT", "jumpF" };
    
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
            sb.append("[").append(super.label).append("]");
        }
        return sb.append(opName).append(" ").append(destReg).append(",").append(memValue).append(",").append(indexFromLabel).toString();
    }

    @Override
    public void execute(Machine m) {
        
        switch(opName){
            case("load"):
                int valFromMem = m.getValueFromMemory(this.memValue);
                if (valFromMem <= Short.MAX_VALUE && valFromMem >= Short.MIN_VALUE){
                    destReg.setValue((short) valFromMem);
                    m.setProgramCounter(m.getProgramCounter() + 1);
                }
                else{
                    // TODO: Make new exception for invalid memory label
                    throw new RuntimeException();
                }
                break;
            case("store"):
                short valForMem = m.getRegister(destReg.getRegNum()).getValue();
                int address = m.getValueFromMemory(this.memValue); //+ m.getRegister(this.indexFromLabel.getRegNum()).getValue();
                if (address <= Short.MAX_VALUE && address >= Short.MIN_VALUE){
                    m.getDataMemory().addToMem(address, new DataStatement(super.label, valForMem));
                    m.setProgramCounter(m.getProgramCounter() + 1);
                }
                else{
                 // TODO: Make new exception for invalid memory label
                    throw new RuntimeException();
                }
                break;
            default:
                break;
        }
        
        m.setRegister(destReg.getRegNum(), destReg.getValue());
        
    }

}
