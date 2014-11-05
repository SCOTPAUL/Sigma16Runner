package main;

import java.io.File;

import parser.Parser;

public class Sigma16RunnerMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
                
        File textFile = new File("/home/paul/Documents/uniWork/year2/cs/cs2/t/q1.asm.txt");
        
        Parser p = new Parser(textFile);
        System.out.print(p.parse());

    }

}
