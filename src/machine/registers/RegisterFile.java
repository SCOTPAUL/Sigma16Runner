package machine.registers;

import java.util.ArrayList;

public class RegisterFile {
    
    public static final int MAX_REG_NUM = 15;
    private ArrayList<Register> regFile;
    
    public RegisterFile(){
        for(byte i = 0; i <= MAX_REG_NUM; i++){
            try {
                regFile.add(new Register(i));
            } catch (RegisterNumberInvalidException e) {
                // Tried to create a register < 0 or >15
            }
        }
    }
    

}
