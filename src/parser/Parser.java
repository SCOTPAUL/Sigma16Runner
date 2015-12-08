package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import machine.Machine;
import machine.instructions.*;
import machine.memory.Memory;
import machine.registers.Register;

/**
 * 
 * @author Paul Cowie
 * 
 */
public class Parser {
    private StringBuilder sb;
    private File sigma16File;

    private Memory<Sigma16Instruction> programMemory = new Memory<>();
    private HashMap<String, Short> programLabelLookupTable = new HashMap<>();

    private Memory<DataStatement> dataMemory = new Memory<>();
    private HashMap<String, Short> dataLabelLookupTable = new HashMap<>();

    private Machine m;

    private short currentDataMemAddress = 0;
    private short currentProgMemAddress = 0;

    public Parser(Machine m, File textFile) {
        this.sigma16File = textFile;
        this.m = m;
    }

    public Parser(Machine m, String textFile) {
        this(m, new File(textFile));


    }

    /**
     * Returns {@code line} up to the point where the comment character ;
     * character is found, preventing comments from being parsed as
     * instructions.
     * 
     * @param line
     *            instruction line to have its comments removed
     * @return {@code line} with comments removed
     */
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

    /**
     * Removes spaces from start and end of line and splits it on whitespace
     * 
     * @param line
     * @return Split line with extra whitespace removed.
     */
    private String[] splitLine(String line) {
        return line.trim().split("\\s+");
    }

