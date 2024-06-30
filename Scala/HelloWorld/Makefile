# Example Makefile

# Default target

run:
	scala -classpath target/hello-world-scala3-1.0-SNAPSHOT.jar ${run}

all:
	@echo "No target specified. Use: make target=<target_name>"

# Target that uses a command-line parameter
hello:
	@echo "Hello, $(name)! Welcome to Makefile."

# Target that prints all command-line parameters
print-args:
	@echo "Command-line arguments are: $(MAKEFLAGS)"

# Example target that uses a command-line parameter
say-hello:
	@echo "Hello, $(1)! Welcome to Makefile."

# Example target that uses multiple command-line parameters
sum:
	@echo "Sum of $(a) and $(b) is: $(shell expr $(a) + $(b))"
