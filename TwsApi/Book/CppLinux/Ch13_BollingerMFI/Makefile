PROJ=BollingerMFI

CC=g++

CFLAGS=-std=c++17

$(PROJ): Main.cpp
	$(CC) $(CFLAGS) -o $@ $^ -lstdc++fs

.PHONY: clean

clean:
	rm $(PROJ)
