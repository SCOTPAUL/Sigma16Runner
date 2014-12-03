package machine.registers;

public class CannotAlterR0Exception extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1112976404168955815L;

    public CannotAlterR0Exception(){
        super("Cannot edit R0's constant value of 0");
    }

}
