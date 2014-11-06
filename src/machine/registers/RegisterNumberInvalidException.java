package machine.registers;

public class RegisterNumberInvalidException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7569574997587924248L;

    public RegisterNumberInvalidException(){
        super("Register number must be between 0 and 15");
    }

}
