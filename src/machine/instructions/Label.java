package machine.instructions;

import machine.Machine;

/**
 * Created by paul on 08/12/15.
 */
public class Label extends Sigma16Instruction {
    public Label(String label){
        this.label = label;
    }


    @Override
    public void execute(Machine m) {
        m.incrementProgramCounter();
    }

    @Override
    public String toString(){
        return "[" + label + "]";
    }
}
