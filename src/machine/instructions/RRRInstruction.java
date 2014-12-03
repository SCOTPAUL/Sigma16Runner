package machine.instructions;

import machine.Machine;
import machine.registers.Register;

/**
 * @author Paul Cowie
 *
 */
public class RRRInstruction extends Sigma16Instruction {
    
    public static final String[] RRRInstructions = {"add", "sub", "mul", "div", "cmplt", "cmpgt", "cmpeq", "trap"};
    
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
    
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (label != null){
            sb.append("[").append(label).append("]");
        }
        return sb.append(opName).append(" ").append(destReg).append(",").append(firstReg).append(",").append(secondReg).toString();
    }

    @Override
    public void execute(Machine m) {
        firstReg.setValue(m.getRegister(firstReg.getRegNum()).getValue());
        secondReg.setValue(m.getRegister(secondReg.getRegNum()).getValue());
        
        switch(opName){
            case("add"):
                short addValue = (short) (firstReg.getValue() + secondReg.getValue());
                destReg.setValue(addValue);
                break;
            case("sub"):
                short subValue = (short) (firstReg.getValue() - secondReg.getValue());
                destReg.setValue(subValue);
                break;
            case("trap"):
                if (destReg.getRegNum() == 0 && firstReg.getRegNum() == 0 && secondReg.getRegNum() == 0){
                    m.terminate();
                }
                break;
            default:
                System.out.println(opName);
                break;
                
        }
        
        m.setRegister(destReg.getRegNum(), destReg.getValue());
        m.setProgramCounter(m.getProgramCounter() + 1);        
        
    }

}
