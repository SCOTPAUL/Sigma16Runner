package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import machine.Machine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MachineTest {
    public Machine m;
    public String currentDir;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    @Before
    public void setUp() throws Exception {
        currentDir = new File("").getAbsolutePath();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUp() {
        System.setOut(null);
    }
    
    /**
     * Test that test.asm.txt file terminates after 5 instructions
     */
    @Test
    public void testEndExecution() {
        m =  new Machine(currentDir + "/test/test.asm.txt");
        m.executeFile();
        assertTrue(m.getProgramCounter() == 5);
        assertTrue(m.getDataMemory().getFromMem(1).getValue() == 20);
    }
    
    @Test
    public void testGreaterThanInstruction() {
        m =  new Machine(currentDir + "/test/test1.asm.txt");
        m.executeFile();
        assertTrue(m.getRegister((byte)7).getValue() == (short)1);
    }

    @Test
    public void testLeaInstruction() {
        m =  new Machine(currentDir + "/test/test.asm.txt");
        m.executeFile();
        assertTrue(m.getRegister((byte)3).getValue() == (short)15);
    }

    @Test
    public void testJumpTInstruction() {
        m =  new Machine(currentDir + "/test/jump.asm.txt");
        m.executeFile();
        assertTrue(m.getRegister((byte)1).getValue() == (short)10);
    }

    @Test
    public void testPrimes() {
        m =  new Machine(currentDir + "/test/primes.asm.txt");
        m.executeFile();
        assertTrue(m.getRegister((byte)3).getValue() == (short)1597);
    }

    @Test
    public void testBarChart() {
        m = new Machine(currentDir + "/test/bar-chart.asm.txt");
        m.executeFile();
        assertEquals(
                "*\n" +
                "**\n" +
                "\n" +
                "***\n" +
                "-\n" +
                "*******\n" +
                "********\n" +
                "*********\n" +
                "**********\n" +
                "**********>\n" +
                "**********>\n" +
                "**********>\n" +
                "***\n" +
                "**\n" +
                "*\n",
                outContent.toString());
    }

}
