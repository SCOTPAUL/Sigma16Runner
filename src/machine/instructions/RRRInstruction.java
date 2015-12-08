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
        if (hasLabel()){
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
                addExecute();
                break;
            case("sub"):
                subExecute();
                break;
            case("mul"):
                mulExecute();
                break;
            case("div"):
                divExecute(m);
                break;
            case("cmplt"):
                cmpltExecute();
                break;
            case("cmpeq"):
                cmpeqExecute();
                break;
            case("cmpgt"):
                cmpgtExecute();
                break;
            case("trap"):
                trapExecute(m);
                break;
            default:
                System.out.println(opName);
                break;
                
        }
        
        m.setRegister(destReg.getRegNum(), destReg.getValue());
        m.incrementProgramCounter();
        
    }

    private void addExecute(){
        short addValue = (short) (firstReg.getValue() + secondReg.getValue());
        destReg.setValue(addValue);
    }

    private void subExecute(){
        short subValue = (short) (firstReg.getValue() - secondReg.getValue());
        destReg.setValue(subValue);
    }

    private void mulExecute(){
        short mulValue = (short) ((firstReg.getValue() * secondReg.getValue()));
        destReg.setValue(mulValue);
    }

    private void divExecute(Machine m){
        short divValue = (short) ((firstReg.getValue() / secondReg.getValue()));
        short remainder = (short) ((firstReg.getValue() % secondReg.getValue()));
        destReg.setValue(divValue);
        m.setRegister((byte) 15, remainder);
    }

    private void cmpltExecute(){
        if(firstReg.getValue() < secondReg.getValue()){
            destReg.setValue((short) 1);
        }
        else{
            destReg.setValue((short) 0);
        }
    }

    private void cmpeqExecute(){
        if(firstReg.getValue() == secondReg.getValue()){
            destReg.setValue((short) 1);
        }
        else{
            destReg.setValue((short) 0);
        }
    }

    private void cmpgtExecute(){
        if(firstReg.getValue() > secondReg.getValue()){
            destReg.setValue((short) 1);
        }
        else{
            destReg.setValue((short) 0);
        }
    }

    private void trapExecute(Machine m){
        if (m.getRegister(destReg.getRegNum()).getValue() == 0 && m.getRegister(firstReg.getRegNum()).getValue() == 0 && m.getRegister(secondReg.getRegNum()).getValue() == 0){
            m.terminate();
        }
        else if(m.getRegister(destReg.getRegNum()).getValue() == 2){
            int currentChar = m.getRegister(firstReg.getRegNum()).getValue();
            int end = currentChar + m.getRegister(secondReg.getRegNum()).getValue();
            for(; currentChar < end; ++currentChar){
                System.out.print(Character.toString((char) m.getDataMemory().getFromMem(currentChar).getValue()));
            }
        }
    }



}
