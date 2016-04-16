package machine.registers;

import machine.Machine;

public class Register {
    
    private byte regNum;
    private short value;
    private Machine m;
    
    public Register(Machine m, byte regNum) throws RegisterNumberInvalidException {
        if (regNum < 0 || regNum > RegisterFile.MAX_REG_NUM){
            throw new RegisterNumberInvalidException();
        }
        else {
            this.regNum = regNum;
            this.value = 0;
            this.m = m;
        }
    }
    
    public void setValue(short value) throws CannotAlterR0Exception {
        if(this.regNum == 0 && value != 0){
            throw new CannotAlterR0Exception();
        }
        else{
            this.value = value;
        }
    }
    
    public short getValue(){
        return this.value;
    }
    
    public byte getRegNum(){
        return this.regNum;
    }
    
    
    @Override
    public String toString(){
        return "R" + regNum + "(" + m.getRegister(getRegNum()).getValue() + ")";
    }

}
