package machine;

public class Sigma16Register {
    
    private byte regNum;
    private short value;
    
    public Sigma16Register(byte regNum) throws RegisterNumberInvalidException{
        if (regNum < 0 || regNum > 15 ){
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

}
