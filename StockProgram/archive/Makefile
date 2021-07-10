sources=$(wildcard src/*.scala)

error.txt: program.jar
	scala -cp program.jar Program input.csv # 2>&1 | grep "Parse Error" #> error.txt

program.jar: $(sources)
	scalac -d program.jar $(sources)

convert.class: convert.java
	javac convert.java

trim: convert.class
	java convert src/*.scala

clean:
	rm -rf program.jar .cache-main out/ *.log





