KNIFE - Java code analysis toolkit for dependency resolution
=================================================================================================
Simple source code analysis for dependency resolution. This is a bit of a foray into the world of
Java compiler tools for me. It's also the first time I've really used Guice, so sorry if it's a
bit messy.

Using KNIFE
-------------------------------------------------------------------------------------------------
KNIFE currently features a few simple analyses, which are called by:

        java -jar KNIFE.jar <analysis> [args] [file1 [file2 [...]]]

The analyses are either over a corpus of java files or over a maven artifact with its submodules.

### Java analyses

#### `reftree`: Follow the reference tree
Picks out the _`-n`_ most references classes and follows their dependencies recursively until
all dependencies are enumerated. Can be used to find natural slicing points in large code trees.

##### Parameters
 * `-n` How many of the most referenced classes should be used as a starting point.
  Default is 3. 

#### `refcount`: Reference congestion
Displays a list of all classes used by the input files, ordered by how many times they are 
referenced, in ascending order.

##### Parameters
 * `-n` Show which classes reference it for all classes that have less than or equal to
   _n_ references. Default is 3.
    
#### `importedby`: Show which classes references a class
Finds out which classes references a certain class. Similar to Ctrl+Shift+G in Eclipse.

##### Parameters
 * `-i` The referenced class to be analysed. Needs to be a qualified name.

#### `imports`: Global list of all imports
Gives a list of all the imports of all the files listed in the input.

### Maven analyses
Maven analyses take a pom.xml as an input, and then analyses this and the maven artifacts 
given as modules of this pom (recursively).

#### `longestpath`: Calculate longest dependency chain
Walks through the artifact graph constructed from the POM and finds the longest chain of
dependencies in the graph.

### Parameters valid for all analyses
Parameters here can be given to any of the analyses.

 * `-x` A package root to exclude from the list of input files. Can be used if you give a
  large input tree from `find` as input but want to slice away some part of the package
  structure, eg `com.mypackage.impl.*`

Building KNIFE
--------------
KNIFE is built with Maven. I use version 2.2.0 to build it.

Testing KNIFE
-------------
KNIFE uses data driven testing with JUnit4 and Yaml-specced files to easily create system tests.
See `src/test/resources/simple-test/spec.yaml` for an example of a collection of spec tests.

To create a new test, you can either put it in one of the existing `spec.yaml`-files, which all
operate on the files inside their directory, or create a new directory with its own `spec.yaml`-
file. If you create a new directory, it must (for now) be listed in the file 
`src/test/resources/spec-tests`.

The format of the spec file is as follows:

    description: Describe the test case
    analysis: analysis to run
    options: the command line after the analysis
    expectFail: true/false if you expect the test to fail
    expected: |
        a string of output you expect the
        test to yield, split across multiple lines.
        
