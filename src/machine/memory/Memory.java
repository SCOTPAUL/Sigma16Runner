package machine.memory;

import java.util.ArrayList;

/**
 * @author Paul Cowie
 *
 */
public class Memory<E> {
    
    private ArrayList<E> memory;

    /**
     * 
     */
    public Memory() {
        this.memory = new ArrayList<>(Short.MAX_VALUE);
    }

    public void addToMem(int address, E value) {
        memory.add(value);
        
    }

    public E getFromMem(int address) {
        return memory.get(address);
    }

}
