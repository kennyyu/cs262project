# this is where the compiled binaries will go
CLASSPATH = classes

# required libraries for compilation
LIB = lib/mongo-2.7.3.jar:lib/javax.servlet-api-3.0.1.jar

# package and project information
PACKAGE = edu.harvard.cs262.grading
PROJECTDIR = edu/harvard/cs262/grading

################################################################################
# src packages
SRCPATH = src
SRCDIR = $(SRCPATH)/$(PROJECTDIR)
SRC = \
	$(SRCDIR)/server/services/* \
	$(SRCDIR)/server/web/* \
	$(SRCDIR)/client/cmdline/* \
	$(SRCDIR)/client/web/*


################################################################################
# test packages and test cases
TESTPATH = test
TEST = $(TESTPATH)/$(PROJECTDIR)/test/*
TESTPACKAGE = $(PACKAGE).test
TESTCASES = \
	$(TESTPACKAGE).TestDummy \
	$(TESTPACKAGE).TestConfigReader \
	$(TESTPACKAGE).TestSharderService \
	$(TESTPACKAGE).TestStudentServiceServer \
	$(TESTPACKAGE).TestSubmissionStorageService \
	$(TESTPACKAGE).TestGradeStorageService \
	$(TESTPACKAGE).TestAssignmentStorageService \
	$(TESTPACKAGE).TestSubmissionReceiverService \
	$(TESTPACKAGE).TestGradeCompilerService

# location of database files for tests
DB = dbtest
MONGO = mongod --dbpath=$(DB)


################################################################################
# compiler flags
JC = javac
FLAGS = -sourcepath $(SRCPATH) -cp $(CLASSPATH):$(LIB) -d $(CLASSPATH)

default: all

all:
	if [ ! -d "$(CLASSPATH)" ]; then mkdir $(CLASSPATH); fi
	$(JC) $(FLAGS) $(SRC)

clean:
	rm -rf $(CLASSPATH)
	rm -rf $(DB)

# tests will start mongod, sleep for 5 seconds while starting up, run unit
# tests, then clean up the database
test: all classes
	mkdir $(DB)
	eval $(MONGO) &
	sleep 5

	$(JC) -cp /usr/share/java/junit.jar:$(LIB):$(CLASSPATH) \
		-sourcepath $(TESTPATH) -d $(CLASSPATH) $(TEST)
	java -cp /usr/share/java/junit.jar:$(LIB):$(CLASSPATH) \
	org.junit.runner.JUnitCore $(TESTCASES)

	kill -9 $$(ps ax | grep -e "${MONGO}" | grep -v grep | awk '{print $$1}')
	rm -rf $(DB)