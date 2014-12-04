package main;

import java.io.File;
import machine.Machine;

public class Sigma16RunnerMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
                
        File textFile = new File(args[0]);
        
        Machine runner = new Machine(textFile);
        runner.executeFile();
        System.out.println(runner);
    }

}
