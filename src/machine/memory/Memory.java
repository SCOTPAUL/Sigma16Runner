/**
 * 
 */
package machine.memory;

/**
 * @author paul
 *
 */
public class Memory {
    public static final short memSize = Short.MAX_VALUE;
    public short[] memory = new short[memSize];
    
    public void addToMem(short address, short value){
        memory[address] = value;
    }
    
    public short getFromMem(short address){
        return memory[address];
    }

}
