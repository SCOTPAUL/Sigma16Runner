package machine.instructions;

/**
 * @author Paul Cowie
 *
 */
public class DataStatement {
    private String label;
    private short value;
    
    public DataStatement(String label, short value){
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }
    
    @Override
    public String toString(){
        return new StringBuilder(label).append(": ").append(value).toString();
    }

}
