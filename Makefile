# this is where the compiled binaries will go
CLASSPATH = classes
LIB = lib/mongo-2.7.3.jar:lib/javax.servlet-api-3.0.1.jar
SRCPATH = src
PACKAGE = edu.harvard.cs262.grading
SRC = $(SRCPATH)/edu/harvard/cs262/grading/*
TEST = test/edu/harvard/cs262/grading/*
JC = javac
FLAGS = -sourcepath $(SRCPATH) -cp $(CLASSPATH):$(LIB) -d $(CLASSPATH)

TESTCASES = \
	$(PACKAGE).TestDummy \
	$(PACKAGE).TestConfigReader

default: all

all:
	if [ ! -d "$(CLASSPATH)" ]; then mkdir $(CLASSPATH); fi
	$(JC) $(FLAGS) $(SRC)

clean:
	rm -rf $(CLASSPATH)

test: all classes
	$(JC) -cp /usr/share/java/junit.jar:$(LIB):$(CLASSPATH) \
		-sourcepath test $(TEST) \
		-d $(CLASSPATH)
	java -cp /usr/share/java/junit.jar:$(CLASSPATH) \
	org.junit.runner.JUnitCore $(TESTCASES)