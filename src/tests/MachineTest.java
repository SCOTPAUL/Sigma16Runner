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
     * Test that test.asm.txt file terminates after 5 instructions
     */
    @Test
    public void testEndExecution() {
        m =  new Machine(currentDir + "/src/tests/test.asm.txt");
        m.executeFile();
        System.out.println(m);
        assertTrue(m.getProgramCounter() == 5);
        assertTrue(m.getDataMemory().getFromMem(1).getValue() == 20);
    }
    
    @Test
    public void testGreaterThanInstruction() {
        m =  new Machine(currentDir + "/src/tests/test1.asm.txt");
        m.executeFile();
        System.out.println(m);
        assertTrue(m.getRegister((byte)7).getValue() == (short)1);        
    }

    @Test
    public void testLeaInstruction() {
        m =  new Machine(currentDir + "/src/tests/test.asm.txt");
        m.executeFile();
        System.out.println(m);
        assertTrue(m.getRegister((byte)3).getValue() == (short)15);
    }

    @Test
    public void testJumpTInstruction() {
        m =  new Machine(currentDir + "/src/tests/jump.asm.txt");
        m.executeFile();
        System.out.println(m);
        assertTrue(m.getRegister((byte)1).getValue() == (short)10);
    }

    @Test
    public void testPrimesInstruction() {
        m =  new Machine(currentDir + "/src/tests/primes.asm.txt");
        m.executeFile();
        System.out.println(m);
        assertTrue(m.getRegister((byte)3).getValue() == (short)1597);
    }

}
