package machine.registers;

public class Register {
    
    private byte regNum;
    private short value;
    
    public Register(byte regNum) throws RegisterNumberInvalidException{
        if (regNum < 0 || regNum > RegisterFile.MAX_REG_NUM ){
            throw new RegisterNumberInvalidException();
        }
        else {
            this.regNum = regNum;
            this.value = 0;
        }
    }
    
    public void setValue(short value) throws CannotAlterR0Exception{
        if(this.regNum == 0){
            throw new CannotAlterR0Exception();
        }
        else{
            this.value = value;
        }
    }
    
    public short getValue(){
        return this.value;
    }
    
    
    @Override
    public String toString(){
        return new StringBuilder("R").append(regNum).append("(").append(value).append(")").toString();
    }

}
