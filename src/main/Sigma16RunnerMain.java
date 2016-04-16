package main;

import java.io.File;
import java.util.Arrays;

import machine.Machine;

public class Sigma16RunnerMain {

    public static void main(String[] args) {
        boolean printStatus = false;
        String file = null;

        for(int i = 0; i < args.length; ++i){
            if(args[i].equals("-s")){
                printStatus = true;
            }
            else {
                file = args[i];
            }
        }

        File textFile = null;
        if(file == null){
            printUsageAndExit("No file selected");
        }
        else{
             textFile = new File(file);
        }

        Machine runner = new Machine(textFile);
        runner.executeFile();

        if(printStatus){
            System.out.println(runner);
        }
    }

    private static void printUsageAndExit(String err){
        if(err != null){
            System.err.println(err);
        }
        System.out.println("USAGE: java main.Sigma16RunnerMain [-s] sigma16file.asm.txt");
        System.exit(1);
    }

}
