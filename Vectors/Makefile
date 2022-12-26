LIBRARY := bin/vector.so
SRC := $(wildcard src/*.py)
TEST := $(wildcard test/*.py)


run: $(LIBRARY) type-check test
	ls $(SRC) | PYTHONPATH=./src:./lib xargs python3

$(LIBRARY): lib/vector.c
	mkdir -p bin/
	gcc -o $@ -shared -fPIC -O2 $<

dependencies: requirements.txt
	pip3 install -r requirements.txt

type-check: dependencies
	PYTHONPATH=./src:./test:./lib pytype src/

test: type-check $(LIBRARY)
	for FILE in $(TEST) ; do \
	PYTHONPATH=./src:./test:./lib python3 $$FILE ; \
	done

clean:
	rm -f bin/
