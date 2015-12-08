package machine.registers;

import machine.Machine;

import java.util.ArrayList;

public class RegisterFile {
    
    public static final int MAX_REG_NUM = 15;
    private ArrayList<Register> regFile;
    
    public RegisterFile(Machine m){
        for(byte i = 0; i <= MAX_REG_NUM; i++){
            try {
                regFile.add(new Register(m, i));
            } catch (RegisterNumberInvalidException e) {
                // Tried to create a register < 0 or >15
            }
        }
    }
    

}
