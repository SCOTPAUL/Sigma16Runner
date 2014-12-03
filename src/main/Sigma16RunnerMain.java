package main;

import java.io.File;

import machine.Machine;

public class Sigma16RunnerMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
                
        File textFile = new File("/home/paul/Documents/uniWork/year2/cs/cs2/t/test.asm.txt");
        
        Machine runner = new Machine(textFile);
        runner.executeFile();
        System.out.println(runner);
    }

}
