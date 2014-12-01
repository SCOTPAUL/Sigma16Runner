package machine.registers;

/**
 * 
 * @author Paul Cowie
 *
 */
public class RegisterNumberInvalidException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 7569574997587924248L;

    public RegisterNumberInvalidException(){
        super("Register number must be between 0 and 15");
    }

}
