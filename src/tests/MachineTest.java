package tests;

import static org.junit.Assert.*;

import java.io.File;

import machine.Machine;

import org.junit.Before;
import org.junit.Test;

public class MachineTest {
    public Machine m;
    public String currentDir;
    
    @Before
    public void setUp() throws Exception {
        currentDir = new File("").getAbsolutePath();
    }
    
    /**
     * Test that test.asm.txt file terminates after 3 instructions
     */
    @Test
    public void testEndExecution() {
        m =  new Machine(currentDir + "/src/tests/test.asm.txt");
        m.executeFile();
        System.out.println(m);
        assertTrue(m.getProgramCounter() == 4);        
    }
    
    @Test
    public void testGreaterThanInstruction() {
        m =  new Machine(currentDir + "/src/tests/test1.asm.txt");
        m.executeFile();
        System.out.println(m);
        assertTrue(m.getRegister((byte)7).getValue() == (short)1);        
    }

}
