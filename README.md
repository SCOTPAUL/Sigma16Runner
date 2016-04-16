Sigma16Runner
=============

[![Build Status](https://travis-ci.org/SCOTPAUL/Sigma16Runner.svg?branch=master)](https://travis-ci.org/SCOTPAUL/Sigma16Runner)

## Project Description
A work-in-progress virtual machine for the Sigma16 assembly language.

## Building

### With Ant

```bash
git clone https://github.com/SCOTPAUL/Sigma16Runner.git
cd Sigma16Runner/
ant
```

## Testing

### With Ant

```bash
ant test
```

This also generates JUnit reports, which are stored in `Sigma16Runner/out/test/Sigma16Runner/reports`.

## Usage

After building, the generated files can be found in `out/production/Sigma16Runner`. Then the main file can be run in the following way:

```bash
java main.Sigma16RunnerMain [-s] sigma16file.asm.txt
```

The `-s` flag will cause the program to include a string output of the machine's status if it terminates.

### Example

```bash
java main.Sigma16RunnerMain -s ../../test/Sigma16Runner/dot-product.asm.txt
pc: 12
regs: [R0(0), R1(3), R2(3), R3(3), R4(4), R5(12), R6(0), R7(0), R8(0), R9(0), R10(0), R11(0), R12(0), R13(0), R14(0), R15(0)]
term: true
mem: [n: 3, x: 2, 5, 3, y: 6, 2, 4, dot: 34]
```

