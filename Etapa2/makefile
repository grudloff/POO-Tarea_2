wJFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
        MyWorld.java \
        Maze.java \
        Stage2.java \
        Vector2D.java \
	Robot.java \
	MyTime.java 

MAIN = Stage1

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class 
	$(JVM) $(MAIN) $(FILE)

clean:
	$(RM) *.class