    public String parse() {
        sb = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(sigma16File))) {

            String line = null;

            line = br.readLine();
            while (line != null) {
                line = removeComments(line);
                if (line.length() > 0) {
                    // TODO: Replace all of this with a way to store these in
                    // some sort of instruction memory
                    // TODO: Add support for lines which contain only a label.
                    String[] splitLine = splitLine(line);
                    InstructionType lineType = checkStatementType(splitLine);

                    // Decide what to do with the line
                    if(lineType == null){
                        return "";
                    }

                    switch (lineType) {
                        case LABEL:
                            Label label = parseLabelStatement(splitLine);
                            if(label.hasLabel()){
                                programLabelLookupTable.put(label.getLabel(), currentProgMemAddress);
                            }

                            programMemory.addToMem(currentProgMemAddress++, label);
                            sb.append(label);
                            break;
                        case DATA:
                            DataStatement ds = parseDataStatement(splitLine);
                            if(ds.hasLabel()){
                                dataLabelLookupTable.put(ds.getLabel(), currentDataMemAddress);
                            }

                            dataMemory.addToMem(currentDataMemAddress++, ds);
                            sb.append(ds);
                            break;
                        case RRR:
                            RRRInstruction ri = parseRRRInstruction(splitLine);
                            if(ri != null && ri.hasLabel()){
                                programLabelLookupTable.put(ri.getLabel(), currentProgMemAddress);
                            }

                            programMemory.addToMem(currentProgMemAddress++, ri);
                            sb.append(ri);
                            break;
                        case RX:
                            RXInstruction rx = parseRXInstruction(splitLine);
                            if(rx != null && rx.hasLabel()){
                                programLabelLookupTable.put(rx.getLabel(), currentProgMemAddress);
                            }

                            programMemory.addToMem(currentProgMemAddress++, rx);
                            sb.append(rx);
                            break;
                        case PARAMETERLESS_JUMP:
                            JumpInstruction ji = parseJumpInstruction(splitLine);
                            if(ji != null && ji.hasLabel()){
                                programLabelLookupTable.put(ji.getLabel(), currentProgMemAddress);
                            }

                            programMemory.addToMem(currentProgMemAddress++, ji);
                            sb.append(ji);
                            break;
                        default:
                            break;
                        }

                    sb.append("\n");
                }

                line = br.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    public Memory<Sigma16Instruction> getProgMem() {
        return this.programMemory;
    }

    public HashMap<String, Short> getProgMemLabels(){
        return programLabelLookupTable;
    }

    public Memory<DataStatement> getDataMem() {
        return this.dataMemory;
    }

    public HashMap<String, Short> getDataMemLabels(){
        return dataLabelLookupTable;
    }

    /**
     * Finds the type of a Sigma16 instruction based on the instruction's
     * opcode.
     * 
     * @param splitLine
     *            an instruction line to be have its type checked
     * @return {@link InstructionType} representing instruction
     */
    private InstructionType checkStatementType(String[] splitLine) {

        if(splitLine.length == 1){
            return InstructionType.LABEL;
        }

        if (splitLine[0].equals("data") || (splitLine.length > 2 && splitLine[1].equals("data"))) {
            return InstructionType.DATA;
        }

        for (String RRROpName : RRRInstruction.RRRInstructions) {
            if (splitLine[0].equals(RRROpName) && splitLine.length == 2) {
                return InstructionType.RRR;
            } else if (splitLine[1].equals(RRROpName) && splitLine.length == 3) {
                return InstructionType.RRR;
            }
        }

        for (String RXOpName : RXInstruction.RXInstructions) {
            if (splitLine[0].equals(RXOpName) && splitLine.length == 2) {
                return InstructionType.RX;
            } else if (splitLine[1].equals(RXOpName) && splitLine.length == 3) {
                return InstructionType.RX;
            }
        }

        if ((splitLine[0].equals("jump") && splitLine.length == 2)
                || (splitLine[1].equals("jump") && splitLine.length == 3)) {
            return InstructionType.PARAMETERLESS_JUMP;
        }

        // Error in line type
        return null;
    }

    /**
     * Creates a new DataStatement object from a data statement line in the file
     * 
     * @param splitLine
     *            the instruction line split on each whitespace character
     *            sequence
     * @return new DataStatement representing the instruction
     */
    private DataStatement parseDataStatement(String[] splitLine) {
        String label = null;
        short value;

        if(splitLine.length > 2) {
            label = splitLine[0];
            if (splitLine[2].startsWith("$")) {
                value = Short.valueOf(splitLine[2].substring(1), 16);
            } else {
                value = Short.parseShort(splitLine[2]);
            }

        }
        else {
            if (splitLine[1].startsWith("$")) {
                value = Short.valueOf(splitLine[1].substring(1), 16);
            } else {
                value = Short.parseShort(splitLine[1]);
            }

        }
        return new DataStatement(label, value);
    }

    /**
     * Creates a new label object from a label line in the file
     * @param splitLine the instruction line split on each whitespace character sequence
     * @return new label representing the instruction
     */
    private Label parseLabelStatement(String[] splitLine){
        return new Label(splitLine[0]);
    }

    /**
     * Creates a new JumpInstruction object from a jump statement line in the
     * file
     * 
     * @param splitLine
     *            the instruction line split on each whitespace character
     *            sequence
     * @return new JumpInstruction representing the instruction
     */
    private JumpInstruction parseJumpInstruction(String[] splitLine) {
        if (splitLine.length == 2) {
            String opName = splitLine[0];
            String[] labelParts = splitLine[1].split("\\[");
            String value = labelParts[0];
            Register indexFromLabel = new Register(m, Byte.parseByte(labelParts[1]
                    .replaceAll("[\\]R]", "")));
            return new JumpInstruction(opName, value, indexFromLabel);

        } else if (splitLine.length == 3) {
            String label = splitLine[0];
            String opName = splitLine[1];
            String[] labelParts = splitLine[2].split("\\[");
            String value = labelParts[0];
            Register indexFromLabel = new Register(m, Byte.parseByte(labelParts[1]
                    .replaceAll("[\\]R]", "")));
            return new JumpInstruction(opName, value, indexFromLabel, label);
        } else {
            return null;
        }
    }

    /**
     * Splits the register part of a RRRInstruction up and returns an array of
     * Registers representing the registers in the line.
     * <p>
     * For example, if RRRCodes is {@code R3,R4,R6}, a Register array
     * representing [R3, R4, R6] will be returned.
     * </p>
     * 
     * @param RRRCodes
     *            String in form "RA,RB,RC" where A,B,C are integers between 0
     *            and 15.
     * @return array of Register objects representing the registers in the line.
     */
    private Register[] splitRRRCode(String RRRCodes) {
        String[] rCodes = RRRCodes.split(",");
        Register[] registers = new Register[3];

        for (int i = 0; i < rCodes.length; i++) {
            registers[i] = new Register(m, Byte.parseByte(rCodes[i].replaceAll(
                    "R", "")));

        }

        return registers;
    }

    /**
     * Splits the register,label,register part of an RXInstruction up and
     * returns an array of Strings representing the registers and labels used in
     * the line. This makes parsing the String easier later.
     * <p>
     * For example, {@code splitRXCode("R3,x[R0]") will return ["3", "x", "0"]}.
     * The first part of this is the destination register's number, the second
     * part is a memory label or constant number, the third part is the register
     * number of the register holding the displacement from the label.
     * </p>
     * 
     * @param RXCodes
     *            String in form RA,b[RC], where A and C are between 0 and 15
     *            and b is a memory label or constant integer.
     * @return String array in form ["destination register num", "label",
     *         "displacement register num"]
     */
    private String[] splitRXCode(String RXCodes) {
        String[] rCodes = RXCodes.split(",");
        String[] codeParts = new String[3];

        codeParts[0] = rCodes[0].replace("R", "");

        String[] splitLabelAndDisplacement = rCodes[1].split("\\[");

        codeParts[1] = splitLabelAndDisplacement[0];
        codeParts[2] = splitLabelAndDisplacement[1].replaceAll("[R\\]]", "");

        return codeParts;

    }

    /**
     * Returns a new RRRInstruction, representing a RRR instruction line in the
     * file.
     * 
     * @param splitLine
     *            String array with RRR instruction in the form ["label",
     *            "operation", "RA,RB,RC"]
     * @return a new RRRInstruction object which represents the line, null if
     *         line is not in valid format.
     */
    private RRRInstruction parseRRRInstruction(String[] splitLine) {
        if (splitLine.length == 2) {
            String opName = splitLine[0];
            Register[] registers = splitRRRCode(splitLine[1]);
            return new RRRInstruction(opName, registers[0], registers[1],
                    registers[2]);
        }

        else if (splitLine.length == 3) {
            String label = splitLine[0].replaceAll(":", "");
            String opName = splitLine[1];
            Register[] registers = splitRRRCode(splitLine[2]);
            return new RRRInstruction(opName, registers[0], registers[1],
                    registers[2], label);
        }

        else {
            return null;
        }
    }

    /**
     * Returns a new RXInstruction, representing an RX instruction line in the
     * file.
     * 
     * @param splitLine
     *            String array with RX instruction in the form ["label",
     *            "operation", "RA,b[RC]"]
     * @return a new RXInstruction object which represents the line, null if
     *         line is not in valid format.
     */
    private RXInstruction parseRXInstruction(String[] splitLine) {
        String opName = splitLine[splitLine.length - 2];
        String[] codeParts = splitRXCode(splitLine[splitLine.length - 1]);
        Register destReg = new Register(m, Byte.parseByte(codeParts[0]));
        String value = codeParts[1];
        Register indexFromLabel = new Register(m, Byte.parseByte(codeParts[2]));

        if (splitLine.length == 2) {
            return new RXInstruction(opName, destReg, value, indexFromLabel);
        } else if (splitLine.length == 3) {
            String label = splitLine[0].replaceAll(":", "");
            return new RXInstruction(opName, destReg, value, indexFromLabel,
                    label);
        }

        else {
            return null;
        }
    }
    
    @Override
    public String toString(){
        return this.dataMemory.toString() + this.programMemory.toString();
    }

}
