

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Foo.java \
        Blah.java \
        Library.java \
        Main.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class

To use the above makefile to compile your java programs:

    create a file named 'makefile' in your homework directory with the identical contents
    modify the CLASSES macro so that it has the names of your .java files;
    run 'make', and if all goes well, it should compile all your java source files that need to be re-built. 

Here is the same makefile, with comments explaining each line:


#
# define compiler and compiler flag variables
#

JFLAGS = -g
JC = javac


#
# Clear any default targets for building .class files from .java files; we 
# will provide our own target entry to do this in this makefile.
# make has a set of default targets for different suffixes (like .c.o) 
# Currently, clearing the default for .java.class is not necessary since 
# make does not have a definition for this target, but later versions of 
# make may, so it doesn't hurt to make sure that we clear any default 
# definitions for these
#

.SUFFIXES: .java .class


#
# Here is our target entry for creating .class files from .java files 
# This is a target entry that uses the suffix rule syntax:
#	DSTS:
#		rule
#  'TS' is the suffix of the target file, 'DS' is the suffix of the dependency 
#  file, and 'rule'  is the rule for building a target	
# '$*' is a built-in macro that gets the basename of the current target 
# Remember that there must be a < tab > before the command line ('rule') 
#

.java.class:
        $(JC) $(JFLAGS) $*.java


#
# CLASSES is a macro consisting of 4 words (one for each java source file)
#

CLASSES = \
        Foo.java \
        Blah.java \
        Library.java \
        Main.java 


#
# the default make target entry
#

default: classes


#
# This target entry uses Suffix Replacement within a macro: 
# $(name:string1=string2)
# 	In the words in the macro named 'name' replace 'string1' with 'string2'
# Below we are replacing the suffix .java of all words in the macro CLASSES 
# with the .class suffix
#

classes: $(CLASSES:.java=.class)


#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
        $(RM) *.class

_________________________________________________________________


JAVAC=javac
sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

clean :
	rm -f *.class

%.class : %.java
	$(JAVAC) $<



