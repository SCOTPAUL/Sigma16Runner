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
        for(int i = 0; i < Short.MAX_VALUE; i++){
            this.memory.add(i, null);
        }
    }

    public void addToMem(int address, E value) {
        memory.set(address, value);
    }

    public E getFromMem(int address) {
        return memory.get(address);
    }
    
    public ArrayList<E> getMemory(){
        return this.memory;
    }
    
    @Override
    public String toString(){
        return memory.toString();
    }

}
