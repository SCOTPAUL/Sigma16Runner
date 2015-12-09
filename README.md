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
ant -f sigma16runner.xml
```

## Testing

### With Ant

```bash
ant -f sigma16runner.xml test
```

This also generates JUnit reports, which are stored in `Sigma16Runner/out/test/Sigma16Runner/reports`.

## Usage

After building, the generated files can be found in `out/production/Sigma16Runner`. Then the main file can be run in the following way:

```bash
java main.Sigma16RunnerMain [-s] sigma16file.asm.txt
```

The `-s` flag will cause the program to include a string output of the machine's status if it terminates.
