# this is where the compiled binaries will go
CLASSPATH = classes

# requiresd libraries for compilation
LIB = lib/mongo-2.7.3.jar:lib/javax.servlet-api-3.0.1.jar

# package and project information
PACKAGE = edu.harvard.cs262.grading
PROJECTDIR = edu/harvard/cs262/grading

# src packages
SRCPATH = src
SRCDIR = $(SRCPATH)/$(PROJECTDIR)
SRC = \
	$(SRCDIR)/server/services/* \
	$(SRCDIR)/server/web/* \
	$(SRCDIR)/client/cmdline/* \
	$(SRCDIR)/client/web/*



# test packages and test cases
TESTPATH = test
TEST = $(TESTPATH)/$(PROJECTDIR)/test/*
TESTPACKAGE = $(PACKAGE).test
TESTCASES = \
	$(TESTPACKAGE).TestDummy \
	$(TESTPACKAGE).TestConfigReader



# compiler flags
JC = javac
FLAGS = -sourcepath $(SRCPATH) -cp $(CLASSPATH):$(LIB) -d $(CLASSPATH)



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