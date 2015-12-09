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