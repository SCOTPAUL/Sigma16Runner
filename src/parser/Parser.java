package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Paul Cowie
 * 
 */
public class Parser {
    private StringBuilder sb;
    private BufferedReader br;
    private File sigma16File;

    public Parser(File textFile) {
        this.sigma16File = textFile;
    }

    public Parser(String textFile) {
        this(new File(textFile));

    }

    private String removeComments(String line) {
        String returnString = null;
        int commentIndex = line.indexOf(';');
        if (commentIndex != -1) {
            returnString = line.substring(0, commentIndex);
        } else {
            returnString = line;
        }

        return returnString;

    }

    public String parse() {
        String returnString = null;
        sb = new StringBuilder();

        try {
            br = new BufferedReader(new FileReader(sigma16File));

            String line = null;

            line = br.readLine();
            while (line != null) {
                line = removeComments(line).replaceAll("\\s+", "");
                if (line.length() > 0) {
                    sb.append(line).append("\n");
                }
                line = br.readLine();
            }

            returnString = sb.toString();


        } catch (IOException e) {
            returnString = null;
        }
        
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;

    }
}
