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
            "jumpt", "jumpf" };
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
        if (hasLabel()){
            sb.append("[").append(super.label).append("]");
        }
        return sb.append(opName).append(" ").append(destReg).append(",").append(memValue).append(",").append(indexFromLabel).toString();
    }

    @Override
    public void execute(Machine m) {
        
        switch(opName){
            case("load"):
                loadExecute(m);
                break;
            case("store"):
                storeExecute(m);
                break;
            case("lea"):
                leaExecute(m);
                break;
            case("jumpt"):
                jumptExecute(m);
                break;
            case("jumpf"):
                jumpfExecute(m);
            default:
                break;
        }
        
        m.setRegister(destReg.getRegNum(), destReg.getValue());
        
    }

    private void jumpfExecute(Machine m) {
        int regValue = m.getRegister(indexFromLabel.getRegNum()).getValue();

        if(m.getRegister(destReg.getRegNum()).getValue() == 0){
            m.setProgramCounter(m.getJumpLabelAddress(memValue) + regValue);
        }
        else {
            m.incrementProgramCounter();
        }
    }

    private void jumptExecute(Machine m) {
        int regValue = m.getRegister(indexFromLabel.getRegNum()).getValue();

        if(m.getRegister(destReg.getRegNum()).getValue() != 0){
            m.setProgramCounter(m.getJumpLabelAddress(memValue) + regValue);
        }
        else {
            m.incrementProgramCounter();
        }
    }

    private void leaExecute(Machine m){
        int val = Integer.valueOf(memValue);
        if (val <= Short.MAX_VALUE && val >= Short.MIN_VALUE){
            destReg.setValue((short) (val + m.getRegister(indexFromLabel.getRegNum()).getValue()));
            m.incrementProgramCounter();
        }
        else {
            throw new RuntimeException();
        }
    }

    private void loadExecute(Machine m){
        int valFromMem = m.getValueFromMemory(this.memValue);
        if (valFromMem <= Short.MAX_VALUE && valFromMem >= Short.MIN_VALUE){
            destReg.setValue((short) valFromMem);
            m.incrementProgramCounter();
        }
        else{
            // TODO: Make new exception for invalid memory label
            throw new RuntimeException();
        }
    }

    private void storeExecute(Machine m){
        short valForMem = m.getRegister(destReg.getRegNum()).getValue();
        int address = m.getLabelAddress(this.memValue) + m.getRegister(this.indexFromLabel.getRegNum()).getValue();
        if (address >= 0){
            m.getDataMemory().addToMem(address, new DataStatement(this.memValue, valForMem));
            m.incrementProgramCounter();
        }
        else{
            // TODO: Make new exception for invalid memory label
            throw new RuntimeException();
        }
    }

}
